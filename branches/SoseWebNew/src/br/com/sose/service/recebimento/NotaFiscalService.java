package br.com.sose.service.recebimento;

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

import br.com.sose.daoImpl.recebimento.ItemNotaFiscalDao;
import br.com.sose.daoImpl.recebimento.NotaFiscalDao;
import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.status.aplicacao.NaoProcessada;
import br.com.sose.status.recebimento.Aberta;
import br.com.sose.status.recebimento.Cancelada;
import br.com.sose.status.recebimento.Finalizada;
import br.com.sose.status.recebimento.Nova;
import br.com.sose.status.recebimento.Processada;


@Service(value="notaFiscalService")
@RemotingDestination(value="notaFiscalService")
public class NotaFiscalService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public NotaFiscalDao notaFiscalDao;

	@Autowired
	public ItemNotaFiscalService itemNotaFiscalService;

	@Autowired
	public OrdemServicoService ordemServicoService;

	@Autowired
	public ObservacaoService observacaoService;

	/********************** Metodos de listagem *********************/
	@RemotingInclude
	@Transactional(readOnly=true)
	public List<NotaFiscal> listarTodasNotaFiscal() throws Exception {
		List<NotaFiscal> notaFiscals;
		try {
			notaFiscals =(List<NotaFiscal>) notaFiscalDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscals;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<NotaFiscal> listarNotaFiscalPorCliente(Pessoa cliente) throws Exception {
		List<NotaFiscal> notaFiscais;
		try {

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return null;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<NotaFiscal> listarNotaFiscal() throws Exception {
		List<NotaFiscal> notasFiscais = new ArrayList<NotaFiscal>();
		List<NotaFiscal> notasFiscaisAux = null;
		try {
			notasFiscaisAux = notaFiscalDao.listarNotaFiscal(Nova.nome);
			if(notasFiscaisAux != null && !notasFiscaisAux.isEmpty()) notasFiscais.addAll(notasFiscaisAux);
			notasFiscaisAux = notaFiscalDao.listarNotaFiscal(Processada.nome);
			if(notasFiscaisAux != null && !notasFiscaisAux.isEmpty()) notasFiscais.addAll(notasFiscaisAux);
			notasFiscaisAux = notaFiscalDao.listarNotaFiscal(Aberta.nome);
			if(notasFiscaisAux != null && !notasFiscaisAux.isEmpty()) notasFiscais.addAll(notasFiscaisAux);
			notasFiscaisAux = notaFiscalDao.listarNotaFiscal(Finalizada.nome);
			if(notasFiscaisAux != null && !notasFiscaisAux.isEmpty()) notasFiscais.addAll(notasFiscaisAux);
			notasFiscaisAux = notaFiscalDao.listarNotaFiscal(Cancelada.nome);
			if(notasFiscaisAux != null && !notasFiscaisAux.isEmpty()) notasFiscais.addAll(notasFiscaisAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notasFiscais;
	}

	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/

	@RemotingInclude
	@Transactional(readOnly=true)
	public NotaFiscal buscarPorId(Long id) throws Exception {
		NotaFiscal notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscal) notaFiscalDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public NotaFiscal buscarPorId2(Long id) throws Exception {
		NotaFiscal notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscal) notaFiscalDao.buscarPorId2(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public NotaFiscal buscarPorIdSimples(Long id) throws Exception {
		NotaFiscal notaFiscalEncontrada;
		try {
			notaFiscalEncontrada =(NotaFiscal) notaFiscalDao.buscarPorIdSimples(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public NotaFiscal buscarPorNumeroNotaFiscal(String numeroNotaFiscal) throws Exception {
		NotaFiscal notaFiscalEncontrada=null;
		try {
//			notaFiscalEncontrada = notaFiscalDao.buscarPorId(notaFiscal.getId());

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}
	
	@RemotingInclude
	@Transactional(readOnly=true)
	public List<NotaFiscal> buscarPorNumeroCliente(String numeroNotaFiscal,Long idCliente) throws Exception {
		List<NotaFiscal> notaFiscalEncontrada=null;
		try {
			notaFiscalEncontrada = notaFiscalDao.buscarPorNumeroCliente(numeroNotaFiscal,idCliente);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalEncontrada;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void finalizarNotaFiscal(NotaFiscal notaFiscal) throws Exception {
		notaFiscal = notaFiscalDao.buscarPorId(notaFiscal.getId());
		if(notaFiscal.getOrdensServico() != null){
			for(OrdemServico o : notaFiscal.getOrdensServico()){
				if(o.getNotaFiscalSaida() == null || (o.getNotaFiscalSaida() != null && o.getNotaFiscalSaida().getSolicitacaoRegistradaEm() == null)){
					return;
				}
			}
			notaFiscal.setStatusString(Finalizada.nome);
			salvarNotaFiscalSimples(notaFiscal);
		}
	}

	public Boolean processamentoAberturaNotaFiscalFinalizado(NotaFiscal notaFiscal) throws Exception {
		notaFiscal = notaFiscalDao.buscarPorId(notaFiscal.getId());
		if(notaFiscal.getOrdensServico() != null){
			for(OrdemServico o : notaFiscal.getOrdensServico()){
				if(o.getStatusString() != null && (o.getStatusString().equals(NaoProcessada.nome))){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal salvarNotaFiscal(NotaFiscal notaFiscal) throws Exception {
		NotaFiscal notaFiscalSalva;
		Set<ItemNotaFiscal> itensNotaFiscal = notaFiscal.getItensDaNotaFiscal();
		notaFiscal.setItensDaNotaFiscal(null);
		try {
			if(notaFiscal.getDataCriacao() == null){
				notaFiscal.setDataCriacao(new Date());
			}
			notaFiscalSalva = salvarNotaFiscalSimples(notaFiscal);
//			if(itensNotaFiscal != null && !itensNotaFiscal.isEmpty()){
//				Set<ItemNotaFiscal> itensNotaFiscalSalvos = new HashSet<ItemNotaFiscal>();
//				for(ItemNotaFiscal inf : itensNotaFiscal){
//					inf.setNotaFiscal(notaFiscalSalva);
//					inf = itemNotaFiscalService.salvarItemNotaFiscal(inf);
//					itensNotaFiscalSalvos.add(inf);
//				}
//				notaFiscalSalva.setItensDaNotaFiscal(itensNotaFiscalSalvos);
//			}
			notaFiscalSalva.setItensDaNotaFiscal(itensNotaFiscal);

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal salvarNotaFiscalSimples(NotaFiscal notaFiscal) throws Exception {
		NotaFiscal notaFiscalSalva;
		try{
			if(notaFiscal.getId() == null || notaFiscal.getId().equals(new Long(0)))
				notaFiscalSalva =(NotaFiscal) notaFiscalDao.save(notaFiscal);
			else
				notaFiscalSalva =(NotaFiscal) notaFiscalDao.update(notaFiscal);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal procesarNotaFiscal(NotaFiscal notaFiscal,Usuario realizadoPor) throws Exception {
		Set<OrdemServico> ordemServicoCriadas;
		try {
			if(notaFiscal.getItensDaNotaFiscal() != null && !notaFiscal.getItensDaNotaFiscal().isEmpty()){
				ordemServicoCriadas = new HashSet<OrdemServico>();

				for(ItemNotaFiscal inf : notaFiscal.getItensDaNotaFiscal()) {
					for (int i=0; i < inf.getQuantidade(); i++) {
						OrdemServico os = new OrdemServico();
						os.setItemNotaFiscal(inf);
						os.setUnidade(inf.getUnidade());
						os.setLpu(inf.getLpu());
						os.setNotaFiscal(notaFiscal);
						os.setCliente(notaFiscal.getCliente());
						os.setStatusString(NaoProcessada.nome);
						os = ordemServicoService.salvarSimplesOrdemServico(os);
						ordemServicoCriadas.add(os);
					}
				}
				notaFiscal.setOrdensServico(ordemServicoCriadas);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}

		observacaoService.log("Recebimento", "Nota fiscal processada", 2, new Date(), notaFiscal, realizadoPor);
		return notaFiscal;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirNotaFiscal(NotaFiscal notaFiscal) throws Exception {
		try {
			notaFiscalDao.delete(notaFiscal);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
