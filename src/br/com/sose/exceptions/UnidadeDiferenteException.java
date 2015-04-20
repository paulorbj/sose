package br.com.sose.exceptions;

public class UnidadeDiferenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UnidadeDiferenteException(String nome){  
		super("As unidades são diferentes" + nome );  
	} 
}
