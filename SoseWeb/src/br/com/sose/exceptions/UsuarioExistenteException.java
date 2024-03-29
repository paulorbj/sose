package br.com.sose.exceptions;

public class UsuarioExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioExistenteException(String nome){  
		super("Nome do usuário: " + nome + " já foi cadastrado!");  
	} 
}
