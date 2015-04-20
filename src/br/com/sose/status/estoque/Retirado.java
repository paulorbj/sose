package br.com.sose.status.estoque;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service("retiradoStatusEstoque")
public class Retirado extends StatusEstoque {

	public static final String nome = "Retirado"; 
	
	public Retirado(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public Retirado() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	
}
