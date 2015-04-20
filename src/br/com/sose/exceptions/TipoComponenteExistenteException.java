package br.com.sose.exceptions;

public class TipoComponenteExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public TipoComponenteExistenteException(String nome){  
		super("Nome do tipo componente: " + nome + " já foi cadastrado!");  
	} 
}
