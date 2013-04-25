package br.com.sose.status.proposta;

import org.springframework.stereotype.Service;

import br.com.sose.entity.proposta.Proposta;

@Service("canceladaStatusProposta")
public class Cancelada extends StatusProposta {

	public static final String nome = "Cancelada"; 
	
	public Cancelada(Proposta p) {
		super.proposta = p;
	}

	public Cancelada() {
		// TODO Auto-generated constructor stub
	}


	
}
