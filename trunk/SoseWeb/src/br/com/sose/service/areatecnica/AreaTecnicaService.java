package br.com.sose.service.areatecnica;

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

import br.com.sose.daoImpl.administrativo.LaboratorioDao;
import br.com.sose.daoImpl.areaTecnica.AreaTecnicaDao;
import br.com.sose.daoImpl.orcamento.OrcamentoDao;
import br.com.sose.daoImpl.reparo.ReparoDao;
import br.com.sose.entity.admistrativo.Laboratorio;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.OrcRepGenerico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.exceptions.AtribuidorNaoEhLiderDoLaboratorioException;
import br.com.sose.exceptions.TecnicoNaoPertenceAoLaboratorioException;
import br.com.sose.status.orcamento.OrcamentoController;
import br.com.sose.status.reparo.ReparoController;
import br.com.sose.to.OrcRepGenericoTO;
import br.com.sose.utils.AreaTecnicaUtils;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.OrdemServicoUtils;

@Service(value="areaTecnicaService")
@RemotingDestination(value="areaTecnicaService")
public class AreaTecnicaService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public OrcamentoDao orcamentoDao;

	@Autowired
	public ReparoDao reparoDao;

	@Autowired
	public LaboratorioDao laboratorioDao;

	@Autowired
	public AreaTecnicaDao areaTecnicaDao;

	@Autowired
	public ReparoController reparoController;

	@Autowired
	public OrcamentoController orcamentoController;
	
	@Autowired
	public OrdemServicoUtils ordemServicoUtils;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<OrcRepGenerico> listarOrcamentoReparo() throws Exception {
		List<OrcRepGenerico> orcamentoReparoRetorno = new ArrayList<OrcRepGenerico>();
		try {
			orcamentoReparoRetorno =(List<OrcRepGenerico>) areaTecnicaDao.listarOrcamentoReparo();
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoReparoRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<OrcRepGenericoTO> listarOrcamentoReparoOtimizado() throws Exception {
		List<OrcRepGenericoTO> orcamentoReparoRetorno = new ArrayList<OrcRepGenericoTO>();
		List<OrcRepGenericoTO> aux = new ArrayList<OrcRepGenericoTO>();

		try {
			aux =(List<OrcRepGenericoTO>) orcamentoDao.listarOrcamentoOtimizado();
			if(aux != null && !aux.isEmpty()){
				orcamentoReparoRetorno.addAll(aux);
			}
			aux =(List<OrcRepGenericoTO>) reparoDao.listarReparoOtimizado();
			if(aux != null && !aux.isEmpty()){
				orcamentoReparoRetorno.addAll(aux);
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoReparoRetorno;
	}

	@Transactional(readOnly = true)
	public void validarAtribuicaoTecnico(List<OrcRepGenericoTO> orcReps, Usuario usuario, Usuario atribuidoPor) throws Exception{
		Laboratorio laboratorio;
		for(OrcRepGenericoTO org : orcReps){
			laboratorio = laboratorioDao.buscarPorId(org.getIdLaboratorio());
			if(!atribuidoPor.getPerfil().getAcaoPrioridadeGerencialParaAtribuicoes()){
				if(!laboratorio.getLider().getId().equals(atribuidoPor.getId())){
					//Atribuidor nao eh lider do laboratorio de uma das atribuicoes
					throw new AtribuidorNaoEhLiderDoLaboratorioException(atribuidoPor.getUsuario(),laboratorio.getNome());
				}
			}

			Boolean tecnicoPertenceAoLaboratorio = false;
			for (Usuario tecnico : laboratorio.getTecnicos()){
				if(tecnico.getId().equals(usuario.getId())){
					tecnicoPertenceAoLaboratorio = true;
					break;
				}
			}
			if(!tecnicoPertenceAoLaboratorio){
				//O tecnico nao pertence ao laboratorio
				throw new TecnicoNaoPertenceAoLaboratorioException(usuario.getUsuario(),laboratorio.getNome());
			}


		}
	}

	@Transactional(readOnly = true)
	public void validarAtribuicaoPrioridade(List<OrcRepGenericoTO> orcReps,Usuario atribuidoPor) throws Exception{
		Laboratorio laboratorio;
		if(!atribuidoPor.getPerfil().getAcaoPrioridadeGerencialParaAtribuicoes()){
			for(OrcRepGenericoTO org : orcReps){
				laboratorio = laboratorioDao.buscarPorId(org.getIdLaboratorio());
				if(!laboratorio.getLider().getId().equals(atribuidoPor.getId())){
					//Atribuidor nao eh lider do laboratorio de uma das atribuicoes
					throw new AtribuidorNaoEhLiderDoLaboratorioException(atribuidoPor.getUsuario(),laboratorio.getNome());
				}
			}
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<OrcRepGenericoTO> atribuirTecnico(List<OrcRepGenericoTO> orcReps, Usuario usuario, Usuario atribuidoPor) throws Exception{
		List<OrcRepGenericoTO> orcRepsRetorno = new ArrayList<OrcRepGenericoTO>();
		Reparo reparo;
		Orcamento orcamento;

		validarAtribuicaoTecnico(orcReps, usuario, atribuidoPor);

		for(OrcRepGenericoTO org : orcReps){
			if(org.getFinalidade().equals("Reparo")){
				reparo = reparoController.getStatus(reparoController.converterOrcRepGenericoToReparo(org));
				reparo.getStatus().atribuirTecnico(reparo, usuario, atribuidoPor);
				orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
			}else if(org.getFinalidade().equals("Orçamento")){
				orcamento = orcamentoController.getStatus(orcamentoController.converterOrcRepGenericoToOrcamento(org));
				orcamento.getStatus().atribuirTecnico(orcamento, usuario, atribuidoPor);
				orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
			}

		}

		return orcRepsRetorno;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<OrcRepGenericoTO> atribuirPrioridade(List<OrcRepGenericoTO> orcReps, Date data, Usuario atribuidoPor) throws Exception{
		try{
		List<OrcRepGenericoTO> orcRepsRetorno = new ArrayList<OrcRepGenericoTO>();
		Reparo reparo;
		Orcamento orcamento;

		validarAtribuicaoPrioridade(orcReps, atribuidoPor);


		for(OrcRepGenericoTO org : orcReps){
			if(org.getFinalidade().equals("Reparo")){
				reparo = reparoController.getStatus(reparoController.converterOrcRepGenericoToReparo(org));
				
				if(ordemServicoUtils.pertenceConjunto(reparo.getOrdemServico())){
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(reparo.getOrdemServico());
					reparo =  reparoController.getStatus(cos.getOsPai().getReparo());
					reparo = reparo.getStatus().atribuirPrioridade(reparo, data,atribuidoPor);
					orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
					
					for(OrdemServico osFilha : cos.getListaFilhas()){
						reparo =  reparoController.getStatus(osFilha.getReparo());
						reparo = reparo.getStatus().atribuirPrioridade(reparo, data,atribuidoPor);
						orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
					}
				}else{
					reparo = reparo.getStatus().atribuirPrioridade(reparo, data,atribuidoPor);
					orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
				}
				
				
//				if(reparo.getOrdemServico().getUnidadePai() != null){
//					//Eh filha
//					orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo.getOrdemServico().getUnidadePai().getReparo()));
//					for(OrdemServico os : reparo.getOrdemServico().getUnidadePai().getPlacasFilhas()){
//						orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(os.getReparo()));
//					}
//				}else if(reparo.getOrdemServico().getUnidadePai() == null && reparo.getOrdemServico().getPlacasFilhas() != null && !reparo.getOrdemServico().getPlacasFilhas().isEmpty()){
//					//Eh pai
//					orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
//					for(OrdemServico os : reparo.getOrdemServico().getPlacasFilhas()){
//						orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(os.getReparo()));
//					}
//				}else{
//					orcRepsRetorno.add(AreaTecnicaUtils.converterReparoToOrcRep(reparo));
//				}
			}else if(org.getFinalidade().equals("Orçamento")){
				orcamento = orcamentoController.getStatus(orcamentoController.converterOrcRepGenericoToOrcamento(org));
				
				if(ordemServicoUtils.pertenceConjunto(orcamento.getOrdemServico())){
					ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(orcamento.getOrdemServico());
					orcamento = orcamentoController.getStatus(cos.getOsPai().getOrcamento());
					orcamento = orcamento.getStatus().atribuirPrioridade(orcamento, data,atribuidoPor);
					orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
					
					for(OrdemServico osFilha : cos.getListaFilhas()){
						orcamento =  orcamentoController.getStatus(osFilha.getOrcamento());
						orcamento = orcamento.getStatus().atribuirPrioridade(orcamento, data,atribuidoPor);
						orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
					}
				}else{
					orcamento = orcamento.getStatus().atribuirPrioridade(orcamento, data,atribuidoPor);
					orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
				}
				
//				orcamento = orcamento.getStatus().atribuirPrioridade(orcamento, data,atribuidoPor);
//				if(orcamento.getOrdemServico().getUnidadePai() != null){
//					//Eh filha
//					orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento.getOrdemServico().getUnidadePai().getOrcamento()));
//					for(OrdemServico os : orcamento.getOrdemServico().getUnidadePai().getPlacasFilhas()){
//						orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(os.getOrcamento()));
//					}
//				}else if(orcamento.getOrdemServico().getUnidadePai() == null && orcamento.getOrdemServico().getPlacasFilhas() != null && !orcamento.getOrdemServico().getPlacasFilhas().isEmpty()){
//					//Eh pai
//					orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
//					for(OrdemServico os : orcamento.getOrdemServico().getPlacasFilhas()){
//						orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(os.getOrcamento()));
//					}
//				}else{
//					orcRepsRetorno.add(AreaTecnicaUtils.converterOrcamentoToOrcRep(orcamento));
//				}
			}

		}

		return orcRepsRetorno;
		
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<OrcRepGenerico> listarHistoricoOrcRep(OrcRepGenerico orcRep){
		List<OrcRepGenerico> orcRepsRetorno = new ArrayList<OrcRepGenerico>();
		List<Orcamento> orcamentos = new ArrayList<Orcamento>();
		List<Reparo> reparos = new ArrayList<Reparo>();
		if(orcRep instanceof Orcamento){
			Orcamento orcamento = (Orcamento)orcRep;
			orcamentos = orcamentoDao.listarHistoricoReparo(orcamento.getOrdemServico().getUnidade(), orcamento.getOrdemServico().getSerieFabricante());
			if(orcamentos != null && !orcamentos.isEmpty()){
				for (Orcamento o : orcamentos){
					if(o.getId() != orcRep.getId()){
						orcRepsRetorno.add(o);
					}
				}
			}
		}else{
			Reparo reparo = (Reparo)orcRep;
			reparos = reparoDao.listarHistoricoReparo(reparo.getOrdemServico().getUnidade(), reparo.getOrdemServico().getSerieFabricante());
			if(reparos != null && !reparos.isEmpty()){
				for (Reparo r : reparos){
					if(r.getId() != orcRep.getId()){
						orcRepsRetorno.add(r);
					}
				}
			}
		}


		return orcRepsRetorno;
	}


}
