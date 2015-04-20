package br.com.sose.exceptions;

public class EnderecoExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public EnderecoExistenteException(String nome){  
		super("Nome de contato: " + nome + " já foi cadastrado!");  
	} 
}
