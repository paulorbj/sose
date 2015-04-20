package br.com.sose.exceptions;

public class OrdemServicoNaoPossuiCaseException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public OrdemServicoNaoPossuiCaseException(String nome){  
		super("A ordem de serviço selecionada não possui case para realizar a substituição" + nome );  
	} 
}
