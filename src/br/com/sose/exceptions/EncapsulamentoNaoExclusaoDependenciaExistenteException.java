package br.com.sose.exceptions;

public class EncapsulamentoNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public EncapsulamentoNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do encapsulamento: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
