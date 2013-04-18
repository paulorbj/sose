package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.estoque.avaya.ItemEstoqueAvayaService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.orcamento.OrcamentoDiferenciadoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.AguardandoEstoqueAvaya;
import br.com.sose.status.aplicacao.AguardandoExpedicao;
import br.com.sose.status.orcamento.AguardandoAprovacaoLiderDevolucao;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("iniciadoStatusReparo")
public class Iniciado extends StatusReparo {

	public static String nome = "Iniciado";

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ReparoService reparoService;

	@Autowired
	private OrcamentoDiferenciadoService orcamentoDiferenciadoService;

	@Autowired
	private ItemEstoqueAvayaService itemEstoqueAvayaService;

	@Autowired
	private ObservacaoService observacaoService;
	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	public Iniciado(Reparo r) {
		super.reparo = r;
	}

	public Iniciado() {
	}

	public String getNome(){
		return nome;
	}

	@Override
	public Reparo editarReparo(Reparo reparo) throws Exception {

		return super.editarReparo(reparo);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo finalizarReparo(Reparo reparo, Usuario usuario) throws Exception {
		if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
			reparo.setStatusString(AguardandoConjuntoExpedicao.nome);
			reparo = reparoService.salvarReparo(reparo);
			if(ordemServicoUtils.conjuntoCompleto(reparo)){
				//TODO - Apos realizar o processo de finalizacao no conjunto nao estah atualizando a tela com 
				//todo o conjunto com status de finalizado. Os reparos estao permanecendo com o status de aguradnado conjunto
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());
				cos.getOsPai().setStatusString(AguardandoExpedicao.nome);
				OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

				cos.getOsPai().getReparo().setStatusString(Finalizado.nome);
				cos.getOsPai().getReparo().setDataFim(new Date());
				reparoService.salvarReparo(cos.getOsPai().getReparo());
				observacaoService.log("Reparo", "Reparo finalizado", 2, new Date(),cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setStatusString(AguardandoExpedicao.nome);
					ordemServicoService.salvarSimplesOrdemServico(osFilha);

					osFilha.getReparo().setStatusString(Finalizado.nome);
					osFilha.getReparo().setDataFim(new Date());
					reparoService.salvarReparo(osFilha.getReparo());

					observacaoService.log("Reparo", "Reparo finalizado", 2, new Date(),osFilha, usuario);
				}
			}
		}else{
			reparo.setDataFim(new Date());
			reparo.setStatusString(Finalizado.nome);
			reparoService.salvarReparo(reparo);

			//Verificar se cliente eh avaya e se os deve seguir para estoque avaya
			if(reparo.getOrdemServico().getCliente().getNomeRazaoSocial().toLowerCase().contains("avaya") && (reparo.getOrdemServico().getCaseAvaya() == null || (reparo.getOrdemServico().getCaseAvaya() != null && reparo.getOrdemServico().getCaseAvaya().equals(""))) && (reparo.getOrdemServico().getClienteAvaya() == null || (reparo.getOrdemServico().getClienteAvaya() != null && reparo.getOrdemServico().getClienteAvaya().equals(""))) && reparo.getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO)){
				reparo.getOrdemServico().setStatusString(AguardandoEstoqueAvaya.nome);
				ordemServicoService.salvarOrdemServico(reparo.getOrdemServico());
				itemEstoqueAvayaService.criarItemEstoqueAvaya(reparo.getOrdemServico());
			}else{
				reparo.getOrdemServico().setStatusString(AguardandoExpedicao.nome);
				ordemServicoService.salvarOrdemServico(reparo.getOrdemServico());
			}
			observacaoService.log("Reparo", "Reparo finalizado", 2, new Date(),reparo, usuario);
		}


		return reparo;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo salvarReparo(Reparo reparo) throws Exception {
		return reparoService.salvarReparo(reparo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarLaudoTecnico(Reparo reparo, Usuario usuario) throws Exception {

		reparo.setStatusString(AguardandoAprovacaoLiderLaudo.nome);
		reparo.setDataRequisicaoLaudoTecnico(new Date());
		reparo = reparoService.salvarReparo(reparo);

		//Se a os eh um conjunto desbloqueia as funcionalidades no reparo
		if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
			ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());

			cos.getOsPai().setBloqueado(1);
			ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

			for(OrdemServico osFilha : cos.getListaFilhas()){
				osFilha.setBloqueado(1);
				ordemServicoService.salvarSimplesOrdemServico(osFilha);
			}
		}

		observacaoService.log("Reparo", "Laudo técnico solicitado", 2, new Date(),reparo, usuario);
		return reparo;

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarOrcamentoDiferenciadoReparo(Reparo reparo, Usuario usuario)throws Exception {
		OrcamentoDiferenciado orcDiferenciado = null;
		
		reparo.setStatusString(AguardandoOrcamentoDiferenciado.nome);
		reparo.setDataRequisicaoOrcDiferenciado(new Date());
		reparoService.salvarReparo(reparo);

		OrdemServico os = reparo.getOrdemServico();
		os.setStatusString(br.com.sose.status.aplicacao.AguardandoOrcamentoDiferenciado.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);
		
		//Se a os eh um conjunto desbloqueia as funcionalidades no reparo
		if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
			ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());

			orcDiferenciado = orcamentoDiferenciadoService.criarOrcamentoDiferenciadoConjuntoReparo(reparo,cos);
			
			cos.getOsPai().setBloqueado(6);
			cos.getOsPai().setOrcamentoDiferenciado(orcDiferenciado);
			ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());
			observacaoService.log("Reparo", "Orçamento diferenciado solicitado", 2, new Date(),cos.getOsPai(), usuario);

			for(OrdemServico osFilha : cos.getListaFilhas()){
				osFilha.setBloqueado(6);
				osFilha.setOrcamentoDiferenciado(orcDiferenciado);
				ordemServicoService.salvarSimplesOrdemServico(osFilha);
				observacaoService.log("Reparo", "Orçamento diferenciado solicitado", 2, new Date(),osFilha, usuario);
			}
		}else{
			orcDiferenciado = orcamentoDiferenciadoService.criarOrcamentoDiferenciado(reparo);
			reparo = reparoService.buscarPorId(reparo.getId());
			reparo.getOrdemServico().setOrcamentoDiferenciado(orcDiferenciado);
			
			observacaoService.log("Reparo", "Orçamento diferenciado solicitado", 2, new Date(),reparo, usuario);
		}
		
		return reparo;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarReparoExterno(Reparo reparo, Usuario usuario) throws Exception {
		reparo.setStatusString(AguardandoReparoExterno.nome);
		//TODO Data requisiscao reparo externo
		reparo.setDataRequisicaoOrcDiferenciado(new Date());
		reparoService.salvarReparo(reparo);

		OrdemServico os = reparo.getOrdemServico();
		os.setStatusString(br.com.sose.status.aplicacao.AguardandoReparoExterno.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);

		observacaoService.log("Reparo", "Reparo externo solicitado", 2, new Date(), reparo, usuario);
		//TODO Criar o reparo externo
		return reparo;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception {
		return reparoService.reAtribuirTecnico(reparo, usuario, atribuidoPor);	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario usuario) throws Exception {
		return reparoService.atribuirPrioridade(reparo,date, usuario);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo devolverSemReparo(Reparo reparo, Usuario realizadoPor) throws Exception {
		reparo.setCondicao(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO);
		reparo.setStatusString(br.com.sose.status.reparo.AguardandoAprovacaoLiderDevolucao.nome);
		reparo = reparoService.salvarReparo(reparo);
		observacaoService.log("Reparo", "Devolução sem reparo solicitada", 2, new Date(), reparo, realizadoPor);
		return reparo;
	}


}
