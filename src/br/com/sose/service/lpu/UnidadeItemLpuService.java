package br.com.sose.service.lpu;

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
import br.com.sose.daoImpl.lpu.UnidadeItemLpuDao;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.lpu.ItemLpu;
import br.com.sose.entity.lpu.Lpu;
import br.com.sose.entity.lpu.UnidadeItemLpu;

@Service(value="unidadeItemLpuService")
@RemotingDestination(value="unidadeItemLpuService")
public class UnidadeItemLpuService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public UnidadeItemLpuDao unidadeItemLpuDao;

	@Autowired
	public ArquivoUploadDao arquivoUploadDao;

	@Autowired
	public UnidadeDao unidadeDao;

	/********************** Metodos de listagem *********************/

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<UnidadeItemLpu> listarUnidadeItemLpu() throws Exception {
		List<UnidadeItemLpu> unidadesItemLpu;
		try {
			unidadesItemLpu = (List<UnidadeItemLpu>) unidadeItemLpuDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return unidadesItemLpu;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<UnidadeItemLpu> listarUnidadeItemLpuPorLpu(Lpu lpu) throws Exception {
		List<UnidadeItemLpu> unidadesItemLpu;
		try {
			unidadesItemLpu =(List<UnidadeItemLpu>) unidadeItemLpuDao.buscarPorLpu(lpu);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return unidadesItemLpu;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<UnidadeItemLpu> listarUnidadeItemLpuPorUnidadePorLpu(Unidade unidade, Lpu lpu) throws Exception {
		List<UnidadeItemLpu> unidadesItemLpu;
		try {
			unidadesItemLpu =(List<UnidadeItemLpu>) unidadeItemLpuDao.listarPorUnidade(unidade,lpu);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return unidadesItemLpu;
	}


	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/

	@RemotingInclude
	@Transactional(readOnly = true)
	public UnidadeItemLpu buscarUnidadeItemLpu(Unidade unidade, Lpu lpu) throws Exception {
		UnidadeItemLpu unidadeItemLpuSalva;
		try {
			unidadeItemLpuSalva = unidadeItemLpuDao.buscarUnidadeItemLpu(unidade, lpu);
		} catch (Exception e) {
			e.printStackTrace();
			unidadeItemLpuSalva = null;
		}
		return unidadeItemLpuSalva;
	}

	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public UnidadeItemLpu salvarUnidadeItemLpu(UnidadeItemLpu unidadeItemLpu) throws Exception {
		UnidadeItemLpu unidadeItemLpuSalva;
		try {
			if(unidadeItemLpu.getId() == null || unidadeItemLpu.getId().equals(new Long(0))){
				unidadeItemLpuSalva =(UnidadeItemLpu) unidadeItemLpuDao.save(unidadeItemLpu);	
			}else{
				unidadeItemLpuSalva =(UnidadeItemLpu) unidadeItemLpuDao.update(unidadeItemLpu);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return unidadeItemLpuSalva;
	}

}
