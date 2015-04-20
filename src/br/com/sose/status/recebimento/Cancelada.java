package br.com.sose.status.recebimento;

import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.NotaFiscal;

@Service("canceladaRecebimentoStatus")
public class Cancelada extends StatusRecebimento {
	
	public static final String nome = "Cancelada"; 

	public Cancelada(NotaFiscal nf) {
		super.notaFiscal = nf;
	}

	public Cancelada() {
	}

	public String getNome(){
		return nome;
	}
	
}
