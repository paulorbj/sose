package br.com.sose.status.estoque;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service("recebidoStatusEstoque")
public class Recebido extends StatusEstoque {

	public static final String nome = "Recebido"; 
	
	public Recebido(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public Recebido() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	
}
