package br.com.sose.status.expedicao;

import org.springframework.stereotype.Service;

import br.com.sose.entity.expedicao.NotaFiscalRemessa;

@Service("finalizadaStatusExpedicao")
public class Finalizada extends StatusExpedicao {

	public static final String nome = "Finalizada"; 
	
	public Finalizada(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}

	public Finalizada() {
		// TODO Auto-generated constructor stub
	}
	
	public String getStatus(){
		return "Finalizada";
	}
	
}
