package br.com.sose.service.orcamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.orcamento.OrcamentoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.administrativo.UsuarioService;
import br.com.sose.service.areatecnica.AtividadeOrcRepService;
import br.com.sose.service.areatecnica.ComponenteOrcRepService;
import br.com.sose.service.areatecnica.DefeitoOrcRepService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.orcamento.Finalizado;
import br.com.sose.to.OrcRepGenericoTO;
import br.com.sose.utils.DateUtils;

@Service(value="orcamentoService")
@RemotingDestination(value="orcamentoService")
public class OrcamentoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public OrcamentoDao orcamentoDao;

	@Autowired
	public UsuarioService usuarioService;

	@Autowired
	public AtividadeOrcRepService atividadeOrcRepService;

	@Autowired
	public DefeitoOrcRepService defeitoOrcRepService;

	@Autowired
	public ComponenteOrcRepService componenteOrcRepService;

	@Autowired
	public RequisicaoComponenteService requisicaoComponenteService;

	@Autowired
	public ObservacaoService observacaoService;

	@Autowired
	public OrdemServicoService ordemServicoService;

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<Orcamento> listarOrcamentos() throws Exception {
		List<Orcamento> orcamentos;
		try {
			orcamentos =(List<Orcamento>) orcamentoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentos;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrcRepGenericoTO> listarOrcamentoOtimizado() throws Exception {
		List<OrcRepGenericoTO> reparos;
		try {
			reparos =(List<OrcRepGenericoTO>) orcamentoDao.listarOrcamentoOtimizado();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparos;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public Orcamento buscarPorId(Long id) throws Exception {
		Orcamento orcamentoEncontrada;
		try {
			orcamentoEncontrada =(Orcamento) orcamentoDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoEncontrada;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento salvarOrcamento(Orcamento orcamento) throws Exception {
		Orcamento orcamentoSalvo;
		//		Set<AtividadeOrcRep> atividades = orcamento.getListaAtividade();
		//		Set<AtividadeOrcRep> atividadesSalvas = new HashSet<AtividadeOrcRep>();
		//		orcamento.setListaAtividade(null);
		//		Set<DefeitoOrcRep> defeitos = orcamento.getListaDefeito();
		//		Set<DefeitoOrcRep> defeitosSalvos = new HashSet<DefeitoOrcRep>();
		//		orcamento.setListaDefeito(null);
		//		Set<RequisicaoComponente> requisicoes = orcamento.getListaRequisicao();
		//		Set<RequisicaoComponente> requisicoesSalvas = new HashSet<RequisicaoComponente>();
		//		orcamento.setListaRequisicao(null);
		Set<ComponenteOrcRep> componentes = orcamento.getListaComponente();
		Set<ComponenteOrcRep> componentesSalvos = new HashSet<ComponenteOrcRep>();
		orcamento.setListaComponente(null);
		try {
			orcamentoSalvo = salvarOrcamentoSimples(orcamento);

			//			if(atividades != null && !atividades.isEmpty()){
			//				for(AtividadeOrcRep aor : atividades){
			//					if(aor.getData() == null){
			//						aor.setData(new Date());
			//					}
			//					aor.setOrcamento(orcamentoSalvo);
			//					aor = atividadeOrcRepDao.merge(aor);
			//					atividadesSalvas.add(aor);
			//				}
			//				orcamentoSalvo.setListaAtividade(atividadesSalvas);
			//			}
			//			if(defeitos != null && !defeitos.isEmpty()){
			//				for(DefeitoOrcRep dor : defeitos){
			//					if(dor.getData() == null){
			//						dor.setData(new Date());
			//					}
			//					dor.setOrcamento(orcamentoSalvo);
			//					dor = defeitoOrcRepDao.merge(dor);
			//					defeitosSalvos.add(dor);
			//				}
			//				orcamentoSalvo.setListaDefeito(defeitosSalvos);
			//			}
			//			if(requisicoes != null && !requisicoes.isEmpty()){
			//				for(RequisicaoComponente r : requisicoes){
			//					if(r.getDataRequisicao() == null){
			//						r.setDataRequisicao(new Date());
			//					}
			//					r.setOrcamento(orcamentoSalvo);
			//					r = requisicaoComponenteDao.merge(r);
			//					requisicoesSalvas.add(r);
			//				}
			//				orcamentoSalvo.setListaRequisicao(requisicoesSalvas);
			//			}
			if(componentes != null && !componentes.isEmpty()){
				for(ComponenteOrcRep c : componentes){
					c = componenteOrcRepService.salvarComponenteOrcRep(c);
					componentesSalvos.add(c);
				}
				orcamentoSalvo.setListaComponente(componentesSalvos);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento salvarOrcamentoSimples(Orcamento orcamento) throws Exception {
		Orcamento orcamentoSalva;
		try {
			if(orcamento.getId() == null || orcamento.getId().equals(new Long(0)))
				orcamentoSalva =(Orcamento) orcamentoDao.save(orcamento);
			else
				orcamentoSalva =(Orcamento) orcamentoDao.update(orcamento);		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento finalizarOrcamento(Orcamento orcamento) throws Exception {
		Orcamento orcamentoSalva;
		try {
			orcamento.setDataFim(new Date());
			orcamento.setStatusString(Finalizado.nome);
			observacaoService.log("Orçamento", "Orçamento finalizado", 2, new Date(),orcamento, orcamento.getTecnicoResponsavel());
			orcamentoSalva =(Orcamento) salvarOrcamentoSimples(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirOrcamento(Orcamento orcamento) throws Exception {
		try {
			orcamentoDao.delete(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		Orcamento orcamentoEditado;
		try {
			usuario = usuarioService.buscarPorId(usuario.getId());
			orcamento.setTecnicoResponsavel(usuario);
			orcamentoEditado =(Orcamento) salvarOrcamentoSimples(orcamento);
			observacaoService.log("Orçamento", "Atribuição de técnico: " + orcamento.getTecnicoResponsavel().getUsuario(),  2, new Date(),orcamento, atribuidoPor);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario atribuidoPor) throws Exception {
		Orcamento orcamentoEditado;
		OrdemServico osPai = null;
		OrdemServico osOrcamento = null;
		Set<OrdemServico> listaFilhas = null;
		try {
			osOrcamento = ordemServicoService.buscarPorId(orcamento.getOrdemServico().getId());

			if(osOrcamento.getUnidadePai() != null){
				//Eh unidade filha
				osPai = ordemServicoService.buscarPorId(osOrcamento.getUnidadePai().getId());
				listaFilhas = osPai.getPlacasFilhas();
			}else if(osOrcamento.getUnidadePai() == null && osOrcamento.getPlacasFilhas() != null && !osOrcamento.getPlacasFilhas().isEmpty()){
				//Eh unidade pai
				osPai = null;
				listaFilhas = osOrcamento.getPlacasFilhas();
			}else{
				//Eh unidade sem filha
				osPai = null;
				listaFilhas = null;
			}

			//Processa osReparo		
			if(orcamento.getPrioridade() == null){
				observacaoService.log("Orçamento", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(), osOrcamento, atribuidoPor);
			}else{
				if(!orcamento.getPrioridade().equals(date))
					observacaoService.log("Orçamento", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(orcamento.getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osOrcamento, atribuidoPor);
			}
			orcamento.setPrioridade(date);
			orcamentoEditado =(Orcamento) salvarOrcamentoSimples(orcamento);


			//Processa osPai se existir
			if(osPai != null){


				//log
				if(osPai.getOrcamento().getPrioridade() == null){
					observacaoService.log("Orçamento", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osPai, atribuidoPor);
				}else{
					if(osPai.getOrcamento().getPrioridade().compareTo(date)!=0){
						observacaoService.log("Orçamento", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(osPai.getOrcamento().getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osPai, atribuidoPor);
					}
				}

				osPai.getOrcamento().setPrioridade(date);
				salvarOrcamentoSimples(osPai.getOrcamento());
			}

			//Processa listaFilhas se existir
			if(listaFilhas != null && !listaFilhas.isEmpty()){
				for(OrdemServico osFilha: listaFilhas){

					//log
					if(osFilha.getOrcamento().getPrioridade() == null){
						observacaoService.log("Orçamento", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osFilha, atribuidoPor);
					}else{
						if(osFilha.getOrcamento().getPrioridade().compareTo(date)!=0){
							observacaoService.log("Orçamento", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(osFilha.getOrcamento().getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osFilha, atribuidoPor);
						}
					}

					osFilha.getOrcamento().setPrioridade(date);
					salvarOrcamentoSimples(osFilha.getOrcamento());
				}
			}

			//Remonta objeto reparoEditado para retorno
			if(osPai != null){
				osPai.setPlacasFilhas(listaFilhas);
				orcamentoEditado.getOrdemServico().setUnidadePai(osPai);
			}else if(osPai == null && listaFilhas != null && !listaFilhas.isEmpty()){
				orcamentoEditado.getOrdemServico().setPlacasFilhas(listaFilhas);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento reAtribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		Orcamento orcamentoEditado;
		try {
			String vazio = " ";
			observacaoService.log("Orçamento", "Reatribuição de técnico: " + (orcamento.getTecnicoResponsavel() != null ?orcamento.getTecnicoResponsavel().getUsuario() : vazio) + " -> " + usuario.getUsuario(), 2, new Date(),orcamento, atribuidoPor);
			usuario = usuarioService.buscarPorId(usuario.getId());
			orcamento.setTecnicoResponsavel(usuario);
			requisicaoComponenteService.reatribuirTecnico(orcamento.getListaRequisicao(), usuario);
			orcamentoEditado =(Orcamento) salvarOrcamentoSimples(orcamento);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<Orcamento> atribuirTecnico(List<Orcamento> orcamentos, Usuario usuario) throws Exception {
		Orcamento orcamentoEditado;
		List<Orcamento> orcamentosEditados = new ArrayList<Orcamento>();
		try {
			usuario = usuarioService.buscarPorId(usuario.getId());
			for(Orcamento orcamento : orcamentos){
				orcamento.setTecnicoResponsavel(usuario);
				orcamentoEditado =(Orcamento) salvarOrcamentoSimples(orcamento);
				orcamentosEditados.add(orcamentoEditado);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentosEditados;
	}

}
