package br.com.sose.exceptions;

public class NumeroOrdemServicoMaiorQuePermitidoException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public NumeroOrdemServicoMaiorQuePermitidoException(String nome, Integer valorPermitido){  
		super("O número da ordem de serviço " + nome + " é maior que o valor permitido " + valorPermitido);  
	} 
}
