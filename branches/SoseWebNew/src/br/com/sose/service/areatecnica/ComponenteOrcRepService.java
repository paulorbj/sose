package br.com.sose.service.areatecnica;

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

import br.com.sose.daoImpl.areaTecnica.ComponenteOrcRepDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.DevolucaoComponente;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.estoque.DevolucaoComponenteService;

@Service(value="componenteOrcRepService")
@RemotingDestination(value="componenteOrcRepService")
public class ComponenteOrcRepService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ComponenteOrcRepDao componenteOrcRepDao;

	@Autowired
	public DevolucaoComponenteService devolucaoComponenteService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ComponenteOrcRep> listarComponenteOrcReps() throws Exception {
		List<ComponenteOrcRep> componenteOrcReps;
		try {
			componenteOrcReps =(List<ComponenteOrcRep>) componenteOrcRepDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return componenteOrcReps;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ComponenteOrcRep> listarComponentesPosicaoPorRequisicao(RequisicaoComponente requisicao) throws Exception {
		List<ComponenteOrcRep> componentesPosicao;
		try {
			componentesPosicao =(List<ComponenteOrcRep>) componenteOrcRepDao.listarComponentesPosicaoPorRequisicao(requisicao);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return componentesPosicao;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ComponenteOrcRep> listarComponentesPosicaoPorReparo(Reparo reparo) throws Exception {
		List<ComponenteOrcRep> componentesPosicao;
		try {
			componentesPosicao =(List<ComponenteOrcRep>) componenteOrcRepDao.listarComponentesPosicaoPorReparo(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return componentesPosicao;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ComponenteOrcRep> listarComponentesPosicaoPorOrcamento(Orcamento orcamento) throws Exception {
		List<ComponenteOrcRep> componentesPosicao;
		try {
			componentesPosicao =(List<ComponenteOrcRep>) componenteOrcRepDao.listarComponentesPosicaoPorOrcamento(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return componentesPosicao;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep salvarComponenteOrcRep(ComponenteOrcRep componenteOrcRep) throws Exception {
		ComponenteOrcRep componenteOrcRepSalva;
		try {
			if(componenteOrcRep.getId() == null || componenteOrcRep.getId().equals(new Long(0)))
				componenteOrcRepSalva =(ComponenteOrcRep) componenteOrcRepDao.save(componenteOrcRep);	
			else
				componenteOrcRepSalva =(ComponenteOrcRep) componenteOrcRepDao.update(componenteOrcRep);	

		}catch (Exception e) {
			throw e;
		}
		return componenteOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep devolverComponenteOrcRep(ComponenteOrcRep componenteOrcRep, Usuario usuario) throws Exception {
		ComponenteOrcRep componenteOrcRepSalva = null;
		try {
			if(componenteOrcRep.getCondicao() != null && (componenteOrcRep.getCondicao().equals("Utilizado") || componenteOrcRep.getCondicao().equals(""))){

				componenteOrcRep.setCondicao("Devolvido");
				componenteOrcRepSalva = salvarComponenteOrcRep(componenteOrcRep);

				DevolucaoComponente devolucaoComponente = new DevolucaoComponente();
				devolucaoComponente.setComponente(componenteOrcRep.getComponente());
				if(componenteOrcRep.getReparo() != null){
					devolucaoComponente.setReparo(componenteOrcRep.getReparo());
				}else{
					devolucaoComponente.setOrcamento(componenteOrcRep.getOrcamento());
				}
				devolucaoComponente.setDevolvidoEm(new Date());
				devolucaoComponente.setDevolvidoPor(usuario);
				devolucaoComponente.setCondicao("Devolvido");
				devolucaoComponente.setRequisicao(componenteOrcRep.getRequisicao());
				devolucaoComponenteService.salvarDevolucaoComponente(devolucaoComponente);
			}else{
				throw new Exception("Operação inválida");
			}
		}catch (Exception e) {
			throw e;
		}
		return componenteOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep componentePerdidoOrcRep(ComponenteOrcRep componenteOrcRep,Usuario usuario) throws Exception {
		ComponenteOrcRep componenteOrcRepSalva;
		try {
			if(componenteOrcRep.getCondicao() != null && (componenteOrcRep.getCondicao().equals("Utilizado") || componenteOrcRep.getCondicao().equals(""))){
				componenteOrcRep.setCondicao("Perdido");
				componenteOrcRepSalva =(ComponenteOrcRep) salvarComponenteOrcRep(componenteOrcRep);

				DevolucaoComponente devolucaoComponente = new DevolucaoComponente();
				devolucaoComponente.setComponente(componenteOrcRep.getComponente());
				if(componenteOrcRep.getReparo() != null){
					devolucaoComponente.setReparo(componenteOrcRep.getReparo());
				}else{
					devolucaoComponente.setOrcamento(componenteOrcRep.getOrcamento());
				}
				devolucaoComponente.setDevolvidoEm(new Date());
				devolucaoComponente.setDevolvidoPor(usuario);
				devolucaoComponente.setCondicao("Perdido");
				devolucaoComponente.setRequisicao(componenteOrcRep.getRequisicao());
				devolucaoComponenteService.salvarDevolucaoComponente(devolucaoComponente);
			}else{
				throw new Exception("Operação inválida");
			}
		}catch (Exception e) {
			throw e;
		}
		return componenteOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep componenteDefeituosoOrcRep(ComponenteOrcRep componenteOrcRep, Usuario usuario) throws Exception {
		ComponenteOrcRep componenteOrcRepSalva;
		try {
			if(componenteOrcRep.getCondicao() != null && (componenteOrcRep.getCondicao().equals("Utilizado") || componenteOrcRep.getCondicao().equals(""))){

				componenteOrcRep.setCondicao("Defeituoso");
				componenteOrcRepSalva =(ComponenteOrcRep) salvarComponenteOrcRep(componenteOrcRep);

				DevolucaoComponente devolucaoComponente = new DevolucaoComponente();
				devolucaoComponente.setComponente(componenteOrcRep.getComponente());
				if(componenteOrcRep.getReparo() != null){
					devolucaoComponente.setReparo(componenteOrcRep.getReparo());
				}else{
					devolucaoComponente.setOrcamento(componenteOrcRep.getOrcamento());
				}
				devolucaoComponente.setDevolvidoEm(new Date());
				devolucaoComponente.setDevolvidoPor(usuario);
				devolucaoComponente.setCondicao("Defeituoso");
				devolucaoComponente.setRequisicao(componenteOrcRep.getRequisicao());
				devolucaoComponenteService.salvarDevolucaoComponente(devolucaoComponente);
			}else{
				throw new Exception("Operação inválida");
			}
		}catch (Exception e) {
			throw e;
		}
		return componenteOrcRepSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ComponenteOrcRep> criarComponentesOrcRep(RequisicaoComponente requisicao) throws Exception {
		List<ComponenteOrcRep> componenteOrcRepCriados = new ArrayList<ComponenteOrcRep>();
		try {
			for(int i=0;i<requisicao.getQuantidade();i++){
				ComponenteOrcRep cor = new ComponenteOrcRep();
				cor.setComponente(requisicao.getComponente());
				cor.setPosicao("");
				cor.setCondicao("Utilizado");
				cor.setRequisicao(requisicao);
				if(requisicao.getReparo() != null){
					cor.setReparo(requisicao.getReparo());
				}else{
					cor.setOrcamento(requisicao.getOrcamento());
				}
				cor = (ComponenteOrcRep) salvarComponenteOrcRep(cor);
				componenteOrcRepCriados.add(cor);
			}

		}catch (Exception e) {
			throw e;
		}
		return componenteOrcRepCriados;
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep excluirComponenteOrcRep(ComponenteOrcRep componenteOrcRep) throws Exception {
		try {
			componenteOrcRepDao.remover(componenteOrcRep);	
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return componenteOrcRep;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ComponenteOrcRep copiarComponenteOrcRep(ComponenteOrcRep componenteOrcRep) throws Exception {
		ComponenteOrcRep cor = new ComponenteOrcRep();
		try {
			cor.setComponente(componenteOrcRep.getComponente());
			cor.setCondicao(componenteOrcRep.getCondicao());
			cor.setPosicao(componenteOrcRep.getPosicao());
			cor.setRequisicao(componenteOrcRep.getRequisicao());
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return cor;
	}

}
