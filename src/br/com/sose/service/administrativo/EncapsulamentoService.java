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

import br.com.sose.daoImpl.administrativo.EncapsulamentoDao;
import br.com.sose.entity.admistrativo.Encapsulamento;
import br.com.sose.exceptions.EncapsulamentoExistenteException;
import br.com.sose.exceptions.EncapsulamentoNaoExclusaoDependenciaExistenteException;

@Service(value="encapsulamentoService")
@RemotingDestination(value="encapsulamentoService")
public class EncapsulamentoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public EncapsulamentoDao encapsulamentoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Encapsulamento> listarEncapsulamentos() throws Exception {
		List<Encapsulamento> encapsulamentos;
		try {
			encapsulamentos =(List<Encapsulamento>) encapsulamentoDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return encapsulamentos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Encapsulamento salvarEncapsulamento(Encapsulamento encapsulamento) throws Exception {
		Encapsulamento encapsulamentoSalvo;
		try {
			encapsulamentoSalvo = buscarPorNome(encapsulamento.getNome());
			if(encapsulamentoSalvo != null && !encapsulamentoSalvo.getId().equals(encapsulamento.getId())){
				throw new EncapsulamentoExistenteException(encapsulamento.getNome()); 
			}
			if(encapsulamento.getId() == null || encapsulamento.getId().equals(new Long(0))){
				encapsulamentoSalvo =(Encapsulamento) encapsulamentoDao.save(encapsulamento);	
			}else{
				encapsulamentoSalvo =(Encapsulamento) encapsulamentoDao.update(encapsulamento);	
			}
		} catch (EncapsulamentoExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar encapsulamento: " + encapsulamento.getNome());;
			throw e;
		}
		return encapsulamentoSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Encapsulamento buscarPorNome(String nome) throws Exception {
		try {
			return encapsulamentoDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há encapsulamento cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Encapsulamento excluirEncapsulamento(Encapsulamento encapsulamento) throws Exception {
		try {
			encapsulamentoDao.remover(encapsulamento);	
			logger.info("Encapsulamento com o nome: "+encapsulamento.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new EncapsulamentoNaoExclusaoDependenciaExistenteException(encapsulamento.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return encapsulamento;
	}
	
}
