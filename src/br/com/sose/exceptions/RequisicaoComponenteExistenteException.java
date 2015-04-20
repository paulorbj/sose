package br.com.sose.exceptions;

public class RequisicaoComponenteExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public RequisicaoComponenteExistenteException(String nome){  
		super("Nome da atividade: " + nome + " já foi cadastrado!");  
	} 
}
