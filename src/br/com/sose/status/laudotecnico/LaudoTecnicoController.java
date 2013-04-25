package br.com.sose.status.laudotecnico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;

@Service(value="laudoTecnicoController")
@RemotingDestination(value="laudoTecnicoController")
public class LaudoTecnicoController {

	@Autowired
	private Finalizado finalizadoLaudoTecnicoStatus;
	@Autowired
	private Rejeitado rejeitadoLaudoTecnicoStatus;
	@Autowired
	private NaoIniciado naoIniciadoLaudoTecnicoStatus;
	@Autowired
	private Iniciado iniciadoLaudoTecnicoStatus;


	private LaudoTecnico getStatus(LaudoTecnico laudoTecnico){
		if(laudoTecnico.getStatusString() == null || laudoTecnico.getStatusString().equals("") || laudoTecnico.getStatusString().equals(NaoIniciado.nome)){
			naoIniciadoLaudoTecnicoStatus.setLaudoTecnico(laudoTecnico);
			laudoTecnico.setStatus(naoIniciadoLaudoTecnicoStatus);
		}else if(laudoTecnico.getStatusString().equals(Iniciado.nome)){
			iniciadoLaudoTecnicoStatus.setLaudoTecnico(laudoTecnico);
			laudoTecnico.setStatus(iniciadoLaudoTecnicoStatus);
		}else if(laudoTecnico.getStatusString().equals(Finalizado.nome)){
			finalizadoLaudoTecnicoStatus.setLaudoTecnico(laudoTecnico);
			laudoTecnico.setStatus(finalizadoLaudoTecnicoStatus);
		}else if(laudoTecnico.getStatusString().equals(Rejeitado.nome)){
			rejeitadoLaudoTecnicoStatus.setLaudoTecnico(laudoTecnico);
			laudoTecnico.setStatus(rejeitadoLaudoTecnicoStatus);
		}
		return laudoTecnico;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico salvarLaudoTecnico(LaudoTecnico laudoTecnico,Usuario usuario) throws Exception{
		laudoTecnico = getStatus(laudoTecnico);
		return laudoTecnico.getStatus().salvarLaudoTecnico(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico iniciarLaudoTecnico(LaudoTecnico laudoTecnico,Usuario usuario) throws Exception{
		laudoTecnico = getStatus(laudoTecnico);
		return laudoTecnico.getStatus().iniciarLaudoTecnico(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico cancelarLaudoTecnico(LaudoTecnico laudoTecnico,Usuario usuario) throws Exception{
		laudoTecnico = getStatus(laudoTecnico);
		return laudoTecnico.getStatus().cancelarLaudoTecnico(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico finalizarLaudoTecnico(LaudoTecnico laudoTecnico,Usuario usuario) throws Exception{
		laudoTecnico = getStatus(laudoTecnico);
		return laudoTecnico.getStatus().finalizarLaudoTecnico(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico rejeitarLaudoTecnico(LaudoTecnico laudoTecnico,Usuario usuario) throws Exception{
		laudoTecnico = getStatus(laudoTecnico);
		return laudoTecnico.getStatus().rejeitarLaudoTecnico(usuario);
	}
	
}
