package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.ReparoSendoRealizado;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("aguardandoAprovacaoLiderDevolucaoStatusReparo")
public class AguardandoAprovacaoLiderDevolucao extends StatusReparo {

	public static String nome = "Devolução sem reparo aguardando aprovação do líder";

	@Autowired
	private ReparoService reparoService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	@Autowired
	private LaudoTecnicoService laudoTecnicoService;

	public AguardandoAprovacaoLiderDevolucao(Reparo r) {
		super.reparo = r;
	}

	public AguardandoAprovacaoLiderDevolucao() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo aprovarReparo(Reparo reparo, Usuario usuario) throws Exception {

		if(usuario.getPerfil().getAcaoPermitirAprovarDevolucaoSemReparo()){
			//Checa se os pertence a um conjunto
			if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){

				//Altera status do reparo para aguardar
				reparo.setStatusString(AguardandoConjuntoExpedicao.nome);
				reparo.setDataAprovacaoDevolucaoSemReparoPeloLider(new Date());
				reparo = reparoService.salvarReparo(reparo);
				observacaoService.log("Reparo", "Devolução sem reparo aprovado pelo líder", 2, new Date(),reparo, usuario);
				if(ordemServicoUtils.conjuntoCompleto(reparo)){
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());

					cos.getOsPai().setStatusString(ReparoSendoRealizado.nome);
					cos.getOsPai().setBloqueado(8);
					OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

					cos.getOsPai().getReparo().setStatusString(Iniciado.nome);
					reparoService.salvarReparo(cos.getOsPai().getReparo());

					for(OrdemServico osFilha : cos.getListaFilhas()){
						osFilha.setStatusString(ReparoSendoRealizado.nome);
						osFilha.setBloqueado(8);
						ordemServicoService.salvarSimplesOrdemServico(osFilha);

						osFilha.getReparo().setStatusString(Iniciado.nome);
						reparoService.salvarReparo(osFilha.getReparo());
					}
				}
			}else{
				reparo.getOrdemServico().setStatusString(ReparoSendoRealizado.nome);
				reparo.getOrdemServico().setBloqueado(8);
				OrdemServico os = ordemServicoService.salvarSimplesOrdemServico(reparo.getOrdemServico());

				reparo.setStatusString(Iniciado.nome);
				reparo.setDataAprovacaoDevolucaoSemReparoPeloLider(new Date());
				reparo = reparoService.salvarReparo(reparo);
				reparo.setOrdemServico(os);
				observacaoService.log("Reparo", "Devolução sem reparo aprovado pelo líder", 2, new Date(),reparo, usuario);
			}


			return reparo;
		}else{
			throw new Exception("Usuário não possui permissão para aprovar a solicitaçao");
		}
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo rejeitarReparo(Reparo reparo, Usuario usuario) throws Exception {
		//Um laudo tecnico nao foi requisitado
		if(usuario.getPerfil().getAcaoPermitirAprovarDevolucaoSemReparo()){
		reparo.setStatusString(Iniciado.nome);
		reparo.setDataRejeitadoDevolucaoSemReparoPeloLider(new Date());
		reparo.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
		reparoService.salvarReparo(reparo);

		observacaoService.log("Reparo", "Devolução sem reparo rejeitado pelo líder", 2, new Date(), reparo, usuario);

		return reparo;
		}else{
			throw new Exception("Usuário não possui permissão para rejeitar a solicitaçao");
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
