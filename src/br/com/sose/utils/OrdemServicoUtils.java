package br.com.sose.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.orcamento.AguardandoConjuntoExpedicao;
import br.com.sose.status.orcamento.AguardandoConjuntoProposta;

@Service(value="ordemServicoUtils")
@RemotingDestination(value="ordemServicoUtils")
public class OrdemServicoUtils {

	@Autowired
	private OrdemServicoService ordemServicoService;

	public Boolean pertenceConjunto(OrdemServico ordemServico) throws Exception{
		try{
			ordemServico = ordemServicoService.montarConjunto(ordemServico.getId());
			if(ordemServico.getUnidadePai() != null){
				//Eh filha
				return true;
			}else if(ordemServico.getUnidadePai() == null && ordemServico.getPlacasFilhas() != null && !ordemServico.getPlacasFilhas().isEmpty()){
				//Eh pai
				return true;
			}else{
				//Eh individual
				return false;
			}
		}catch(Exception e){
			throw e; 
		}
	}

	public Boolean conjuntoCompleto(Orcamento orcamento) throws Exception{
		try{
			OrdemServico ordemServico = ordemServicoService.montarConjunto(orcamento.getOrdemServico().getId());
			if(ordemServico.getUnidadePai() != null){
				//Eh filha
				OrdemServico osPai = ordemServico.getUnidadePai();
				osPai = ordemServicoService.montarConjunto(osPai.getId());
				if(osPai.getOrcamento().getStatusString().equals(AguardandoConjuntoProposta.nome) || osPai.getOrcamento().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
					for(OrdemServico osFilha : osPai.getPlacasFilhas()){
						if(!(osFilha.getOrcamento().getStatusString().equals(AguardandoConjuntoProposta.nome) || osFilha.getOrcamento().getStatusString().equals(AguardandoConjuntoExpedicao.nome))){
							return false;
						}
					}
				}else{
					return false;
				}
				return true;
			}else if(ordemServico.getUnidadePai() == null && ordemServico.getPlacasFilhas() != null && !ordemServico.getPlacasFilhas().isEmpty()){
				//Eh pai
				if(ordemServico.getOrcamento().getStatusString().equals(AguardandoConjuntoProposta.nome) || ordemServico.getOrcamento().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
					for(OrdemServico osFilha : ordemServico.getPlacasFilhas()){
						if(!(osFilha.getOrcamento().getStatusString().equals(AguardandoConjuntoProposta.nome) || osFilha.getOrcamento().getStatusString().equals(AguardandoConjuntoExpedicao.nome))){
							return false;
						}
					}
				}else{
					return false;
				}
				return true;
			}else{
				//Eh individual
				throw new Exception();
			}
		}catch(Exception e){
			throw e; 
		}
	}
	
	public Boolean conjuntoCompleto(Reparo reparo) throws Exception{
		try{
			OrdemServico ordemServico = ordemServicoService.montarConjunto(reparo.getOrdemServico().getId());
			if(ordemServico.getUnidadePai() != null){
				//Eh filha
				OrdemServico osPai = ordemServico.getUnidadePai();
				osPai = ordemServicoService.montarConjunto(osPai.getId());
				if(osPai.getReparo().getStatusString().equals(AguardandoConjuntoProposta.nome) || osPai.getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
					for(OrdemServico osFilha : osPai.getPlacasFilhas()){
						if(!(osFilha.getReparo().getStatusString().equals(AguardandoConjuntoProposta.nome) || osFilha.getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome))){
							return false;
						}
					}
				}else{
					return false;
				}
				return true;
			}else if(ordemServico.getUnidadePai() == null && ordemServico.getPlacasFilhas() != null && !ordemServico.getPlacasFilhas().isEmpty()){
				//Eh pai
				if(ordemServico.getReparo().getStatusString().equals(AguardandoConjuntoProposta.nome) || ordemServico.getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
					for(OrdemServico osFilha : ordemServico.getPlacasFilhas()){
						if(!(osFilha.getReparo().getStatusString().equals(AguardandoConjuntoProposta.nome) || osFilha.getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome))){
							return false;
						}
					}
				}else{
					return false;
				}
				return true;
			}else{
				//Eh individual
				throw new Exception();
			}
		}catch(Exception e){
			throw e; 
		}
	}

	public ConjuntoOrdemServico montarConjunto(OrdemServico ordemServico) throws Exception{
		try{
			ConjuntoOrdemServico cos = new ConjuntoOrdemServico();
			ordemServico = ordemServicoService.montarConjunto(ordemServico.getId());
			if(ordemServico.getUnidadePai() != null){
				//Eh filha
				OrdemServico osPai = ordemServico.getUnidadePai();
				osPai = ordemServicoService.montarConjunto(osPai.getId());
				cos.setOsPai(osPai);
				for(OrdemServico osFilha : osPai.getPlacasFilhas()){
					osFilha = ordemServicoService.montarConjunto(osFilha.getId());
					cos.getListaFilhas().add(osFilha);
				}
			}else if(ordemServico.getUnidadePai() == null && ordemServico.getPlacasFilhas() != null && !ordemServico.getPlacasFilhas().isEmpty()){
				//Eh pai
				cos.setOsPai(ordemServico);
				for(OrdemServico osFilha : ordemServico.getPlacasFilhas()){
					osFilha = ordemServicoService.montarConjunto(osFilha.getId());
					cos.getListaFilhas().add(osFilha);
				}
			}else{
				//Eh individual
				throw new Exception();
			}
			return cos;
		}catch(Exception e){
			throw e; 
		}
	}
}
