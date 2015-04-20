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

import br.com.sose.daoImpl.administrativo.PerfilDao;
import br.com.sose.entity.admistrativo.Perfil;
import br.com.sose.exceptions.PerfilExistenteException;
import br.com.sose.exceptions.PerfilNaoExclusaoDependenciaExistenteException;

@Service(value="perfilService")
@RemotingDestination(value="perfilService")
public class PerfilService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public PerfilDao perfilDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Perfil> listarPerfis() throws Exception {
		List<Perfil> perfis;
		try {
			perfis =(List<Perfil>) perfilDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return perfis;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Perfil> listarPerfisAtivas() throws Exception {
		List<Perfil> perfis;
		try {
			perfis =(List<Perfil>) perfilDao.findAllAtivoOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return perfis;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Perfil salvarPerfil(Perfil perfil) throws Exception {
		Perfil perfilSalva;
		try {
			perfilSalva = buscarPorNome(perfil.getNome());
			if(perfilSalva != null && !perfilSalva.getId().equals(perfil.getId())){
				throw new PerfilExistenteException(perfil.getNome()); 
			}
			
			if(perfil.getId() == null || perfil.getId().equals(new Long(0))){
				perfilSalva =(Perfil) perfilDao.save(perfil);	
			}else{
				perfilSalva =(Perfil) perfilDao.update(perfil);	
			}
		} catch (PerfilExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar perfil: " + perfil.getNome());;
			throw e;
		}
		return perfilSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Perfil buscarPorNome(String nome) {
		try {
			Perfil p = perfilDao.buscarPorNome(nome);
			return p;
		} catch (Exception e) {
			logger.info("Não há perfil cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Perfil excluirPerfil(Perfil perfil) throws Exception {
		try {
			perfilDao.remover(perfil);	
			logger.info("Perfil com o nome: "+perfil.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new PerfilNaoExclusaoDependenciaExistenteException(perfil.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return perfil;
	}
	
}
