package br.com.sose.service.proposta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.proposta.ItemPropostaDao;
import br.com.sose.daoImpl.proposta.PropostaDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.AguardandoReparo;
import br.com.sose.status.aplicacao.OrcamentoSendoRealizado;
import br.com.sose.status.aplicacao.PropostaSendoRealizada;
import br.com.sose.status.aplicacao.ReparoSendoRealizado;
import br.com.sose.status.expedicao.Nova;
import br.com.sose.status.orcamento.Iniciado;
import br.com.sose.status.proposta.AguardandoAprovacaoCliente;
import br.com.sose.status.proposta.EmElaboracao;
import br.com.sose.status.proposta.Finalizada;
import br.com.sose.status.reparo.AguardandoProposta;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service(value="propostaService")
@RemotingDestination(value="propostaService")
public class PropostaService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public PropostaDao propostaDao;

	@Autowired
	public ItemPropostaService itemPropostaService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ReparoService reparoService;

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<Proposta> listarPropostas() throws Exception {
		List<Proposta> propostas = new ArrayList<Proposta>();
		List<Proposta> propostasAux = null;
		try {
			propostasAux = propostaDao.listarProposta(br.com.sose.status.proposta.Nova.nome);
			if(propostasAux != null && !propostasAux.isEmpty()) propostas.addAll(propostasAux);
			propostasAux = propostaDao.listarProposta(EmElaboracao.nome);
			if(propostasAux != null && !propostasAux.isEmpty()) propostas.addAll(propostasAux);
			propostasAux = propostaDao.listarProposta(AguardandoAprovacaoCliente.nome);
			if(propostasAux != null && !propostasAux.isEmpty()) propostas.addAll(propostasAux);
			propostasAux = propostaDao.listarProposta(Finalizada.nome);
			if(propostasAux != null && !propostasAux.isEmpty()) propostas.addAll(propostasAux);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return propostas;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<Pessoa> listarClientesBaixa() throws Exception {
		List<Pessoa> clientes;
		try {
			clientes =(List<Pessoa>) propostaDao.listarClientesBaixa();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return clientes;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrdemServico> listarOrdemServicoBaixaProposta(Pessoa cliente) throws Exception {
		List<OrdemServico> ordemServicos;
		List<OrdemServico> ordemServicosRetorno = new ArrayList<OrdemServico>();
		try {
			ordemServicos =(List<OrdemServico>) propostaDao.listarOrdemServicoBaixaProposta(cliente);	
			for(OrdemServico os: ordemServicos){
				if(os.getUnidadePai() == null){
					if(!ordemServicosRetorno.contains(os)){
						ordemServicosRetorno.add(os);
					}
				}else{
					if(!ordemServicosRetorno.contains(os.getUnidadePai())){
						ordemServicosRetorno.add(os.getUnidadePai());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return ordemServicosRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public Proposta buscarPorId(Long id) throws Exception {
		Proposta propostaEncontrada;
		try {
			propostaEncontrada =(Proposta) propostaDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<Proposta> buscarPropostasAberta() throws Exception {
		try {
			return propostaDao.buscarPropostasAberta();
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta criarPropostaOrcamento(List<OrdemServico> ordensServico) throws Exception {
		Proposta proposta = null;
		try {
			if(ordensServico != null){
				proposta = new Proposta();
				OrdemServico osAuxiliar = ordemServicoService.buscarPorIdCriarProposta(ordensServico.get(0).getId());

				proposta.setCliente(osAuxiliar.getCliente());
				proposta.setDataCriacao(new Date());
				proposta.setStatusString(Nova.nome);
				proposta.setTipo(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO);
				proposta = salvarPropostaSimples(proposta);	
				proposta.setNumero(proposta.getId().toString());
				proposta = salvarPropostaSimples(proposta);	
				for(OrdemServico os : ordensServico){
					os = ordemServicoService.buscarPorIdCriarProposta(os.getId());

					if(ordemServicoUtils.pertenceConjunto(os)){
						ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
						cos.getOsPai().setProposta(proposta);
						cos.getOsPai().setStatusString(PropostaSendoRealizada.nome);
						ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

						ItemProposta itemProposta = new ItemProposta();
						itemProposta.setOrdemServico(cos.getOsPai());
						itemProposta.setProposta(proposta);					
						itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

						proposta.getItensProposta().add(itemProposta);

						for(OrdemServico osFilha : cos.getListaFilhas()){
							osFilha.setProposta(proposta);
							osFilha.setStatusString(PropostaSendoRealizada.nome);
							ordemServicoService.salvarSimplesOrdemServico(osFilha);

							ItemProposta ip = new ItemProposta();
							ip.setOrdemServico(osFilha);
							ip.setProposta(proposta);					
							ip = itemPropostaService.salvarItemProposta(ip);

							proposta.getItensProposta().add(ip);
						}
					}else{
						os.setProposta(proposta);
						os.setStatusString(PropostaSendoRealizada.nome);
						ordemServicoService.salvarSimplesOrdemServico(os);

						ItemProposta itemProposta = new ItemProposta();
						itemProposta.setOrdemServico(os);
						itemProposta.setProposta(proposta);					
						itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

						proposta.getItensProposta().add(itemProposta);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" criada", 2, new Date(),proposta, null);
		return proposta;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Collection<Proposta> criarMultiplasPropostaOrcamento(List<OrdemServico> ordensServico) throws Exception {
		Proposta proposta = null;
		Hashtable<Long, Proposta> listaProposta = new Hashtable<Long, Proposta>();
		try {
			if(ordensServico != null){

				for(OrdemServico os : ordensServico){
					os = ordemServicoService.buscarPorIdCriarProposta(os.getId());

					proposta = listaProposta.get(os.getNotaFiscal().getId());
					if(proposta == null){
						proposta = new Proposta();
						proposta.setCliente(ordensServico.get(0).getCliente());
						proposta.setDataCriacao(new Date());
						proposta.setStatusString(Nova.nome);
						proposta.setTipo(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO);
						proposta = propostaDao.save(proposta);	
						proposta.setNumero(proposta.getId().toString());
						proposta = propostaDao.update(proposta);
						listaProposta.put(os.getNotaFiscal().getId(), proposta);
					}
					os.setProposta(proposta);
					os.setStatusString(PropostaSendoRealizada.nome);
					ordemServicoService.salvarSimplesOrdemServico(os);

					ItemProposta itemProposta = new ItemProposta();
					itemProposta.setOrdemServico(os);
					itemProposta.setProposta(proposta);					
					itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

					proposta.getItensProposta().add(itemProposta);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return listaProposta.values();
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta criarPropostaOrcamentoDiferenciado(OrdemServico ordemServico) throws Exception {
		Proposta proposta = null;
		try {
			if(ordemServico != null){
				ordemServico = ordemServicoService.buscarPorIdCriarProposta(ordemServico.getId());
				proposta = new Proposta();
				proposta.setCliente(ordemServico.getCliente());
				proposta.setDataCriacao(new Date());
				proposta.setStatusString(Nova.nome);
				proposta.setTipo(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO);
				proposta.setOrcamentoDiferenciado(ordemServico.getOrcamentoDiferenciado());
				proposta = salvarPropostaSimples(proposta);	
				proposta.setNumero(proposta.getId().toString());
				proposta = salvarPropostaSimples(proposta);

				ordemServico.setProposta(proposta);
				ordemServico.setStatusString(PropostaSendoRealizada.nome);
				ordemServicoService.salvarSimplesOrdemServico(ordemServico);

				ItemProposta itemProposta = new ItemProposta();
				itemProposta.setOrdemServico(ordemServico);
				itemProposta.setProposta(proposta);					
				itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

				proposta.getItensProposta().add(itemProposta);

				if(ordemServicoUtils.pertenceConjunto(ordemServico)){
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(ordemServico);

					for(OrdemServico osFilha : cos.getListaFilhas()){
						osFilha.setProposta(proposta);
						ordemServicoService.salvarSimplesOrdemServico(osFilha);

						ItemProposta ip = new ItemProposta();
						ip.setOrdemServico(osFilha);
						ip.setProposta(proposta);					
						ip = itemPropostaService.salvarItemProposta(ip);

						proposta.getItensProposta().add(ip);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" criada", 2, new Date(),proposta, null);

		return proposta;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<Proposta> criarPropostasOrcamentoDiferenciado(List<OrdemServico> ordensServico)throws Exception {
		List<Proposta> propostasCriadas = null;
		Proposta proposta = null;
		try{
			if(ordensServico != null){
				propostasCriadas = new ArrayList<Proposta>();
				for(OrdemServico os : ordensServico){
					proposta = criarPropostaOrcamentoDiferenciado(os);
					propostasCriadas.add(proposta);
				}
			}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostasCriadas;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta criarPropostaOrcamentoDiferenciado(List<OrdemServico> ordensServico) throws Exception {
		Proposta proposta = null;
		try {
			if(ordensServico != null){
				proposta = new Proposta();
				OrdemServico osAuxiliar = ordemServicoService.buscarPorIdCriarProposta(ordensServico.get(0).getId());

				proposta.setCliente(osAuxiliar.getCliente());
				proposta.setDataCriacao(new Date());
				proposta.setStatusString(Nova.nome);
				proposta.setTipo(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO);
				proposta = salvarPropostaSimples(proposta);	
				proposta.setNumero(proposta.getId().toString());
				proposta = salvarPropostaSimples(proposta);	
				for(OrdemServico os : ordensServico){
					os = ordemServicoService.buscarPorIdCriarProposta(os.getId());

					if(ordemServicoUtils.pertenceConjunto(os)){
						ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
						cos.getOsPai().setProposta(proposta);
						cos.getOsPai().setStatusString(PropostaSendoRealizada.nome);
						ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

						ItemProposta itemProposta = new ItemProposta();
						itemProposta.setOrdemServico(cos.getOsPai());
						itemProposta.setProposta(proposta);					
						itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

						proposta.getItensProposta().add(itemProposta);

						for(OrdemServico osFilha : cos.getListaFilhas()){
							osFilha.setProposta(proposta);
							osFilha.setStatusString(PropostaSendoRealizada.nome);
							ordemServicoService.salvarSimplesOrdemServico(osFilha);

							ItemProposta ip = new ItemProposta();
							ip.setOrdemServico(osFilha);
							ip.setProposta(proposta);					
							ip = itemPropostaService.salvarItemProposta(ip);

							proposta.getItensProposta().add(ip);
						}
					}else{
						os.setProposta(proposta);
						os.setStatusString(PropostaSendoRealizada.nome);
						ordemServicoService.salvarSimplesOrdemServico(os);

						ItemProposta itemProposta = new ItemProposta();
						itemProposta.setOrdemServico(os);
						itemProposta.setProposta(proposta);					
						itemProposta = itemPropostaService.salvarItemProposta(itemProposta);

						proposta.getItensProposta().add(itemProposta);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" criada", 2, new Date(),proposta, null);
		return proposta;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta salvarProposta(Proposta proposta) throws Exception {
		Proposta propostaSalva;
		Set<ItemProposta> itensProposta = proposta.getItensProposta();
		Set<ItemProposta> itensPropostaSalvos = new HashSet<ItemProposta>();
		proposta.setItensProposta(null);
		try {
			propostaSalva =(Proposta) salvarPropostaSimples(proposta);
			for(ItemProposta ip : itensProposta){
				ip = itemPropostaService.salvarItemProposta(ip);
				itensPropostaSalvos.add(ip);
			}
			propostaSalva.setItensProposta(itensPropostaSalvos);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta salvarPropostaSimples(Proposta proposta) throws Exception {
		Proposta propostaSalva;
		try{
			if(proposta.getId() == null || proposta.getId().equals(new Long(0)))
				propostaSalva =(Proposta) propostaDao.save(proposta);
			else
				propostaSalva =(Proposta) propostaDao.update(proposta);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta finalizarProposta(Proposta proposta) throws Exception {
		Proposta propostaSalva;
		try {

			propostaSalva =(Proposta) salvarPropostaSimples(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemAprovado(OrdemServico os){
		try{
			os.setStatusString(AguardandoReparo.nome);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Orcamento orc = os.getOrcamento();
			orc = orcamentoService.finalizarOrcamento(orc);
			Reparo rep = reparoService.criarReparo(os);
			os.setOrcamento(orc);
			os.setReparo(rep);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemReprovado(OrdemServico os){
		try{
			os.setStatusString(OrcamentoSendoRealizado.nome);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Orcamento orc = os.getOrcamento();
			orc.setStatusString(Iniciado.nome);
			orc.setPropostaReprovada(new Date());
			orc.setDataLimite(new Date());
			orc.setCondicao(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO);
			orcamentoService.salvarOrcamentoSimples(orc);
			os.setOrcamento(orc);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemSemCondicao(OrdemServico os){
		try{
			os.setStatusString(OrcamentoSendoRealizado.nome);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Orcamento orc = os.getOrcamento();
			orc.setStatusString(Iniciado.nome);
			orc.setPropostaReprovada(new Date());
			orc.setDataLimite(new Date());
			orc = orcamentoService.salvarOrcamentoSimples(orc);
			os.setOrcamento(orc);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemSemCondicaoReparo(OrdemServico os){
		try{
			os.setStatusString(ReparoSendoRealizado.nome);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Reparo rep = os.getReparo();
			rep.setStatusString(Iniciado.nome);
			rep.setPropostaReprovada(new Date());
			rep.setDataLimite(new Date());
			rep = reparoService.salvarReparoSimples(rep);
			os.setReparo(rep);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemDevolucaoSemReparo(OrdemServico os){
		try{
			os.setStatusString(OrcamentoSendoRealizado.nome);
			os.setBloqueado(9);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Orcamento orc = os.getOrcamento();
			orc.setStatusString(Iniciado.nome);
			orc.setPropostaReprovada(new Date());
			orc.setDataLimite(new Date());
			orc = orcamentoService.salvarOrcamentoSimples(orc);
			os.setOrcamento(orc);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemDevolucaoSemReparoReparo(OrdemServico os){
		try{
			os.setStatusString(ReparoSendoRealizado.nome);
			os.setBloqueado(9);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			Reparo rep = os.getReparo();
			rep.setStatusString(Iniciado.nome);
			rep.setPropostaReprovada(new Date());
			rep.setDataLimite(new Date());
			rep = reparoService.salvarReparoSimples(rep);
			os.setReparo(rep);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemAprovadoReparo(OrdemServico os){
		try{
			Reparo rep = os.getReparo();
			if(rep.getStatusString().equals(AguardandoProposta.nome)){
				rep.setStatusString(br.com.sose.status.reparo.Iniciado.nome);
				os.setStatusString(ReparoSendoRealizado.nome);
			}
			os.setBloqueado(0);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			rep.setPropostaAprovada(new Date());
			rep.setDataLimite(new Date());
			rep.setPrioridade(null);
			rep = reparoService.salvarReparoSimples(rep);
			os.setReparo(rep);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private OrdemServico processarItemReprovadoReparo(OrdemServico os){
		try{

			Reparo rep = os.getReparo();
			if(os.getReparo().getStatusString().equals(AguardandoProposta.nome)){
				os.setStatusString(ReparoSendoRealizado.nome);
				rep.setStatusString(Iniciado.nome);
			}
			os.setBloqueado(0);
			rep.setPropostaReprovada(new Date());
			rep.setDataLimite(new Date());
			rep.setPrioridade(null);
			rep.setCondicao(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO);
			reparoService.salvarReparoSimples(rep);
			os.setReparo(rep);
			os = ordemServicoService.salvarSimplesOrdemServico(os);
			return os;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			return null;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta liberarItens(Proposta proposta, Set<ItemProposta> itens) throws Exception {
		Proposta propostaSalva;
		try {
			calcularFretePorOrdemServico(proposta);

			if(proposta.getTipo().equals(ConstantesAplicacao.TIPO_PROPOSTA_ORCAMENTO)){
				for(ItemProposta ip : itens){
					//if(ip.getDataAprovacao() != null){
						OrdemServico os = ip.getOrdemServico();
						//Item foi aprovado - O orcamento eh finalizado e o reparo eh criado
						if(ip.getIsAprovado() && ip.getDataAprovacao() != null && os.getOrcamento().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO) && os.getReparo() == null){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemAprovado(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemAprovado(osFilha);
								}
							}else{
								os = processarItemAprovado(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
							//Item foi reprovado - O orcamento nao eh finalizado, o status do orcamento eh alterado
							//para 'Iniciado', a legenda incrementa o dado de proposta rejeitada, o usuario deve ser
							//notificado
						}else if(!ip.getIsAprovado() && ip.getDataAprovacao() != null && os.getOrcamento().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO) && os.getReparo() == null){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemReprovado(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemReprovado(osFilha);
								}
							}else{
								os = processarItemReprovado(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
							//Item nao tem condicao de reparo. Foi realizado um laudo tecnico	
						}else if(os.getOrcamento().getCondicao().equals(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO)){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemSemCondicao(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemSemCondicao(osFilha);
								}
							}else{
								os = processarItemSemCondicao(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
							//Item serah devolvido sem reparo	
						}else if(os.getOrcamento().getCondicao().equals(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO)){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemDevolucaoSemReparo(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemDevolucaoSemReparo(osFilha);
								}
							}else{
								os = processarItemDevolucaoSemReparo(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
						}

					//}
				}
			}else{
				for(ItemProposta ip : itens){
					//if(ip.getDataAprovacao() != null){
						OrdemServico os = ip.getOrdemServico();

						if(ip.getIsAprovado() && ip.getDataAprovacao() != null){

							//Desbloquear as os's do conjunto
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemAprovadoReparo(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemAprovadoReparo(osFilha);
								}
							}else{
								os = processarItemAprovadoReparo(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
						}else if(!ip.getIsAprovado() && ip.getDataAprovacao() != null){

							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemReprovadoReparo(cos.getOsPai()));

								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemReprovadoReparo(osFilha);
								}
							}else{
								os = processarItemReprovadoReparo(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
						}else if(os.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO)){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemSemCondicaoReparo(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemSemCondicaoReparo(osFilha);
								}
							}else{
								os = processarItemSemCondicaoReparo(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
							//Item serah devolvido sem reparo	
						}else if(os.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_DEVOLUCAO_SEM_REPARO)){
							if(ordemServicoUtils.pertenceConjunto(os)){
								ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
								cos.setOsPai(processarItemDevolucaoSemReparoReparo(cos.getOsPai()));
								for(OrdemServico osFilha : cos.getListaFilhas()){
									osFilha = processarItemDevolucaoSemReparoReparo(osFilha);
								}
							}else{
								os = processarItemDevolucaoSemReparoReparo(os);
								ip.setOrdemServico(os);
							}
							ip.setDataLiberacao(new Date());
							itemPropostaService.salvarItemProposta(ip);
						}
					//}
				}
			}

			propostaSalva =(Proposta) salvarPropostaSimples(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void calcularFretePorOrdemServico(Proposta proposta){
		Integer total = 0;
		try{
			for(ItemProposta ip : proposta.getItensProposta()){
				if(ip.getOrdemServico().getUnidadePai() == null){
					total++;
				}
			}
			BigDecimal divisor = new BigDecimal(total);
			BigDecimal freteUnitario = null;
			if(proposta.getValorFrete() != null){
				freteUnitario = proposta.getValorFrete().divide(divisor,8,RoundingMode.HALF_UP);
			}else{
				freteUnitario = new BigDecimal(0);
			}


			for(ItemProposta ip : proposta.getItensProposta()){
				if(ip.getOrdemServico().getUnidadePai() == null){
					ip.getOrdemServico().setFreteUnitarioProposta(freteUnitario);
					ordemServicoService.salvarSimplesOrdemServico(ip.getOrdemServico());
				}
			}
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			logger.error(e);
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta enviarAoCliente(Proposta proposta) throws Exception {
		Proposta propostaSalva;
		try {

			propostaSalva =(Proposta) salvarPropostaSimples(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta aprovarProposta(Proposta proposta,Usuario usr) throws Exception {
		Proposta propostaSalva;
		Set<ItemProposta> itensProposta = proposta.getItensProposta();
		proposta.setItensProposta(null);

		try {
			for(ItemProposta ip : itensProposta){
				if(ip.getIsAprovado() && ip.getDataFinalizacao() != null){
					ip = itemPropostaService.aprovarItemProposta(ip,usr);
				}else if(!ip.getIsAprovado() && ip.getDataFinalizacao() != null) {
					ip = itemPropostaService.reprovarItemProposta(ip,usr);
				}else{
					//Erro
				}
			}
			proposta.setDataFinalizacao(new Date());

			propostaSalva =(Proposta) salvarPropostaSimples(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return propostaSalva;
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirProposta(Proposta proposta) throws Exception {
		try {
			propostaDao.delete(proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
	}

}
