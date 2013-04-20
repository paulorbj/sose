package br.com.sose.service.recebimento;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.recebimento.ItemNotaFiscalDao;
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;

@Service(value="itemNotaFiscalService")
@RemotingDestination(value="itemNotaFiscalService")
public class ItemNotaFiscalService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ItemNotaFiscalDao itemNotaFiscalDao;

	/********************** Metodos de listagem *********************/
	@RemotingInclude
	@Transactional(readOnly=true)
	public List<ItemNotaFiscal> listarItemNotaFiscal() throws Exception {
		List<ItemNotaFiscal> itensNotaFiscal;
		try {
			itensNotaFiscal =(List<ItemNotaFiscal>) itemNotaFiscalDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensNotaFiscal;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<ItemNotaFiscal> listarPorOrdemServico(OrdemServico o) throws Exception {
		List<ItemNotaFiscal> itensNotaFiscal;
		try {
			itensNotaFiscal =(List<ItemNotaFiscal>) itemNotaFiscalDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensNotaFiscal;
	}
	
	

	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemNotaFiscal> buscarPorNotaFiscal(NotaFiscal notaFiscal) throws Exception {
		List<ItemNotaFiscal> itemNotaFiscalSalva;
		try {
			itemNotaFiscalSalva = itemNotaFiscalDao.buscarPorNotaFiscal(notaFiscal);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemNotaFiscalSalva;
	}

	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemNotaFiscal salvarItemNotaFiscal(ItemNotaFiscal itemNotaFiscal) throws Exception {
		ItemNotaFiscal itemNotaFiscalSalva;
		try {
			if(itemNotaFiscal.getId() == null || itemNotaFiscal.getId().equals(new Long(0)))
				itemNotaFiscalSalva =(ItemNotaFiscal) itemNotaFiscalDao.save(itemNotaFiscal);	
			else
				itemNotaFiscalSalva =(ItemNotaFiscal) itemNotaFiscalDao.update(itemNotaFiscal);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemNotaFiscalSalva;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemNotaFiscal adicionarItemNotaFiscal(ItemNotaFiscal itemNotaFiscal) throws Exception {
		ItemNotaFiscal itemNotaFiscalSalva;
		try {
			if(itemNotaFiscal.getId() == null || itemNotaFiscal.getId().equals(new Long(0)))
				itemNotaFiscalSalva =(ItemNotaFiscal) itemNotaFiscalDao.save(itemNotaFiscal);	
			else
				itemNotaFiscalSalva =(ItemNotaFiscal) itemNotaFiscalDao.update(itemNotaFiscal);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemNotaFiscalSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemNotaFiscal excluirItemNotaFiscal(ItemNotaFiscal itemNotaFiscal) throws Exception {
		ItemNotaFiscal itemRemovido = null;
		try {
			itemNotaFiscalDao.delete(itemNotaFiscal);
			if(itemRemovido != null)
				return 	itemNotaFiscal;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
