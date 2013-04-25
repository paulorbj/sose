package br.com.sose.exceptions;

public class EquipamentoExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public EquipamentoExistenteException(String nome){  
		super("Nome de equipamento: " + nome + " já foi cadastrado!");  
	} 
}
