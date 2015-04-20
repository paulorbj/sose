package br.com.sose.status.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.OrdemServico;

@Service(value="aplicacaoController")
@RemotingDestination(value="aplicacaoController")
public class AplicacaoController {
		
	@Autowired
	private NaoProcessada naoProcessadaAplicacaoStatus;
	@Autowired
	private AguardandoReparo aguardandoReparoAplicacaoStatus;
	@Autowired
	private ReparoSendoRealizado reparoSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoOrcamento aguardandoOrcamentoAplicacaoStatus;
	@Autowired
	private OrcamentoSendoRealizado orcamentoSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoProposta aguardandoPropostaAplicacaoStatus;
	@Autowired
	private PropostaSendoRealizada propostaSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoLaudoTecnico aguardandoLaudoTecnicoAplicacaoStatus;
	@Autowired
	private LaudoTecnicoSendoRealizado laudoTecnicoSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoExpedicao aguardandoExpedicaoAplicacaoStatus;
	@Autowired
	private ExpedicaoSendoRealizada expedicaoSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoReparoExterno aguardandoReparoExternoAplicacaoStatus;
	@Autowired
	private ReparoExternoSendoRealizado reparoExternoSendoRealizadoAplicacaoStatus;
	@Autowired
	private AguardandoFaturamento aguardandoFaturamentoAplicacaoStatus;
	@Autowired
	private FaturamentoSendoRealizado faturamentoSendoRealizadoAplicacaoStatus;
	@Autowired
	private Finalizada finalizadaAplicacaoStatus;
	
