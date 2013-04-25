package br.com.sose.status.laudotecnico;

import org.springframework.stereotype.Service;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;

@Service("finalizadoStatusLaudoTecnico")
public class Finalizado extends StatusLaudoTecnico {

	public static String nome = "Aprovado";

	public Finalizado(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public Finalizado() {
	}

}
