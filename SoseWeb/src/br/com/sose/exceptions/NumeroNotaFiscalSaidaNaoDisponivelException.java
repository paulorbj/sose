package br.com.sose.exceptions;

public class NumeroNotaFiscalSaidaNaoDisponivelException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public NumeroNotaFiscalSaidaNaoDisponivelException(String nome){  
		super("Uma nota fiscal de número " + nome + " já existe no sistema!");  
	} 
}
