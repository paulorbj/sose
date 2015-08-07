package br.com.sose.service.componentes;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.componentes.AvisoDao;
import br.com.sose.entity.componentes.Aviso;

@Service(value="avisoService")
@RemotingDestination(value="avisoService")
public class AvisoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AvisoDao avisoDao;
	
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Aviso> listarAvisos() throws Exception {
		List<Aviso> avisos = null;
		try {
			avisos = avisoDao.listarAvisos();
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return avisos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Aviso buscarPorId(Long id) throws Exception {
		try {
			return avisoDao.findById(id);
		} catch (Exception e) {
			logger.info("Erro");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aviso> listarAvisosVisiveis() throws Exception {
		List<Aviso> avisos = null;
		try {
			avisos = avisoDao.listarAvisosVisiveis();
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return avisos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aviso> listarAvisosNaoVisiveis() throws Exception {
		List<Aviso> avisos = null;
		try {
			avisos = avisoDao.listarAvisosNaoVisiveis();
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return avisos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Aviso salvarAviso(Aviso aviso) throws Exception {
		Aviso avisoSalvo;
		try {
			if(aviso.getId() == null || aviso.getId().equals(new Long(0))){
				avisoSalvo =(Aviso) avisoDao.save(aviso);	
			}else{
				avisoSalvo =(Aviso) avisoDao.update(aviso);	
			}
		} catch (Exception e) {
			logger.error("Erro ao salvar aviso: " + aviso.getId());
			throw e;
		}
		return avisoSalvo;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Aviso excluirAviso(Aviso aviso) throws Exception {
		try {
			avisoDao.remover(aviso);	
			logger.info("Aviso "+aviso.getId()+" foi removido do sistema");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return aviso;
	}
	
}
