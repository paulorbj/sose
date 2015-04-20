package br.com.sose.exceptions;

public class NumeroNFExistenteParaClienteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public NumeroNFExistenteParaClienteException(String numero){  
		super("A nota fiscal " + numero + " já foi cadastrada para esse cliente!");  
	} 
}
