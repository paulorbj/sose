package br.com.sose.service.compra;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.compra.CompraDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.ItemCompra;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.status.compra.AguardandoCompra;
import br.com.sose.status.compra.ProcessandoPreCompra;

@Service(value="compraService")
@RemotingDestination(value="compraService")
public class CompraService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public CompraDao compraDao;

	@Autowired
	public PedidoCompraService pedidoCompraService;

	@Autowired
	public ItemCompraService itemCompraService;
	
	@Autowired
	private ObservacaoService observacaoService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Compra> listarPreCompras() throws Exception {
		List<Compra> compras;
		try {
			compras =(List<Compra>) compraDao.listarPreCompras();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compras;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public Compra buscarPorId(Long id) throws Exception {
		Compra compraEncontrada;
		try {
			compraEncontrada =(Compra) compraDao.buscarPorId(id);
			if(compraEncontrada != null){
				List<ItemCompra> listaItemCompra = itemCompraService.buscarPorCompra(compraEncontrada);
				if(compraEncontrada.getListaItemCompra() == null){
					compraEncontrada.setListaItemCompra(new HashSet<ItemCompra>());
				}
				compraEncontrada.getListaItemCompra().addAll(listaItemCompra);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compraEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Compra> listaCompras() throws Exception {
		List<Compra> compras = new ArrayList<Compra>();
		List<Compra> comprasAux;
		try {

			comprasAux = compraDao.listarCompra("Aberta");
			if(comprasAux != null && !comprasAux.isEmpty()){
				for(Compra c : comprasAux){
					c = finalizarCompra(c);
					if(c.getStatusString().equals("Aberta")){
						compras.add(c);
					}
				}
			}
			
			comprasAux = compraDao.listarCompra("Finalizada");
			if(comprasAux != null && !comprasAux.isEmpty()){
				compras.addAll(comprasAux);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compras;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra salvarCompra(Compra compra) throws Exception {
		Compra compraSalvo;
		try {
			if(compra.getId() == null || compra.getId().equals(new Long(0)))
				compraSalvo =(Compra) compraDao.save(compra);	
			else
				compraSalvo =(Compra) compraDao.update(compra);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compraSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra criarCompra(Compra compra) throws Exception {
		Compra compraSalvo;
		try {
			compra.setStatusString(AguardandoCompra.nome); // nao sei se eh necessario
			compraSalvo =(Compra) salvarCompra(compra);	

			for(ItemCompra ic : compra.getListaItemCompra()){
				for(PedidoCompra pc : ic.getListaPedidoCompra()){
					if(pc.getOrdemServico() != null){
						observacaoService.log("Compra", "Componente em compra", 2, new Date(), pc.getOrdemServico(), null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compraSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra adicionarPedidoCompra(Compra compra, List<PedidoCompra> pedidosCompra) throws Exception {
		try {	
			Hashtable<Long, ItemCompra> mapItemCompra = new Hashtable<Long, ItemCompra>();
			Hashtable<Long, HashSet<String>> mapTecnicos = new Hashtable<Long, HashSet<String>>();

			for(ItemCompra ic : compra.getListaItemCompra()){
				if(!mapItemCompra.containsKey(ic.getComponente().getId())){
					mapItemCompra.put(ic.getComponente().getId(), ic);
				}

				mapTecnicos.put(ic.getId(), new HashSet<String>());
			}

			compra.setListaItemCompra(new HashSet<ItemCompra>());

			ItemCompra novoItemCompra = null;
			Componente componenteAux = null;
			Integer quantidadeAux = null;
			for(PedidoCompra pc : pedidosCompra){
				if(pc.getRequisicao() != null){
					componenteAux = pc.getRequisicao().getComponente();
					quantidadeAux = pc.getRequisicao().getQuantidade();
				}else{
					componenteAux = pc.getComponente();
					quantidadeAux = pc.getQuantidade();
				}

				if(!mapItemCompra.containsKey(componenteAux.getId())){
					novoItemCompra = new ItemCompra();
					novoItemCompra.setComponente(componenteAux);
					novoItemCompra.setCompra(compra);
					novoItemCompra.setQtdTotalRequisitada(quantidadeAux);
					novoItemCompra.setQtdPedido(1);

					if(pc.getPossuiAmostra()){
						novoItemCompra.setPossuiAmostra(true);
					}

					novoItemCompra.setStatus("Pendente");

					//Salvar item compra
					novoItemCompra = itemCompraService.salvarItemCompra(novoItemCompra);					
					mapItemCompra.put(componenteAux.getId(), novoItemCompra);
					mapTecnicos.put(novoItemCompra.getId(), new HashSet<String>());
					if(pc.getTecnico() != null){
						mapTecnicos.get(novoItemCompra.getId()).add(pc.getTecnico().getUsuario());
					}


					//Associar o pedido de compra ao item de compra
					pc.setItemCompra(novoItemCompra);
					pc.setStatusString(ProcessandoPreCompra.nome);
					pc = pedidoCompraService.salvarPedidoCompra(pc);

				}else{
					novoItemCompra = mapItemCompra.get(componenteAux.getId());
					novoItemCompra.setQtdTotalRequisitada(novoItemCompra.getQtdTotalRequisitada() + pc.getQuantidade());

					novoItemCompra.setQtdPedido(novoItemCompra.getQtdPedido()+1);

					if(pc.getPossuiAmostra()){
						novoItemCompra.setPossuiAmostra(true);
					}

					novoItemCompra = itemCompraService.salvarItemCompra(novoItemCompra);

					mapItemCompra.put(componenteAux.getId(), novoItemCompra);
					if(pc.getTecnico() != null){
						mapTecnicos.get(novoItemCompra.getId()).add(pc.getTecnico().getUsuario());
					}
					pc.setItemCompra(novoItemCompra);
					pc.setStatusString(ProcessandoPreCompra.nome);
					pc = pedidoCompraService.salvarPedidoCompra(pc);

				}
			}

			HashSet<ItemCompra> itensCompra = new HashSet<ItemCompra>();
			/*
			for(ItemCompra ic : mapItemCompra.values()){
				ic.setTecnicos(criarStringTecnicos(mapTecnicos.get(ic.getId())));
				itemCompraService.salvarItemCompra(ic);
				itensCompra.add(ic);
			}
			 */

			compra = buscarPorId(compra.getId());

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compra;
	}

	private String criarStringTecnicos(HashSet<String> nomes){
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for(String nome:nomes){
			sb.append(nome);
			if(nomes.size() > 1 && i < nomes.size()){
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra deletarCompra(Compra compra) throws Exception {
		try {
			compra = buscarPorId(compra.getId());
			for(ItemCompra ic : compra.getListaItemCompra()){
				itemCompraService.removerItemCompra(ic);
			}

			compraDao.delete(compra);

			return compra;
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra excluirPedidoCompra(Compra compra,PedidoCompra pedidoCompra) throws Exception {

		try {	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compra;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra editarCompra(Compra compra) throws Exception {
		Compra compraSalva = null;

		try {

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compraSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra gerarCompra(Compra compra) throws Exception {
		Compra compraSalva = null;
		compra.setStatusString("Aberta");
		compra.setDataCriacaoCompra(new Date());
		compraSalva = salvarCompra(compra);

		try {

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return compraSalva;
	}

	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Compra finalizarCompra(Compra compra) throws Exception {
		Boolean finalizarCompra = true;
		List<ItemCompra> itensCompra = itemCompraService.buscarPorCompra(compra);
		for(ItemCompra ic : itensCompra){
			if(!ic.getStatus().equals("Finalizado") && !ic.getStatus().equals("Componente não encontrado")){
				finalizarCompra = false;
				break;
			}
		}

		if(finalizarCompra){
			compra.setStatusString("Finalizada");
			compra = salvarCompra(compra);
		}
		
		return compra;
	}
	
}
