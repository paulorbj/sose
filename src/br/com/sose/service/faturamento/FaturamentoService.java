package br.com.sose.service.faturamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.faturamento.FaturamentoDao;
import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.estoque.Cancelado;
import br.com.sose.status.faturamento.Cancelada;
import br.com.sose.status.faturamento.Emitida;
import br.com.sose.status.faturamento.Finalizada;
import br.com.sose.status.faturamento.Iniciada;
import br.com.sose.status.faturamento.NaoIniciado;
import br.com.sose.status.faturamento.PreFatura;
import br.com.sose.utils.DateUtils;

@Service(value="faturamentoService")
@RemotingDestination(value="faturamentoService")
public class FaturamentoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public FaturamentoDao faturamentoDao;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoDao ordemServicoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Faturamento> listarFaturamentos() throws Exception {
		List<Faturamento> faturamentos;
		try {
			faturamentos =(List<Faturamento>) faturamentoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return faturamentos;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Faturamento> listarPreFaturas() throws Exception {
		List<Faturamento> faturamentos;
		try {
			faturamentos =(List<Faturamento>) faturamentoDao.listarPreFaturas();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentos;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Faturamento> listarFaturas() throws Exception {
		List<Faturamento> faturamentos = new ArrayList<Faturamento>();
		List<Faturamento> faturamentosAux = null;
		try {
			faturamentosAux = faturamentoDao.listarFaturas(NaoIniciado.nome);
			if(faturamentosAux != null && !faturamentosAux.isEmpty()) faturamentos.addAll(faturamentosAux);
			faturamentosAux = faturamentoDao.listarFaturas(Iniciada.nome);
			if(faturamentosAux != null && !faturamentosAux.isEmpty()) faturamentos.addAll(faturamentosAux);
			faturamentosAux = faturamentoDao.listarFaturas(Emitida.nome);
			if(faturamentosAux != null && !faturamentosAux.isEmpty()) faturamentos.addAll(faturamentosAux);
			faturamentosAux = faturamentoDao.listarFaturas(Finalizada.nome);
			if(faturamentosAux != null && !faturamentosAux.isEmpty()) faturamentos.addAll(faturamentosAux);
			faturamentosAux = faturamentoDao.listarFaturas(Cancelada.nome);
			if(faturamentosAux != null && !faturamentosAux.isEmpty()) faturamentos.addAll(faturamentosAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentos;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void logarAlteracoes(Faturamento faturamento, Usuario alteradoPor) throws Exception {
		Faturamento faturaSalva = faturamentoDao.buscarPorId(faturamento.getId());
		if(!faturamento.getNumeroFatura().equals(faturaSalva.getNumeroFatura())){
			observacaoService.log("Faturamento", "Nº fatura alterado de "+faturaSalva.getNumeroFatura()+" para "+faturamento.getNumeroFatura(), 2, new Date(), faturamento, alteradoPor);
		}
		
		if(!DateUtils.formatarDataDDMMYYYY(faturamento.getDataEmissaoFatura()).equals(DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataEmissaoFatura()))){
			observacaoService.log("Faturamento", "Data emissão fatura alterado de "+DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataEmissaoFatura())+" para "+DateUtils.formatarDataDDMMYYYY(faturamento.getDataEmissaoFatura()), 2, new Date(), faturamento, alteradoPor);
		}
		if(!DateUtils.formatarDataDDMMYYYY(faturamento.getDataPagamentoFatura()).equals(DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataPagamentoFatura()))){
			observacaoService.log("Faturamento", "Data pagamento fatura alterado de "+DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataPagamentoFatura())+" para "+DateUtils.formatarDataDDMMYYYY(faturamento.getDataPagamentoFatura()), 2, new Date(), faturamento, alteradoPor);
		}
		if(!DateUtils.formatarDataDDMMYYYY(faturamento.getDataVencimentoFatura()).equals(DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataVencimentoFatura()))){
			observacaoService.log("Faturamento", "Data vencimento fatura alterado de "+DateUtils.formatarDataDDMMYYYY(faturaSalva.getDataVencimentoFatura())+" para "+DateUtils.formatarDataDDMMYYYY(faturamento.getDataVencimentoFatura()), 2, new Date(), faturamento, alteradoPor);
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento salvarFaturamento(Faturamento faturamento) throws Exception {
		Faturamento faturamentoSalva;
		try {
			if(faturamento.getId() == null || faturamento.getId().equals(new Long(0)))
				faturamentoSalva =(Faturamento) faturamentoDao.save(faturamento);
			else
				faturamentoSalva =(Faturamento) faturamentoDao.update(faturamento);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento criarPreFatura(Faturamento faturamento) throws Exception {
		Faturamento faturamentoSalva;
		try {
			faturamento.setStatusString(PreFatura.nome);
			faturamento.setDataCriacaoPreFatura(new Date());
			faturamentoSalva =(Faturamento) salvarFaturamento(faturamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento editarFaturamento(Faturamento faturamento) throws Exception {
		Faturamento faturamentoSalva;
		Set<OrdemServico> listaOrdemServico = faturamento.getListaOrdemServico();
		faturamento.setListaOrdemServico(null);
		try {
			faturamentoSalva =(Faturamento) salvarFaturamento(faturamento);	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for (OrdemServico os: listaOrdemServico){
					os.setFaturamento(faturamentoSalva);
					ordemServicoService.salvarSimplesOrdemServico(os);
				}
			}
			faturamentoSalva.setListaOrdemServico(listaOrdemServico);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentoSalva;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public Faturamento buscarPorId(Long id) throws Exception {
		Faturamento faturamentoEncontrado;
		try {
			faturamentoEncontrado =(Faturamento) faturamentoDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentoEncontrado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento excluirOrdemServico(Faturamento faturamento,OrdemServico ordemServico) throws Exception {
		//faturamento = buscarPorId(faturamento.getId());
		Set<OrdemServico> listaOrdemServico = faturamento.getListaOrdemServico(); 
		Set<OrdemServico> listaOrdemServicoRetorno = new HashSet<OrdemServico>();
		faturamento.setListaOrdemServico(null);
		try {	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for(OrdemServico os : listaOrdemServico){
					if(os.getId().equals(ordemServico.getId())){
						os.setFaturamento(null);
						ordemServicoService.salvarSimplesOrdemServico(os);
					}else{
						listaOrdemServicoRetorno.add(os);
					}
				}
				faturamento.setListaOrdemServico(listaOrdemServicoRetorno);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamento;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento adicionarOrdemServico(Faturamento faturamento) throws Exception {
		try {	
			for(OrdemServico os : faturamento.getListaOrdemServico()){
				os = ordemServicoService.buscarPorId(os.getId());
				os.setFaturamento(faturamento);
				ordemServicoService.salvarSimplesOrdemServico(os);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamento;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento gerarFaturamento(Faturamento faturamento) throws Exception {
		Faturamento faturamentoSalva;
		Set<OrdemServico> listaOrdemServico = faturamento.getListaOrdemServico();
		faturamento.setListaOrdemServico(null);
		try {
			faturamento.setDataCriacaoFatura(new Date());
			faturamentoSalva =(Faturamento) salvarFaturamento(faturamento);	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for (OrdemServico os: listaOrdemServico){
					os.setFaturamento(faturamentoSalva);
					ordemServicoService.salvarSimplesOrdemServico(os);
				}
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamentoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento deletarFaturamento(Faturamento faturamento) throws Exception {
		Set<OrdemServico> listaOrdemServico = faturamento.getListaOrdemServico();
		Set<OrdemServico> listaOrdemServicoRetorno = new HashSet<OrdemServico>();

		faturamento.setListaOrdemServico(null);
		try {	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for (OrdemServico os: listaOrdemServico){
					os.setFaturamento(null);
					ordemServicoService.salvarSimplesOrdemServico(os);
					listaOrdemServicoRetorno.add(os);
				}
			}
			faturamento.setListaOrdemServico(null);
			excluirFaturamento(faturamento);
			faturamento.setListaOrdemServico(listaOrdemServicoRetorno);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamento;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento cancelarFaturamento(Faturamento faturamento, Usuario canceladoPor) throws Exception {
		Set<OrdemServico> listaOrdemServico = faturamento.getListaOrdemServico();

		faturamento.setListaOrdemServico(null);
		try {	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for (OrdemServico os: listaOrdemServico){
					os.setFaturamento(null);
					ordemServicoService.salvarSimplesOrdemServico(os);
					
					if(faturamento.getNumeroFatura() != null){
						observacaoService.log("Faturamento", "Fatura Nº " + faturamento.getNumeroFatura() + " foi cancelada", 2, new Date(), os, canceladoPor);
					}else{
						observacaoService.log("Faturamento", "Fatura Apelido " + faturamento.getNome() + " foi cancelada", 2, new Date(), os, canceladoPor);
					}
				}
			}
			faturamento.setStatusString(Cancelado.nome);
			faturamento.setDataCancelamento(new Date());
			faturamento.setListaOrdemServico(null);
			faturamento =(Faturamento) salvarFaturamento(faturamento);	

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return faturamento;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento excluirFaturamento(Faturamento faturamento) throws Exception {
		try {
			faturamentoDao.remover(faturamento);	
			//			logger.info("Atividade com o nome: "+faturamento.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			//			logger.error(e);
			e.printStackTrace(); logger.error(e);
			//			throw new AtividadeNaoExclusaoDependenciaExistenteException(atividade.getNome());
		}catch (Exception e) {
			//			logger.error(e);
			e.printStackTrace(); logger.error(e);

			throw e;
		}
		return faturamento;
	}

}
