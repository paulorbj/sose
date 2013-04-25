package br.com.sose.exceptions;

public class TipoComponenteNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public TipoComponenteNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do tipo componente: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
