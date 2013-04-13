package br.com.sose.exceptions;

public class LaboratorioExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public LaboratorioExistenteException(String nome){  
		super("Nome do laboratório: " + nome + " já foi cadastrado!");  
	} 
}
