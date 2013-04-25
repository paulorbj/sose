package br.com.sose.service.estoque.avaya;

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

import br.com.sose.daoImpl.estoque.avaya.ItemEstoqueAvayaDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.exceptions.ClienteNaoEhAvayaException;
import br.com.sose.exceptions.OrdemServicoNaoEncontradaException;
import br.com.sose.exceptions.OrdemServicoNaoPossuiCaseException;
import br.com.sose.exceptions.UnidadeComCondicaoReparoException;
import br.com.sose.exceptions.UnidadeDiferenteException;
import br.com.sose.exceptions.UnidadeSemCondicaoReparoException;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.AguardandoExpedicao;
import br.com.sose.status.estoque.avaya.DisponivelEstoque;
import br.com.sose.status.estoque.avaya.Reposto;
import br.com.sose.status.estoque.avaya.Substituido;
import br.com.sose.utils.ConstantesAplicacao;

@Service(value="itemEstoqueAvayaService")
@RemotingDestination(value="itemEstoqueAvayaService")
public class ItemEstoqueAvayaService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public ItemEstoqueAvayaDao itemEstoqueAvayaDao;

	@Autowired
	public OrdemServicoService ordemServicoService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public ItemEstoqueAvaya buscarPorId(Long id) throws Exception {
		ItemEstoqueAvaya osEncontrada;
		try {
			osEncontrada =(ItemEstoqueAvaya) itemEstoqueAvayaDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return osEncontrada;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ItemEstoqueAvaya> listarItemEstoqueAvaya() throws Exception {
		List<ItemEstoqueAvaya> itensEstoqueAvaya;
		try {
			itensEstoqueAvaya =(List<ItemEstoqueAvaya>) itemEstoqueAvayaDao.listar();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itensEstoqueAvaya;
	}	

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya criarItemEstoqueAvaya(OrdemServico ordemServico) throws Exception {
		ItemEstoqueAvaya itemEstoqueAvaya = new ItemEstoqueAvaya();
		try {
			itemEstoqueAvaya.setOrdemServicoOriginal(ordemServico);
			itemEstoqueAvaya.setStatusString(DisponivelEstoque.nome);
			itemEstoqueAvaya =(ItemEstoqueAvaya) salvarItemEstoqueAvaya(itemEstoqueAvaya);
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itemEstoqueAvaya;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya salvarItemEstoqueAvaya(ItemEstoqueAvaya itemEstoqueAvaya) throws Exception {
		ItemEstoqueAvaya itemEstoqueAvayaSalvo;
		try {
			if(itemEstoqueAvaya.getId() == null || itemEstoqueAvaya.getId().equals(new Long(0)))
				itemEstoqueAvayaSalvo =(ItemEstoqueAvaya) itemEstoqueAvayaDao.save(itemEstoqueAvaya);
			else
				itemEstoqueAvayaSalvo =(ItemEstoqueAvaya) itemEstoqueAvayaDao.update(itemEstoqueAvaya);
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itemEstoqueAvayaSalvo;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemEstoqueAvaya> salvarItens(List<ItemEstoqueAvaya> itens) throws Exception {
		List<ItemEstoqueAvaya> itensSalvos = new ArrayList<ItemEstoqueAvaya>();
		ItemEstoqueAvaya itemAux = null;
		try {
			for (ItemEstoqueAvaya iea : itens){
				itemAux = buscarPorId(iea.getId());
				itemAux.setPosicao(iea.getPosicao());
				iea = salvarItemEstoqueAvaya(itemAux);
				itensSalvos.add(iea);
			}
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itensSalvos;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya substituir(ItemEstoqueAvaya itemEstoqueAvaya, String idOS, Usuario usuario) throws Exception {
		ItemEstoqueAvaya itemEstoqueAvayaSalvo = null;
		try {
			itemEstoqueAvaya = itemEstoqueAvayaDao.buscarPorId(itemEstoqueAvaya.getId());
			OrdemServico osParaSubstituir = ordemServicoService.buscarPorOrdemServico(idOS);
			if(osParaSubstituir == null){
				logger.warn("Ordem de serviço não foi encontrada: "+idOS);
				throw new OrdemServicoNaoEncontradaException(idOS);
			}
			if(osParaSubstituir != null){
				if(osParaSubstituir.getCliente().getNomeRazaoSocial().toLowerCase().contains("avaya")){
					if(osParaSubstituir.getUnidade().getNome().equalsIgnoreCase(itemEstoqueAvaya.getOrdemServicoOriginal().getUnidade().getNome())){
						if(!osParaSubstituir.getNotaFiscal().getCaseAvaya().isEmpty() && !osParaSubstituir.getNotaFiscal().getClienteAvaya().isEmpty()){
							if(osParaSubstituir.getReparo() != null && osParaSubstituir.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO)){
								itemEstoqueAvaya.setDataOperacao(new Date());
								itemEstoqueAvaya.setOperacaoRealizadaPor(usuario);
								itemEstoqueAvaya.setStatusString(Substituido.nome);
								itemEstoqueAvaya.setOrdemServicoSubstituida(osParaSubstituir);
								itemEstoqueAvayaSalvo = salvarItemEstoqueAvaya(itemEstoqueAvaya);

								OrdemServico osSubstituida = ordemServicoService.buscarPorIdSimples(itemEstoqueAvaya.getOrdemServicoOriginal().getId());
								osSubstituida.setCaseAvaya(osParaSubstituir.getCaseAvaya());
								osSubstituida.setClienteAvaya(osParaSubstituir.getClienteAvaya());
								osSubstituida.setStatusString(AguardandoExpedicao.nome);
								ordemServicoService.salvarSimplesOrdemServico(osSubstituida);

								osParaSubstituir.setCaseAvaya(null);
								osParaSubstituir.setClienteAvaya(null);
								ordemServicoService.salvarSimplesOrdemServico(osParaSubstituir);

							}else{
								throw new UnidadeSemCondicaoReparoException("");
							}
						}else{
							throw new OrdemServicoNaoPossuiCaseException("");
						}
					}else{
						throw new UnidadeDiferenteException("");
					}
				}else{
					throw new ClienteNaoEhAvayaException(osParaSubstituir.getCliente().getNomeRazaoSocial());
				}
			}else{
				throw new OrdemServicoNaoEncontradaException(idOS);
			}
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itemEstoqueAvayaSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya repor(ItemEstoqueAvaya itemEstoqueAvaya, String idOS, Usuario usuario) throws Exception {
		ItemEstoqueAvaya itemEstoqueAvayaSalvo = null;
		try {
			OrdemServico osParaRepor = ordemServicoService.buscarPorOrdemServico(idOS);
			if(osParaRepor == null){
				logger.warn("Ordem de serviço não foi encontrada: "+idOS);
				throw new OrdemServicoNaoEncontradaException(idOS);
			}
			itemEstoqueAvaya = itemEstoqueAvayaDao.buscarPorId(itemEstoqueAvaya.getId());
			if(osParaRepor.getCliente().getNomeRazaoSocial().toLowerCase().contains("avaya")){
				if(osParaRepor.getUnidade().getNome().equalsIgnoreCase(itemEstoqueAvaya.getOrdemServicoOriginal().getUnidade().getNome())){
					if(!osParaRepor.getNotaFiscal().getCaseAvaya().isEmpty() && !osParaRepor.getNotaFiscal().getClienteAvaya().isEmpty()){
						if(osParaRepor.getReparo() != null && osParaRepor.getReparo().getCondicao().equals(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO)){
							itemEstoqueAvaya.setDataOperacao(new Date());
							itemEstoqueAvaya.setOperacaoRealizadaPor(usuario);
							itemEstoqueAvaya.setStatusString(Reposto.nome);
							itemEstoqueAvaya.setOrdemServicoSubstituida(osParaRepor);
							itemEstoqueAvayaSalvo = salvarItemEstoqueAvaya(itemEstoqueAvaya);

//							OrdemServico osSubstituida = itemEstoqueAvaya.getOrdemServicoOriginal();
							OrdemServico osSubstituida = ordemServicoService.buscarPorIdSimples(itemEstoqueAvaya.getOrdemServicoOriginal().getId());

							osSubstituida.setCaseAvaya(osParaRepor.getCaseAvaya());
							osSubstituida.setClienteAvaya(osParaRepor.getClienteAvaya());
							osSubstituida.setStatusString(AguardandoExpedicao.nome);
							ordemServicoService.salvarSimplesOrdemServico(osSubstituida);

							osParaRepor.setCaseAvaya("");
							osParaRepor.setClienteAvaya("");
							ordemServicoService.salvarSimplesOrdemServico(osParaRepor);

						}else{
							throw new UnidadeComCondicaoReparoException("");
						}
					}else{
						throw new OrdemServicoNaoPossuiCaseException("");
					}
				}else{
					throw new UnidadeDiferenteException("");
				}
			}else{
				throw new ClienteNaoEhAvayaException(osParaRepor.getCliente().getNomeRazaoSocial());
			}
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itemEstoqueAvayaSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemEstoqueAvaya retirar(ItemEstoqueAvaya itemEstoqueAvaya, Usuario usuario) throws Exception {
		ItemEstoqueAvaya itemEstoqueAvayaSalvo;
		try {
			itemEstoqueAvaya = itemEstoqueAvayaDao.buscarPorId(itemEstoqueAvaya.getId());
			itemEstoqueAvaya.setDataOperacao(new Date());
			itemEstoqueAvaya.setOperacaoRealizadaPor(usuario);
			itemEstoqueAvaya.setStatusString(br.com.sose.status.estoque.avaya.Retirado.nome);
			itemEstoqueAvayaSalvo =(ItemEstoqueAvaya) salvarItemEstoqueAvaya(itemEstoqueAvaya);

			OrdemServico osRetirada = ordemServicoService.buscarPorIdSimples(itemEstoqueAvaya.getOrdemServicoOriginal().getId());
			osRetirada.setStatusString(AguardandoExpedicao.nome);
			ordemServicoService.salvarSimplesOrdemServico(osRetirada);
		}catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error(e);
			throw e;
		}
		return itemEstoqueAvayaSalvo;
	}


}
