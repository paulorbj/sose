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

import br.com.sose.daoImpl.administrativo.LaboratorioDao;
import br.com.sose.entity.admistrativo.Laboratorio;
import br.com.sose.exceptions.LaboratorioExistenteException;
import br.com.sose.exceptions.LaboratorioNaoExclusaoDependenciaExistenteException;

@Service(value="laboratorioService")
@RemotingDestination(value="laboratorioService")
public class LaboratorioService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public LaboratorioDao laboratorioDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Laboratorio> listarLaboratorios() throws Exception {
		List<Laboratorio> laboratorios;
		try {
			laboratorios =(List<Laboratorio>) laboratorioDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return laboratorios;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Laboratorio salvarLaboratorio(Laboratorio laboratorio) throws Exception {
		Laboratorio laboratorioSalvo;
//		Set<Usuario> membrosSelecionados = laboratorio.getTecnicos();
//		laboratorio
		try {
			laboratorioSalvo = buscarPorNome(laboratorio.getNome());
			if(laboratorioSalvo != null && !laboratorioSalvo.getId().equals(laboratorio.getId())){
				throw new LaboratorioExistenteException(laboratorio.getNome()); 
			}
			if(laboratorio.getId() == null || laboratorio.getId().equals(new Long(0))){
				laboratorioSalvo =(Laboratorio) laboratorioDao.save(laboratorio);	
			}else{
				laboratorioSalvo =(Laboratorio) laboratorioDao.update(laboratorio);	
			}
		} catch (LaboratorioExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar laboratório: " + laboratorio.getNome());;
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return laboratorioSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Laboratorio buscarPorNome(String nome) throws Exception {
		try {
			return laboratorioDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há laboratório cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Laboratorio excluirLaboratorio(Laboratorio laboratorio) throws Exception {
		try {
			laboratorioDao.remover(laboratorio);	
			logger.info("Laboratório com o nome: "+laboratorio.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new LaboratorioNaoExclusaoDependenciaExistenteException(laboratorio.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return laboratorio;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Laboratorio buscarPorId(Long id) throws Exception {
		Laboratorio laboratorioSalvo;
		try {
			laboratorioSalvo = laboratorioDao.buscarPorId(id);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return laboratorioSalvo;
	}

}
