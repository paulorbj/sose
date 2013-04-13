package br.com.sose.exceptions;

public class DefeitoNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public DefeitoNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do defeito: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
