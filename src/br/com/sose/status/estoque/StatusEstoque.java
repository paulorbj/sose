package br.com.sose.status.estoque;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

public abstract class StatusEstoque {
	
	protected RequisicaoComponente requisicaoComponente;
	
	public abstract String getStatus();

	public RequisicaoComponente getRequisicaoComponente() {
		return requisicaoComponente;
	}

	public void setRequisicaoComponente(RequisicaoComponente requisicaoComponente) {
		this.requisicaoComponente = requisicaoComponente;
	}

	
	public RequisicaoComponente atenderRequisicao(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public RequisicaoComponente finalizarColeta(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public RequisicaoComponente entregarMaterial(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public RequisicaoComponente retirarMaterial(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public RequisicaoComponente receberMaterial(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public RequisicaoComponente cancelarRequisicao(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public RequisicaoComponente disponibilizarComponente(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	

}
