package br.com.sose.status.estoque.avaya;

import org.springframework.stereotype.Service;

import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;

@Service("substituidoStatusEstoqueAvaya")
public class Substituido extends StatusEstoqueAvaya {

	public static final String nome = "Substituído"; 
	
	public Substituido(ItemEstoqueAvaya iea) {
		super.itemEstoqueAvaya = iea;
	}
	
	public Substituido() {
	}
	
}
