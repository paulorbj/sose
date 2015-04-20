package br.com.sose.exceptions;

public class TecnicoNaoPertenceAoLaboratorioException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public TecnicoNaoPertenceAoLaboratorioException(String atribuidor, String laboratorio){  
		super("O técnico " + atribuidor + " não pertence ao laboratório "+ laboratorio +"!");  
	} 
}
