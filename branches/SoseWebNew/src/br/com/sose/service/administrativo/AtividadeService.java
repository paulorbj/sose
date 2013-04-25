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

import br.com.sose.daoImpl.administrativo.AtividadeDao;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.exceptions.AtividadeExistenteException;
import br.com.sose.exceptions.AtividadeNaoExclusaoDependenciaExistenteException;

@Service(value="atividadeService")
@RemotingDestination(value="atividadeService")
public class AtividadeService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public AtividadeDao atividadeDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Atividade> listarAtividades() throws Exception {
		List<Atividade> atividades;
		try {
			atividades =(List<Atividade>) atividadeDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return atividades;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Atividade> listarAtividadesAtivas() throws Exception {
		List<Atividade> atividades;
		try {
			atividades =(List<Atividade>) atividadeDao.findAllAtivoOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return atividades;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Atividade salvarAtividade(Atividade atividade) throws Exception {
		Atividade atividadeSalva;
		try {
			atividadeSalva = buscarPorNome(atividade.getNome());
			if(atividadeSalva != null && !atividadeSalva.getId().equals(atividade.getId())){
				throw new AtividadeExistenteException(atividade.getNome()); 
			}
			if(atividade.getId() == null || atividade.getId().equals(new Long(0))){
				atividadeSalva =(Atividade) atividadeDao.save(atividade);	

			}else{
				atividadeSalva =(Atividade) atividadeDao.update(atividade);	
			}
		} catch (AtividadeExistenteException e) {
			logger.error(e);
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar atividade: " + atividade.getNome());;
			throw e;
		}
		return atividadeSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Atividade buscarPorNome(String nome) throws Exception {
		try {
			return atividadeDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há atividade cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Atividade excluirAtividade(Atividade atividade) throws Exception {
		try {
			atividadeDao.remover(atividade);	
			logger.info("Atividade com o nome: "+atividade.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new AtividadeNaoExclusaoDependenciaExistenteException(atividade.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return atividade;
	}
	
}
