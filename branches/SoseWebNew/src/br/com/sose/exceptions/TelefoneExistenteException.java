package br.com.sose.exceptions;

public class TelefoneExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public TelefoneExistenteException(String nome){  
		super("Nome de contato: " + nome + " já foi cadastrado!");  
	} 
}
