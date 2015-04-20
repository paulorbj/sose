package br.com.sose.exceptions;

public class LaboratorioNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public LaboratorioNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do laboratório: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
