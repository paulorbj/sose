package br.com.sose.service.lpu;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.ArquivoUploadDao;
import br.com.sose.daoImpl.administrativo.UnidadeDao;
import br.com.sose.daoImpl.lpu.ItemLpuDao;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.lpu.ItemLpu;
import br.com.sose.entity.lpu.Lpu;
import br.com.sose.entity.lpu.UnidadeItemLpu;

@Service(value="itemLpuService")
@RemotingDestination(value="itemLpuService")
public class ItemLpuService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ItemLpuDao itemLpuDao;

	@Autowired
	public ArquivoUploadDao arquivoUploadDao;

	@Autowired
	public UnidadeDao unidadeDao;
	
	@Autowired
	public UnidadeItemLpuService unidadeItemLpuService;

	/********************** Metodos de listagem *********************/

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemLpu> listarItemLpu() throws Exception {
		List<ItemLpu> itensLpu;
		try {
			itensLpu =(List<ItemLpu>) itemLpuDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensLpu;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemLpu> listarItemLpuPorLpu(Lpu lpu) throws Exception {
		List<ItemLpu> itensLpu;
		try {
			itensLpu =(List<ItemLpu>) itemLpuDao.buscarPorLpu(lpu);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensLpu;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemLpu> listarItemLpuPorLpuPorUnidade(Lpu lpu,Unidade unidade) throws Exception {
		List<ItemLpu> itensLpu;
		try {
			itensLpu =(List<ItemLpu>) itemLpuDao.listarItemLpuPorLpuPorUnidade(lpu,unidade);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensLpu;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemLpu> listarPorCliente(Pessoa pessoa) throws Exception {
		List<ItemLpu> itenslpu;
		try {
			itenslpu = itemLpuDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itenslpu;
	}

	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/


	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemLpu salvarItemLpu(ItemLpu itemLpu) throws Exception {
		ItemLpu itemLpuSalva;
		try {
			if(itemLpu.getId() == null || itemLpu.getId().equals(new Long(0))){
				itemLpuSalva =(ItemLpu) itemLpuDao.save(itemLpu);	
			}else{
				itemLpuSalva =(ItemLpu) itemLpuDao.update(itemLpu);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return itemLpuSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemLpu> autoAssociarItensLpu(List<ItemLpu> listaItemLpu) throws Exception {
		List<Unidade> unidadeEncontrada = null;
		try {
			for(ItemLpu itemLpu :listaItemLpu){
				if(!itemLpu.getAssociacaoRealizada()){
					unidadeEncontrada = unidadeDao.listarPorNome(itemLpu.getUnidade());
					if(unidadeEncontrada != null && !unidadeEncontrada.isEmpty()){
						itemLpu.setAssociacaoRealizada(true);
						//itemLpu.setUnidadeServilogi(unidadeEncontrada.get(0));
					}else{
						itemLpu.setAssociacaoRealizada(false);
					}
					salvarItemLpu(itemLpu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaItemLpu;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private ItemLpu associarItemLpu(ItemLpu itemLpu, UnidadeItemLpu unidadeItemLpu) throws Exception {
		try {
			itemLpu.setAssociacaoRealizada(true);
			itemLpu.setUnidadeItemLpu(unidadeItemLpu);
			itemLpu = salvarItemLpu(itemLpu);
			return itemLpu;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemLpu> associarItensLpu(List<ItemLpu> itensLpu, Unidade unidade, BigDecimal valorReparo, BigDecimal valorMinimo, BigDecimal valorMaximo, BigDecimal valorTeste) throws Exception {
		try {
			Lpu lpu = itensLpu.get(0).getLpu();
			UnidadeItemLpu unidadeItemLpu = unidadeItemLpuService.buscarUnidadeItemLpu(unidade, lpu);
			if(unidadeItemLpu == null){
				unidadeItemLpu = new UnidadeItemLpu();
				unidadeItemLpu.setLpu(lpu);
				unidadeItemLpu.setUnidadeServilogi(unidade);
			}
			unidadeItemLpu.setValorReparo(valorReparo);
			unidadeItemLpu.setValorMinimo(valorMinimo);
			unidadeItemLpu.setValorMaximo(valorMaximo);
			unidadeItemLpu.setValorTeste(valorTeste);
			unidadeItemLpu = unidadeItemLpuService.salvarUnidadeItemLpu(unidadeItemLpu);
			
			for(ItemLpu il : itensLpu){
				il = associarItemLpu(il, unidadeItemLpu);
			}
			
			return itensLpu;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemLpu deletarItemLpu(ItemLpu itemLpu) throws Exception {
		try {
			itemLpuDao.remover(itemLpu);
			return itemLpu;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
