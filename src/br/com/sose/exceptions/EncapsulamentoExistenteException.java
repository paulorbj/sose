package br.com.sose.exceptions;

public class EncapsulamentoExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public EncapsulamentoExistenteException(String nome){  
		super("Nome do encapsulamento: " + nome + " já foi cadastrado!");  
	} 
}
