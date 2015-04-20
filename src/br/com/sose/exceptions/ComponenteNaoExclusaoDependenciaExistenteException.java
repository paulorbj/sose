package br.com.sose.exceptions;

public class ComponenteNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public ComponenteNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do componente: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
