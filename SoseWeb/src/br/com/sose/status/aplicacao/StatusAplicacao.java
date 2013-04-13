package br.com.sose.status.aplicacao;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.OrdemServico;

public abstract class StatusAplicacao {
	
	protected OrdemServico ordemServico;
	
	public abstract String getNome();

	public OrdemServico processarOrdemServico(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrdemServico salvarOrdemServico() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrdemServico editarOrdemServico() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrdemServico cancelarOrdemServico() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
	
	
}
