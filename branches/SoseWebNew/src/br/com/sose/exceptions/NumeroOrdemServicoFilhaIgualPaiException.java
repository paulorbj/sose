package br.com.sose.exceptions;

public class NumeroOrdemServicoFilhaIgualPaiException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public NumeroOrdemServicoFilhaIgualPaiException(String osPai, String osFilha){  
		super("A OS filha de número " + osFilha + " tem o mesmo número de sua OS pai "+ osPai +"!");  
	} 
}
