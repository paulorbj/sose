package br.com.sose.exceptions;

public class NumeroOrdemServicoNaoDisponivelException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public NumeroOrdemServicoNaoDisponivelException(String nome){  
		super("A OS de número " + nome + " já está sendo utilizada!");  
	} 
}
