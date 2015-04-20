package br.com.sose.exceptions;

public class UsuarioNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome do usuário: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
