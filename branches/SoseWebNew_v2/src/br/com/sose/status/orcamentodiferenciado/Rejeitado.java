package br.com.sose.status.orcamentodiferenciado;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcamento.OrcamentoDiferenciado;

@Service("rejeitadoStatusOrcamentoDiferenciado")
public class Rejeitado extends StatusOrcamentoDiferenciado {

	public static String nome = "Rejeitado";

	public Rejeitado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}

	public Rejeitado() {
		// TODO Auto-generated constructor stub
	}

}
