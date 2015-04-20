package br.com.sose.exceptions;

public class ContatoExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public ContatoExistenteException(String nome){  
		super("Nome de contato: " + nome + " já foi cadastrado!");  
	} 
}
