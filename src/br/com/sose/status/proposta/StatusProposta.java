package br.com.sose.status.proposta;

import java.util.List;
import java.util.Set;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;

public abstract class StatusProposta {
	
	protected Proposta proposta;	
	
	public Proposta salvarProposta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta finalizarProposta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta cancelarProposta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta arquivarProposta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta iniciarProposta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta enviarAoCliente(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Proposta liberarItens(Set<ItemProposta> itens,Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Proposta getProposta() {
		return proposta;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}

	
}
