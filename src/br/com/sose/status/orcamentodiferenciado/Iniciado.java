package br.com.sose.status.orcamentodiferenciado;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.orcamento.OrcamentoDiferenciadoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.ReparoSendoRealizado;
import br.com.sose.status.reparo.AguardandoLaudoTecnico;
import br.com.sose.status.reparo.AguardandoOrcamentoDiferenciado;
import br.com.sose.status.reparo.AguardandoProposta;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("iniciadoStatusOrcamentoDiferenciado")
public class Iniciado extends StatusOrcamentoDiferenciado {

	public static String nome = "Iniciado";
	
	@Autowired
	private OrcamentoDiferenciadoService orcamentoDiferenciadoService;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	@Autowired
	private OrdemServicoUtils ordemServicoUtils;
	
	public Iniciado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}
	
	public Iniciado() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado aprovarOrcamentoDiferenciado(Usuario usuario) throws Exception {
		OrdemServico os = orcamentoDiferenciado.getOrdemServico();
		orcamentoDiferenciado.setDataFim(new Date());
		orcamentoDiferenciado.setRealizadoPor(usuario);
		orcamentoDiferenciado.setStatusString(Aprovado.nome);
		
		if(ordemServicoUtils.pertenceConjunto(os)){
			ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
			cos.getOsPai().setBloqueado(7);//notificar a realização de um orcamento diferenciado 
			if(cos.getOsPai().getReparo().getStatusString().equals(AguardandoOrcamentoDiferenciado.nome)){
				cos.getOsPai().getReparo().setStatusString(AguardandoProposta.nome);
				cos.getOsPai().setStatusString(br.com.sose.status.aplicacao.AguardandoProposta.nome);
			}
			reparoService.salvarReparo(cos.getOsPai().getReparo());
			//TODO - Talvez seja necessario setar o laudo para todas as os's do conjunto (VERIFICAR)
			ordemServicoService.salvarOrdemServico(cos.getOsPai());
			orcamentoDiferenciado.setOrdemServico(cos.getOsPai());
			orcamentoDiferenciado = orcamentoDiferenciadoService.salvarOrcamentoDiferenciado(orcamentoDiferenciado); 
			observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado aprovado", 2, new Date(), cos.getOsPai(), usuario);

			for(OrdemServico osFilha : cos.getListaFilhas()){
				osFilha.setBloqueado(7); //notificar a realização de um orcamento diferenciado 
				if(osFilha.getReparo().getStatusString().equals(AguardandoOrcamentoDiferenciado.nome)){
					osFilha.getReparo().setStatusString(AguardandoProposta.nome);
					osFilha.setStatusString(br.com.sose.status.aplicacao.AguardandoProposta.nome);
				}
				reparoService.salvarReparo(osFilha.getReparo());
				ordemServicoService.salvarOrdemServico(osFilha);
				observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado aprovado", 2, new Date(), osFilha, usuario);
			}
		}else{
			orcamentoDiferenciado.setDataFim(new Date());
			orcamentoDiferenciado.setStatusString(Aprovado.nome);
			
			Reparo reparo = orcamentoDiferenciado.getReparo();
			reparo.setStatusString(AguardandoProposta.nome);
			reparo = reparoService.salvarReparo(reparo);
			
			os = orcamentoDiferenciado.getOrdemServico();
			os.setBloqueado(7);
			os.setStatusString(br.com.sose.status.aplicacao.AguardandoProposta.nome);
			ordemServicoService.salvarSimplesOrdemServico(os);
			orcamentoDiferenciado = orcamentoDiferenciadoService.salvarOrcamentoDiferenciado(orcamentoDiferenciado);
			
			reparo = reparoService.buscarPorId(reparo.getId());
			orcamentoDiferenciado.setReparo(reparo);
			observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado aprovado", 2, new Date(),os, usuario);

		}
		orcamentoDiferenciado = orcamentoDiferenciadoService.buscarPorId(orcamentoDiferenciado.getId());
		return orcamentoDiferenciado;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado rejeitarOrcamentoDiferenciado(Usuario usuario) throws Exception {
		orcamentoDiferenciado.setDataFim(new Date());
		orcamentoDiferenciado.setStatusString(Rejeitado.nome);
		orcamentoDiferenciado.setRealizadoPor(usuario);
		
		Reparo reparo = orcamentoDiferenciado.getReparo();
		reparo.setStatusString(br.com.sose.status.reparo.Iniciado.nome);
		reparo.setOrcamentoDiferenciadoRejeitado(new Date());
		reparoService.salvarReparo(reparo);
		
		OrdemServico os = orcamentoDiferenciado.getOrdemServico();
		os.setStatusString(ReparoSendoRealizado.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);
		orcamentoDiferenciado = orcamentoDiferenciadoService.salvarOrcamentoDiferenciado(orcamentoDiferenciado);
		
		reparo = reparoService.buscarPorId(reparo.getId());
		orcamentoDiferenciado.setReparo(reparo);
		
		//Se a os eh um conjunto desbloqueia as funcionalidades no reparo
		if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
			
			ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
			cos.getOsPai().getReparo().setOrcamentoDiferenciadoRejeitado(new Date());
			cos.getOsPai().getReparo().setDataRequisicaoOrcamentoDiferenciado(null);
			cos.getOsPai().setBloqueado(0);//TODO notificar a rejeição de um laudo tecnico 
			cos.getOsPai().setOrcamentoDiferenciado(null);
			if(cos.getOsPai().getReparo().getStatusString().equals(AguardandoOrcamentoDiferenciado.nome)){
				cos.getOsPai().getReparo().setStatusString(Iniciado.nome);
				cos.getOsPai().setStatusString(ReparoSendoRealizado.nome);
			}
			reparoService.salvarReparo(cos.getOsPai().getReparo());
			ordemServicoService.salvarOrdemServico(cos.getOsPai());
			observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado rejeitado", 2, new Date(),cos.getOsPai(), usuario);

			for(OrdemServico osFilha : cos.getListaFilhas()){
				osFilha.getReparo().setOrcamentoDiferenciadoRejeitado(new Date());
				osFilha.getReparo().setDataRequisicaoOrcamentoDiferenciado(null);
				osFilha.setBloqueado(0);
//				osFilha.setLaudoTecnico(null);
				if(osFilha.getReparo().getStatusString().equals(AguardandoOrcamentoDiferenciado.nome)){
					osFilha.getReparo().setStatusString(Iniciado.nome);
					osFilha.setStatusString(ReparoSendoRealizado.nome);
				}
				reparoService.salvarReparo(osFilha.getReparo());
				ordemServicoService.salvarOrdemServico(osFilha);
				observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado rejeitado", 2, new Date(),osFilha, usuario);

			}
		}else{
			observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado rejeitado", 2, new Date(),os, usuario);
		}
		
		orcamentoDiferenciado = orcamentoDiferenciadoService.buscarPorId(orcamentoDiferenciado.getId());
		return orcamentoDiferenciado;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado salvarOrcamentoDiferenciado(Usuario usuario) throws Exception {
		orcamentoDiferenciado = orcamentoDiferenciadoService.salvarOrcamentoDiferenciado(orcamentoDiferenciado);
		orcamentoDiferenciado = orcamentoDiferenciadoService.buscarPorId(orcamentoDiferenciado.getId());
		return orcamentoDiferenciado;
	}
	
	

}
