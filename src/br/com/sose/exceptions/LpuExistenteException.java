package br.com.sose.exceptions;

public class LpuExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public LpuExistenteException(String nome){  
		super("Nome da lpu: " + nome + " já foi cadastrado!");  
	} 
}
