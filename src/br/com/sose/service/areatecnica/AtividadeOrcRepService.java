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

import br.com.sose.daoImpl.areaTecnica.AtividadeOrcRepDao;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.AtividadeOrcRep;
import br.com.sose.entity.reparo.Reparo;

@Service(value="atividadeOrcRepService")
@RemotingDestination(value="atividadeOrcRepService")
public class AtividadeOrcRepService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public AtividadeOrcRepDao atividadeOrcRepDao;


	@RemotingInclude
	@Transactional(readOnly = true)
	public List<AtividadeOrcRep> listarAtividadeOrcReps() throws Exception {
		List<AtividadeOrcRep> atividades;
		try {
			atividades =(List<AtividadeOrcRep>) atividadeOrcRepDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return atividades;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<AtividadeOrcRep> listarAtividadesPorReparo(Reparo reparo) throws Exception {
		List<AtividadeOrcRep> atividades;
		try {
			atividades =(List<AtividadeOrcRep>) atividadeOrcRepDao.listarAtividadesPorReparo(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return atividades;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<AtividadeOrcRep> listarAtividadesPorOrcamento(Orcamento orcamento) throws Exception {
		List<AtividadeOrcRep> atividades;
		try {
			atividades =(List<AtividadeOrcRep>) atividadeOrcRepDao.listarAtividadesPorOrcamento(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return atividades;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public AtividadeOrcRep salvarAtividadeOrcRep(AtividadeOrcRep atividadeOrcRep) throws Exception {
		AtividadeOrcRep atividadeOrcRepSalva;
		try {
			if(atividadeOrcRep.getData() == null){
				atividadeOrcRep.setData(new Date());
			}

			if(atividadeOrcRep.getId() == null || atividadeOrcRep.getId().equals(new Long(0)))
				atividadeOrcRepSalva =(AtividadeOrcRep) atividadeOrcRepDao.save(atividadeOrcRep);
			else
				atividadeOrcRepSalva =(AtividadeOrcRep) atividadeOrcRepDao.update(atividadeOrcRep);

		}catch (Exception e) {
			throw e;
		}
		return atividadeOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public AtividadeOrcRep excluirAtividadeOrcRep(AtividadeOrcRep atividadeOrcRep) throws Exception {
		try {
			atividadeOrcRepDao.remover(atividadeOrcRep);	
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return atividadeOrcRep;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public AtividadeOrcRep copiarAtividadeOrcRep(AtividadeOrcRep atividadeOrcRep) throws Exception {
		AtividadeOrcRep aor = new AtividadeOrcRep();
		try {
			aor.setAtividade(atividadeOrcRep.getAtividade());
			aor.setData(atividadeOrcRep.getData());
			aor.setJustificativa(atividadeOrcRep.getJustificativa());
			aor.setUsuario(atividadeOrcRep.getUsuario());
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return aor;
	}

}
