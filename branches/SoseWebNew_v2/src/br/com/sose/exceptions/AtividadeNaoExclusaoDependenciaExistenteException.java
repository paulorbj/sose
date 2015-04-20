package br.com.sose.exceptions;

public class AtividadeNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public AtividadeNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome da atividade: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
