package br.com.sose.service.estoque;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.estoque.DevolucaoComponenteDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.DevolucaoComponente;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ComponenteService;

@Service(value="devolucaoComponenteService")
@RemotingDestination(value="devolucaoComponenteService")
public class DevolucaoComponenteService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public DevolucaoComponenteDao devolucaoComponenteDao;

	@Autowired
	public ComponenteService componenteService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarDevolucoes() throws Exception {
		List<DevolucaoComponente> devolucoesComponente;
		try {
			devolucoesComponente =(List<DevolucaoComponente>) devolucaoComponenteDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoesComponente;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarDevolucoesEstoque() throws Exception {
		List<DevolucaoComponente> devolucoesComponente = new ArrayList<DevolucaoComponente>();
		List<DevolucaoComponente> devolucoesComponenteAux = null;
		try {
			devolucoesComponenteAux = devolucaoComponenteDao.listarDevolucaoComponenteEstoque();
			if(devolucoesComponenteAux != null && !devolucoesComponenteAux.isEmpty()) devolucoesComponente.addAll(devolucoesComponenteAux);
			devolucoesComponenteAux = devolucaoComponenteDao.listarDevolucaoComponentePorCondicao("Perdido");
			if(devolucoesComponenteAux != null && !devolucoesComponenteAux.isEmpty()) devolucoesComponente.addAll(devolucoesComponenteAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoesComponente;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarDevolucoesAreaTecnica() throws Exception {
		List<DevolucaoComponente> devolucoesComponente = new ArrayList<DevolucaoComponente>();
		List<DevolucaoComponente> devolucoesComponenteAux = null;
		try {
			devolucoesComponenteAux = devolucaoComponenteDao.listarDevolucaoComponente();
			if(devolucoesComponenteAux != null && !devolucoesComponenteAux.isEmpty()) devolucoesComponente.addAll(devolucoesComponenteAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoesComponente;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarDevolucaoPorRequisicao(RequisicaoComponente requisicao) throws Exception {
		List<DevolucaoComponente> devolucoesComponente;
		try {
			devolucoesComponente =(List<DevolucaoComponente>) devolucaoComponenteDao.listarComponentesPosicaoPorRequisicao(requisicao);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoesComponente;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarComponentesPosicaoPorReparo(Reparo reparo) throws Exception {
		List<DevolucaoComponente> devolucoes;
		try {
			devolucoes =(List<DevolucaoComponente>) devolucaoComponenteDao.listarComponentesPosicaoPorReparo(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DevolucaoComponente> listarComponentesPosicaoPorOrcamento(Orcamento orcamento) throws Exception {
		List<DevolucaoComponente> devolucoes;
		try {
			devolucoes =(List<DevolucaoComponente>) devolucaoComponenteDao.listarComponentesPosicaoPorOrcamento(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return devolucoes;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DevolucaoComponente salvarDevolucaoComponente(DevolucaoComponente devolucaoComponente) throws Exception {
		DevolucaoComponente devolucaoComponenteSalvo;
		try {
			if(devolucaoComponente.getId() == null || devolucaoComponente.getId().equals(new Long(0)))
				devolucaoComponenteSalvo =(DevolucaoComponente) devolucaoComponenteDao.save(devolucaoComponente);
			else
				devolucaoComponenteSalvo =(DevolucaoComponente) devolucaoComponenteDao.update(devolucaoComponente);	

		}catch (Exception e) {
			throw e;
		}
		return devolucaoComponenteSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DevolucaoComponente receberDevolucaoComponente(DevolucaoComponente devolucaoComponente, Usuario recebidoPor) throws Exception {
		DevolucaoComponente devolucaoComponenteSalvo;
		try {
			devolucaoComponente.setRecebidoEm(new Date());
			devolucaoComponente.setRecebidoPor(recebidoPor);

			if(devolucaoComponente.getCondicao().equals("Devolvido")){
				//Possui saldo suficiente no estoque
				Componente componente = componenteService.buscarPorId(devolucaoComponente.getComponente().getId());
				componente.setQtdEstoque(componente.getQtdEstoque()+1);
				componente = componenteService.salvarComponente(componente);
				devolucaoComponente.setComponente(componente);
			}
			devolucaoComponenteSalvo =(DevolucaoComponente) salvarDevolucaoComponente(devolucaoComponente);
		}catch (Exception e) {
			throw e;
		}
		return devolucaoComponenteSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DevolucaoComponente excluirDevolucaoComponenteDao(DevolucaoComponente devolucaoComponente) throws Exception {
		try {
			devolucaoComponenteDao.remover(devolucaoComponente);	
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return devolucaoComponente;
	}

}
