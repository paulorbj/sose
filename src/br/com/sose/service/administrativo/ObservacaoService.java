package br.com.sose.service.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.ObservacaoDao;
import br.com.sose.entity.admistrativo.Observacao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import edu.emory.mathcs.backport.java.util.Collections;

@Service(value="observacaoService")
@RemotingDestination(value="observacaoService")
public class ObservacaoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ObservacaoDao observacaoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoes(OrdemServico ordemServico,NotaFiscal notaFiscal,NotaFiscalRemessa notaFiscalSaida) throws Exception {
		List<Observacao> observacoes = new ArrayList<Observacao>();
		Set<Observacao> obsAux = new HashSet<Observacao>();
		try {
			if(ordemServico != null){
				List<Observacao> obs = observacaoDao.listarPorOrdemServico(ordemServico);
				if(obs != null && !obs.isEmpty()){
					obsAux.addAll(obs);
					obs = null;
				}

				obs = observacaoDao.listarPorNotaFiscal(ordemServico.getNotaFiscal());
				if(obs != null && !obs.isEmpty()){
					obsAux.addAll(obs);
					obs = null;
				}
				
//				if(ordemServico.getNotaFiscal() != null && ordemServico.getNotaFiscal().getObservacao() != null && !ordemServico.getNotaFiscal().getObservacao().isEmpty()){
//					Observacao observacao = new Observacao();
//					observacao.setOrigem("Recebimento");
//					observacao.setTexto(ordemServico.getNotaFiscal().getObservacao());
//					observacao.setData(ordemServico.getNotaFiscal().getDataCriacao());
//					obsAux.add(observacao);
//				}

				if(ordemServico.getReparo() != null){
					obs = observacaoDao.listarPorReparo(ordemServico.getReparo());
					if(obs != null && !obs.isEmpty()){
						obsAux.addAll(obs);
						obs = null;
					}
				}

				if(ordemServico.getOrcamento() != null){
					obs = observacaoDao.listarPorOrcamento(ordemServico.getOrcamento());
					if(obs != null && !obs.isEmpty()){
						obsAux.addAll(obs);
						obs = null;
					}
				}

				if(ordemServico.getProposta() != null){
					obs = observacaoDao.listarPorProposta(ordemServico.getProposta());
					if(obs != null && !obs.isEmpty()){
						obsAux.addAll(obs);
						obs = null;
					}
				}

//				if(ordemServico.getLaudoTecnico() != null){
//					obs = observacaoDao.listarPorLaudoTecnico(ordemServico.getLaudoTecnico());
//					if(obs != null && !obs.isEmpty()){
//						obsAux.addAll(obs);
//						obs = null;
//					}
//				}

				if(ordemServico.getFaturamento() != null){
					obs = observacaoDao.listarPorFaturamento(ordemServico.getFaturamento());
					if(obs != null && !obs.isEmpty()){
						obsAux.addAll(obs);
						obs = null;
					}
				}
			}

			if(notaFiscal != null){
				List<Observacao> obs = observacaoDao.listarPorNotaFiscal(notaFiscal);
				if(obs != null && !obs.isEmpty()){
					obsAux.addAll(obs);
					obs = null;
				}
				
//				if(notaFiscal.getObservacao() != null && !notaFiscal.getObservacao().isEmpty()){
//					Observacao obs1 = new Observacao();
//					obs1.setOrigem("Expedição");
//					obs1.setTexto(notaFiscal.getObservacao());
//					obs1.setData(notaFiscal.getDataCriacao());
//					if(observacoes != null){
//						observacoes.add(obs1);
//					}
//				}
			}

			if(notaFiscalSaida != null){
				List<Observacao> obs = observacaoDao.listarPorNotaFiscalSaida(notaFiscalSaida);
				if(obs != null && !obs.isEmpty()){
					obsAux.addAll(obs);
					obs = null;
				}
				
//				if(notaFiscalSaida.getObservacao() != null && !notaFiscalSaida.getObservacao().isEmpty()){
//					Observacao obs1 = new Observacao();
//					obs1.setOrigem("Expedição");
//					obs1.setTexto(notaFiscalSaida.getObservacao());
//					obs1.setData(notaFiscalSaida.getDtCriacao());
//					if(obsAux != null){
//						obsAux.add(obs1);
//					}
//				}
			}

			observacoes.addAll(obsAux);
			Collections.sort(observacoes);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return observacoes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoes() throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return observacaos;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoes(Boolean permitirVisualizarSigilos) throws Exception {
		List<Observacao> observacaos;
		try {
			if(permitirVisualizarSigilos){
				observacaos =(List<Observacao>) observacaoDao.listAll();	
			}else{
				observacaos =(List<Observacao>) observacaoDao.listarTodasObservacoesSemSigilo();	
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return observacaos;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Observacao salvarObservacao(Observacao observacao) throws Exception {
		Observacao observacaoSalva;
		try {
			if(observacao == null || (observacao.getId() == null || observacao.getId() == 0)){
				observacaoSalva =(Observacao) observacaoDao.save(observacao);	
			}else{
				observacaoSalva =(Observacao) observacaoDao.update(observacao);	
			}
		} catch (Exception e) {
			logger.error("Erro ao salvar observacao: " + observacao.getId());;
			throw e;
		}
		return observacaoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Observacao criarObservacao(Observacao observacao) throws Exception {
		Observacao observacaoSalva;
		try {
			observacao.setData(new Date());
			observacaoSalva =(Observacao) observacaoDao.save(observacao);	
		} catch (Exception e) {
			logger.error("Erro ao salvar observacao: " + observacao.getId());;
			throw e;
		}
		return observacaoSalva;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> buscarObservacoesRecebimento(OrdemServico ordemServico, NotaFiscal notaFiscal) throws Exception {
		List<Observacao> observacoesRetorno = new ArrayList<Observacao>();
		List<Observacao> observacoesAux;

		try {
			if(notaFiscal != null){
				observacoesAux = observacaoDao.listarPorNotaFiscal(notaFiscal);
				if(observacoesAux != null && !observacoesAux.isEmpty()){
					observacoesRetorno.addAll(observacoesAux);
				}
			}

			if(ordemServico != null){
				observacoesAux = observacaoDao.listarPorOrdemServico(ordemServico);
				if(observacoesAux != null && !observacoesAux.isEmpty()){
					observacoesRetorno.addAll(observacoesAux);
				}
			}	

			observacoesAux = new ArrayList<Observacao>();
			if(!observacoesRetorno.isEmpty()){
				for(Observacao obs : observacoesRetorno){
					if(obs.getEscopo() == 3 && obs.getOrigem().equals("Recebimento")){
						observacoesAux.add(obs);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Erro ao buscar observacoes");
			throw e;
		}
		return observacoesAux;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void log(String origem, String texto, Integer escopo, Date data, Object entidade, Usuario usuario) throws Exception {

		Observacao observacao = new Observacao();
		try {
			if(entidade instanceof OrdemServico){
				observacao.setNotaFiscal(null);
				observacao.setOrdemServico((OrdemServico)entidade);
				observacao.setProposta(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof NotaFiscal){
				observacao.setNotaFiscal((NotaFiscal)entidade);
				observacao.setOrdemServico(null);
				observacao.setProposta(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof Proposta){
				observacao.setProposta((Proposta)entidade);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof ItemProposta){
				observacao.setItemProposta((ItemProposta)entidade);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setProposta(null);
				observacao.setFaturamento(null);
			}			else if(entidade instanceof Reparo){
				observacao.setReparo((Reparo)entidade);
				observacao.setProposta(null);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof Orcamento){
				observacao.setOrcamento((Orcamento)entidade);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof LaudoTecnico){
				observacao.setLaudoTecnico((LaudoTecnico)entidade);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof NotaFiscalRemessa){
				observacao.setNotaFiscalSaida((NotaFiscalRemessa)entidade);
				observacao.setLaudoTecnico(null);
				observacao.setOrcamento(null);
				observacao.setReparo(null);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
				observacao.setFaturamento(null);
			}else if(entidade instanceof Faturamento){
				observacao.setFaturamento((Faturamento)entidade);
				observacao.setNotaFiscalSaida(null);
				observacao.setLaudoTecnico(null);
				observacao.setOrcamento(null);
				observacao.setReparo(null);
				observacao.setOrdemServico(null);
				observacao.setNotaFiscal(null);
			}

			observacao.setOrigem(origem);
			if(data != null){
				observacao.setData(data);
			}else{
				observacao.setData(new Date());
			}
			observacao.setTexto(texto);
			observacao.setUsuario(usuario);
			observacao.setEscopo(escopo);


			this.salvarObservacao(observacao);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e.getMessage());
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Observacao excluirObservacao(Observacao observacao) throws Exception {
		try {
			observacaoDao.remover(observacao);	
			logger.info("Observacao com o id: "+observacao.getId()+" foi removido do sistema");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return observacao;
	}

	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoes(Integer escopo, String origem) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesReparo(Reparo reparo) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesReparo(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(readOnly = true)
	@RemotingInclude
	public List<Observacao> listarObservacoesOrdemServico(OrdemServico ordemServico) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesOrdemServico(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesProposta(Proposta proposta) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesProposta(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesItemProposta(OrdemServico ordemServico) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesItemProposta(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesOrcamento(Orcamento orcamento) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesOrcamento(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesNotaFiscal(NotaFiscal notaFiscal) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesNotaFiscal(notaFiscal);				
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<Observacao> listarObservacoesNotaFiscalSaida(NotaFiscalRemessa notaFiscalSaida) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesNotaFiscalSaida(notaFiscalSaida);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesExpedicao(OrdemServico ordemServico) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesExpedicao(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Observacao> listarObservacoesFaturamento(Faturamento faturamento) throws Exception {
		List<Observacao> observacaos;
		try {
			observacaos =(List<Observacao>) observacaoDao.listarObservacoesFaturamento(faturamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		}
		return observacaos;
	}

}
