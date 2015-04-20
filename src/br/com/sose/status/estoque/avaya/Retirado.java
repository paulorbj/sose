package br.com.sose.status.estoque.avaya;

import org.springframework.stereotype.Service;

import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;

@Service("retiradoStatusEstoqueAvaya")
public class Retirado extends StatusEstoqueAvaya {

	public static final String nome = "Retirado"; 
	
	public Retirado(ItemEstoqueAvaya iea) {
		super.itemEstoqueAvaya = iea;
	}
	
	public Retirado() {
	}

	
}
