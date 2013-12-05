package br.com.sose.status.estoque;

import org.springframework.stereotype.Service;

import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service("componenteNaoEncontradoStatusEstoque")
public class ComponenteNaoEncontrado extends StatusEstoque {

	public static final String nome = "Componente não encontrado"; 
	
	public ComponenteNaoEncontrado(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public ComponenteNaoEncontrado() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	
}
