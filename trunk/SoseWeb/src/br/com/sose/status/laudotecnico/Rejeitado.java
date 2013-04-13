package br.com.sose.status.laudotecnico;

import org.springframework.stereotype.Service;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;

@Service("rejeitadoStatusLaudoTecnico")
public class Rejeitado extends StatusLaudoTecnico {

	public static String nome = "Rejeitado";
	
	public Rejeitado(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public Rejeitado() {
	}
	
}
