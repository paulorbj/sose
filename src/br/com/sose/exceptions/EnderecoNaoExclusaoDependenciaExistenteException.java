package br.com.sose.exceptions;

public class EnderecoNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public EnderecoNaoExclusaoDependenciaExistenteException(Long id){  
		super("Endereço  " + id + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
