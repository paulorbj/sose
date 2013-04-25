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
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.AguardandoProposta;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.OrdemServicoUtils;

@Service("aguardandoAprovacaoLiderPropostaStatusOrcamento")
public class AguardandoAprovacaoLiderProposta extends StatusOrcamento {

	public static String nome = "Proposta aguardando aprovação do líder";

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

	public AguardandoAprovacaoLiderProposta(Orcamento r) {
		super.orcamento = r;
	}

	public AguardandoAprovacaoLiderProposta() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento aprovarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		//Foi encaminhado ao lider
		if(realizadoPor.getPerfil().getAcaoPermitirAprovarSolicitacaoProposta()){
			if(orcamento.getDataRequisicaoLaudoTecnico() == null){
				//Checa se os pertence a um conjunto
				if(ordemServicoUtils.pertenceConjunto(orcamento.getOrdemServico())){
					orcamento.setStatusString(AguardandoConjuntoProposta.nome);
					orcamento = orcamentoService.salvarOrcamento(orcamento);
					if(ordemServicoUtils.conjuntoCompleto(orcamento)){
						ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(orcamento.getOrdemServico());

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
				observacaoService.log("Orçamento", "Orçamento aprovado pelo líder", 2, new Date(),orcamento, realizadoPor);
			}else{
				//Foi requisitado laudo tecnico
				//Checa se os pertence a um conjunto
				if(ordemServicoUtils.pertenceConjunto(orcamento.getOrdemServico())){
					orcamento.setStatusString(AguardandoLaudoTecnico.nome);
					orcamento = orcamentoService.salvarOrcamento(orcamento);
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(orcamento.getOrdemServico());

					cos.getOsPai().setBloqueado(1);
					ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());
					for(OrdemServico osFilha : cos.getListaFilhas()){
						osFilha.setBloqueado(1);
						ordemServicoService.salvarSimplesOrdemServico(osFilha);
					}

					LaudoTecnico laudoTecnico = laudoTecnicoService.criarLaudoTecnicoConjuntoOrcamento(orcamento,cos);
//					orcamento.getOrdemServico().setLaudoTecnico(laudoTecnico);
				}else{
					orcamento.getOrdemServico().setStatusString(br.com.sose.status.aplicacao.AguardandoLaudoTecnico.nome);
					OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(orcamento.getOrdemServico());

					orcamento.setStatusString(AguardandoLaudoTecnico.nome);
					orcamento.setDataEnvioParaLaudoTecnico(new Date());
					orcamento = orcamentoService.salvarOrcamento(orcamento);
					orcamento.setOrdemServico(os);

					LaudoTecnico laudoTecnico = laudoTecnicoService.criarLaudoTecnico(orcamento);
//					orcamento.getOrdemServico().setLaudoTecnico(laudoTecnico);
				}
				observacaoService.log("Orçamento", "Laudo técnico aprovado pelo líder", 2, new Date(),orcamento, null);
			}

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
		if(realizadoPor.getPerfil().getAcaoPermitirAprovarSolicitacaoProposta()){
			orcamento.setStatusString(Iniciado.nome);
			orcamento.setRejeitadoPeloLider(new Date());
			orcamentoService.salvarOrcamento(orcamento);

			if(orcamento.getDataRequisicaoLaudoTecnico() == null){
				observacaoService.log("Orçamento", "Orçamento rejeitado pelo líder", 2, new Date(), orcamento, realizadoPor);
			}else{
				observacaoService.log("Orçamento", "Requisição de laudo técnico foi rejeitado pelo líder", 2, new Date(), orcamento, realizadoPor);
				orcamento.setDataRequisicaoLaudoTecnico(null);
			}

			orcamento = orcamentoService.buscarPorId(orcamento.getId());
			return orcamento;
		}else{
			throw new Exception("Usuário não possui permissão para rejeitar a solicitaçao");
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
