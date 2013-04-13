package br.com.sose.status.faturamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.service.faturamento.FaturamentoService;

@Service(value="faturamentoController")
@RemotingDestination(value="faturamentoController")
public class FaturamentoController {

	@Autowired
	private Finalizada finalizadaFaturamentoStatus;
	@Autowired
	private PreFatura preFaturaFaturamentoStatus;
	@Autowired
	private NaoIniciado naoIniciadoFaturamentoStatus;
	@Autowired
	private Iniciada iniciadaFaturamentoStatus;
	@Autowired
	private Emitida emitidaFaturamentoStatus;
	@Autowired
	private FaturamentoService faturamentoService;

	private Faturamento getStatus(Faturamento faturamento){
		if(faturamento.getStatusString() == null || faturamento.getStatusString().equals("") || faturamento.getStatusString().equals(PreFatura.nome)){
			preFaturaFaturamentoStatus.setFaturamento(faturamento);
			faturamento.setStatus(preFaturaFaturamentoStatus);
		}else if(faturamento.getStatusString().equals(Iniciada.nome)){
			iniciadaFaturamentoStatus.setFaturamento(faturamento);
			faturamento.setStatus(iniciadaFaturamentoStatus);
		}else if(faturamento.getStatusString().equals(Finalizada.nome)){
			finalizadaFaturamentoStatus.setFaturamento(faturamento);
			faturamento.setStatus(finalizadaFaturamentoStatus);
		}else if(faturamento.getStatusString().equals(NaoIniciado.nome)){
			naoIniciadoFaturamentoStatus.setFaturamento(faturamento);
			faturamento.setStatus(naoIniciadoFaturamentoStatus);
		}else if(faturamento.getStatusString().equals(Emitida.nome)){
			emitidaFaturamentoStatus.setFaturamento(faturamento);
			faturamento.setStatus(emitidaFaturamentoStatus);
		}
		return faturamento;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento salvarFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		faturamentoService.logarAlteracoes(faturamento, usuario);
		return faturamento.getStatus().salvarFatura(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento iniciarFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		return faturamento.getStatus().iniciarFatura(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento cancelarFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		return faturamento.getStatus().cancelarFatura(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento finalizarFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		return faturamento.getStatus().finalizarFatura(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento criarFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		return faturamento.getStatus().criarFatura(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento emitirFatura(Faturamento faturamento,Usuario usuario) throws Exception{
		faturamento = getStatus(faturamento);
		return faturamento.getStatus().emitirFatura(usuario);
	}
	
	
}
