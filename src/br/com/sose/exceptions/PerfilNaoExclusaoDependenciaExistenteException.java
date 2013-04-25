package br.com.sose.exceptions;

public class PerfilNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;
	
	public PerfilNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome de perfil: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
