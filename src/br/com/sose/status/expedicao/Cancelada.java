package br.com.sose.status.expedicao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;

@Service("canceladaStatusExpedicao")
public class Cancelada extends StatusExpedicao {

	public static final String nome = "Cancelada"; 
	
	
	public Cancelada(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}
	
	public Cancelada() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return "Cancelada";
	}

	
}
