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

import br.com.sose.daoImpl.administrativo.LpuDao;
import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.exceptions.LpuExistenteException;
import br.com.sose.exceptions.LpuNaoExclusaoDependenciaExistenteException;

@Service(value="lpuService")
@RemotingDestination(value="lpuService")
public class LpuService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public LpuDao lpuDao;

	/********************** Metodos de listagem *********************/
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarLpu() throws Exception {
		List<Lpu> lpus;
		try {
			lpus =(List<Lpu>) lpuDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorUnidade(Unidade unidade) throws Exception {
		List<Lpu> lpus = null;
		try {
			
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorCliente(Pessoa pessoa) throws Exception {
		List<Lpu> lpus;
		try {
			lpus = lpuDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Lpu> listarPorClienteCombo(Pessoa pessoa) throws Exception {
		List<Lpu> lpus;
		try {
			lpus = lpuDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return lpus;
	}
	
	/********************** Metodos de listagem *********************/
	
	/*********************** Metodos de busca ***********************/
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarPorNome(String nome) throws Exception {
		try {
			return lpuDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Lpu buscarPorNomeECliente(String nome, Pessoa cliente) throws Exception {
		try {
			return lpuDao.buscarPorNomeECliente(nome,cliente);
		} catch (Exception e) {
			logger.info("Não há lpu cadastrada com o nome: "+nome+" no sistema");
		}
		return null;
	}

	
	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu salvarLpu(Lpu lpu) throws Exception {
		Lpu lpuSalva;
		try {
			lpuSalva = buscarPorNomeECliente(lpu.getUnidade(), lpu.getCliente());
			if(lpuSalva != null && !lpuSalva.getId().equals(lpu.getId())){
				throw new LpuExistenteException(lpu.getUnidade()); 
			}

			if(lpu.getId() == null || lpu.getId().equals(new Long(0))){
				lpuSalva =(Lpu) lpuDao.save(lpu);	
			}else{
				lpuSalva =(Lpu) lpuDao.update(lpu);	
			}
			
			lpuDao.atualizarValor(lpu);
		} catch (LpuExistenteException e) {
			logger.error(e);
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar lpu: " + lpu.getUnidade());;
			throw e;
		}
		return lpuSalva;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Lpu excluirLpu(Lpu lpu) throws Exception {
		try {
			lpuDao.remover(lpu);	
			logger.info("Lpu com o nome: "+lpu.getUnidade()+" foi removida do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new LpuNaoExclusaoDependenciaExistenteException(lpu.getUnidade());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return lpu;
	}

}
