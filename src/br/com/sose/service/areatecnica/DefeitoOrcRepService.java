package br.com.sose.service.areatecnica;

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

import br.com.sose.daoImpl.areaTecnica.DefeitoOrcRepDao;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.DefeitoOrcRep;
import br.com.sose.entity.reparo.Reparo;

@Service(value="defeitoOrcRepService")
@RemotingDestination(value="defeitoOrcRepService")
public class DefeitoOrcRepService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public DefeitoOrcRepDao defeitoOrcRepDao;


	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DefeitoOrcRep> listarDefeitoOrcReps() throws Exception {
		List<DefeitoOrcRep> defeitos;
		try {
			defeitos =(List<DefeitoOrcRep>) defeitoOrcRepDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return defeitos;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DefeitoOrcRep> listarDefeitosPorReparo(Reparo reparo) throws Exception {
		List<DefeitoOrcRep> defeitos;
		try {
			defeitos =(List<DefeitoOrcRep>) defeitoOrcRepDao.listarDefeitosPorReparo(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return defeitos;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<DefeitoOrcRep> listarDefeitosPorOrcamento(Orcamento orcamento) throws Exception {
		List<DefeitoOrcRep> defeitos;
		try {
			defeitos =(List<DefeitoOrcRep>) defeitoOrcRepDao.listarDefeitosPorOrcamento(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return defeitos;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DefeitoOrcRep salvarDefeitoOrcRep(DefeitoOrcRep defeitoOrcRep) throws Exception {
		DefeitoOrcRep defeitoOrcRepSalva;
		try {
			if(defeitoOrcRep.getData() == null){
				defeitoOrcRep.setData(new Date());
			}
			if(defeitoOrcRep.getId() == null || defeitoOrcRep.getId().equals(new Long(0)))
				defeitoOrcRepSalva =(DefeitoOrcRep) defeitoOrcRepDao.save(defeitoOrcRep);	
			else
				defeitoOrcRepSalva =(DefeitoOrcRep) defeitoOrcRepDao.update(defeitoOrcRep);	

		}catch (Exception e) {
			throw e;
		}
		return defeitoOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DefeitoOrcRep excluirDefeitoOrcRep(DefeitoOrcRep defeitoOrcRep) throws Exception {
		try {
			defeitoOrcRepDao.remover(defeitoOrcRep);	
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return defeitoOrcRep;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DefeitoOrcRep copiarDefeitoOrcRep(DefeitoOrcRep defeitoOrcRep) throws Exception {
		DefeitoOrcRep dor = new DefeitoOrcRep();
		try {
			dor.setDefeito(defeitoOrcRep.getDefeito());
			dor.setData(defeitoOrcRep.getData());
			dor.setJustificativa(defeitoOrcRep.getJustificativa());
			dor.setUsuario(defeitoOrcRep.getUsuario());
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return dor;
	}

}
