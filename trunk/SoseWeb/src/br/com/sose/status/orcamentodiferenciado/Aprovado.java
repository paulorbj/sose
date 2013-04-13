package br.com.sose.status.orcamentodiferenciado;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcamento.OrcamentoDiferenciado;

@Service("aprovadoStatusOrcamentoDiferenciado")
public class Aprovado extends StatusOrcamentoDiferenciado {

	public static String nome = "Aprovado";
	
	public Aprovado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}
	
	public Aprovado() {
	}
	
}
