package br.com.sose.status.compra;

import org.springframework.stereotype.Service;

import br.com.sose.entity.compra.PedidoCompra;

@Service("compradoStatusCompra")
public class Comprado extends StatusCompra {

	public static final String nome = "Comprado"; 
	
	public Comprado(PedidoCompra pc) {
		super.pedidoCompra = pc;
	}
	
	public Comprado() {
	}
	
}
