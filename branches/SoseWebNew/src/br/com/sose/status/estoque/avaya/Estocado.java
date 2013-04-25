package br.com.sose.status.estoque.avaya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;
import br.com.sose.service.estoque.avaya.ItemEstoqueAvayaService;

@Service("estocadoStatusEstoqueAvaya")
public class Estocado extends StatusEstoqueAvaya {

	public static final String nome = "Estocado"; 
	
	@Autowired
	private ItemEstoqueAvayaService itemEstoqueAvayaService;
	
	public Estocado(ItemEstoqueAvaya iea) {
		super.itemEstoqueAvaya = iea;
	}
	
	public Estocado() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya substituir(String idOS, Usuario usuario) throws Exception {
		itemEstoqueAvaya = itemEstoqueAvayaService.substituir(itemEstoqueAvaya, idOS, usuario);
		return itemEstoqueAvaya;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya repor(String idOS, Usuario usuario)	throws Exception {
		itemEstoqueAvaya = itemEstoqueAvayaService.repor(itemEstoqueAvaya, idOS, usuario);
		return itemEstoqueAvaya;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya retirar(Usuario usuario) throws Exception {
		itemEstoqueAvaya = itemEstoqueAvayaService.retirar(itemEstoqueAvaya, usuario);
		return itemEstoqueAvaya;
	}
	
	
	
}
