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

import br.com.sose.daoImpl.administrativo.DefeitoDao;
import br.com.sose.entity.admistrativo.Defeito;
import br.com.sose.exceptions.DefeitoExistenteException;
import br.com.sose.exceptions.DefeitoNaoExclusaoDependenciaExistenteException;

@Service(value="defeitoService")
@RemotingDestination(value="defeitoService")
public class DefeitoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public DefeitoDao defeitoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Defeito> listarDefeitos() throws Exception {
		List<Defeito> defeitos;
		try {
			defeitos =(List<Defeito>) defeitoDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return defeitos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Defeito salvarDefeito(Defeito defeito) throws Exception {
		Defeito defeitoSalvo;
		try {
			defeitoSalvo = buscarPorNome(defeito.getNome());
			if(defeitoSalvo != null && !defeitoSalvo.getId().equals(defeito.getId())){
				throw new DefeitoExistenteException(defeito.getNome()); 
			}
			if(defeito.getId() == null || defeito.getId().equals(new Long(0))){
				defeitoSalvo =(Defeito) defeitoDao.save(defeito);	
			}else{
				defeitoSalvo =(Defeito) defeitoDao.update(defeito);	
			}
		} catch (DefeitoExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar defeito: " + defeito.getNome());;
			throw e;
		}
		return defeitoSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Defeito buscarPorNome(String nome) throws Exception {
		try {
			return defeitoDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há defeito cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Defeito excluirDefeito(Defeito defeito) throws Exception {
		try {
			defeitoDao.remover(defeito);	
			logger.info("Defeito com o nome: "+defeito.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new DefeitoNaoExclusaoDependenciaExistenteException(defeito.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return defeito;
	}

}
