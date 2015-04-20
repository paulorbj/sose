package br.com.sose.status.compra;

import org.springframework.stereotype.Service;

import br.com.sose.entity.compra.PedidoCompra;

@Service("processandoPreCompraStatusCompra")
public class ProcessandoPreCompra extends StatusCompra {

	public static final String nome = "Processando pré compra"; 
	
	public ProcessandoPreCompra(PedidoCompra pc) {
		super.pedidoCompra = pc;
	}
	
	public ProcessandoPreCompra() {
	}
	
}
