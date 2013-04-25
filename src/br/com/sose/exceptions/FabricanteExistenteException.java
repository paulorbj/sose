package br.com.sose.exceptions;

public class FabricanteExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public FabricanteExistenteException(String nome){  
		super("Nome de fabricante: " + nome + " já foi cadastrado!");  
	} 
}
