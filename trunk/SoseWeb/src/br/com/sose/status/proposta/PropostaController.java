package br.com.sose.status.proposta;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;

@Service(value="propostaController")
@RemotingDestination(value="propostaController")
public class PropostaController {

	@Autowired
	private EmElaboracao emElaboracaoStatusProposta;
	@Autowired
	private Finalizada finalizadaStatusProposta;
	@Autowired
	private Nova novaStatusProposta;
	@Autowired
	private Cancelada canceladaStatusProposta;
	@Autowired
	private AguardandoAprovacaoCliente aguardandoAprovacaoClienteStatusProposta;


	private Proposta getStatus(Proposta proposta){
		if(proposta.getStatusString() == null || proposta.getStatusString().equals("") || proposta.getStatusString().equals(novaStatusProposta.nome)){
			novaStatusProposta.setProposta(proposta);
			proposta.setStatus(novaStatusProposta);
		}else if(proposta.getStatusString().equals(emElaboracaoStatusProposta.nome)){
			emElaboracaoStatusProposta.setProposta(proposta);
			proposta.setStatus(emElaboracaoStatusProposta);
		}else if(proposta.getStatusString().equals(aguardandoAprovacaoClienteStatusProposta.nome)){
			aguardandoAprovacaoClienteStatusProposta.setProposta(proposta);
			proposta.setStatus(aguardandoAprovacaoClienteStatusProposta);
		}else if(proposta.getStatusString().equals(finalizadaStatusProposta.nome)){
			finalizadaStatusProposta.setProposta(proposta);
			proposta.setStatus(finalizadaStatusProposta);
		}else if(proposta.getStatusString().equals(canceladaStatusProposta.nome)){
			canceladaStatusProposta.setProposta(proposta);
			proposta.setStatus(canceladaStatusProposta);
		}
		return proposta;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta salvarProposta(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().salvarProposta(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta iniciarProposta(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().iniciarProposta(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta enviarAoCliente(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().enviarAoCliente(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta cancelarProposta(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().cancelarProposta(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta arquivarProposta(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().arquivarProposta(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta finalizarProposta(Proposta proposta,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().finalizarProposta(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta liberarItens(Proposta proposta,Set<ItemProposta> itens,Usuario usuario) throws Exception{
		proposta = getStatus(proposta);
		return proposta.getStatus().liberarItens(itens,usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta gerarPdfProposta(Proposta proposta,Usuario usuario) throws Exception{
		return null;
	}

}
