package br.com.sose.status.orcamentodiferenciado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;

@Service(value="orcamentoDiferenciadoController")
@RemotingDestination(value="orcamentoDiferenciadoController")
public class OrcamentoDiferenciadoController {

	@Autowired
	private Aprovado aprovadoOrcamentoDiferenciadoStatus;
	@Autowired
	private Rejeitado rejeitadoOrcamentoDiferenciadoStatus;
	@Autowired
	private NaoIniciado naoIniciadoOrcamentoDiferenciadoStatus;
	@Autowired
	private Iniciado iniciadoOrcamentoDiferenciadoStatus;


	private OrcamentoDiferenciado getStatus(OrcamentoDiferenciado orcamentoDiferenciado){
		if(orcamentoDiferenciado.getStatusString() == null || orcamentoDiferenciado.getStatusString().equals("") || orcamentoDiferenciado.getStatusString().equals(NaoIniciado.nome)){
			naoIniciadoOrcamentoDiferenciadoStatus.setOrcamentoDiferenciado(orcamentoDiferenciado);
			orcamentoDiferenciado.setStatus(naoIniciadoOrcamentoDiferenciadoStatus);
		}else if(orcamentoDiferenciado.getStatusString().equals(Iniciado.nome)){
			iniciadoOrcamentoDiferenciadoStatus.setOrcamentoDiferenciado(orcamentoDiferenciado);
			orcamentoDiferenciado.setStatus(iniciadoOrcamentoDiferenciadoStatus);
		}else if(orcamentoDiferenciado.getStatusString().equals(Aprovado.nome)){
			aprovadoOrcamentoDiferenciadoStatus.setOrcamentoDiferenciado(orcamentoDiferenciado);
			orcamentoDiferenciado.setStatus(aprovadoOrcamentoDiferenciadoStatus);
		}else if(orcamentoDiferenciado.getStatusString().equals(Rejeitado.nome)){
			rejeitadoOrcamentoDiferenciadoStatus.setOrcamentoDiferenciado(orcamentoDiferenciado);
			orcamentoDiferenciado.setStatus(rejeitadoOrcamentoDiferenciadoStatus);
		}
		return orcamentoDiferenciado;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado salvarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado,Usuario realizadoPor) throws Exception{
		orcamentoDiferenciado = getStatus(orcamentoDiferenciado);
		return orcamentoDiferenciado.getStatus().salvarOrcamentoDiferenciado(realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado iniciarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado,Usuario realizadoPor) throws Exception{
		orcamentoDiferenciado = getStatus(orcamentoDiferenciado);
		return orcamentoDiferenciado.getStatus().iniciarOrcamentoDiferenciado(realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado cancelarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado,Usuario realizadoPor) throws Exception{
		orcamentoDiferenciado = getStatus(orcamentoDiferenciado);
		return orcamentoDiferenciado.getStatus().cancelarOrcamentoDiferenciado(realizadoPor);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado aprovarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado,Usuario realizadoPor) throws Exception{
		orcamentoDiferenciado = getStatus(orcamentoDiferenciado);
		return orcamentoDiferenciado.getStatus().aprovarOrcamentoDiferenciado(realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado rejeitarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado,Usuario realizadoPor) throws Exception{
		orcamentoDiferenciado = getStatus(orcamentoDiferenciado);
		return orcamentoDiferenciado.getStatus().rejeitarOrcamentoDiferenciado(realizadoPor);
	}
	
}
