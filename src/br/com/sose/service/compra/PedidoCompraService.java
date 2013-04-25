package br.com.sose.service.compra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.compra.PedidoCompraDao;
import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.status.compra.AguardandoCompra;
import br.com.sose.status.compra.Comprado;

@Service(value="pedidoCompraService")
@RemotingDestination(value="pedidoCompraService")
public class PedidoCompraService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public PedidoCompraDao pedidoCompraDao;

	@Autowired
	public OrdemServicoDao ordemServicoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<PedidoCompra> listaPedidosCompra() throws Exception {
		List<PedidoCompra> pedidosCompra = new ArrayList<PedidoCompra>();
		List<PedidoCompra> pedidosCompraAux = null;
		try {
			pedidosCompraAux = pedidoCompraDao.listarPedidoCompra(AguardandoCompra.nome);
			if(pedidosCompraAux != null && !pedidosCompraAux.isEmpty()) pedidosCompra.addAll(pedidosCompraAux);
			pedidosCompraAux = pedidoCompraDao.listarPedidoCompra(Comprado.nome);
			if(pedidosCompraAux != null && !pedidosCompraAux.isEmpty()) pedidosCompra.addAll(pedidosCompraAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pedidosCompra;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PedidoCompra salvarPedidoCompra(PedidoCompra pedidoCompra) throws Exception {
		PedidoCompra pedidoCompraSalvo;
		try {
			if(pedidoCompra.getId() == null || pedidoCompra.getId().equals(new Long(0)))
				pedidoCompraSalvo =(PedidoCompra) pedidoCompraDao.save(pedidoCompra);	
			else
				pedidoCompraSalvo =(PedidoCompra) pedidoCompraDao.update(pedidoCompra);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pedidoCompraSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PedidoCompra criarPedidoCompra(Integer quantidade) throws Exception {
		PedidoCompra pedidoCompraSalvo;
		try {
			pedidoCompraSalvo = new PedidoCompra();
			pedidoCompraSalvo.setDataCriacao(new Date());
			pedidoCompraSalvo.setOrdemServico(null);
			pedidoCompraSalvo.setPossuiAmostra(false);
			pedidoCompraSalvo.setQuantidade(quantidade);
			pedidoCompraSalvo.setRealizadoPor(null);
			pedidoCompraSalvo.setStatusString(AguardandoCompra.nome);
			pedidoCompraSalvo.setTecnico(null);
			pedidoCompraSalvo =(PedidoCompra) salvarPedidoCompra(pedidoCompraSalvo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pedidoCompraSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirPedidoCompra(PedidoCompra pedidoCompra) throws Exception {
		try {
			pedidoCompraDao.delete(pedidoCompra);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}



}
