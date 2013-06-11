package br.com.sose.service.recebimento;

import java.math.BigDecimal;
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

import br.com.sose.daoImpl.administrativo.parceiros.EnderecoDao;
import br.com.sose.daoImpl.administrativo.parceiros.PessoaDao;
import br.com.sose.daoImpl.expedicao.VolumeDao;
import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.expedicao.Volume;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.exceptions.NumeroOrdemServicoFilhaIgualPaiException;
import br.com.sose.exceptions.NumeroOrdemServicoMaiorQuePermitidoException;
import br.com.sose.exceptions.NumeroOrdemServicoNaoDisponivelException;
import br.com.sose.exceptions.OrdemServicoNaoEncontradaException;
import br.com.sose.mensagens.RecebimentoMessageService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.proposta.ItemPropostaService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.AguardandoOrcamento;
import br.com.sose.status.aplicacao.AguardandoReparo;
import br.com.sose.status.reparo.AguardandoLiberacao;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.DateUtils;
import br.com.sose.utils.OrdemServicoUtils;

@Service(value="ordemServicoService")
@RemotingDestination(value="ordemServicoService")
public class OrdemServicoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public OrdemServicoDao ordemServicoDao;

	@Autowired
	private ReparoService reparoService;

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	public ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	@Autowired
	private ItemPropostaService itemPropostaService;

	@Autowired
	private NotaFiscalService notaFiscalService;

	@Autowired
	private ItemNotaFiscalService itemNotaFiscalService;

	@Autowired
	private NotaFiscalRemessaService notaFiscalSaidaService;

	@Autowired
	private RecebimentoMessageService recebimentoMessageService;

	@Autowired
	private PessoaDao pessoaDao;

	@Autowired
	private EnderecoDao enderecoDao;

	@Autowired
	private VolumeDao volumeDao;

	/********************** Metodos de listagem *********************/
	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrdemServico> listarOrdemServicos() throws Exception {
		List<OrdemServico> ordemServicos;
		try {
			ordemServicos =(List<OrdemServico>) ordemServicoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return ordemServicos;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrdemServico> listarHistorico(OrdemServico ordemServico){
		List<OrdemServico> ordemServicoRetorno = new ArrayList<OrdemServico>();
		List<OrdemServico> ordemServicos = new ArrayList<OrdemServico>();
		ordemServicos = ordemServicoDao.listarHistorico(ordemServico.getUnidade(), ordemServico.getSerieFabricante(),ordemServico.getSerieCliente());
		if(ordemServicos != null && !ordemServicos.isEmpty()){
			for (OrdemServico o : ordemServicos){
				if(!o.getId().equals(ordemServico.getId())){
					ordemServicoRetorno.add(o);
				}
			}
		}
		return ordemServicoRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public String observacaoConsolidadaConsulta(OrdemServico ordemServico) throws Exception {
		String ordemServicoRetorno = null;
		ordemServicoRetorno = ordemServicoDao.observacaoConsolidadaConsulta(ordemServico);		
		return ordemServicoRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrdemServico> listarOrdemServicoAFaturar(Pessoa cliente, Date de, Date ate){
		List<OrdemServico> ordemServicoRetorno = null;
		ordemServicoRetorno = ordemServicoDao.listarOrdemFaturamentoAFaturar(cliente, de, ate);		
		return ordemServicoRetorno;
	}
	/********************** Metodos de listagem *********************/

	/*********************** Metodos de busca ***********************/

	@RemotingInclude
	public void testarPublicacao(){
		try{
			recebimentoMessageService.enviarMensagemRecebimento("TESTE", new OrdemServico());
		}catch(Exception e){

		}
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarPorId(Long id) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico montarConjunto(Long id) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.montarConjunto(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarPorIdSimples(Long id) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorIdSimples(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarPorIdCriarProposta(Long id) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorIdCriarProposta(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarPorOrdemServico(String numero) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorNumeroOrdemServico(numero);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw new OrdemServicoNaoEncontradaException(numero);
		}
		return osEncontrada;
	}
	
	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarPorOrdemServicoSimples(String numero) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorNumeroOrdemServicoSimples(numero);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw new OrdemServicoNaoEncontradaException(numero);
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarConsultaId(Long id) throws Exception {
		OrdemServico osEncontrada = null;
		try {
			osEncontrada = ordemServicoDao.buscarConsultaId(id);
			if(osEncontrada.getNotaFiscal() != null && osEncontrada.getNotaFiscal().getId() != null){
				NotaFiscal nf = osEncontrada.getNotaFiscal();
				List<ItemNotaFiscal> inf = itemNotaFiscalService.buscarPorNotaFiscal(nf);
				nf.setItensDaNotaFiscal(new HashSet<ItemNotaFiscal>());
				nf.getItensDaNotaFiscal().addAll(inf);
				osEncontrada.setNotaFiscal(nf);
			}

			if(osEncontrada.getProposta() != null && osEncontrada.getProposta().getId() != null){
				Proposta p = osEncontrada.getProposta();
				List<ItemProposta> ip = itemPropostaService.listarPorProposta(p);
				p.setItensProposta(new HashSet<ItemProposta>());
				p.getItensProposta().addAll(ip);
				osEncontrada.setProposta(p);
			}

			if(osEncontrada.getNotaFiscalSaida() != null && osEncontrada.getNotaFiscalSaida().getId() != null){
				NotaFiscalRemessa nfs = osEncontrada.getNotaFiscalSaida();
				if(nfs.getCliente() != null){
					//					nfs.setCliente(pessoaDao.buscarPorIdOtimizado(nfs.getIdCliente()));
					List<Endereco> ends = enderecoDao.listarPorCliente(nfs.getCliente());
					nfs.getCliente().setEnderecos(new HashSet<Endereco>());
					nfs.getCliente().getEnderecos().addAll(ends);					
				}

				List<OrdemServico> ossnfs = ordemServicoDao.listarOrdemServicoPorNotaFiscalSaida(nfs);
				if(ossnfs != null){
					nfs.setOrdensServico(new HashSet<OrdemServico>());
					nfs.getOrdensServico().addAll(ossnfs);
				}

				List<Volume> vls = volumeDao.listarPorNotaFiscalSaida(nfs);
				if(vls != null){
					nfs.setVolumes(new HashSet<Volume>());
					nfs.getVolumes().addAll(vls);
				}
				osEncontrada.setNotaFiscalSaida(nfs);
			}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarConsultaId2(Long id) throws Exception {
		OrdemServico osEncontrada = null;
		try {
			osEncontrada = ordemServicoDao.buscarPorId2(id);
			if(osEncontrada.getNotaFiscal() != null && osEncontrada.getNotaFiscal().getId() != null){
				NotaFiscal nf = notaFiscalService.buscarPorIdSimples(osEncontrada.getNotaFiscal().getId());
				List<ItemNotaFiscal> inf = itemNotaFiscalService.buscarPorNotaFiscal(nf);
				nf.setItensDaNotaFiscal(new HashSet<ItemNotaFiscal>());
				nf.getItensDaNotaFiscal().addAll(inf);
				osEncontrada.setNotaFiscal(nf);
			}
			if(osEncontrada.getNotaFiscalSaida() != null && osEncontrada.getNotaFiscalSaida().getId() != null){
				NotaFiscalRemessa nfs = notaFiscalSaidaService.buscarPorIdOtimizado(osEncontrada.getNotaFiscalSaida().getId());
				if(nfs.getIdCliente() != null){
					nfs.setCliente(pessoaDao.buscarPorIdOtimizado(nfs.getIdCliente()));
					List<Endereco> ends = enderecoDao.listarPorCliente(nfs.getCliente());
					nfs.getCliente().setEnderecos(new HashSet<Endereco>());
					nfs.getCliente().getEnderecos().addAll(ends);					
				}
				if(nfs.getIdTransportador() != null){
					nfs.setTransportador(pessoaDao.buscarPorIdOtimizado(nfs.getIdTransportador()));
				}
				if(nfs.getIdEnderecoEntrega() != null){
					nfs.setEnderecoEntrega(enderecoDao.buscarPorIdOtimizado(nfs.getIdEnderecoEntrega()));
				}

				List<Volume> vls = volumeDao.listarPorNotaFiscalSaida(nfs);
				if(vls != null){
					nfs.setVolumes(new HashSet<Volume>());
					nfs.getVolumes().addAll(vls);
				}
				osEncontrada.setNotaFiscalSaida(nfs);
			}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
		}
		return osEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico buscarCompletoPorId(Long id) throws Exception {
		OrdemServico osEncontrada;
		try {
			osEncontrada =(OrdemServico) ordemServicoDao.buscarPorId(id);	
			if(osEncontrada != null){
				if(osEncontrada.getReparo() != null){
					Reparo rep = reparoService.buscarPorId(osEncontrada.getReparo().getId());
					if(rep != null){
						osEncontrada.setReparo(rep);
					}else{
						osEncontrada.setReparo(null);
					}
				}
				if(osEncontrada.getOrcamento() != null){
					Orcamento orc = orcamentoService.buscarPorId(osEncontrada.getOrcamento().getId());
					if(orc != null){
						osEncontrada.setOrcamento(orc);
					}else{
						osEncontrada.setOrcamento(null);
					}
				}
				if(osEncontrada.getPlacasFilhas() != null && !osEncontrada.getPlacasFilhas().isEmpty()){
					for(OrdemServico osFilha : osEncontrada.getPlacasFilhas()){
						if(osFilha.getReparo() != null){
							Reparo rep = reparoService.buscarPorId(osFilha.getReparo().getId());
							if(rep != null){
								osFilha.setReparo(rep);
							}else{
								osFilha.setReparo(null);
							}
						}
						if(osFilha.getOrcamento() != null){
							Orcamento orc = orcamentoService.buscarPorId(osFilha.getOrcamento().getId());
							if(orc != null){
								osFilha.setOrcamento(orc);
							}else{
								osFilha.setOrcamento(null);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}


	/*********************** Metodos de busca ***********************/

	/*********************** Metodos de acao ************************/

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico salvarOrdemServico(OrdemServico ordemServico) throws Exception {
		OrdemServico ordemServicoSalva;
		Set<OrdemServico> placasFilha = ordemServico.getPlacasFilhas();
		Set<OrdemServico> placasFilhaRetorno;
		try {
			ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServico);	
			if(placasFilha != null && !placasFilha.isEmpty()){
				placasFilhaRetorno = new HashSet<OrdemServico>();
				for(OrdemServico pf : placasFilha){
					pf.setUnidadePai(ordemServicoSalva);
					pf = salvarSimplesOrdemServico(pf);
					placasFilhaRetorno.add(pf);
				}
				ordemServicoSalva.setPlacasFilhas(placasFilhaRetorno);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return ordemServicoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico salvarSimplesOrdemServico(OrdemServico ordemServico) throws Exception {
		OrdemServico ordemServicoSalva;
		try {
			if(ordemServico.getId() == null || ordemServico.getId().equals(new Long(0)))
				ordemServicoSalva =(OrdemServico) ordemServicoDao.save(ordemServico);	
			else
				ordemServicoSalva =(OrdemServico) ordemServicoDao.update(ordemServico);	

			ordemServicoDao.flush();
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return ordemServicoSalva;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Boolean corrigirOrdemServico(String nOSBuscada, String nOS, String nsFabricante, String nsCliente,String osCliente, Usuario realizadoPor) throws Exception {
		Boolean updated = false;
		try {
			OrdemServico osEncontrada = ordemServicoDao.buscarPorNumeroOrdemServicoSimples(nOSBuscada);
			OrdemServico osInformada = ordemServicoDao.buscarPorNumeroOrdemServicoSimples(nOS);
			if(osInformada != null && !osEncontrada.getId().equals(osInformada.getId())){
				throw new NumeroOrdemServicoNaoDisponivelException(osInformada.getNumeroOrdemServico());
			}
			int i = ordemServicoDao.corrigirOrdemServico(nOSBuscada, nOS, nsFabricante, nsCliente, osCliente);
			
			observacaoService.log("Recebimento", "Atualização de informações: Nº OS("+osEncontrada.getNumeroOrdemServico()+"->"+nOS+"), N/S Fabricante("+osEncontrada.getSerieFabricante()+"->"+nsFabricante+"), N/S Cliente("+osEncontrada.getSerieCliente()+"->"+nsCliente+"), OS Cliente("+osEncontrada.getOrdemServicoCliente()+"->"+osCliente+")", 2, new Date(), osEncontrada, realizadoPor);

			if(i > 0){
				updated = true;
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return updated;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico processarOrdemServico(OrdemServico ordemServico, Usuario realizadoPor) throws Exception {
		if(verificarDisponibilidadeNumeroOrdemServico(ordemServico)){
			OrdemServico ordemServicoSalva = null;
			Set<OrdemServico> placasFilha = ordemServico.getPlacasFilhas();
			ordemServico.setPlacasFilhas(null);
			Set<OrdemServico> placasFilhaRetornada;
			if(placasFilha != null && !placasFilha.isEmpty()){
				for(OrdemServico os : placasFilha){
					if(os.getNumeroOrdemServico().equals(ordemServico.getNumeroOrdemServico())){
						throw new NumeroOrdemServicoFilhaIgualPaiException(ordemServico.getNumeroOrdemServico(),os.getNumeroOrdemServico());
					}
					if(!verificarDisponibilidadeNumeroOrdemServico(os)){
						throw new NumeroOrdemServicoNaoDisponivelException(os.getNumeroOrdemServico());
					}
				}
			}
			try {
				ordemServicoSalva = configurarParametrosOrdemServico(ordemServico,realizadoPor);

				if(placasFilha != null && !placasFilha.isEmpty()){
					placasFilhaRetornada = new HashSet<OrdemServico>();
					for(OrdemServico pf : placasFilha){
						pf.setCliente(pf.getCliente());
						pf.setNotaFiscal(pf.getNotaFiscal());
						pf.setLpu(pf.getLpu());
						pf.setUnidade(pf.getUnidade());
						pf.setUnidadePai(ordemServicoSalva);
						pf = configurarParametrosOrdemServico(pf,realizadoPor);
						placasFilhaRetornada.add(pf);
					}
					ordemServicoSalva.setPlacasFilhas(placasFilhaRetornada);
				}
			} catch (Exception e) {
				e.printStackTrace(); logger.error(e);
				throw e;
			}
			observacaoService.log("Recebimento", "Ordem de serviço processada", 2, new Date(), ordemServicoSalva, realizadoPor);
			return ordemServicoSalva;
		}else{
			throw new NumeroOrdemServicoNaoDisponivelException(ordemServico.getNumeroOrdemServico());
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico configurarParametrosOrdemServico(OrdemServico ordemServico,Usuario realizadoPor)throws Exception {
		OrdemServico ordemServicoSalva = null;

		try{

			//Seta a data de abertura da os
			ordemServico.setDataAbertura(new Date());
			if(ordemServico.getNotaFiscal().getCaseAvaya() != null && !ordemServico.getNotaFiscal().getCaseAvaya().isEmpty()){
				ordemServico.setCaseAvaya(ordemServico.getNotaFiscal().getCaseAvaya());
			}

			if(ordemServico.getNotaFiscal().getClienteAvaya() != null && !ordemServico.getNotaFiscal().getClienteAvaya().isEmpty()){
				ordemServico.setClienteAvaya(ordemServico.getNotaFiscal().getClienteAvaya());
			}

			//NAO EH NECESSARIO CHECAR/TRATAR REPARO EXTERNO
			//			if(ordemServico.getUnidade().getLaboratorio() != null && ordemServico.getUnidade().getLaboratorio().getNome().equalsIgnoreCase("Externo")){
			//				ordemServico.setStatusString(AguardandoReparoExterno.nome);
			//				ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServico);
			//				Orcamento orcamento = new Orcamento();
			//				orcamento.setStatusString(br.com.sose.status.orcamento.AguardandoLiberacao.nome);
			//				orcamento.setDataEntrada(new Date());
			//				orcamento.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
			//				orcamento.setDataLimite(ordemServicoSalva.getNotaFiscal().getDataChegada());
			//				orcamento.setOrdemServico(ordemServicoSalva);
			//				orcamento = orcamentoService.salvarOrcamentoSimples(orcamento);
			//				ordemServicoSalva.setOrcamento(orcamento);
			//				observacaoService.log("Orçamento", "Orçamento criado", 2, new Date(), orcamento, realizadoPor);
			//			} 

			//Seta o status da os
			if(ordemServico.getCliente().getPossuiContrato()){
				//Cliente possui contrato
				ordemServico.setStatusString(AguardandoReparo.nome);
				Reparo reparo = new Reparo();
				ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServico);
				reparo.setStatusString(AguardandoLiberacao.nome);
				reparo.setDataEntrada(new Date());
				reparo.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				reparo.setDataLimite(ordemServicoSalva.getNotaFiscal().getDataChegada());
				reparo.setOrdemServico(ordemServicoSalva);
				reparo = reparoService.salvarReparoSimples(reparo);
				ordemServicoSalva.setReparo(reparo);
				observacaoService.log("Reparo", "Reparo criado", 2, new Date(),reparo, realizadoPor);
			} else if(!ordemServico.getCliente().getPossuiContrato() && ordemServico.getGarantia()){
				//Cliente nao tem contrato mas a unidade estah na garantia
				ordemServico.setStatusString(AguardandoReparo.nome);
				ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServico);
				Reparo reparo = new Reparo();
				reparo.setStatusString(AguardandoLiberacao.nome);
				reparo.setDataEntrada(new Date());
				reparo.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				reparo.setDataLimite(ordemServicoSalva.getNotaFiscal().getDataChegada());
				reparo.setOrdemServico(ordemServicoSalva);
				reparo = reparoService.salvarReparoSimples(reparo);
				ordemServicoSalva.setReparo(reparo);
				observacaoService.log("Reparo", "Reparo criado", 2, new Date(), reparo, realizadoPor);
			} else if(!ordemServico.getCliente().getPossuiContrato() && !ordemServico.getGarantia()){
				ordemServico.setStatusString(AguardandoOrcamento.nome);

				ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServico);
				Orcamento orcamento = new Orcamento();
				orcamento.setStatusString(br.com.sose.status.orcamento.AguardandoLiberacao.nome);
				orcamento.setDataEntrada(new Date());
				orcamento.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				orcamento.setDataLimite(ordemServicoSalva.getNotaFiscal().getDataChegada());
				orcamento.setOrdemServico(ordemServicoSalva);
				orcamento = orcamentoService.salvarOrcamentoSimples(orcamento);
				ordemServicoSalva.setOrcamento(orcamento);
				observacaoService.log("Orçamento", "Orçamento criado", 2, new Date(), orcamento, realizadoPor);
			}

			ordemServicoSalva =(OrdemServico) salvarSimplesOrdemServico(ordemServicoSalva);	

			if(ordemServico.getObservacao() != null && !ordemServico.getObservacao().equals("")){
				observacaoService.log("Recebimento", ordemServico.getObservacao(), 3, new Date(), ordemServicoSalva, realizadoPor);
			}


		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return ordemServicoSalva;
	}

	/**
	 * Retorna null se a ordemServico Não possui garantia
	 * Retorna a ordem de serviço encontrada como garantia
	 * @param ordemServico
	 * @return
	 */
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico verificarGarantia(OrdemServico ordemServico) throws Exception{
		List<OrdemServico> osRetornadas = ordemServicoDao.buscarOSporSeries(ordemServico.getCliente(),ordemServico.getSerieFabricante(),ordemServico.getSerieCliente(),ordemServico.getUnidade());
		Long  maiorNumeroOS = ordemServicoDao.buscarMaiorNumeroOrdemServico();
		try{
			if(Integer.parseInt(ordemServico.getNumeroOrdemServico()) > maiorNumeroOS.intValue() + 3000){
				throw new NumeroOrdemServicoMaiorQuePermitidoException(ordemServico.getNumeroOrdemServico(),  maiorNumeroOS.intValue() + 3000);
			}
			
			
			if(osRetornadas == null || osRetornadas.isEmpty()){
				//Busca invertida
				osRetornadas = ordemServicoDao.buscarOSporSeries(ordemServico.getCliente(),ordemServico.getSerieCliente(),ordemServico.getSerieFabricante(),ordemServico.getUnidade());
			}

			if(osRetornadas != null && !osRetornadas.isEmpty()){
				Date dataChegada = null;
				Date dataGarantiaAte = null;
				OrdemServico osGarantia = null;
				if(osRetornadas != null && !osRetornadas.isEmpty()){
					OrdemServico osRetornada = osRetornadas.get(0);
					if(osRetornada.getDataFinalizacao() == null ||
					   osRetornada.getReparo() != null && (osRetornada.getReparo().getCondicao().equalsIgnoreCase("Sem condição de reparo") || osRetornada.getReparo().getCondicao().equalsIgnoreCase("Devolução sem reparo")) ||
					   osRetornada.getOrcamento() != null && (osRetornada.getOrcamento().getCondicao().equalsIgnoreCase("Sem condição de reparo") || osRetornada.getOrcamento().getCondicao().equalsIgnoreCase("Devolução sem reparo"))
					   ){
						// os ainda Não foi finalizada e deve estar na servilogi
						// verificar o que deve ser feito nesse caso
						//os foi devolvida sem condicao de reparo ou devolucao sem reparo
						osGarantia = null;
					}else{
						dataChegada = ordemServico.getNotaFiscal().getDataChegada(); //Possivel null pointer
						dataGarantiaAte = DateUtils.nextDay(osRetornada.getDataGarantiaAte());
						// os Não se encontra na servilogi
						// verifica se a possui garantia (data limite de garantia eh maior que data atual)
						//TODO - talvez seja a data de abertura ou de chegada do material, e Não a data atual.
						if(dataChegada.before(dataGarantiaAte)){
							osRetornada = buscarPorId(osRetornada.getId());
							osGarantia = osRetornada;
						}
					}
				}else{
					osGarantia = null;
				}

				if(osGarantia != null){
					observacaoService.log("Recebimento", "O sistema identificou unidade em garantia (OS garantia:"+ osGarantia.getNumeroOrdemServico()+")", 2, new Date(),ordemServico, null);

				}

				return osGarantia;
			}else{
				//Nenhuma das duas buscas retornou algum valor
				return null;
			}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@Transactional(readOnly=true)
	public Boolean verificarDisponibilidadeNumeroOrdemServico(OrdemServico ordemServico) {
		try{
			OrdemServico osEncontrada = ordemServicoDao.verificarDisponibilidadeNumeroOrdemServico(ordemServico.getNumeroOrdemServico());
			if(osEncontrada == null){
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

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico totalizarValorFaturadoConjunto(ConjuntoOrdemServico conjunto) throws Exception {
		OrdemServico osPai = conjunto.getOsPai();
		BigDecimal valorTotal = new BigDecimal(0);
		ItemProposta itemProposta = null;
		try{
			itemProposta = itemPropostaService.buscarPorOrdemServico(osPai);
			if(itemProposta.getIsAprovado()){
				if(itemProposta.getValorSemDesconto() != null){
					valorTotal = valorTotal.add(itemProposta.getValorSemDesconto());
				}
			}

			for(OrdemServico osFilha : conjunto.getListaFilhas()){
				itemProposta = itemPropostaService.buscarPorOrdemServico(osFilha);
				if(itemProposta.getIsAprovado()){
					if(itemProposta.getValorSemDesconto() != null){
						valorTotal = valorTotal.add(itemProposta.getValorSemDesconto());
					}
				}
			}

			osPai.setValorFaturado(valorTotal);
			osPai.setValorSistema(valorTotal);

		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			throw e;
		}

		return osPai;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrdemServico calcularValorFaturado(OrdemServico ordemServico) throws Exception {
		try {
			//Configura valores
			if(ordemServico.getGarantia() || ordemServico.getUnidadePai() != null){
				ordemServico.setValorFaturado(new BigDecimal(0));
				ordemServico.setValorSistema(new BigDecimal(0));
			}else{
				if((ordemServico.getReparo() != null )){
					//Se possui reparo, este pode ser: COM CONDICAO, SEM CONDICAO OU DEVLOUCAO SEM REPARO
					if(ordemServico.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO)){
						//COM CONDICAO
						if(ordemServico.getProposta() != null && ordemServico.getProposta().getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO)){
							//ORCAMENTO DIFERENCIDO
							ItemProposta itemProposta = itemPropostaService.buscarPorOrdemServico(ordemServico);
							ordemServico.setValorFaturado(itemProposta.getValorSemDesconto());
							ordemServico.setValorSistema(itemProposta.getValorSemDesconto());
						}else if(ordemServico.getLpu() != null){
							//LPU
							//TODO - modificar o metodo calcular valor faturado
							//ordemServico.setValorFaturado(ordemServico.getLpu().getValorReparo());
							//ordemServico.setValorSistema(ordemServico.getLpu().getValorReparo());
						}else{
							//ORCAMENTO
							ItemProposta itemProposta = itemPropostaService.buscarPorOrdemServico(ordemServico);
							ordemServico.setValorFaturado(itemProposta.getValorSemDesconto());
							ordemServico.setValorSistema(itemProposta.getValorSemDesconto());
						}
					}else{
						//SEM CONDICAO OU DEVOLUCAO SEM REPARO
						ordemServico.setValorFaturado(new BigDecimal(0));
						ordemServico.setValorSistema(new BigDecimal(0));
					}
				}else{
					//ORCAMENTO - se chegou aqui ou Devolvido sem reparo ou Sem condicao de reparo ou proposta reprovada
					ordemServico.setValorFaturado(new BigDecimal(0));
					ordemServico.setValorSistema(new BigDecimal(0));
				}
			}
			
			//Configura origem
			if(ordemServico.getGarantia()){
				ordemServico.setOrigemFaturamento("Garantia");
			}else{
				if((ordemServico.getReparo() != null )){
					if(ordemServico.getProposta() != null && ordemServico.getProposta().getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO)){
						//ORCAMENTO DIFERENCIDO
						ordemServico.setOrigemFaturamento("Orçamento diferenciado");
					}else if(ordemServico.getLpu() != null){
						//LPU
						ordemServico.setOrigemFaturamento("LPU");
					}else{
						//ORCAMENTO
						ordemServico.setOrigemFaturamento("Orçamento");
					}
				}else{
					ordemServico.setOrigemFaturamento("Orçamento");
				}
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		
		return ordemServico;
	}
	
	
	/**
	 * 
	 * 
	 * 
				if(ordemServicoUtils.pertenceConjunto(ordemServico)){
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);
					if(cos.getOsPai().getGarantia()){
						cos.getOsPai().setValorFaturado(new BigDecimal(0));
						cos.getOsPai().setValorSistema(new BigDecimal(0));
						cos.getOsPai().setOrigemFaturamento("Garantia");
						salvarSimplesOrdemServico(cos.getOsPai());
					}else{
						if((cos.getOsPai().getReparo() != null && cos.getOsPai().getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO))){
							if(cos.getOsPai().getProposta() != null && cos.getOsPai().getProposta().getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO)){
								OrdemServico osPai = totalizarValorFaturadoConjunto(cos);
								osPai.setOrigemFaturamento("Orçamento diferenciado");
								salvarSimplesOrdemServico(osPai);
							}else if(cos.getOsPai().getLpu() != null){
								cos.getOsPai().setValorFaturado(cos.getOsPai().getLpu().getValorReparo());
								cos.getOsPai().setValorSistema(cos.getOsPai().getValorFaturado());
								cos.getOsPai().setOrigemFaturamento("LPU");
								salvarSimplesOrdemServico(cos.getOsPai());
							}else{
								OrdemServico osPai = totalizarValorFaturadoConjunto(cos);
								osPai.setOrigemFaturamento("Orçamento");
								salvarSimplesOrdemServico(osPai);
							}
						}else{
							cos.getOsPai().setValorFaturado(new BigDecimal(0));
							cos.getOsPai().setValorSistema(new BigDecimal(0));
							cos.getOsPai().setOrigemFaturamento("");
							salvarSimplesOrdemServico(cos.getOsPai());
						}


					}

				}else{
					if(ordemServico.getGarantia()){
						ordemServico.setValorFaturado(new BigDecimal(0));
						ordemServico.setValorSistema(new BigDecimal(0));
						ordemServico.setOrigemFaturamento("Garantia");
						salvarSimplesOrdemServico(ordemServico);
					}else{
						if((ordemServico.getReparo() != null )){
							if(ordemServico.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO)){
								if(ordemServico.getProposta() != null && ordemServico.getProposta().getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO)){
									ItemProposta itemProposta = itemPropostaService.buscarPorOrdemServico(ordemServico);
									ordemServico.setValorFaturado(itemProposta.getValorSemDesconto());
									ordemServico.setValorSistema(itemProposta.getValorSemDesconto());
									ordemServico.setOrigemFaturamento("Orçamento diferenciado");
								}else if(ordemServico.getLpu() != null){
									ordemServico.setValorFaturado(ordemServico.getLpu().getValorReparo());
									ordemServico.setValorSistema(ordemServico.getLpu().getValorReparo());
									ordemServico.setOrigemFaturamento("LPU");
								}else{
									ItemProposta itemProposta = itemPropostaService.buscarPorOrdemServico(ordemServico);
									ordemServico.setValorFaturado(itemProposta.getValorSemDesconto());
									ordemServico.setValorSistema(itemProposta.getValorSemDesconto());
									ordemServico.setOrigemFaturamento("Orçamento");
								}
//								ordemServicoDao.clear();
								salvarSimplesOrdemServico(ordemServico);
							}else{
								ordemServico.setValorFaturado(new BigDecimal(0));
								ordemServico.setValorSistema(new BigDecimal(0));
								if(ordemServico.getProposta() != null && ordemServico.getProposta().getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO)){
									ordemServico.setOrigemFaturamento("Orçamento diferenciado");
								}else if(ordemServico.getLpu() != null){
									ordemServico.setOrigemFaturamento("LPU");
								}else{
									ordemServico.setOrigemFaturamento("Orçamento");
								}
//								ordemServicoDao.clear();
								salvarSimplesOrdemServico(ordemServico);
							}
						}else{
							ordemServico.setValorFaturado(new BigDecimal(0));
							ordemServico.setValorSistema(new BigDecimal(0));
							ordemServico.setOrigemFaturamento("Orçamento");
							salvarSimplesOrdemServico(ordemServico);
						}


					}

				}
	 */

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirOrdemServico(OrdemServico ordemServico) throws Exception {
		try {
			ordemServicoDao.delete(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico darGarantia(OrdemServico ordemServico, Usuario realizadoPor) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(ordemServico)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);
				cos.getOsPai().setGarantia(true);
				observacaoService.log("Recebimento", "Garantia dada pelo usuário", 2, new Date(), cos.getOsPai(), realizadoPor);
				salvarSimplesOrdemServico(cos.getOsPai());

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setGarantia(true);
					observacaoService.log("Recebimento", "Garantia dada pelo usuário", 2, new Date(), osFilha, realizadoPor);
					salvarSimplesOrdemServico(osFilha);
				}
				cos.getOsPai().setPlacasFilhas(cos.getListaFilhas());
				return cos.getOsPai(); 

			}else{
				ordemServico.setGarantia(true);
				salvarSimplesOrdemServico(ordemServico);
				observacaoService.log("Recebimento", "Garantia dada pelo usuário", 2, new Date(), ordemServico, realizadoPor);
				return ordemServico;
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico retirarGarantia(OrdemServico ordemServico, Usuario realizadoPor) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(ordemServico)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);
				cos.getOsPai().setGarantia(false);
				salvarSimplesOrdemServico(cos.getOsPai());
				observacaoService.log("Recebimento", "Garantia retirada pelo usuário", 2, new Date(), cos.getOsPai(), realizadoPor);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setGarantia(false);
					salvarSimplesOrdemServico(osFilha);
					observacaoService.log("Recebimento", "Garantia retirada pelo usuário", 2, new Date(), osFilha, realizadoPor);
				}
				cos.getOsPai().setPlacasFilhas(cos.getListaFilhas());
				return cos.getOsPai(); 

			}else{
				ordemServico.setGarantia(false);
				salvarSimplesOrdemServico(ordemServico);
				observacaoService.log("Recebimento", "Garantia retirada pelo usuário", 2, new Date(), ordemServico, realizadoPor);
				return ordemServico;
			}	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico darExtensaoGarantia(OrdemServico ordemServico, Usuario realizadoPor) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(ordemServico)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);
				cos.getOsPai().setEstenderGarantia(true);
				salvarSimplesOrdemServico(cos.getOsPai());
				observacaoService.log("Recebimento", "Garantia extendida", 2, new Date(), cos.getOsPai(), realizadoPor);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setEstenderGarantia(true);
					salvarSimplesOrdemServico(osFilha);
					observacaoService.log("Recebimento", "Garantia extendida", 2, new Date(), osFilha, realizadoPor);
				}
				cos.getOsPai().setPlacasFilhas(cos.getListaFilhas());
				return cos.getOsPai(); 

			}else{
				ordemServico.setEstenderGarantia(true);
				salvarSimplesOrdemServico(ordemServico);
				observacaoService.log("Recebimento", "Garantia extendida", 2, new Date(), ordemServico, realizadoPor);
				return ordemServico;
			}	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico retirarExtensaoGarantia(OrdemServico ordemServico, Usuario realizadoPor) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(ordemServico)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);
				cos.getOsPai().setEstenderGarantia(false);
				salvarSimplesOrdemServico(cos.getOsPai());
				observacaoService.log("Recebimento", "Extensão da garantia retirada", 2, new Date(), cos.getOsPai(), realizadoPor);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.setEstenderGarantia(false);
					salvarSimplesOrdemServico(osFilha);
					observacaoService.log("Recebimento", "Extensão da garantia retirada", 2, new Date(), osFilha, realizadoPor);
				}
				cos.getOsPai().setPlacasFilhas(cos.getListaFilhas());
				return cos.getOsPai(); 

			}else{
				ordemServico.setEstenderGarantia(false);
				salvarSimplesOrdemServico(ordemServico);
				observacaoService.log("Recebimento", "Extensão da garantia retirada", 2, new Date(), ordemServico, realizadoPor);
				return ordemServico;
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}


}
