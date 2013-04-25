package br.com.sose.exceptions;

public class UnidadeNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UnidadeNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome da unidade: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
