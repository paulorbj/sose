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

import br.com.sose.daoImpl.administrativo.UnidadeDao;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.exceptions.UnidadeExistenteException;
import br.com.sose.exceptions.UnidadeNaoExclusaoDependenciaExistenteException;

@Service(value="unidadeService")
@RemotingDestination(value="unidadeService")
public class UnidadeService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public UnidadeDao unidadeDao;

	/********************** Metodos de listagem *********************/
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Unidade> listarUnidades() throws Exception {
		List<Unidade> unidades;
		try {
			unidades =(List<Unidade>) unidadeDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return unidades;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Unidade> listarUnidadesCombo() throws Exception {
		List<Unidade> unidades;
		try {
			unidades =(List<Unidade>) unidadeDao.findAllOrderByNomeCombo();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return unidades;
	}
	
	/********************** Metodos de listagem *********************/
	
	/*********************** Metodos de busca ***********************/
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Unidade buscarPorNome(String nome) throws Exception {
		try {
			return unidadeDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há unidade cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Unidade buscarCompletoPorId(Long id) throws Exception {
		try {
			return unidadeDao.buscarPorIdCompleto(id);
		} catch (Exception e) {
			logger.info("Não há unidade cadastrado com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Unidade buscarPorNomeCodigo(String nome,String codigo) throws Exception {
		try {
			return unidadeDao.buscarPorNomeCodigo(nome,codigo);
		} catch (Exception e) {
			logger.info("Não há unidade cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Unidade salvarUnidade(Unidade unidade) throws Exception {
		Unidade unidadeSalva;
		try {
			unidadeSalva = buscarPorNomeCodigo(unidade.getNome(), unidade.getCodigo());
			if(unidadeSalva != null && !unidadeSalva.getId().equals(unidade.getId())){
				throw new UnidadeExistenteException(unidade.getNome()); 
			}
			
			if(unidade.getId() == null || unidade.getId().equals(new Long(0))){
				unidadeSalva =(Unidade) unidadeDao.save(unidade);	
			}else{
				unidadeSalva =(Unidade) unidadeDao.update(unidade);	
			}
		} catch (UnidadeExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar unidade: " + unidade.getNome());;
			throw e;
		}
		return unidadeSalva;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Unidade excluirUnidade(Unidade unidade) throws Exception {
		try {
			unidadeDao.remover(unidade);	
			logger.info("Unidade com o nome: "+unidade.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new UnidadeNaoExclusaoDependenciaExistenteException(unidade.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return unidade;
	}

}
