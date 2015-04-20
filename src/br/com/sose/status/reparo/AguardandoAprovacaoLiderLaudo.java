package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.OrdemServicoUtils;

@Service("aguardandoAprovacaoLiderLaudoStatusReparo")
public class AguardandoAprovacaoLiderLaudo extends StatusReparo {

	public static String nome = "Laudo aguardando aprovação do líder";

	@Autowired
	private br.com.sose.service.reparo.ReparoService reparoService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	@Autowired
	private LaudoTecnicoService laudoTecnicoService;

	public AguardandoAprovacaoLiderLaudo(Reparo r) {
		super.reparo = r;
	}

	public AguardandoAprovacaoLiderLaudo() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo aprovarReparo(Reparo reparo, Usuario usuario) throws Exception {

		if(usuario.getPerfil().getAcaoPermitirAprovarSolicitacaoLaudoTecnico()){
			//Altera status da os que requisitou o laudo tecnico
			reparo.getOrdemServico().setStatusString(br.com.sose.status.aplicacao.AguardandoLaudoTecnico.nome);
			OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(reparo.getOrdemServico());

			//Altera status do reparo da os que requisitou o laudo tecnico
			reparo.setStatusString(AguardandoLaudoTecnico.nome);
			reparo.setDataEnvioParaLaudoTecnico(new Date());
			reparo = reparoService.salvarReparo(reparo);
			reparo.setOrdemServico(os);



			//Se a os eh um conjunto bloqueia as funcionalidades no reparo
			if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());

				cos.getOsPai().setBloqueado(1);
				ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());
				observacaoService.log("Reparo", "Laudo técnico aprovado pelo líder", 2, new Date(),cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setBloqueado(1);
					ordemServicoService.salvarSimplesOrdemServico(osFilha);
					observacaoService.log("Reparo", "Laudo técnico aprovado pelo líder", 2, new Date(),osFilha, usuario);
				}

				LaudoTecnico laudoTecnico = laudoTecnicoService.criarLaudoTecnicoConjuntoReparo(reparo,cos);
//				reparo.getOrdemServico().setLaudoTecnico(laudoTecnico);
			}else{
				//Cria o laudo tecnico para a os que requisitou o laudo tecnico
				LaudoTecnico laudoTecnico = laudoTecnicoService.criarLaudoTecnico(reparo);
//				reparo.getOrdemServico().setLaudoTecnico(laudoTecnico);
				observacaoService.log("Reparo", "Laudo técnico aprovado pelo líder", 2, new Date(),reparo, usuario);

			}


			return reparo;
		}else{
			throw new Exception("Usuário não possui permissão para aprovar a solicitaçao");
		}
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo rejeitarReparo(Reparo reparo, Usuario usuario) throws Exception {
		if(usuario.getPerfil().getAcaoPermitirAprovarSolicitacaoLaudoTecnico()){
			//Altera o status do reparo
			reparo.setStatusString(Iniciado.nome);
			reparo.setDataRejeitadoLaudoPeloLider(new Date());
			reparo.setDataRequisicaoLaudoTecnico(null);

			reparoService.salvarReparoSimples(reparo);

			//Se a os eh um conjunto desbloqueia as funcionalidades no reparo
			if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());

				cos.getOsPai().setBloqueado(0);
				ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());
				observacaoService.log("Reparo", "Requisição de laudo técnico foi rejeitado pelo líder", 2, new Date(), cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setBloqueado(0);
					ordemServicoService.salvarSimplesOrdemServico(osFilha);
					observacaoService.log("Reparo", "Requisição de laudo técnico foi rejeitado pelo líder", 2, new Date(), osFilha, usuario);
				}
			}

			observacaoService.log("Reparo", "Requisição de laudo técnico foi rejeitado pelo líder", 2, new Date(), reparo, usuario);
			return reparo;
		}else{
			throw new Exception("Usuário não possui permissão para reprovar a solicitaçao");
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception {
		return reparoService.reAtribuirTecnico(reparo, usuario, atribuidoPor);	
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario usuario) throws Exception {
		return reparoService.atribuirPrioridade(reparo,date, usuario);
	}

}
