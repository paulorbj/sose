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

import br.com.sose.daoImpl.administrativo.EquipamentoDao;
import br.com.sose.entity.admistrativo.Equipamento;
import br.com.sose.exceptions.EquipamentoExistenteException;
import br.com.sose.exceptions.EquipamentoNaoExclusaoDependenciaExistenteException;

@Service(value="equipamentoService")
@RemotingDestination(value="equipamentoService")
public class EquipamentoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public EquipamentoDao equipamentoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Equipamento> listarEquipamentos() throws Exception {
		List<Equipamento> equipamentos;
		try {
			equipamentos =(List<Equipamento>) equipamentoDao.findAllOrderByNome();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return equipamentos;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Equipamento salvarEquipamento(Equipamento equipamento) throws Exception {
		Equipamento equipamentoSalva;
		try {
			equipamentoSalva = buscarPorNome(equipamento.getNome());
			if(equipamentoSalva != null && !equipamentoSalva.getId().equals(equipamento.getId())){
				throw new EquipamentoExistenteException(equipamento.getNome()); 
			}
			if(equipamento.getId() == null || equipamento.getId().equals(new Long(0))){
				equipamentoSalva =(Equipamento) equipamentoDao.save(equipamento);	
			}else{
				equipamentoSalva =(Equipamento) equipamentoDao.update(equipamento);	
			}
		} catch (EquipamentoExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar equipamento: " + equipamento.getNome());;
			throw e;
		}
		return equipamentoSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Equipamento buscarPorNome(String nome) throws Exception {
		try {
			return equipamentoDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há equipamento cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Equipamento excluirEquipamento(Equipamento equipamento) throws Exception {
		try {
			equipamentoDao.remover(equipamento);	
			logger.info("Equipamento com o nome: "+equipamento.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new EquipamentoNaoExclusaoDependenciaExistenteException(equipamento.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return equipamento;
	}
	
}
