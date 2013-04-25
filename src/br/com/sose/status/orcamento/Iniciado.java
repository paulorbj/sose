package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.status.aplicacao.AguardandoExpedicao;
import br.com.sose.status.aplicacao.AguardandoProposta;
import br.com.sose.status.aplicacao.AplicacaoController;
import br.com.sose.status.reparo.AguardandoLaudoTecnico;
import br.com.sose.status.reparo.AguardandoReparoExterno;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("iniciadoStatusOrcamento")
public class Iniciado extends StatusOrcamento {

	public static String nome = "Iniciado";

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private LaudoTecnicoService laudoTecnicoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	public Iniciado(Orcamento r) {
		super.orcamento = r;
	}

	public Iniciado() {
	}

	public String getNome(){
		return nome;
	}

	@Override
	public Orcamento editarOrcamento(Orcamento orcamento) throws Exception {

		return super.editarOrcamento(orcamento);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento finalizarOrcamento(Orcamento orcamento, Usuario realizadoPor) throws Exception {

		if(ordemServicoUtils.pertenceConjunto(orcamento.getOrdemServico())){
			orcamento.setStatusString(AguardandoConjuntoExpedicao.nome);
			orcamento = orcamentoService.salvarOrcamento(orcamento);
			if(ordemServicoUtils.conjuntoCompleto(orcamento)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(orcamento.getOrdemServico());
				cos.getOsPai().setStatusString(AguardandoExpedicao.nome);
				OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

				cos.getOsPai().getOrcamento().setStatusString(Finalizado.nome);
				cos.getOsPai().getOrcamento().setDataFim(new Date());
				orcamentoService.salvarOrcamento(cos.getOsPai().getOrcamento());
				observacaoService.log("Orçamento", "Orçamento finalizado", 2, new Date(),cos.getOsPai(), realizadoPor);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setStatusString(AguardandoExpedicao.nome);
					ordemServicoService.salvarSimplesOrdemServico(osFilha);

					osFilha.getOrcamento().setStatusString(Finalizado.nome);
					osFilha.getOrcamento().setDataFim(new Date());
					orcamentoService.salvarOrcamento(osFilha.getOrcamento());

					observacaoService.log("Orçamento", "Orçamento finalizado", 2, new Date(),osFilha, realizadoPor);
				}
			}
		}else{
			orcamento.setDataFim(new Date());
			orcamento.setStatusString(Finalizado.nome);
			orcamentoService.salvarOrcamento(orcamento);

			orcamento.getOrdemServico().setStatusString(AguardandoExpedicao.nome);
			ordemServicoService.salvarOrdemServico(orcamento.getOrdemServico());

			observacaoService.log("Orçamento", "Orçamento finalizado", 2, new Date(),orcamento, realizadoPor);
		}

		return orcamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento encaminharAoLider(Orcamento orcamento, Usuario realizadoPor) throws Exception {

		orcamento.setStatusString(AguardandoAprovacaoLiderProposta.nome);
		orcamento.setDataEncaminhadoAoLider(new Date());
		orcamento = orcamentoService.salvarOrcamento(orcamento);

		observacaoService.log("Orçamento", "Orçamento encaminhado ao líder", 2, new Date(),orcamento, realizadoPor);
		return orcamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento realocarTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		orcamento.setTecnicoResponsavel(usuario);
		return orcamentoService.atribuirTecnico(orcamento, usuario, atribuidoPor);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento salvarOrcamento(Orcamento orcamento) throws Exception {
		return orcamentoService.salvarOrcamento(orcamento);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento solicitarLaudoTecnico(Orcamento orcamento, Usuario realizadoPor) throws Exception {
		orcamento.setStatusString(AguardandoAprovacaoLiderLaudo.nome);
		orcamento.setDataRequisicaoLaudoTecnico(new Date());
		orcamento = orcamentoService.salvarOrcamento(orcamento);

		observacaoService.log("Orçamento", "Laudo técnico requisitado", 2, new Date(),orcamento, realizadoPor);
		return orcamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento solicitarReparoExterno(Orcamento orcamento, Usuario realizadoPor) throws Exception {
		orcamento.setStatusString(AguardandoReparoExterno.nome);
		//TODO Data requisiscao reparo externo
		orcamento.setDataRequisicaoOrcDiferenciado(new Date());
		orcamentoService.salvarOrcamento(orcamento);

		OrdemServico os = orcamento.getOrdemServico();
		os.setStatusString(br.com.sose.status.aplicacao.AguardandoReparoExterno.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);

		observacaoService.log("Orçamento", "Reparo externo solicitado", 2, new Date(), os,realizadoPor);
		//TODO Criar o reparo externo
		return orcamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario,Usuario atribuidoPor) throws Exception {
		return orcamentoService.reAtribuirTecnico(orcamento, usuario,atribuidoPor);	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario usuario) throws Exception {
		return orcamentoService.atribuirPrioridade(orcamento,date, usuario);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento devolverSemReparo(Orcamento orcamento, Usuario realizadoPor) throws Exception {
		orcamento.setCondicao(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO);
		orcamento.setStatusString(AguardandoAprovacaoLiderDevolucao.nome);
		orcamento.setDataEncaminhadoAoLider(new Date());
		orcamento = orcamentoService.salvarOrcamento(orcamento);
		observacaoService.log("Orçamento", "Devolução sem reparo solicitada", 2, new Date(), orcamento,realizadoPor);
		return orcamento;
	}


}
