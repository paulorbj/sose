package br.com.sose.status.recebimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.service.recebimento.NotaFiscalService;

@Service("processadaRecebimentoStatus")
public class Processada extends StatusRecebimento {

	public static final String nome = "Processada"; 
	
	@Autowired
	private NotaFiscalService notaFiscalService;
	
	public Processada(NotaFiscal nf) {
		super.notaFiscal = nf;
	}
	
	public Processada() {
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal salvarNotaFiscal(Usuario usuario) throws Exception {
		NotaFiscal notaFiscalRetorno;
		notaFiscalRetorno = notaFiscalService.salvarNotaFiscal(notaFiscal);
		salvarObservacaoNotaFiscal(usuario);
//		notaFiscalRetorno = notaFiscalService.buscarPorId(notaFiscal.getId());
		return notaFiscalRetorno;
	}

	public String getNome(){
		return nome;
	}
	
}
