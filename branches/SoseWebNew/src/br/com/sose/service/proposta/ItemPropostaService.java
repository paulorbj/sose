package br.com.sose.service.proposta;

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

import br.com.sose.daoImpl.proposta.ItemPropostaDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.OrdemServicoUtils;

@Service(value="itemPropostaService")
@RemotingDestination(value="itemPropostaService")
public class ItemPropostaService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ItemPropostaDao itemPropostaDao;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;
	
	@Autowired
	private PropostaService propostaService;

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemProposta salvarItemProposta(ItemProposta itemProposta) throws Exception {
		ItemProposta itemPropostaSalva;
		try {
			if(itemProposta.getId() == null || itemProposta.getId().equals(new Long(0)))
				itemPropostaSalva =(ItemProposta) itemPropostaDao.save(itemProposta);	
			else
				itemPropostaSalva =(ItemProposta) itemPropostaDao.update(itemProposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemPropostaSalva;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public ItemProposta buscarPorOrdemServico(OrdemServico ordemServico) throws Exception {
		ItemProposta itemPropostaSalva;
		try {
			itemPropostaSalva =(ItemProposta) itemPropostaDao.buscarPorOrdemServico(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemPropostaSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly=true)
	public ItemProposta buscarPorOrdemServico(OrdemServico ordemServico, Proposta proposta) throws Exception {
		ItemProposta itemPropostaSalva;
		try {
			itemPropostaSalva =(ItemProposta) itemPropostaDao.buscarPorOrdemServico(ordemServico,proposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			return null;
		} 
		return itemPropostaSalva;
	}
	
	@RemotingInclude
	@Transactional(readOnly=true)
	public ItemProposta buscarPorOS(OrdemServico ordemServico) throws Exception {
		ItemProposta itemPropostaSalva;
		try {
			itemPropostaSalva =(ItemProposta) itemPropostaDao.buscarPorOS(ordemServico);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return itemPropostaSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemProposta aprovarItemProposta(ItemProposta itemProposta, Usuario usuario) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(itemProposta.getOrdemServico())){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(itemProposta.getOrdemServico());
				Set<ItemProposta> itensPropostasRetorno = new HashSet<ItemProposta>();

				itemProposta.setIsAprovado(true);
				itemProposta.setDataAprovacao(new Date());
				itemProposta = salvarItemProposta(itemProposta);
				itensPropostasRetorno.add(itemProposta);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					ItemProposta ipFilha = itemPropostaDao.buscarPorOS(osFilha);
					ipFilha.setIsAprovado(true);
					ipFilha.setDataAprovacao(new Date());
					ipFilha = salvarItemProposta(ipFilha);
					itensPropostasRetorno.add(ipFilha);
				}
				
				Proposta proposta = itemProposta.getProposta();
				proposta = propostaService.buscarPorId(proposta.getId());

				for(ItemProposta item : itensPropostasRetorno){
					for(ItemProposta itemP : proposta.getItensProposta()){
						if(item.getId().equals(itemP.getId())){
							itemP = item;
							break;
						}
					}
				}
				
				itemProposta.setProposta(proposta);
				
			}else{
				itemProposta.setIsAprovado(true);
				itemProposta.setDataAprovacao(new Date());
				salvarItemProposta(itemProposta);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		observacaoService.log("Item proposta", "Item proposta aprovado", 2, new Date(),itemProposta.getOrdemServico(), usuario);
		return itemProposta;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemProposta restaurarItemProposta(ItemProposta itemProposta, Usuario usuario) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(itemProposta.getOrdemServico())){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(itemProposta.getOrdemServico());
				Set<ItemProposta> itensPropostasRetorno = new HashSet<ItemProposta>();

				itemProposta.setIsAprovado(false);
				itemProposta.setDataAprovacao(null);
				itemProposta = salvarItemProposta(itemProposta);
				itensPropostasRetorno.add(itemProposta);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					ItemProposta ipFilha = itemPropostaDao.buscarPorOS(osFilha);
					ipFilha.setIsAprovado(false);
					ipFilha.setDataAprovacao(null);
					ipFilha = salvarItemProposta(ipFilha);
					itensPropostasRetorno.add(ipFilha);
				}
				
				Proposta proposta = itemProposta.getProposta();
				proposta = propostaService.buscarPorId(proposta.getId());

				for(ItemProposta item : itensPropostasRetorno){
					for(ItemProposta itemP : proposta.getItensProposta()){
						if(item.getId().equals(itemP.getId())){
							itemP = item;
							break;
						}
					}
				}
				
				itemProposta.setProposta(proposta);
				
			}else{
				itemProposta.setIsAprovado(false);
				itemProposta.setDataAprovacao(null);
				salvarItemProposta(itemProposta);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		observacaoService.log("Item proposta", "Item proposta restaurado", 2, new Date(),itemProposta.getOrdemServico(), usuario);
		return itemProposta;
	}
	
	@RemotingInclude
	@Transactional(readOnly=true)
	public List<ItemProposta> listarPorProposta(Proposta proposta) throws Exception {
		List<ItemProposta> listaItensPropostas = new ArrayList<ItemProposta>();
		try {
			listaItensPropostas = itemPropostaDao.listarPorProposta(proposta);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaItensPropostas;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemProposta> aprovarItensProposta(List<ItemProposta> itensPropostas, Usuario usuario) throws Exception {
		List<ItemProposta> listaItensPropostas = new ArrayList<ItemProposta>();
		try {
			for(ItemProposta ip : itensPropostas){
				ip = aprovarItemProposta(ip,usuario);
				listaItensPropostas.add(ip);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaItensPropostas;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ItemProposta reprovarItemProposta(ItemProposta itemProposta, Usuario usuario) throws Exception {
		try {
			if(ordemServicoUtils.pertenceConjunto(itemProposta.getOrdemServico())){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(itemProposta.getOrdemServico());
				Set<ItemProposta> itensPropostasRetorno = new HashSet<ItemProposta>();

				itemProposta.setIsAprovado(false);
				itemProposta.setDataAprovacao(new Date());
				itemProposta = salvarItemProposta(itemProposta);
				itensPropostasRetorno.add(itemProposta);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					ItemProposta ipFilha = itemPropostaDao.buscarPorOS(osFilha);
					ipFilha.setIsAprovado(false);
					ipFilha.setDataAprovacao(new Date());
					ipFilha = salvarItemProposta(ipFilha);
					itensPropostasRetorno.add(ipFilha);
				}
				
				Proposta proposta = itemProposta.getProposta();
				proposta = propostaService.buscarPorId(proposta.getId());

				for(ItemProposta item : itensPropostasRetorno){
					for(ItemProposta itemP : proposta.getItensProposta()){
						if(item.getId().equals(itemP.getId())){
							itemP = item;
							break;
						}
					}
				}
				
				itemProposta.setProposta(proposta);
			}else{
				itemProposta.setIsAprovado(false);
				itemProposta.setDataAprovacao(new Date());
				salvarItemProposta(itemProposta);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		observacaoService.log("Item proposta", "Item proposta reprovado", 2, new Date(),itemProposta.getOrdemServico(), usuario);
		return itemProposta;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ItemProposta> reprovarItensProposta(List<ItemProposta> itensPropostas, Usuario usuario) throws Exception {
		List<ItemProposta> listaItensPropostas = new ArrayList<ItemProposta>();
		try {
			for(ItemProposta ip : itensPropostas){
				ip = reprovarItemProposta(ip,usuario);
				listaItensPropostas.add(ip);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return listaItensPropostas;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirItemProposta(ItemProposta itemProposta) throws Exception {
		try {
			itemPropostaDao.delete(itemProposta);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
