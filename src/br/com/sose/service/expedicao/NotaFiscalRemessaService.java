package br.com.sose.service.expedicao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.expedicao.NotaFiscalRemessaDao;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.expedicao.Volume;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.exceptions.NumeroNotaFiscalSaidaNaoDisponivelException;
import br.com.sose.service.administrativo.parceiros.PessoaService;
import br.com.sose.service.recebimento.NotaFiscalService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.AguardandoExpedicao;
import br.com.sose.status.aplicacao.ExpedicaoSendoRealizada;
import br.com.sose.status.expedicao.Emitida;
import br.com.sose.status.expedicao.Finalizada;
import br.com.sose.status.expedicao.Iniciada;
import br.com.sose.status.expedicao.Nova;
import br.com.sose.status.expedicao.PreExpedicao;
import br.com.sose.status.expedicao.Solicitada;
import br.com.sose.status.recebimento.Aberta;

@Service(value="notaFiscalRemessaService")
@RemotingDestination(value="notaFiscalRemessaService")
public class NotaFiscalRemessaService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public NotaFiscalRemessaDao notaFiscalRemessaDao;
	
	@Autowired
	public NotaFiscalService notaFiscalService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private VolumeService volumeService;

	@Autowired
	private PessoaService pessoaService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<NotaFiscalRemessa> listarNotaFiscalRemessas() throws Exception {
		List<NotaFiscalRemessa> notaFiscalRemessas;
		try {
			notaFiscalRemessas =(List<NotaFiscalRemessa>) notaFiscalRemessaDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessas;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<NotaFiscalRemessa> listarNotaFiscais() throws Exception {
		List<NotaFiscalRemessa> notaFiscalRemessas;
		try {
			notaFiscalRemessas =(List<NotaFiscalRemessa>) notaFiscalRemessaDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessas;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<NotaFiscalRemessa> listarPreNotaFiscalSaida() throws Exception {
		List<NotaFiscalRemessa> notasFiscaisSaida;
		try {
			notasFiscaisSaida =(List<NotaFiscalRemessa>) notaFiscalRemessaDao.listarPreNotaFiscalSaida();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notasFiscaisSaida;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<NotaFiscalRemessa> listarNotaFiscalSaida() throws Exception {
		List<NotaFiscalRemessa> notasFiscaisSaida = new ArrayList<NotaFiscalRemessa>();
		List<NotaFiscalRemessa> notasFiscaisSaidaAux = null;
		try {
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Nova.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Iniciada.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Solicitada.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Emitida.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Finalizada.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notasFiscaisSaida;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<NotaFiscalRemessa> listarNotaFiscalSaidaEmitidaOuFinalizada() throws Exception {
		List<NotaFiscalRemessa> notasFiscaisSaida = new ArrayList<NotaFiscalRemessa>();
		List<NotaFiscalRemessa> notasFiscaisSaidaAux = null;
		try {
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Emitida.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
			notasFiscaisSaidaAux = notaFiscalRemessaDao.listarNotaFiscalSaida(Finalizada.nome);
			if(notasFiscaisSaidaAux != null && !notasFiscaisSaidaAux.isEmpty()) notasFiscaisSaida.addAll(notasFiscaisSaidaAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notasFiscaisSaida;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarClientesBaixa() throws Exception {
		List<Pessoa> clientes;
		try {
			clientes =(List<Pessoa>) notaFiscalRemessaDao.listarClientesBaixa();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return clientes;
	}



	@RemotingInclude
	@Transactional(readOnly = true)
	public List<OrdemServico> listarOrdemServicoBaixaExpedicao(Pessoa cliente) throws Exception {
		List<OrdemServico> ordemServicos;
		try {
			ordemServicos =(List<OrdemServico>) notaFiscalRemessaDao.listarOrdemServicoBaixaExpedicao(cliente);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return ordemServicos;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public NotaFiscalRemessa buscarPorIdBaixaOS(Long id) throws Exception {
		NotaFiscalRemessa notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscalRemessa) notaFiscalRemessaDao.buscarPorIdBaixaOS(id);	
			if(notaFiscalEncontrada.getCliente() != null){
				Pessoa cliente = pessoaService.buscarPorId(notaFiscalEncontrada.getCliente().getId());
				if(cliente != null){
					notaFiscalEncontrada.setCliente(cliente);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public NotaFiscalRemessa buscarPorId(Long id) throws Exception {
		NotaFiscalRemessa notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscalRemessa) notaFiscalRemessaDao.buscarPorId(id);	
			if(notaFiscalEncontrada.getCliente() != null){
				Pessoa cliente = pessoaService.buscarPorId(notaFiscalEncontrada.getCliente().getId());
				if(cliente != null){
					notaFiscalEncontrada.setCliente(cliente);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public NotaFiscalRemessa buscarPorIdListagemNotaFiscalSaida(Long id) throws Exception {
		NotaFiscalRemessa notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscalRemessa) notaFiscalRemessaDao.buscarPorIdListagemNotaFiscalSaida(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void calcularFretePorOrdemServico(NotaFiscalRemessa notaFiscalSaida) throws Exception{
		Integer total = 0;
		try{
		for(OrdemServico os : notaFiscalSaida.getOrdensServico()){
			if(os.getUnidadePai() == null){
				total++;
			}
		}
		BigDecimal divisor = new BigDecimal(total);
		BigDecimal freteUnitario = notaFiscalSaida.getValorFrete().divide(divisor,8,RoundingMode.HALF_UP);

		for(OrdemServico os : notaFiscalSaida.getOrdensServico()){
			if(os.getUnidadePai() == null){
				os.setFreteUnitarioExpedicao(freteUnitario);
				ordemServicoService.salvarSimplesOrdemServico(os);
			}
		}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
		}
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public NotaFiscalRemessa buscarPorIdOtimizado(Long id) throws Exception {
		NotaFiscalRemessa notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscalRemessa) notaFiscalRemessaDao.buscarPorIdOtimizado(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa criarPreNotaFiscal(NotaFiscalRemessa notaFiscal) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva = null;
		try {
			notaFiscal.setStatusString(PreExpedicao.nome);
			notaFiscal.setDataCriacaoPreExpedicao(new Date());
			notaFiscalRemessaSalva =(NotaFiscalRemessa) notaFiscalRemessaDao.save(notaFiscal);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa criarNotaFiscalRemessaExpedicao(List<OrdemServico> ordensServico) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva = null;
		Set<OrdemServico> listaOrdemServico = new HashSet<OrdemServico>();
		try {
			if(ordensServico != null){
				notaFiscalRemessaSalva = new NotaFiscalRemessa();
				notaFiscalRemessaSalva.setCliente(ordensServico.get(0).getCliente());
				notaFiscalRemessaSalva.setDtCriacao(new Date());
				notaFiscalRemessaSalva.setStatusString(Nova.nome);
				notaFiscalRemessaSalva =(NotaFiscalRemessa) notaFiscalRemessaDao.save(notaFiscalRemessaSalva);	

				for(OrdemServico os : ordensServico){
					os.setNotaFiscalSaida(notaFiscalRemessaSalva);
					os.setStatusString(ExpedicaoSendoRealizada.nome);
					ordemServicoService.salvarSimplesOrdemServico(os);
					listaOrdemServico.add(os);
				}

				notaFiscalRemessaSalva.setOrdensServico(listaOrdemServico);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa editarNotaFiscal(NotaFiscalRemessa notafiscal) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva = null;
		Set<OrdemServico> listaOrdemServico = notafiscal.getOrdensServico();
		notafiscal.setOrdensServico(null);
		try {

			notaFiscalRemessaSalva =(NotaFiscalRemessa) salvarNotaFiscalRemessaSimples(notafiscal);	

			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for(OrdemServico os : listaOrdemServico){
					os.setNotaFiscalSaida(notaFiscalRemessaSalva);
					//					os.setStatusString(ExpedicaoSendoRealizada.nome);
					ordemServicoService.salvarSimplesOrdemServico(os);
				}
			}

			notaFiscalRemessaSalva.setOrdensServico(listaOrdemServico);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa deletarNotaFiscal(NotaFiscalRemessa notaFiscal) throws Exception {
		notaFiscal = buscarPorId(notaFiscal.getId());
		Set<OrdemServico> listaOrdemServico = notaFiscal.getOrdensServico();
		notaFiscal.setOrdensServico(null);
		try {	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for (OrdemServico os: listaOrdemServico){
					os.setNotaFiscalSaida(null);
					ordemServicoService.salvarSimplesOrdemServico(os);
				}
			}

			excluirNotaFiscal(notaFiscal);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscal;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirNotaFiscal(NotaFiscalRemessa notaFiscal) throws Exception {
		try {
			notaFiscalRemessaDao.delete(notaFiscal);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa excluirOrdemServico(NotaFiscalRemessa notaFiscal,OrdemServico ordemServico) throws Exception {
		//notaFiscal = buscarPorId(notaFiscal.getId());
		Set<OrdemServico> listaOrdemServico = notaFiscal.getOrdensServico(); 
		Set<OrdemServico> listaOrdemServicoRetorno = new HashSet<OrdemServico>(); 
		notaFiscal.setOrdensServico(null);
		try {	
			if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
				for(OrdemServico os : listaOrdemServico){
					os = ordemServicoService.buscarPorIdSimples(os.getId());
					if(os.getId().equals(ordemServico.getId()) || (os.getUnidadePai() != null && os.getUnidadePai().getId().equals(ordemServico.getId()))){
						os.setNotaFiscalSaida(null);
						ordemServicoService.salvarSimplesOrdemServico(os);
					}else{
						listaOrdemServicoRetorno.add(os);
					}
				}
				notaFiscal.setOrdensServico(listaOrdemServicoRetorno);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscal;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa adicionarOrdemServico(NotaFiscalRemessa notaFiscal) throws Exception {
		try {	
			for(OrdemServico os : notaFiscal.getOrdensServico()){
				os = ordemServicoService.buscarPorIdSimples(os.getId());
				os.setNotaFiscalSaida(notaFiscal);
				ordemServicoService.salvarSimplesOrdemServico(os);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscal;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa salvarNotaFiscalRemessa(NotaFiscalRemessa notaFiscalRemessa) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva;
		Set<Volume> volumesNFSaida = notaFiscalRemessa.getVolumes();
		Set<Volume> volumesSalvos = new HashSet<Volume>();
		notaFiscalRemessa.setVolumes(null);
		try {
			notaFiscalRemessaSalva =(NotaFiscalRemessa) salvarNotaFiscalRemessaSimples(notaFiscalRemessa);
			for(Volume v : volumesNFSaida){
				v.setNotaFiscalSaida(notaFiscalRemessaSalva);
				v = volumeService.salvarVolume(v);
				volumesSalvos.add(v);
			}
			notaFiscalRemessaSalva.setVolumes(volumesSalvos);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa salvarNotaFiscalRemessaSimples(NotaFiscalRemessa notaFiscalRemessa) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva;

		try {
			if(notaFiscalRemessa.getId() == null || notaFiscalRemessa.getId().equals(new Long(0)))
				notaFiscalRemessaSalva =(NotaFiscalRemessa) notaFiscalRemessaDao.save(notaFiscalRemessa);
			else
				notaFiscalRemessaSalva =(NotaFiscalRemessa) notaFiscalRemessaDao.update(notaFiscalRemessa);

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa emitirNotaFiscalRemessa(NotaFiscalRemessa notaFiscalRemessa) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaSalva;
		Set<Volume> volumesNFSaida = notaFiscalRemessa.getVolumes();
		Set<Volume> volumesSalvos = new HashSet<Volume>();
		notaFiscalRemessa.setVolumes(null);
		try {
			notaFiscalRemessa.setNumero(notaFiscalRemessa.getNumero().trim());
			notaFiscalRemessa.setStatusString(Emitida.nome);
			notaFiscalRemessa.setDtEmissao(new Date());
			notaFiscalRemessaSalva =(NotaFiscalRemessa) notaFiscalRemessaDao.update(notaFiscalRemessa);
			for(Volume v : volumesNFSaida){
				v.setNotaFiscalSaida(notaFiscalRemessaSalva);
				v = volumeService.salvarVolume(v);
				volumesSalvos.add(v);
			}
			notaFiscalRemessaSalva.setVolumes(volumesSalvos);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirNotaFiscalRemessa(NotaFiscalRemessa notaFiscalRemessa) throws Exception {
		try {
			notaFiscalRemessaDao.delete(notaFiscalRemessa);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Boolean verificarDisponibilidadeNumeroOrdemServico(NotaFiscalRemessa notaFiscalSaida) {
		try{
			NotaFiscalRemessa nfEncontrada = notaFiscalRemessaDao.verificarDisponibilidadeNumeroNotaFiscalSaida(notaFiscalSaida.getNumero());
			if(nfEncontrada == null){
				return true;
			}else{
				return false;
			}
		}catch(EmptyResultDataAccessException e){
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public Boolean verificarNumeroNotaFiscalSaida(NotaFiscalRemessa notaFiscalSaida, String numeroNotaFiscal) {
		try{
			NotaFiscalRemessa nfEncontrada = notaFiscalRemessaDao.verificarNumeroNotaFiscalSaida(numeroNotaFiscal);
			if(nfEncontrada == null){
				return true;
			}else{
				if(notaFiscalSaida.getId().equals(nfEncontrada.getId())){
					return true;
				}
				return false;
			}
		}catch(EmptyResultDataAccessException e){
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Boolean retornarOsParaBaixa(NotaFiscalRemessa notaFiscalSaida) {
		try{
			Set<NotaFiscal> listaNotaFiscal = new HashSet<NotaFiscal>();
			
			notaFiscalSaida = buscarPorId(notaFiscalSaida.getId());
			
			//Colocar as OS's nos estado para baixa
			for(OrdemServico ordemServico : notaFiscalSaida.getOrdensServico()){
				ordemServico.setBloqueado(0);
				ordemServico.setDataConhecimentoExpedicao(null);
				ordemServico.setDataEmissaoNotaFiscalSaida(null);
				ordemServico.setDataFinalizacao(null);
				ordemServico.setDataGarantiaAte(null);
				ordemServico.setNumeroNotaFiscalSaida(null);
				ordemServico.setOrigemFaturamento(null);
				ordemServico.setStatusString(AguardandoExpedicao.nome);
				ordemServico.setValorFaturado(new BigDecimal(0));
				ordemServico.setValorSistema(new BigDecimal(0));
				ordemServico.setValorUnitario(new BigDecimal(0));
				ordemServico.setNotaFiscalSaida(null);
				
				if(ordemServico.getNotaFiscal() != null) {
					listaNotaFiscal.add(ordemServico.getNotaFiscal());
				}
				
				ordemServicoService.salvarSimplesOrdemServico(ordemServico);
			}
			
			//Atualizar status das notas fiscais de entrada
			for(NotaFiscal notaFiscal : listaNotaFiscal) {
				notaFiscal.setStatusString(Aberta.nome);
				notaFiscalService.salvarNotaFiscalSimples(notaFiscal);				
			}
			
			//Deletar os volumes associados
			for(Volume volume : notaFiscalSaida.getVolumes()){
				volumeService.excluirVolume(volume);
			}
			
			//Deletar a notafiscal
			notaFiscalRemessaDao.delete(notaFiscalSaida);
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa atualizarNumeroNotaFiscalSaida(NotaFiscalRemessa notaFiscalSaida, String novoNumero) {
		try{
			if(notaFiscalSaida.getNumero() == novoNumero || verificarNumeroNotaFiscalSaida(notaFiscalSaida,novoNumero)){
			verificarDisponibilidadeNumeroOrdemServico(notaFiscalSaida);
			notaFiscalSaida = buscarPorId(notaFiscalSaida.getId());
			notaFiscalSaida.setNumero(novoNumero);
			notaFiscalRemessaDao.update(notaFiscalSaida);	
			}else{
				throw new NumeroNotaFiscalSaidaNaoDisponivelException(novoNumero);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return notaFiscalSaida;
	}
	
}
