package br.com.sose.status.reparo;

import java.util.Date;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;

public abstract class StatusReparo {
	
	protected Reparo reparo;
	
	public abstract String getNome();

	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo salvarReparo(Reparo reparo) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo iniciarReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo editarReparo(Reparo reparo) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo solicitarOrcamentoDiferenciadoReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Reparo finalizarReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo solicitarLaudoTecnico(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo solicitarReparoExterno(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo aprovarReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo devolverSemReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Reparo rejeitarReparo(Reparo reparo, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Reparo getReparo() {
		return reparo;
	}

	public void setReparo(Reparo reparo) {
		this.reparo = reparo;
	}

}
