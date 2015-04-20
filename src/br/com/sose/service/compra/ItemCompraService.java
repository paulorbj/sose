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

import br.com.sose.daoImpl.compra.ItemCompraDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.ItemCompra;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ComponenteService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.compra.AguardandoCompra;

@Service(value="itemCompraService")
@RemotingDestination(value="itemCompraService")
public class ItemCompraService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ItemCompraDao itemCompraDao;

	@Autowired
	public CompraService compraService;

	@Autowired
	public PedidoCompraService pedidoCompraService;

	@Autowired
	public ComponenteService componenteService;

	@Autowired
	public RequisicaoComponenteService requisicaoComponenteService;

	@Autowired
	public ReparoService reparoService;
	
	@Autowired
	public OrcamentoService orcamentoService;
	
	@Autowired
	private ObservacaoService observacaoService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemCompra> listarUltimas10Compras(Componente componente) throws Exception {
		List<ItemCompra> listaRetorno;
		try {
			listaRetorno =(List<ItemCompra>) itemCompraDao.listarUltimas10Compras(componente);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public Integer calcularQuantidadeAguardandoCompra(Componente componente) throws Exception {
		List<ItemCompra> listaRetorno;
		Integer quantidade = 0;
		try {
			listaRetorno =(List<ItemCompra>) itemCompraDao.listarItemCompraEmAguardandoCompra(componente);	
			for(ItemCompra ic : listaRetorno) {
				quantidade += ic.getQtdTotalRequisitada();
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			quantidade = 0;;
		}
		return quantidade;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemCompra> listarUltimasCompras(Componente componente, int qtdResultado) throws Exception {
		List<ItemCompra> listaRetorno;
		try {
			listaRetorno =(List<ItemCompra>) itemCompraDao.listarUltimasCompras(componente, qtdResultado);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaRetorno;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemCompra> listarItemCompraPendenteNotificacao(Componente componente) throws Exception {
		List<ItemCompra> listaRetorno;
		try {
			listaRetorno =(List<ItemCompra>) itemCompraDao.listarItemCompraPendenteNotificacao(componente);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemCompra> listarItemCompraPorCompra(Compra compra) throws Exception {
		List<ItemCompra> listaRetorno;
		try {
			listaRetorno =(List<ItemCompra>) itemCompraDao.listarItemCompraPorCompra(compra);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemCompra> buscarPorCompra(Compra compra) throws Exception {
		List<ItemCompra> itemCompras = new ArrayList<ItemCompra>();
		List<ItemCompra> itemComprasAux;
		try {
			itemComprasAux = itemCompraDao.listarItemCompraPorCompraPorStatus(compra, "Pendente");
			if(itemComprasAux != null && !itemComprasAux.isEmpty()){
				itemCompras.addAll(itemComprasAux);
			}
			itemComprasAux = itemCompraDao.listarItemCompraPorCompraPorStatus(compra, "Comprado");
			if(itemComprasAux != null && !itemComprasAux.isEmpty()){
				itemCompras.addAll(itemComprasAux);
			}
			itemComprasAux = itemCompraDao.listarItemCompraPorCompraPorStatus(compra, "Notificado");
			if(itemComprasAux != null && !itemComprasAux.isEmpty()){
				itemCompras.addAll(itemComprasAux);
			}
			itemComprasAux = itemCompraDao.listarItemCompraPorCompraPorStatus(compra, "Componente não encontrado");
			if(itemComprasAux != null && !itemComprasAux.isEmpty()){
				itemCompras.addAll(itemComprasAux);
			}
			itemComprasAux = itemCompraDao.listarItemCompraPorCompraPorStatus(compra, "Finalizado");
			if(itemComprasAux != null && !itemComprasAux.isEmpty()){
				itemCompras.addAll(itemComprasAux);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemCompras;
	}


	@RemotingInclude
	@Transactional(readOnly = true)
	public ItemCompra buscarPorId(Long id) throws Exception {
		ItemCompra itemCompraEncontrado;
		try {
			itemCompraEncontrado =(ItemCompra) itemCompraDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemCompraEncontrado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemCompra salvarItemCompra(ItemCompra itemCompra) throws Exception {
		ItemCompra itemCompraSalvo;
		try {
			if(itemCompra.getQtdComprada() != null && itemCompra.getQtdComprada() > 0 && itemCompra.getStatus().equals("Pendente")){
				itemCompra.setStatus("Comprado");
				for(PedidoCompra pc : itemCompra.getListaPedidoCompra()){
					if(pc.getOrdemServico() != null){
						observacaoService.log("Compra", "Componente comprado, aguardando chegada", 2, new Date(), pc.getOrdemServico(), null);
					}
				}
			}
			if(itemCompra.getId() == null || itemCompra.getId().equals(new Long(0)))
				itemCompraSalvo =(ItemCompra) itemCompraDao.save(itemCompra);	
			else
				itemCompraSalvo =(ItemCompra) itemCompraDao.update(itemCompra);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemCompraSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemCompra removerItemCompra(ItemCompra itemCompra) throws Exception {
		try {
			Compra compra = null;
			itemCompra = buscarPorId(itemCompra.getId());

			for(PedidoCompra pc : itemCompra.getListaPedidoCompra()){
				pc.setItemCompra(null);
				pc.setStatusString(AguardandoCompra.nome);
				pedidoCompraService.salvarPedidoCompra(pc);
			}

			compra = itemCompra.getCompra();
			itemCompra.setCompra(null);
			itemCompra.setListaPedidoCompra(null);
			salvarItemCompra(itemCompra);

			itemCompraDao.flush();

			itemCompraDao.remover(itemCompra);

			compra = compraService.buscarPorId(compra.getId());

			//caso todo objeto item compra tenha sido removido: Deleta a compra
			if(compra.getListaItemCompra() == null || compra.getListaItemCompra().isEmpty()){
				compraService.deletarCompra(compra);
			}
			//caso todos os itens compra jah foram notificados: Alterar status da compra para 'Finalizado' (Finalizar compra)
			Boolean finalizarCompra = true;
			for(ItemCompra ic : compra.getListaItemCompra()){
				if(!ic.getStatus().equals("Notificado")){
					finalizarCompra = false;
					break;
				}
			}
			if(finalizarCompra){
				compra.setStatusString("Finalizada");
				compraService.salvarCompra(compra);
			}

			return itemCompra;
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemCompra notificarEstoque(ItemCompra itemCompra) throws Exception {
		Componente componenteComprado;
		try {
			componenteComprado = itemCompra.getComponente();

			itemCompra.setStatus("Notificado");
			for(PedidoCompra pc : itemCompra.getListaPedidoCompra()){
				if(pc.getOrdemServico() != null){
					observacaoService.log("Compra", "Componente enviado para o estoque", 2, new Date(), pc.getOrdemServico(), null);
				}
			}
			itemCompra.setDataEntrada(new Date());
			itemCompra.setComponenteNotificacao(componenteComprado);
			itemCompra =  salvarItemCompra(itemCompra);

			if(componenteComprado.getQtdComprada() != null){
				componenteComprado.setQtdComprada(componenteComprado.getQtdComprada() + itemCompra.getQtdComprada());
			}else{
				componenteComprado.setQtdComprada(itemCompra.getQtdComprada());
			}
			componenteComprado = componenteService.salvarComponente(componenteComprado);

			itemCompraDao.flush();

			Compra compra = itemCompra.getCompra();
			compra = compraService.buscarPorId(compra.getId());
			Boolean alterarStatusCompra = true;
			for(ItemCompra ic :compra.getListaItemCompra()){
				if(!ic.getStatus().equals("Notificado") && !ic.getStatus().equals("Componente não encontrado")){
					alterarStatusCompra = false;
					break;
				}
			}

			if(alterarStatusCompra){
				compra.setStatusString("Finalizada");
				compra = compraService.salvarCompra(compra);
				itemCompra.setCompra(compra);
			}

			itemCompra.setComponente(componenteComprado);

			return itemCompra;
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemCompra componenteNaoEncontrado(ItemCompra itemCompra) throws Exception {
		try {
			
			RequisicaoComponente requisicao = null;
			for(PedidoCompra pc :itemCompra.getListaPedidoCompra()){
				pc.setDataFinalizacao(new Date());
				pc.setStatusString("Componente não encontrado");
				pedidoCompraService.salvarPedidoCompra(pc);

				requisicao = pc.getRequisicao();
				
				if(requisicao != null){
					requisicao.setDataCancelamento(new Date());
					requisicao.setStatusString("Componente não encontrado");
					requisicaoComponenteService.salvarRequisicao(requisicao);
					
					if(requisicaoComponenteService.alterarStatusAguardandoComponente(requisicao)){
						if(requisicao.getReparo() != null){
							Reparo rep = requisicao.getReparo();
							rep.setComponentePendente(false);
							rep.setComponenteEmFalta(false);
							rep = reparoService.salvarReparo(rep);
							requisicao.setReparo(rep);
						}else{
							Orcamento orc = requisicao.getOrcamento();
							orc.setComponentePendente(false);
							orc.setComponenteEmFalta(false);
							orc = orcamentoService.salvarOrcamento(orc);
							requisicao.setOrcamento(orc);
						}
					}
					pc.setRequisicao(requisicao);
				}
			}

			itemCompra.setStatus("Componente não encontrado");
			itemCompra.setDataEntrada(new Date());
			itemCompra = salvarItemCompra(itemCompra);

			itemCompraDao.flush();

			Compra compra = itemCompra.getCompra();
			compra = compraService.buscarPorId(compra.getId());
			
			compra = compraService.finalizarCompra(compra);
			itemCompra.setCompra(compra);
			
			return itemCompra;
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
