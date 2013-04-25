package br.com.sose.exceptions;

public class OrdemServicoNaoEncontradaException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public OrdemServicoNaoEncontradaException(String nome){  
		super("Ordem de serviço " + nome + " não foi encontrada no sistema");  
	} 
}
