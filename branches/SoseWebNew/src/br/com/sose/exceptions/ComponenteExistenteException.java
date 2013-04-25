package br.com.sose.exceptions;

public class ComponenteExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public ComponenteExistenteException(String nome){  
		super("Nome do componente: " + nome + " já foi cadastrado!");  
	} 
}
