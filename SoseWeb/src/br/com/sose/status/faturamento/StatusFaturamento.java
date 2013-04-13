package br.com.sose.status.faturamento;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.faturamento.Faturamento;

public abstract class StatusFaturamento {
	
	protected Faturamento faturamento;
	
	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

	public Faturamento iniciarFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Faturamento salvarFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Faturamento finalizarFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Faturamento cancelarFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Faturamento emitirFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Faturamento criarFatura(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
}
