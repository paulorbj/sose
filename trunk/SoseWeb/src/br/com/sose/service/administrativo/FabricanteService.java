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

import br.com.sose.daoImpl.administrativo.FabricanteDao;
import br.com.sose.entity.admistrativo.Fabricante;
import br.com.sose.exceptions.FabricanteExistenteException;
import br.com.sose.exceptions.FabricanteNaoExclusaoDependenciaExistenteException;

@Service(value="fabricanteService")
@RemotingDestination(value="fabricanteService")
public class FabricanteService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public FabricanteDao fabricanteDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Fabricante> listarFabricantes() throws Exception {
		List<Fabricante> fabricantes;
		try {
			fabricantes =(List<Fabricante>) fabricanteDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return fabricantes;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Fabricante salvarFabricante(Fabricante fabricante) throws Exception {
		Fabricante fabricanteSalva;
		try {
			fabricanteSalva = buscarPorNome(fabricante.getNome());
			if(fabricanteSalva != null && !fabricanteSalva.getId().equals(fabricante.getId())){
				throw new FabricanteExistenteException(fabricante.getNome()); 
			}
			if(fabricante.getId() == null || fabricante.getId().equals(new Long(0))){
				fabricanteSalva =(Fabricante) fabricanteDao.save(fabricante);	
			}else{
				fabricanteSalva =(Fabricante) fabricanteDao.update(fabricante);	
			}
		} catch (FabricanteExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar fabricante: " + fabricante.getNome());;
			throw e;
		}
		return fabricanteSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Fabricante buscarPorNome(String nome) throws Exception {
		try {
			return fabricanteDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há fabricante cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Fabricante excluirFabricante(Fabricante fabricante) throws Exception {
		try {
			fabricanteDao.remover(fabricante);	
			logger.info("Fabricante com o nome: "+fabricante.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new FabricanteNaoExclusaoDependenciaExistenteException(fabricante.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return fabricante;
	}
	
}
