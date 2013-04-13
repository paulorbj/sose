package br.com.sose.status.expedicao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;

@Service(value="expedicaoController")
@RemotingDestination(value="expedicaoController")
public class ExpedicaoController {

	@Autowired
	private PreExpedicao preExpedicaoStatus;
	@Autowired
	private Emitida emitidaExpedicaoStatus;
	@Autowired
	private Finalizada finalizadaExpedicaoStatus;
	@Autowired
	private Nova novaExpedicaoStatus;
	@Autowired
	private Solicitada solicitadaExpedicaoStatus;
	@Autowired
	private Iniciada iniciadaExpedicaoStatus;


	private NotaFiscalRemessa getStatus(NotaFiscalRemessa notaFiscalRemessa){
		if(notaFiscalRemessa.getStatusString() == null || notaFiscalRemessa.getStatusString().equals("") || notaFiscalRemessa.getStatusString().equals(novaExpedicaoStatus.nome)){
			novaExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(novaExpedicaoStatus);
		}else if(notaFiscalRemessa.getStatusString().equals(finalizadaExpedicaoStatus.nome)){
			finalizadaExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(finalizadaExpedicaoStatus);
		}else if(notaFiscalRemessa.getStatusString().equals(emitidaExpedicaoStatus.nome)){
			emitidaExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(emitidaExpedicaoStatus);
		}else if(notaFiscalRemessa.getStatusString().equals(solicitadaExpedicaoStatus.nome)){
			solicitadaExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(solicitadaExpedicaoStatus);
		}else if(notaFiscalRemessa.getStatusString().equals(iniciadaExpedicaoStatus.nome)){
			iniciadaExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(iniciadaExpedicaoStatus);
		}else if(notaFiscalRemessa.getStatusString().equals(preExpedicaoStatus.nome)){
			preExpedicaoStatus.setNotaFiscalRemessa(notaFiscalRemessa);
			notaFiscalRemessa.setStatus(preExpedicaoStatus);
		}
		return notaFiscalRemessa;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa criarNotaFiscal(NotaFiscalRemessa notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().criarNotaFiscal(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa salvarNotaFiscalSaida(NotaFiscalRemessa notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		notaFiscal = notaFiscal.getStatus().salvarNotaFiscalSaida(usuario);
		return notaFiscal;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa iniciarNotaFiscalSaida(NotaFiscalRemessa notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().iniciarNotaFiscalSaida(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa emitirNotaFiscalSaida(NotaFiscalRemessa notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().emitirNotaFiscalSaida(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa registrarDataSaidaNotaFiscalSaida(NotaFiscalRemessa notaFiscal, Date dataSaida,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().registrarDataSaidaNotaFiscalSaida(dataSaida,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa solicitarNotaFiscalSaida(NotaFiscalRemessa notaFiscal, Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().solicitarNotaFiscalSaida(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa editarNotaFiscalSaida(NotaFiscalRemessa notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().editarNotaFiscalSaida();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa cancelarNotaFiscalSaida(NotaFiscalRemessa notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().cancelarNotaFiscalSaida();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa duplicarNotaFiscalSaida(NotaFiscalRemessa notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().duplicarNotaFiscalSaida();
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa finalizarNotaFiscalSaida(NotaFiscalRemessa notaFiscal,Usuario usuario) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().finalizarNotaFiscalSaida(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa gerarEtiquetas(NotaFiscalRemessa notaFiscal) throws Exception{
		notaFiscal = getStatus(notaFiscal);
		return notaFiscal.getStatus().gerarEtiquetas();
	}

}
