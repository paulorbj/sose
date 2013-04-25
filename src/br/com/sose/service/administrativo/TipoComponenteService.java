package br.com.sose.service.administrativo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.TipoComponenteDao;
import br.com.sose.entity.admistrativo.TipoComponente;
import br.com.sose.exceptions.TipoComponenteExistenteException;
import br.com.sose.exceptions.TipoComponenteNaoExclusaoDependenciaExistenteException;

@Service(value="tipoComponenteService")
@RemotingDestination(value="tipoComponenteService")
public class TipoComponenteService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public TipoComponenteDao tipoComponenteDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<TipoComponente> listarTipoComponentes() throws Exception {
		List<TipoComponente> tipoComponentes;
		try {
			tipoComponentes =(List<TipoComponente>) tipoComponenteDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return tipoComponentes;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public TipoComponente salvarTipoComponente(TipoComponente tipoComponente) throws Exception {
		TipoComponente tipoComponenteSalvo;
		try {
			tipoComponenteSalvo = buscarPorNome(tipoComponente.getNome());
			if(tipoComponenteSalvo != null && !tipoComponenteSalvo.getId().equals(tipoComponente.getId())){
				throw new TipoComponenteExistenteException(tipoComponente.getNome()); 
			}
			
			if(tipoComponente.getId() == null || tipoComponente.getId().equals(new Long(0))){
				tipoComponenteSalvo =(TipoComponente) tipoComponenteDao.save(tipoComponente);	
			}else{
				tipoComponenteSalvo =(TipoComponente) tipoComponenteDao.update(tipoComponente);	
			}
		} catch (TipoComponenteExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar tipoComponente: " + tipoComponente.getNome());;
			throw e;
		}
		return tipoComponenteSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public TipoComponente buscarPorNome(String nome) throws Exception {
		try {
			return tipoComponenteDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há tipoComponente cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public TipoComponente excluirTipoComponente(TipoComponente tipoComponente) throws Exception {
		try {
			tipoComponenteDao.remover(tipoComponente);	
			logger.info("TipoComponente com o nome: "+tipoComponente.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new TipoComponenteNaoExclusaoDependenciaExistenteException(tipoComponente.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return tipoComponente;
	}
	
}
