package br.com.sose.exceptions;

public class EquipamentoNaoExclusaoDependenciaExistenteException extends CustomException {

	private static final long serialVersionUID = 1L;

	public EquipamentoNaoExclusaoDependenciaExistenteException(String nome){  
		super("Nome de equipamento: " + nome + " não pode ser excluído pois possui relacionamento no banco de dados!");  
	} 
}
