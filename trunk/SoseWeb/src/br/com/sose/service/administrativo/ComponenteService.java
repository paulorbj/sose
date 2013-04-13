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

import br.com.sose.daoImpl.administrativo.ComponenteDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.exceptions.ComponenteExistenteException;
import br.com.sose.exceptions.ComponenteNaoExclusaoDependenciaExistenteException;

@Service(value="componenteService")
@RemotingDestination(value="componenteService")
public class ComponenteService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public ComponenteDao componenteDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Componente> listarComponentes() throws Exception {
		List<Componente> componentes;
		try {
			componentes =(List<Componente>) componenteDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return componentes;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Componente salvarComponente(Componente componente) throws Exception {
		Componente componenteSalvo;
		try {
			componenteSalvo = buscarPorNome(componente.getNome());
			if(componenteSalvo != null && !componenteSalvo.getId().equals(componente.getId())){
				throw new ComponenteExistenteException(componente.getNome()); 
			}
			if(componente.getId() == null || componente.getId().equals(new Long(0))){
				componenteSalvo =(Componente) componenteDao.save(componente);	
			}else{
				componenteSalvo =(Componente) componenteDao.update(componente);	
			}
		} catch (ComponenteExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar componente: " + componente.getNome());;
			throw e;
		}
		return componenteSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Componente buscarPorNome(String nome) throws Exception {
		try {
			return componenteDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há componente cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Componente buscarPorId(Long id) throws Exception {
		try {
			return componenteDao.buscarPorId(id);
		} catch (Exception e) {
			logger.info("Não há componente cadastrado com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Componente buscarCompletoPorId(Long id) throws Exception {
		try {
			return componenteDao.buscarCompletoPorId(id);
		} catch (Exception e) {
			logger.info("Não há componente cadastrado com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Componente excluirComponente(Componente componente) throws Exception {
		try {
			componenteDao.remover(componente);	
			logger.info("Componente com o nome: "+componente.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new ComponenteNaoExclusaoDependenciaExistenteException(componente.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return componente;
	}

}
