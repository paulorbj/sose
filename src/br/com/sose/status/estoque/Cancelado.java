package br.com.sose.status.estoque;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service("canceladoStatusEstoque")
public class Cancelado extends StatusEstoque {

	public static final String nome = "Cancelado"; 
	
	public Cancelado(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public Cancelado() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	
}
