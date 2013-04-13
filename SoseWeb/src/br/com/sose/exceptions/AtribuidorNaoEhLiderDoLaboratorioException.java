package br.com.sose.exceptions;

public class AtribuidorNaoEhLiderDoLaboratorioException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public AtribuidorNaoEhLiderDoLaboratorioException(String atribuidor, String laboratorio){  
		super("O usuário " + atribuidor + " não é líder do laboratório "+ laboratorio +"!");  
	} 
}
