package br.com.sose.status.faturamento;

import org.springframework.stereotype.Service;

import br.com.sose.entity.faturamento.Faturamento;

@Service("canceladaStatusFaturamento")
public class Cancelada extends StatusFaturamento {

	public static final String nome = "Cancelada"; 
	
	public Cancelada(Faturamento nfr) {
		super.faturamento = nfr;
	}

	public Cancelada() {
		// TODO Auto-generated constructor stub
	}
	
}
