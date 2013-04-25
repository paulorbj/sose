package br.com.sose.service.administrativo.parceiros;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.parceiros.ContatoDao;
import br.com.sose.entity.admistrativo.parceiros.Contato;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.exceptions.ContatoNaoExclusaoDependenciaExistenteException;

@Service(value="contatoService")
@RemotingDestination(value="contatoService")
public class ContatoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public ContatoDao contatoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Contato> listarContatos() throws Exception {
		List<Contato> contatos;
		try {
			contatos =(List<Contato>) contatoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return contatos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Contato> listarPorCliente(Pessoa pessoa) throws Exception {
		List<Contato> contatos;
		try {
			contatos = contatoDao.listarPorCliente(pessoa);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return contatos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Contato salvarContato(Contato contato) throws Exception {
		Contato contatoSalvo;
		try {
			if(contato.getId() == null || contato.getId().equals(new Long(0))){
				contatoSalvo =(Contato) contatoDao.save(contato);	
			}else{
				contatoSalvo =(Contato) contatoDao.update(contato);	
			}
		} catch (Exception e) {
			logger.error("Erro ao salvar contato: " + contato.getNome());;
			throw e;
		}
		return contatoSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Contato buscarPorNome(String nome) throws Exception {
		try {
			return contatoDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há contato cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Contato excluirContato(Contato contato) throws Exception {
		try {
			contatoDao.remover(contato);	
			logger.info("Contato com o nome: "+contato.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new ContatoNaoExclusaoDependenciaExistenteException(contato.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return contato;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Boolean excluirContatoByPessoa(Pessoa pessoa) throws Exception {
		try {
			contatoDao.removerByPessoa(pessoa);	
			logger.info("Contatos do cliente: "+pessoa.getNomeSistema()+" foram removidos do sistema");
			return true;
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new ContatoNaoExclusaoDependenciaExistenteException(pessoa.getNomeSistema());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

}
