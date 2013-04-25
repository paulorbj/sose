package br.com.sose.exceptions;

public class LpuNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public LpuNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome da lpu: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
