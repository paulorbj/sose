package br.com.sose.service.administrativo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.ArquivoUploadDao;
import br.com.sose.daoImpl.administrativo.ItemLpuDao;
import br.com.sose.daoImpl.administrativo.UnidadeDao;
import br.com.sose.entity.admistrativo.ItemLpu;
import br.com.sose.entity.admistrativo.Unidade;

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
						itemLpu.setUnidadeServilogi(unidadeEncontrada.get(0));
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

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemLpu associarItemLpu(ItemLpu itemLpu, Unidade unidade) throws Exception {
		try {
			itemLpu.setAssociacaoRealizada(true);
			itemLpu.setUnidadeServilogi(unidade);
			itemLpu = salvarItemLpu(itemLpu);
			return itemLpu;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
