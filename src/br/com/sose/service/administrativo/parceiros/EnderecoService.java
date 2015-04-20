package br.com.sose.service.administrativo.parceiros;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.parceiros.EnderecoDao;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.exceptions.EnderecoNaoExclusaoDependenciaExistenteException;

@Service(value="enderecoService")
@RemotingDestination(value="enderecoService")
public class EnderecoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public EnderecoDao enderecoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Endereco> listarEnderecos() throws Exception {
		List<Endereco> enderecos;
		try {
			enderecos =(List<Endereco>) enderecoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return enderecos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Endereco salvarEndereco(Endereco endereco) throws Exception {
		Endereco enderecoSalvo;
		try {
			if(endereco.getId() == null || endereco.getId().equals(new Long(0))){
				enderecoSalvo =(Endereco) enderecoDao.save(endereco);		
			}else{
				enderecoSalvo =(Endereco) enderecoDao.update(endereco);	
			}
			
		} catch (Exception e) {
			logger.error("Erro ao salvar endereco: " + endereco.getLogradouro());;
			throw e;
		}
		return enderecoSalvo;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirEndereco(Endereco endereco) throws Exception {
		try {
			enderecoDao.remover(endereco);	
			logger.info("Endereço: "+endereco.getLogradouro()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new EnderecoNaoExclusaoDependenciaExistenteException(endereco.getId());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Boolean excluirEnderecoByPessoa(Pessoa pessoa) throws Exception {
		try {
			enderecoDao.removerByPessoa(pessoa);	
			return true;
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new EnderecoNaoExclusaoDependenciaExistenteException(pessoa.getId());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}


}
