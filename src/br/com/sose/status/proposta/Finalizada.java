package br.com.sose.status.proposta;

import org.springframework.stereotype.Service;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.proposta.Proposta;

@Service("finalizadaStatusProposta")
public class Finalizada extends StatusProposta {

	public static final String nome = "Finalizada"; 
	
	public Finalizada(Proposta p) {
		super.proposta = p;
	}

	public Finalizada() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Proposta arquivarProposta(Usuario usuario) throws Exception {
		// TODO Auto-generated method stub
		return super.arquivarProposta(usuario);
	}
	

	
}
