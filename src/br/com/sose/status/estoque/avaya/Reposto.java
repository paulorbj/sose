package br.com.sose.status.estoque.avaya;

import org.springframework.stereotype.Service;

import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;

@Service("repostoStatusEstoqueAvaya")
public class Reposto extends StatusEstoqueAvaya {

	public static final String nome = "Reposto"; 
	
	public Reposto(ItemEstoqueAvaya iea) {
		super.itemEstoqueAvaya = iea;
	}
	
	public Reposto() {
	}

	
}
