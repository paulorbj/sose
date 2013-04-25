package br.com.sose.status.recebimento;

import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.NotaFiscal;

@Service("abertaRecebimentoStatus")
public class Aberta extends StatusRecebimento {
	
	public static final String nome = "Aberta"; 

	public Aberta(NotaFiscal nf) {
		super.notaFiscal = nf;
	}
	
	public Aberta() {
	
	}

	public String getNome(){
		return nome;
	}
	
}
