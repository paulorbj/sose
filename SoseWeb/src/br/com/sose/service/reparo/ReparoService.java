package br.com.sose.service.reparo;

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

import br.com.sose.daoImpl.areaTecnica.RequisicaoComponenteDao;
import br.com.sose.daoImpl.reparo.ReparoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.AtividadeOrcRep;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.orcrepGenerico.DefeitoOrcRep;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.administrativo.UsuarioService;
import br.com.sose.service.areatecnica.AtividadeOrcRepService;
import br.com.sose.service.areatecnica.ComponenteOrcRepService;
import br.com.sose.service.areatecnica.DefeitoOrcRepService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.reparo.AguardandoLiberacao;
import br.com.sose.status.reparo.NaoIniciado;
import br.com.sose.to.OrcRepGenericoTO;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.DateUtils;

@Service(value="reparoService")
@RemotingDestination(value="reparoService")
public class ReparoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ReparoDao reparoDao;

	@Autowired
	public UsuarioService usuarioService;

	@Autowired
	public OrdemServicoService ordemServicoService;

	@Autowired
	public AtividadeOrcRepService atividadeOrcRepService;

	@Autowired
	public DefeitoOrcRepService defeitoOrcRepService;

	@Autowired
	public ComponenteOrcRepService componenteOrcRepService;

	@Autowired
	public RequisicaoComponenteDao requisicaoComponenteDao;

	@Autowired
	public ObservacaoService observacaoService;

	@Autowired
	private OrcamentoService orcamentoService;

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<Reparo> listarReparos() throws Exception {
		List<Reparo> reparos;
		try {
			reparos =(List<Reparo>) reparoDao.listAll();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparos;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrcRepGenericoTO> listarReparoOtimizado() throws Exception {
		List<OrcRepGenericoTO> reparos;
		try {
			reparos =(List<OrcRepGenericoTO>) reparoDao.listarReparoOtimizado();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparos;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo salvarReparo(Reparo reparo) throws Exception {
		Reparo reparoSalva;
		//		Set<AtividadeOrcRep> atividades = reparo.getListaAtividade();
		//		Set<AtividadeOrcRep> atividadesSalvas = new HashSet<AtividadeOrcRep>();
		//		reparo.setListaAtividade(null);
		//		Set<DefeitoOrcRep> defeitos = reparo.getListaDefeito();
		//		Set<DefeitoOrcRep> defeitosSalvos = new HashSet<DefeitoOrcRep>();
		//		reparo.setListaDefeito(null);
		//		Set<RequisicaoComponente> requisicoes = reparo.getListaRequisicao();
		//		Set<RequisicaoComponente> requisicoesSalvas = new HashSet<RequisicaoComponente>();
		//		reparo.setListaRequisicao(null);
		Set<ComponenteOrcRep> componentes = reparo.getListaComponente();
		Set<ComponenteOrcRep> componentesSalvos = new HashSet<ComponenteOrcRep>();
		reparo.setListaComponente(null);
		try {
			reparoSalva =(Reparo) salvarReparoSimples(reparo);

			//			if(requisicoes != null && !requisicoes.isEmpty()){
			//				for(RequisicaoComponente r : requisicoes){
			//					if(r.getDataRequisicao() == null){
			//						r.setDataRequisicao(new Date());
			//					}
			//					r.setReparo(reparoSalva);
			//					r = requisicaoComponenteDao.merge(r);
			//					requisicoesSalvas.add(r);
			//				}
			//				reparoSalva.setListaRequisicao(requisicoesSalvas);
			//			}
			//			if(atividades != null && !atividades.isEmpty()){
			//				for(AtividadeOrcRep aor : atividades){
			//					if(aor.getData() == null){
			//						aor.setData(new Date());
			//					}
			//					aor.setReparo(reparoSalva);
			//					aor = atividadeOrcRepDao.merge(aor);
			//					atividadesSalvas.add(aor);
			//				}
			//				reparoSalva.setListaAtividade(atividadesSalvas);
			//			}
			//			if(defeitos != null && !defeitos.isEmpty()){
			//				for(DefeitoOrcRep dor : defeitos){
			//					if(dor.getData() == null){
			//						dor.setData(new Date());
			//					}
			//					dor.setReparo(reparoSalva);
			//					dor = defeitoOrcRepDao.merge(dor);
			//					defeitosSalvos.add(dor);
			//				}
			//				reparoSalva.setListaDefeito(defeitosSalvos);
			//			}
			if(componentes != null && !componentes.isEmpty()){
				for(ComponenteOrcRep cor : componentes){
					cor = componenteOrcRepService.salvarComponenteOrcRep(cor);
					componentesSalvos.add(cor);
				}
				reparoSalva.setListaComponente(componentesSalvos);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo salvarReparoSimples(Reparo reparo) throws Exception {
		Reparo reparoEditado;
		try {
			if(reparo.getId() == null || reparo.getId().equals(new Long(0)))
				reparoEditado =(Reparo) reparoDao.save(reparo);	
			else
				reparoEditado =(Reparo) reparoDao.update(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo criarReparo(OrdemServico ordemServico) throws Exception {
		Reparo reparo = null;
		Orcamento orcamento = null;
		OrdemServico ordemServicoSalva; 
		try {
			reparo = new Reparo();
			ordemServicoSalva =(OrdemServico) ordemServicoService.salvarSimplesOrdemServico(ordemServico);

			if(ordemServico.getOrcamento() != null){
				reparo.setStatusString(NaoIniciado.nome);
				reparo.setCriadoFromOrcamento(true);
				reparo.setTecnicoResponsavel(ordemServico.getOrcamento().getTecnicoResponsavel());
				reparo.setJaReparado(ordemServico.getOrcamento().getJaReparado());
			}else{
				reparo.setStatusString(AguardandoLiberacao.nome);
				reparo.setCriadoFromOrcamento(false);
			}

			reparo.setDataEntrada(new Date());
			reparo.setDataLimite(new Date());
			reparo.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
			reparo.setOrdemServico(ordemServicoSalva);
			reparo = salvarReparoSimples(reparo);
			observacaoService.log("Reparo", "Reparo criado", 2, new Date(),reparo, reparo.getTecnicoResponsavel());
			if(ordemServico.getOrcamento() != null){
				orcamento = orcamentoService.buscarPorId(ordemServico.getOrcamento().getId());
				if(orcamento.getListaAtividade() != null && !orcamento.getListaAtividade().isEmpty()){
					AtividadeOrcRep aorAux = null;
					for(AtividadeOrcRep aor : orcamento.getListaAtividade()){
						aorAux = atividadeOrcRepService.copiarAtividadeOrcRep(aor);
						aorAux.setReparo(reparo);
						aorAux = atividadeOrcRepService.salvarAtividadeOrcRep(aorAux);
						reparo.getListaAtividade().add(aor);
					}
				}

				if(orcamento.getListaDefeito() != null && !orcamento.getListaDefeito().isEmpty()){
					DefeitoOrcRep aorAux = null;
					for(DefeitoOrcRep aor : orcamento.getListaDefeito()){
						aorAux = defeitoOrcRepService.copiarDefeitoOrcRep(aor);
						aorAux.setReparo(reparo);
						aorAux = defeitoOrcRepService.salvarDefeitoOrcRep(aorAux);
						reparo.getListaDefeito().add(aor);
					}
				}

				if(orcamento.getListaComponente() != null && !orcamento.getListaComponente().isEmpty()){
					ComponenteOrcRep aorAux = null;
					for(ComponenteOrcRep aor : orcamento.getListaComponente()){
						aorAux = componenteOrcRepService.copiarComponenteOrcRep(aor);
						aorAux.setReparo(reparo);
						aorAux = componenteOrcRepService.salvarComponenteOrcRep(aorAux);
						reparo.getListaComponente().add(aor);
					}
				}

			}

			ordemServicoSalva.setReparo(reparo);
			ordemServico.setReparo(reparo);
			ordemServicoSalva =(OrdemServico) ordemServicoService.salvarSimplesOrdemServico(ordemServico);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparo;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public Reparo buscarPorId(Long id) throws Exception {
		Reparo reparoEncontrada;
		try {
			reparoEncontrada =(Reparo) reparoDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEncontrada;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public Reparo buscarPorOrdemServico(Long id) throws Exception {
		Reparo reparoEncontrado=null;
		try {
			OrdemServico os = ordemServicoService.buscarPorIdSimples(id);
			if(os.getReparo() != null && os.getReparo().getId() != null){
				reparoEncontrado = buscarPorId(os.getReparo().getId());
			}	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEncontrado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception {
		Reparo reparoEditado;
		try {
			usuario = usuarioService.buscarPorId(usuario.getId());
			reparo.setTecnicoResponsavel(usuario);
			reparoEditado =(Reparo) salvarReparoSimples(reparo);
			observacaoService.log("Reparo", "Atribuição de técnico: " + reparo.getTecnicoResponsavel().getUsuario(),  2, new Date(),reparo, atribuidoPor);

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario atribuidoPor) throws Exception {
		Reparo reparoEditado;
		OrdemServico osPai = null;
		OrdemServico osReparo = null;
		Set<OrdemServico> listaFilhas = null;
		try {
			osReparo = ordemServicoService.buscarPorIdSimples(reparo.getOrdemServico().getId());

			if(osReparo.getUnidadePai() != null){
				//Eh unidade filha
				osPai = ordemServicoService.buscarPorIdSimples(osReparo.getUnidadePai().getId());
				listaFilhas = osPai.getPlacasFilhas();
			}else if(osReparo.getUnidadePai() == null && osReparo.getPlacasFilhas() != null && !osReparo.getPlacasFilhas().isEmpty()){
				//Eh unidade pai
				osPai = null;
				listaFilhas = osReparo.getPlacasFilhas();
			}else{
				//Eh unidade sem filha
				osPai = null;
				listaFilhas = null;
			}

			//Processa osReparo		
			if(reparo.getPrioridade() == null){
				observacaoService.log("Reparo", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osReparo, atribuidoPor);
			}else{
				if(!reparo.getPrioridade().equals(date))
					observacaoService.log("Reparo", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(reparo.getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osReparo, atribuidoPor);
			}
			reparo.setPrioridade(date);
			reparoEditado =(Reparo) salvarReparoSimples(reparo);


			//Processa osPai se existir
			if(osPai != null){


				//log
				if(osPai.getReparo().getPrioridade() == null){
					observacaoService.log("Reparo", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osPai, atribuidoPor);
				}else{
					if(osPai.getReparo().getPrioridade().compareTo(date)!=0){
						observacaoService.log("Reparo", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(osPai.getReparo().getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osPai, atribuidoPor);
					}
				}

				osPai.getReparo().setPrioridade(date);
				salvarReparoSimples(osPai.getReparo());
			}

			//Processa listaFilhas se existir
			if(listaFilhas != null && !listaFilhas.isEmpty()){
				for(OrdemServico osFilha: listaFilhas){

					//log
					if(osFilha.getReparo().getPrioridade() == null){
						observacaoService.log("Reparo", "Prioridade para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osFilha, atribuidoPor);
					}else{
						if(osFilha.getReparo().getPrioridade().compareTo(date)!=0){
							observacaoService.log("Reparo", "Prioridade alterada de: " + DateUtils.formatarDataDDMMYYYY(osFilha.getReparo().getPrioridade()) + " para: " + DateUtils.formatarDataDDMMYYYY(date),  2, new Date(),osFilha, atribuidoPor);
						}
					}

					osFilha.getReparo().setPrioridade(date);
					salvarReparoSimples(osFilha.getReparo());
				}
			}

			//Remonta objeto reparoEditado para retorno
			if(osPai != null){
				osPai.setPlacasFilhas(listaFilhas);
				reparoEditado.getOrdemServico().setUnidadePai(osPai);
			}else if(osPai == null && listaFilhas != null && !listaFilhas.isEmpty()){
				reparoEditado.getOrdemServico().setPlacasFilhas(listaFilhas);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEditado;
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo reAtribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception {
		Reparo reparoEditado;
		try {
			String vazio = " ";
			observacaoService.log("Reparo", "Reatribuição de técnico: " + (reparo.getTecnicoResponsavel() != null ?reparo.getTecnicoResponsavel().getUsuario() : vazio) + " -> " + usuario.getUsuario(), 2, new Date(),reparo.getOrdemServico(), atribuidoPor);
			usuario = usuarioService.buscarPorId(usuario.getId());
			reparo.setTecnicoResponsavel(usuario);
			reparoEditado =(Reparo) salvarReparoSimples(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparoEditado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<Reparo> atribuirTecnico(List<Reparo> reparos, Usuario usuario) throws Exception {
		Reparo reparoEditado;
		List<Reparo> reparosEditados = new ArrayList<Reparo>();
		try {
			usuario = usuarioService.buscarPorId(usuario.getId());
			for(Reparo reparo : reparos){
				reparo.setTecnicoResponsavel(usuario);
				reparoEditado =(Reparo) salvarReparoSimples(reparo);
				reparosEditados.add(reparoEditado);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return reparosEditados;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirReparo(Reparo reparo) throws Exception {
		try {
			reparoDao.delete(reparo);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

}
