package br.com.sose.status.laudotecnico;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;

public abstract class StatusLaudoTecnico {
	
	protected LaudoTecnico laudoTecnico;
	
	public LaudoTecnico getLaudoTecnico() {
		return laudoTecnico;
	}

	public void setLaudoTecnico(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public LaudoTecnico iniciarLaudoTecnico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public LaudoTecnico salvarLaudoTecnico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public LaudoTecnico finalizarLaudoTecnico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public LaudoTecnico rejeitarLaudoTecnico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public LaudoTecnico cancelarLaudoTecnico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
}
