package br.com.sose.status.reparo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.to.OrcRepGenericoTO;

@Service(value="reparoController")
@RemotingDestination(value="reparoController")
public class ReparoController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AguardandoLiberacao reparoAguardandoLiberacao;
	@Autowired
	private NaoIniciado reparoNaoIniciado;
	@Autowired
	private Iniciado reparoIniciado;	
	@Autowired
	private Finalizado reparoFinalizado;
	@Autowired
	private AguardandoLaudoTecnico reparoAguardandoLaudoTecnico;
	@Autowired
	private AguardandoAprovacaoLiderLaudo aguardandoAprovacaoLiderLaudo;
	@Autowired
	private AguardandoAprovacaoLiderDevolucao aguardandoAprovacaoLiderDevolucao;
	@Autowired
	private AguardandoOrcamentoDiferenciado reparoOrcamentoDiferenciado;
	@Autowired
	private AguardandoProposta reparoAguardandoProposta;
	@Autowired
	private AguardandoReparoExterno reparoAguardandoReparoExterno;
	@Autowired
	private AguardandoConjuntoExpedicao reparoAguardandoConjuntoExpedicao;
	@Autowired
	private ReparoService reparoService;
	
	public Reparo converterOrcRepGenericoToReparo(OrcRepGenericoTO orcRep){
		try{
			Reparo reparo = reparoService.buscarPorId(orcRep.getIdOrcRep());
			return reparo;	
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
			
		}
	}
	
	public Reparo getStatus(Reparo reparo){
		try{
			
		if(reparo.getStatusString() == null || reparo.getStatusString().equals("") || reparo.getStatusString().equals(reparoAguardandoLiberacao.getNome())){
			reparoAguardandoLiberacao.setReparo(reparo);
			reparo.setStatus(reparoAguardandoLiberacao);
		}else if(reparo.getStatusString().equals(reparoNaoIniciado.getNome())){
			reparoNaoIniciado.setReparo(reparo);
			reparo.setStatus(reparoNaoIniciado);
		}else if(reparo.getStatusString().equals(reparoIniciado.getNome())){
			reparoIniciado.setReparo(reparo);
			reparo.setStatus(reparoIniciado);
		}else if(reparo.getStatusString().equals(reparoFinalizado.getNome())){
			reparoFinalizado.setReparo(reparo);
			reparo.setStatus(reparoFinalizado);
		}else if(reparo.getStatusString().equals(reparoAguardandoLaudoTecnico.getNome())){
			reparoAguardandoLaudoTecnico.setReparo(reparo);
			reparo.setStatus(reparoAguardandoLaudoTecnico);
		}else if(reparo.getStatusString().equals(aguardandoAprovacaoLiderLaudo.getNome())){
			aguardandoAprovacaoLiderLaudo.setReparo(reparo);
			reparo.setStatus(aguardandoAprovacaoLiderLaudo);
		}else if(reparo.getStatusString().equals(aguardandoAprovacaoLiderDevolucao.getNome())){
			aguardandoAprovacaoLiderDevolucao.setReparo(reparo);
			reparo.setStatus(aguardandoAprovacaoLiderDevolucao);
		}		else if(reparo.getStatusString().equals(reparoOrcamentoDiferenciado.getNome())){
			reparoOrcamentoDiferenciado.setReparo(reparo);
			reparo.setStatus(reparoOrcamentoDiferenciado);
		}else if(reparo.getStatusString().equals(reparoAguardandoProposta.getNome())){
			reparoAguardandoProposta.setReparo(reparo);
			reparo.setStatus(reparoAguardandoProposta);
		}else if(reparo.getStatusString().equals(reparoAguardandoReparoExterno.getNome())){
			reparoAguardandoReparoExterno.setReparo(reparo);
			reparo.setStatus(reparoAguardandoReparoExterno);
		}else if(reparo.getStatusString().equals(reparoAguardandoConjuntoExpedicao.getNome())){
			reparoAguardandoConjuntoExpedicao.setReparo(reparo);
			reparo.setStatus(reparoAguardandoConjuntoExpedicao);
		}
		return reparo;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo salvarReparo(Reparo reparo) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().salvarReparo(reparo);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo iniciarReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().iniciarReparo(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo editarReparo(Reparo reparo) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().editarReparo(reparo);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarOrcamentoDiferenciadoReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().solicitarOrcamentoDiferenciadoReparo(reparo,usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo finalizarReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().finalizarReparo(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarLaudoTecnico(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().solicitarLaudoTecnico(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo devolverSemReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().devolverSemReparo(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo aprovarReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().aprovarReparo(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo rejeitarReparo(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().rejeitarReparo(reparo,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo solicitarReparoExterno(Reparo reparo, Usuario usuario) throws Exception{
		reparo = getStatus(reparo);
		return reparo.getStatus().solicitarReparoExterno(reparo,usuario);
	}

	public AguardandoLiberacao getReparoAguardandoLiberacao() {
		return reparoAguardandoLiberacao;
	}

	public void setReparoAguardandoLiberacao(
			AguardandoLiberacao reparoAguardandoLiberacao) {
		this.reparoAguardandoLiberacao = reparoAguardandoLiberacao;
	}

	public NaoIniciado getReparoNaoIniciado() {
		return reparoNaoIniciado;
	}

	public void setReparoNaoIniciado(NaoIniciado reparoNaoIniciado) {
		this.reparoNaoIniciado = reparoNaoIniciado;
	}

	public Iniciado getReparoIniciado() {
		return reparoIniciado;
	}

	public void setReparoIniciado(Iniciado reparoIniciado) {
		this.reparoIniciado = reparoIniciado;
	}

	public Finalizado getReparoFinalizado() {
		return reparoFinalizado;
	}

	public void setReparoFinalizado(Finalizado reparoFinalizado) {
		this.reparoFinalizado = reparoFinalizado;
	}

	public AguardandoLaudoTecnico getReparoAguardandoLaudoTecnico() {
		return reparoAguardandoLaudoTecnico;
	}

	public void setReparoAguardandoLaudoTecnico(
			AguardandoLaudoTecnico reparoAguardandoLaudoTecnico) {
		this.reparoAguardandoLaudoTecnico = reparoAguardandoLaudoTecnico;
	}

	public AguardandoOrcamentoDiferenciado getReparoOrcamentoDiferenciado() {
		return reparoOrcamentoDiferenciado;
	}

	public void setReparoOrcamentoDiferenciado(
			AguardandoOrcamentoDiferenciado reparoOrcamentoDiferenciado) {
		this.reparoOrcamentoDiferenciado = reparoOrcamentoDiferenciado;
	}

	public AguardandoProposta getReparoAguardandoProposta() {
		return reparoAguardandoProposta;
	}

	public void setReparoAguardandoProposta(
			AguardandoProposta reparoAguardandoProposta) {
		this.reparoAguardandoProposta = reparoAguardandoProposta;
	}

	public AguardandoReparoExterno getReparoAguardandoReparoExterno() {
		return reparoAguardandoReparoExterno;
	}

	public void setReparoAguardandoReparoExterno(
			AguardandoReparoExterno reparoAguardandoReparoExterno) {
		this.reparoAguardandoReparoExterno = reparoAguardandoReparoExterno;
	}

	
	
}
