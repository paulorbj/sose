package br.com.sose.exceptions;

public class PerfilExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public PerfilExistenteException(String nome){  
		super("Nome da atividade: " + nome + " já foi cadastrado!");  
	} 
}
