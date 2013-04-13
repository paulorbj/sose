package br.com.sose.status.estoque.avaya;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;

public abstract class StatusEstoqueAvaya {
	
	protected ItemEstoqueAvaya itemEstoqueAvaya;

	public ItemEstoqueAvaya getItemEstoqueAvaya() {
		return itemEstoqueAvaya;
	}

	public void setItemEstoqueAvaya(ItemEstoqueAvaya itemEstoqueAvaya) {
		this.itemEstoqueAvaya = itemEstoqueAvaya;
	}
	
	public ItemEstoqueAvaya estocar(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public ItemEstoqueAvaya substituir(String idOS, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public ItemEstoqueAvaya repor(String idOS, Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public ItemEstoqueAvaya retirar(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
		

}
