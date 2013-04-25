package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.AguardandoProposta;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("aguardandoAprovacaoLiderDevolucaoStatusOrcamento")
public class AguardandoAprovacaoLiderDevolucao extends StatusOrcamento {

	public static String nome = "Devolução sem reparo aguardando aprovação do líder";

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	@Autowired
	private LaudoTecnicoService laudoTecnicoService;

	public AguardandoAprovacaoLiderDevolucao(Orcamento r) {
		super.orcamento = r;
	}

	public AguardandoAprovacaoLiderDevolucao() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento aprovarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		//Foi encaminhado ao lider
		if(realizadoPor.getPerfil().getAcaoPermitirAprovarDevolucaoSemReparo()){
			//Checa se os pertence a um conjunto
			if(ordemServicoUtils.pertenceConjunto(orcamento.getOrdemServico())){
				orcamento.setStatusString(AguardandoConjuntoProposta.nome);
				orcamento = orcamentoService.salvarOrcamento(orcamento);

				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(orcamento.getOrdemServico());
				cos.getOsPai().setBloqueado(8);
				ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());
				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setBloqueado(8);
					ordemServicoService.salvarSimplesOrdemServico(osFilha);
				}

				if(ordemServicoUtils.conjuntoCompleto(orcamento)){

					cos.getOsPai().setStatusString(AguardandoProposta.nome);
					OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

					cos.getOsPai().getOrcamento().setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
					orcamentoService.salvarOrcamento(cos.getOsPai().getOrcamento());

					for(OrdemServico osFilha : cos.getListaFilhas()){
						osFilha.setStatusString(AguardandoProposta.nome);
						ordemServicoService.salvarSimplesOrdemServico(osFilha);

						osFilha.getOrcamento().setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
						orcamentoService.salvarOrcamento(osFilha.getOrcamento());
					}
				}
			}else{
				orcamento.getOrdemServico().setStatusString(AguardandoProposta.nome);
				OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(orcamento.getOrdemServico());

				orcamento.setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
				orcamento.setDataAprovacao(new Date());
				orcamento = orcamentoService.salvarOrcamento(orcamento);
				orcamento.setOrdemServico(os);
			}
			observacaoService.log("Orçamento", "Devolução sem reparo aprovado pelo líder", 2, new Date(),orcamento, realizadoPor);

//			orcamento = orcamentoService.buscarPorId(orcamento.getId());
			return orcamento;
		}else{
			throw new Exception("Usuário não possui permissão para aprovar a solicitaçao");
		}
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento rejeitarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		//Um laudo tecnico nao foi requisitado
		if(realizadoPor.getPerfil().getAcaoPermitirAprovarDevolucaoSemReparo()){
			orcamento.setStatusString(Iniciado.nome);
			orcamento.setRejeitadoPeloLider(new Date());
			orcamento.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);

			orcamentoService.salvarOrcamento(orcamento);

			observacaoService.log("Orçamento", "Devolução sem reparo rejeitado pelo líder", 2, new Date(), orcamento, realizadoPor);

//			orcamento = orcamentoService.buscarPorId(orcamento.getId());
			return orcamento;
		}else{
			throw new Exception("Usuário não possui permissão para reprovar a solicitaçao");
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		return orcamentoService.reAtribuirTecnico(orcamento, usuario, atribuidoPor);	
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario usuario) throws Exception {
		return orcamentoService.atribuirPrioridade(orcamento,date, usuario);
	}

}