	private OrdemServico getStatus(OrdemServico ordemServico){
		if(ordemServico.getStatusString() == null || ordemServico.getStatusString().equals("") || ordemServico.getStatusString().equals(NaoProcessada.nome)){
			naoProcessadaAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(naoProcessadaAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoReparoAplicacaoStatus.getNome())){
			aguardandoReparoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoReparoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(reparoSendoRealizadoAplicacaoStatus.getNome())){
			reparoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(reparoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoOrcamentoAplicacaoStatus.getNome())){
			aguardandoOrcamentoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoOrcamentoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(orcamentoSendoRealizadoAplicacaoStatus.getNome())){
			orcamentoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(orcamentoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoPropostaAplicacaoStatus.getNome())){
			aguardandoPropostaAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoPropostaAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(propostaSendoRealizadoAplicacaoStatus.getNome())){
			propostaSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(propostaSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoLaudoTecnicoAplicacaoStatus.getNome())){
			aguardandoLaudoTecnicoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoLaudoTecnicoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(laudoTecnicoSendoRealizadoAplicacaoStatus.getNome())){
			laudoTecnicoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(laudoTecnicoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoExpedicaoAplicacaoStatus.getNome())){
			aguardandoExpedicaoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoExpedicaoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(expedicaoSendoRealizadoAplicacaoStatus.getNome())){
			expedicaoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(expedicaoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoReparoExternoAplicacaoStatus.getNome())){
			aguardandoReparoExternoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoReparoExternoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(reparoExternoSendoRealizadoAplicacaoStatus.getNome())){
			reparoExternoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(reparoExternoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(aguardandoFaturamentoAplicacaoStatus.getNome())){
			aguardandoFaturamentoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(aguardandoFaturamentoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(faturamentoSendoRealizadoAplicacaoStatus.getNome())){
			faturamentoSendoRealizadoAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(faturamentoSendoRealizadoAplicacaoStatus);
		}else if(ordemServico.getStatusString().equals(finalizadaAplicacaoStatus.getNome())){
			finalizadaAplicacaoStatus.setOrdemServico(ordemServico);
			ordemServico.setStatus(finalizadaAplicacaoStatus);
		}
		return ordemServico;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico salvarOrdemServico(OrdemServico ordemServico) throws Exception{
		ordemServico = getStatus(ordemServico);
		return ordemServico.getStatus().salvarOrdemServico();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico processarOrdemServico(OrdemServico ordemServico,Usuario usuario) throws Exception{
		ordemServico = getStatus(ordemServico);
		return ordemServico.getStatus().processarOrdemServico(usuario);
	}

	public NaoProcessada getNaoProcessadaAplicacaoStatus() {
		return naoProcessadaAplicacaoStatus;
	}

	public void setNaoProcessadaAplicacaoStatus(NaoProcessada naoProcessadaAplicacaoStatus) {
		this.naoProcessadaAplicacaoStatus = naoProcessadaAplicacaoStatus;
	}

	public AguardandoReparo getAguardandoReparoAplicacaoStatus() {
		return aguardandoReparoAplicacaoStatus;
	}

	public void setAguardandoReparoAplicacaoStatus(
			AguardandoReparo aguardandoReparoAplicacaoStatus) {
		this.aguardandoReparoAplicacaoStatus = aguardandoReparoAplicacaoStatus;
	}

	public ReparoSendoRealizado getReparoSendoRealizadoAplicacaoStatus() {
		return reparoSendoRealizadoAplicacaoStatus;
	}

	public void setReparoSendoRealizadoAplicacaoStatus(
			ReparoSendoRealizado reparoSendoRealizadoAplicacaoStatus) {
		this.reparoSendoRealizadoAplicacaoStatus = reparoSendoRealizadoAplicacaoStatus;
	}

	public AguardandoOrcamento getAguardandoOrcamentoAplicacaoStatus() {
		return aguardandoOrcamentoAplicacaoStatus;
	}

	public void setAguardandoOrcamentoAplicacaoStatus(
			AguardandoOrcamento aguardandoOrcamentoAplicacaoStatus) {
		this.aguardandoOrcamentoAplicacaoStatus = aguardandoOrcamentoAplicacaoStatus;
	}

	public OrcamentoSendoRealizado getOrcamentoSendoRealizadoAplicacaoStatus() {
		return orcamentoSendoRealizadoAplicacaoStatus;
	}

	public void setOrcamentoSendoRealizadoAplicacaoStatus(
			OrcamentoSendoRealizado orcamentoSendoRealizadoAplicacaoStatus) {
		this.orcamentoSendoRealizadoAplicacaoStatus = orcamentoSendoRealizadoAplicacaoStatus;
	}

	public AguardandoProposta getAguardandoPropostaAplicacaoStatus() {
		return aguardandoPropostaAplicacaoStatus;
	}

	public void setAguardandoPropostaAplicacaoStatus(
			AguardandoProposta aguardandoPropostaAplicacaoStatus) {
		this.aguardandoPropostaAplicacaoStatus = aguardandoPropostaAplicacaoStatus;
	}

	public PropostaSendoRealizada getPropostaSendoRealizadoAplicacaoStatus() {
		return propostaSendoRealizadoAplicacaoStatus;
	}

	public void setPropostaSendoRealizadoAplicacaoStatus(
			PropostaSendoRealizada propostaSendoRealizadoAplicacaoStatus) {
		this.propostaSendoRealizadoAplicacaoStatus = propostaSendoRealizadoAplicacaoStatus;
	}

	public AguardandoLaudoTecnico getAguardandoLaudoTecnicoAplicacaoStatus() {
		return aguardandoLaudoTecnicoAplicacaoStatus;
	}

	public void setAguardandoLaudoTecnicoAplicacaoStatus(
			AguardandoLaudoTecnico aguardandoLaudoTecnicoAplicacaoStatus) {
		this.aguardandoLaudoTecnicoAplicacaoStatus = aguardandoLaudoTecnicoAplicacaoStatus;
	}

	public LaudoTecnicoSendoRealizado getLaudoTecnicoSendoRealizadoAplicacaoStatus() {
		return laudoTecnicoSendoRealizadoAplicacaoStatus;
	}

	public void setLaudoTecnicoSendoRealizadoAplicacaoStatus(
			LaudoTecnicoSendoRealizado laudoTecnicoSendoRealizadoAplicacaoStatus) {
		this.laudoTecnicoSendoRealizadoAplicacaoStatus = laudoTecnicoSendoRealizadoAplicacaoStatus;
	}

	public AguardandoExpedicao getAguardandoExpedicaoAplicacaoStatus() {
		return aguardandoExpedicaoAplicacaoStatus;
	}

	public void setAguardandoExpedicaoAplicacaoStatus(
			AguardandoExpedicao aguardandoExpedicaoAplicacaoStatus) {
		this.aguardandoExpedicaoAplicacaoStatus = aguardandoExpedicaoAplicacaoStatus;
	}

	public ExpedicaoSendoRealizada getExpedicaoSendoRealizadoAplicacaoStatus() {
		return expedicaoSendoRealizadoAplicacaoStatus;
	}

	public void setExpedicaoSendoRealizadoAplicacaoStatus(
			ExpedicaoSendoRealizada expedicaoSendoRealizadoAplicacaoStatus) {
		this.expedicaoSendoRealizadoAplicacaoStatus = expedicaoSendoRealizadoAplicacaoStatus;
	}

	public AguardandoReparoExterno getAguardandoReparoExternoAplicacaoStatus() {
		return aguardandoReparoExternoAplicacaoStatus;
	}

	public void setAguardandoReparoExternoAplicacaoStatus(
			AguardandoReparoExterno aguardandoReparoExternoAplicacaoStatus) {
		this.aguardandoReparoExternoAplicacaoStatus = aguardandoReparoExternoAplicacaoStatus;
	}

	public ReparoExternoSendoRealizado getReparoExternoSendoRealizadoAplicacaoStatus() {
		return reparoExternoSendoRealizadoAplicacaoStatus;
	}

	public void setReparoExternoSendoRealizadoAplicacaoStatus(
			ReparoExternoSendoRealizado reparoExternoSendoRealizadoAplicacaoStatus) {
		this.reparoExternoSendoRealizadoAplicacaoStatus = reparoExternoSendoRealizadoAplicacaoStatus;
	}

	public AguardandoFaturamento getAguardandoFaturamentoAplicacaoStatus() {
		return aguardandoFaturamentoAplicacaoStatus;
	}

	public void setAguardandoFaturamentoAplicacaoStatus(
			AguardandoFaturamento aguardandoFaturamentoAplicacaoStatus) {
		this.aguardandoFaturamentoAplicacaoStatus = aguardandoFaturamentoAplicacaoStatus;
	}

	public FaturamentoSendoRealizado getFaturamentoSendoRealizadoAplicacaoStatus() {
		return faturamentoSendoRealizadoAplicacaoStatus;
	}

	public void setFaturamentoSendoRealizadoAplicacaoStatus(
			FaturamentoSendoRealizado faturamentoSendoRealizadoAplicacaoStatus) {
		this.faturamentoSendoRealizadoAplicacaoStatus = faturamentoSendoRealizadoAplicacaoStatus;
	}

	public Finalizada getFinalizadaAplicacaoStatus() {
		return finalizadaAplicacaoStatus;
	}

	public void setFinalizadaAplicacaoStatus(Finalizada finalizadaAplicacaoStatus) {
		this.finalizadaAplicacaoStatus = finalizadaAplicacaoStatus;
	}


}
