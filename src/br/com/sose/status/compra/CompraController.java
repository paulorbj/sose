package br.com.sose.status.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.compra.PedidoCompra;


@Service(value="compraController")
@RemotingDestination(value="compraController")
public class CompraController {

	@Autowired
	private AguardandoCompra aguaradandoCompraCompraStatus;
	@Autowired
	private Comprado compradoCompraStatus;

	
	private PedidoCompra getStatus(PedidoCompra pedidoCompra){
		if (pedidoCompra.getStatusString() == null || pedidoCompra.getStatusString().equals("") || pedidoCompra.getStatusString().equals(AguardandoCompra.nome)){
			aguaradandoCompraCompraStatus.setPedidoCompra(pedidoCompra);
			pedidoCompra.setStatus(aguaradandoCompraCompraStatus);
		}else if(pedidoCompra.getStatusString().equals(Comprado.nome)){
			compradoCompraStatus.setPedidoCompra(pedidoCompra);
			pedidoCompra.setStatus(compradoCompraStatus);
		}
		return pedidoCompra;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PedidoCompra comprar(PedidoCompra pedidoCompra,Usuario usuario) throws Exception{
		pedidoCompra = getStatus(pedidoCompra);
		return pedidoCompra.getStatus().comprar(usuario);
	}
	
	
}
