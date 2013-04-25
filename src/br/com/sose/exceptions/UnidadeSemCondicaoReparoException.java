package br.com.sose.exceptions;

public class UnidadeSemCondicaoReparoException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UnidadeSemCondicaoReparoException(String nome){  
		super("A unidade selecionada não possui condição de reparo. Reponha a unidade" + nome );  
	} 
}
