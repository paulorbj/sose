package br.com.sose.exceptions;

public class FabricanteNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public FabricanteNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome de fabricante: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
