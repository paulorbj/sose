package br.com.sose.exceptions;

public class ContatoNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public ContatoNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome de contato: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
