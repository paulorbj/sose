package br.com.sose.exceptions;

public class AtividadeExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public AtividadeExistenteException(String nome){  
		super("Nome de perfil: " + nome + " já cadastrado!");  
	} 
}
