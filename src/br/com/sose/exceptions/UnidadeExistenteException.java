package br.com.sose.exceptions;

public class UnidadeExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UnidadeExistenteException(String nome){  
		super("Unidade e código já foram cadastrados!");  
	} 
}
