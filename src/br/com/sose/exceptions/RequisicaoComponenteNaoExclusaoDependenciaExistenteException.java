package br.com.sose.exceptions;

public class RequisicaoComponenteNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public RequisicaoComponenteNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome da atividade: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
