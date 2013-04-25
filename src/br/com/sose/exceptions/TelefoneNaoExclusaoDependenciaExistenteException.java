package br.com.sose.exceptions;

public class TelefoneNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public TelefoneNaoExclusaoDependenciaExistenteException(Long id){  
		super("Telefone id: " + id + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
