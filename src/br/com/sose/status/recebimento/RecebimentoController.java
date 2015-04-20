package br.com.sose.status.recebimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.NotaFiscal;

@Service(value="recebimentoController")
@RemotingDestination(value="recebimentoController")
public class RecebimentoController {
		
	@Autowired
	private Aberta abertaRecebimentoStatus;
	@Autowired
	private Cancelada canceladaRecebimentoStatus;
	@Autowired
	private Finalizada finalizadaRecebimentoStatus;	
	@Autowired
	private Nova novaRecebimentoStatus;
	@Autowired
	private Processada processadaRecebimentoStatus;
	
	private NotaFiscal getStatus(NotaFiscal notaFiscal){
		if(notaFiscal.getStatusString() == null || notaFiscal.getStatusString().equals("") || notaFiscal.getStatusString().equals(Nova.nome)){
			novaRecebimentoStatus.setNotaFiscal(notaFiscal);
			notaFiscal.setStatus(novaRecebimentoStatus);
		}else if(notaFiscal.getStatusString().equals(abertaRecebimentoStatus.getNome())){
			abertaRecebimentoStatus.setNotaFiscal(notaFiscal);
			notaFiscal.setStatus(abertaRecebimentoStatus);
		}else if(notaFiscal.getStatusString().equals(canceladaRecebimentoStatus.getNome())){
			canceladaRecebimentoStatus.setNotaFiscal(notaFiscal);
			notaFiscal.setStatus(canceladaRecebimentoStatus);
		}else if(notaFiscal.getStatusString().equals(finalizadaRecebimentoStatus.getNome())){
			finalizadaRecebimentoStatus.setNotaFiscal(notaFiscal);
			notaFiscal.setStatus(finalizadaRecebimentoStatus);
		}else if(notaFiscal.getStatusString().equals(processadaRecebimentoStatus.getNome())){
			processadaRecebimentoStatus.setNotaFiscal(notaFiscal);
			notaFiscal.setStatus(processadaRecebimentoStatus);
		}
		return notaFiscal;
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal salvarNotaFiscal(NotaFiscal notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().salvarNotaFiscal(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal editarNotaFiscal(NotaFiscal notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().editarNotaFiscal();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal cancelarNotaFiscal(NotaFiscal notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().cancelarNotaFiscal();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal processarNotaFiscal(NotaFiscal notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().processarNotaFiscal(usuario);

	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal duplicarNotaFiscal(NotaFiscal notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().duplicarNotaFiscal();
	}


	public Aberta getAbertaRecebimentoStatus() {
		return abertaRecebimentoStatus;
	}


	public void setAbertaRecebimentoStatus(Aberta abertaRecebimentoStatus) {
		this.abertaRecebimentoStatus = abertaRecebimentoStatus;
	}


	public Cancelada getCanceladaRecebimentoStatus() {
		return canceladaRecebimentoStatus;
	}


	public void setCanceladaRecebimentoStatus(Cancelada canceladaRecebimentoStatus) {
		this.canceladaRecebimentoStatus = canceladaRecebimentoStatus;
	}


	public Finalizada getFinalizadaRecebimentoStatus() {
		return finalizadaRecebimentoStatus;
	}


	public void setFinalizadaRecebimentoStatus(Finalizada finalizadaRecebimentoStatus) {
		this.finalizadaRecebimentoStatus = finalizadaRecebimentoStatus;
	}


	public Nova getNovaRecebimentoStatus() {
		return novaRecebimentoStatus;
	}


	public void setNovaRecebimentoStatus(Nova novaRecebimentoStatus) {
		this.novaRecebimentoStatus = novaRecebimentoStatus;
	}


	public Processada getProcessadaRecebimentoStatus() {
		return processadaRecebimentoStatus;
	}


	public void setProcessadaRecebimentoStatus(Processada processadaRecebimentoStatus) {
		this.processadaRecebimentoStatus = processadaRecebimentoStatus;
	}

}
