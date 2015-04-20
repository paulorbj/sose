package br.com.sose.exceptions;

public class DefeitoExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public DefeitoExistenteException(String nome){  
		super("Nome do defeito: " + nome + " já foi cadastrado!");  
	} 
}
