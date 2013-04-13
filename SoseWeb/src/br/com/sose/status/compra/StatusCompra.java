package br.com.sose.status.compra;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.compra.PedidoCompra;

public abstract class StatusCompra {
	
	protected PedidoCompra pedidoCompra;

	public PedidoCompra getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(PedidoCompra pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}
	
	public PedidoCompra comprar(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}


}
