package br.com.sose.exceptions;

public class UnidadeComCondicaoReparoException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UnidadeComCondicaoReparoException(String nome){  
		super("A unidade selecionada possui condição de reparo. Substitua a unidade" + nome );  
	} 
}
