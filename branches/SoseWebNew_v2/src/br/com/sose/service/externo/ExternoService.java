package br.com.sose.service.externo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.externo.ExternoDao;
import br.com.sose.daoImpl.laudoTecnico.LaudoTecnicoDao;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.OrcRepGenerico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.laudotecnico.Finalizado;
import br.com.sose.status.laudotecnico.Iniciado;
import br.com.sose.status.laudotecnico.NaoIniciado;
import br.com.sose.status.laudotecnico.Rejeitado;
import br.com.sose.utils.ConjuntoOrdemServico;

@Service(value="externoService")
@RemotingDestination(value="externoService")
public class ExternoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ExternoDao externoDao;

	@Autowired
	public OrdemServicoService ordemServicoService;
	
//	@RemotingInclude
//	@Transactional(readOnly = true)
//	public List<LaudoTecnico> listarLaudosTecnicos() throws Exception {
//		List<LaudoTecnico> laudosTecnicos = new ArrayList<LaudoTecnico>();
//		List<LaudoTecnico> laudosTecnicosAux = null;
//		try {
//			laudosTecnicosAux = laudoTecnicoDao.listarLaudosTecnicos(NaoIniciado.nome);
//			if(laudosTecnicosAux != null && !laudosTecnicosAux.isEmpty()) laudosTecnicos.addAll(laudosTecnicosAux);
//			laudosTecnicosAux = laudoTecnicoDao.listarLaudosTecnicos(Iniciado.nome);
//			if(laudosTecnicosAux != null && !laudosTecnicosAux.isEmpty()) laudosTecnicos.addAll(laudosTecnicosAux);
//			laudosTecnicosAux = laudoTecnicoDao.listarLaudosTecnicos(Finalizado.nome);
//			if(laudosTecnicosAux != null && !laudosTecnicosAux.isEmpty()) laudosTecnicos.addAll(laudosTecnicosAux);
//			laudosTecnicosAux = laudoTecnicoDao.listarLaudosTecnicos(Rejeitado.nome);
//			if(laudosTecnicosAux != null && !laudosTecnicosAux.isEmpty()) laudosTecnicos.addAll(laudosTecnicosAux);
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return laudosTecnicos;
//	}
//	
//	@RemotingInclude
//	@Transactional(readOnly = true)
//	public LaudoTecnico buscarPorId(Long id) throws Exception {
//		LaudoTecnico laudoTecnicoEncontrado;
//		try {
//			laudoTecnicoEncontrado =(LaudoTecnico) laudoTecnicoDao.buscarPorId(id);	
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return laudoTecnicoEncontrado;
//	}
//	
//	@RemotingInclude
//	@Transactional(readOnly = true)
//	public LaudoTecnico buscarUltimoLaudoPorOrdemServico(Long id) throws Exception {
//		LaudoTecnico laudoTecnicoEncontrado;
//		try {
//			laudoTecnicoEncontrado =(LaudoTecnico) laudoTecnicoDao.buscarUltimoLaudoPorOrdemServico(id);	
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return laudoTecnicoEncontrado;
//	}
//
//	@RemotingInclude
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public LaudoTecnico salvarLaudoTecnico(LaudoTecnico laudoTecnico) throws Exception {
//		LaudoTecnico laudoTecnicoSalvo;
//		try {
//			if(laudoTecnico.getId() == null || laudoTecnico.getId().equals(new Long(0)))
//				laudoTecnicoSalvo =(LaudoTecnico) laudoTecnicoDao.save(laudoTecnico);	
//			else
//				laudoTecnicoSalvo =(LaudoTecnico) laudoTecnicoDao.update(laudoTecnico);	
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return laudoTecnicoSalvo;
//	}
//
//	@RemotingInclude
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public void excluirLaudoTecnico(LaudoTecnico laudoTecnico) throws Exception {
//		try {
//			laudoTecnicoDao.delete(laudoTecnico);	
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//	}
//
//	@RemotingInclude
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public LaudoTecnico criarLaudoTecnico(OrcRepGenerico reparo) throws Exception {
//		LaudoTecnico novoLaudoTecnico;
//		try {
//			novoLaudoTecnico = new LaudoTecnico();
//			novoLaudoTecnico.setDataCriacao(new Date());
//			novoLaudoTecnico.setCriadoPor(reparo.getTecnicoResponsavel());
//			novoLaudoTecnico.setStatusString(NaoIniciado.nome);
//			OrdemServico os = null;
//			if(reparo instanceof Reparo){
//				os = ordemServicoService.buscarPorIdSimples(((Reparo)reparo).getOrdemServico().getId());
//				novoLaudoTecnico.setReparo((Reparo)reparo);
//			}else{
//				os = ordemServicoService.buscarPorIdSimples(((Orcamento)reparo).getOrdemServico().getId());
//				novoLaudoTecnico.setOrcamento((Orcamento)reparo);
//			}
//			if(os != null){
//				novoLaudoTecnico.setOrdemServico(os);
////				os.setLaudoTecnico(novoLaudoTecnico);
//
//				novoLaudoTecnico =(LaudoTecnico) salvarLaudoTecnico(novoLaudoTecnico);
//
//				novoLaudoTecnico.setControle(novoLaudoTecnico.getId().toString());
//
//				os = ordemServicoService.salvarSimplesOrdemServico(os);
//			}else{
//				throw new Exception();
//			}
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return novoLaudoTecnico;
//	}
//
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public LaudoTecnico criarLaudoTecnicoConjuntoOrcamento(Orcamento orcamento, ConjuntoOrdemServico conjunto) throws Exception {
//		LaudoTecnico novoLaudoTecnico;
//		try {
//			novoLaudoTecnico = new LaudoTecnico();
//			novoLaudoTecnico.setDataCriacao(new Date());
//			novoLaudoTecnico.setCriadoPor(orcamento.getTecnicoResponsavel());
//			novoLaudoTecnico.setStatusString(NaoIniciado.nome);
//			novoLaudoTecnico.setOrcamento(conjunto.getOsPai().getOrcamento());
//
//			novoLaudoTecnico.setOrdemServico(conjunto.getOsPai());
////			conjunto.getOsPai().setLaudoTecnico(novoLaudoTecnico);
//
//			novoLaudoTecnico =(LaudoTecnico) salvarLaudoTecnico(novoLaudoTecnico);
//			novoLaudoTecnico.setControle(novoLaudoTecnico.getId().toString());
//			novoLaudoTecnico = salvarLaudoTecnico(novoLaudoTecnico);
//
//			ordemServicoService.salvarSimplesOrdemServico(conjunto.getOsPai());
//			
//			for(OrdemServico osFilha : conjunto.getListaFilhas()){
////				osFilha.setLaudoTecnico(novoLaudoTecnico);
//				ordemServicoService.salvarSimplesOrdemServico(osFilha);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return novoLaudoTecnico;
//	}
//	
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public LaudoTecnico criarLaudoTecnicoConjuntoReparo(Reparo reparo, ConjuntoOrdemServico conjunto) throws Exception {
//		LaudoTecnico novoLaudoTecnico;
//		try {
//			novoLaudoTecnico = new LaudoTecnico();
//			novoLaudoTecnico.setDataCriacao(new Date());
//			novoLaudoTecnico.setCriadoPor(reparo.getTecnicoResponsavel());
//			novoLaudoTecnico.setStatusString(NaoIniciado.nome);
//			novoLaudoTecnico.setReparo(conjunto.getOsPai().getReparo());
//
//			novoLaudoTecnico.setOrdemServico(conjunto.getOsPai());
////			conjunto.getOsPai().setLaudoTecnico(novoLaudoTecnico);
//
//			novoLaudoTecnico =(LaudoTecnico) salvarLaudoTecnico(novoLaudoTecnico);
//			novoLaudoTecnico.setControle(novoLaudoTecnico.getId().toString());
//			novoLaudoTecnico = salvarLaudoTecnico(novoLaudoTecnico);
//
//			ordemServicoService.salvarSimplesOrdemServico(conjunto.getOsPai());
//			
//			for(OrdemServico osFilha : conjunto.getListaFilhas()){
////				osFilha.setLaudoTecnico(novoLaudoTecnico);
//				ordemServicoService.salvarSimplesOrdemServico(osFilha);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace(); logger.error(e);
//			throw e;
//		}
//		return novoLaudoTecnico;
//	}

}
