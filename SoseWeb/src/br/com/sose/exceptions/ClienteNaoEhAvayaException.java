package br.com.sose.exceptions;

public class ClienteNaoEhAvayaException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public ClienteNaoEhAvayaException(String nome){  
		super("A unidade selecionada não é da Avaya! Cliente -> " + nome );  
	} 
}
