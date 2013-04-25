package br.com.sose.status.recebimento;

import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.NotaFiscal;

@Service("finalizadaRecebimentoStatus")
public class Finalizada extends StatusRecebimento {
	
	public static final String nome = "Finalizada"; 

	public Finalizada(NotaFiscal nf) {
		super.notaFiscal = nf;
	}

	public Finalizada() {
	}

	public String getNome(){
		return nome;
	}
	
}
