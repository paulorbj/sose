package br.com.sose.service.compra;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.compra.CotacaoDao;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.Cotacao;
import br.com.sose.exceptions.AtividadeNaoExclusaoDependenciaExistenteException;

@Service(value="cotacaoService")
@RemotingDestination(value="cotacaoService")
public class CotacaoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CotacaoDao cotacaoDao;
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Cotacao> listarCotacaoPorComponente(Componente componente) throws Exception {
		List<Cotacao> listaRetorno;
		try {
			listaRetorno =(List<Cotacao>) cotacaoDao.listarCotacaoPorComponente(componente);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaRetorno;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Cotacao salvarCotacao(Cotacao cotacao) throws Exception {
		Cotacao cotacaoSalvo;
		try {
			if(cotacao.getId() == null || cotacao.getId().equals(new Long(0)))
				cotacaoSalvo =(Cotacao) cotacaoDao.save(cotacao);	
			else
				cotacaoSalvo =(Cotacao) cotacaoDao.update(cotacao);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return cotacaoSalvo;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Cotacao excluirCotacao(Cotacao cotacao) throws Exception {
		try {
			cotacaoDao.remover(cotacao);	
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return cotacao;
	}

}
