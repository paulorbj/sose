package br.com.sose.status.estoque;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service("notificaStatusEstoque")
public class NotificarEstoque extends StatusEstoque {

	public static final String nome = "Notificar estoque"; 
	
	public NotificarEstoque(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public NotificarEstoque() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	
}
