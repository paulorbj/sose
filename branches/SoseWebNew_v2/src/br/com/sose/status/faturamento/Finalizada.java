package br.com.sose.status.faturamento;

import org.springframework.stereotype.Service;

import br.com.sose.entity.faturamento.Faturamento;

@Service("finalizadaStatusFaturamento")
public class Finalizada extends StatusFaturamento {

	public static final String nome = "Finalizada"; 
	
	public Finalizada(Faturamento nfr) {
		super.faturamento = nfr;
	}

	public Finalizada() {
		// TODO Auto-generated constructor stub
	}
	
}
