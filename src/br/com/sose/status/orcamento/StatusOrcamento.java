package br.com.sose.status.orcamento;

import java.util.Date;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;

public abstract class StatusOrcamento {
	
	protected Orcamento orcamento;
	
	public abstract String getNome();

	public Orcamento salvarOrcamento(Orcamento orcamento) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento iniciarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento editarOrcamento(Orcamento orcamento) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Orcamento finalizarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento solicitarLaudoTecnico(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento solicitarReparoExterno(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date data, Usuario atribuidoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento realocarTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento visualizarOrcamento(Orcamento orcamento) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento encaminharAoLider(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Orcamento devolverSemReparo(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento aprovarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public Orcamento rejeitarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}
}
