package br.com.sose.status.orcamentodiferenciado;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;

public abstract class StatusOrcamentoDiferenciado {
	
	protected OrcamentoDiferenciado orcamentoDiferenciado;

	public OrcamentoDiferenciado getOrcamentoDiferenciado() {
		return orcamentoDiferenciado;
	}

	public void setOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}

	public OrcamentoDiferenciado iniciarOrcamentoDiferenciado(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrcamentoDiferenciado salvarOrcamentoDiferenciado(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrcamentoDiferenciado aprovarOrcamentoDiferenciado(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrcamentoDiferenciado rejeitarOrcamentoDiferenciado(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public OrcamentoDiferenciado cancelarOrcamentoDiferenciado(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
}
