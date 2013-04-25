package br.com.sose.utils;

import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.to.OrcRepGenericoTO;

public class AreaTecnicaUtils {

	public static OrcRepGenericoTO converterReparoToOrcRep(Reparo reparo){
		OrcRepGenericoTO orcRep = new OrcRepGenericoTO();
		orcRep.setDataEntradaReparo(reparo.getDataEntrada());
		orcRep.setDataPrioridade(reparo.getPrioridade());
		orcRep.setIdOrcRep(reparo.getId());
		orcRep.setDataChegadaServilogi(reparo.getOrdemServico().getNotaFiscal().getDataChegada());
		orcRep.setDataLimite(reparo.getDataLimite());
		orcRep.setFinalidade("Reparo");
		if(reparo.getTecnicoResponsavel() != null){
			orcRep.setIdTecnicoResponsavel(reparo.getTecnicoResponsavel().getId());
			orcRep.setTecnico(reparo.getTecnicoResponsavel().getUsuario());
		}
		orcRep.setLaboratorio(reparo.getOrdemServico().getUnidade().getLaboratorio().getNome());
		orcRep.setIdLaboratorio(reparo.getOrdemServico().getUnidade().getLaboratorio().getId());

		orcRep.setLaudoTecnicoAprovado(reparo.getLaudoTecnicoAprovado());
		orcRep.setLaudoTecnicoReprovado(reparo.getLaudoTecnicoReprovado());
		orcRep.setNumeroNotaFiscal(reparo.getOrdemServico().getNotaFiscal().getNumero());
		orcRep.setNumeroOrdemServico(reparo.getOrdemServico().getNumeroOrdemServico());
		if(reparo.getOrdemServico().getUnidadePai() != null){
			orcRep.setNumeroOrdemServicoPai(reparo.getOrdemServico().getUnidadePai().getNumeroOrdemServico());
		}
		orcRep.setBloqueado(reparo.getOrdemServico().getBloqueado());
		orcRep.setCliente(reparo.getOrdemServico().getCliente().getNomeSistema());
		orcRep.setNumeroSerieCliente(reparo.getOrdemServico().getSerieCliente());
		orcRep.setNumeroSerieFabricante(reparo.getOrdemServico().getSerieFabricante());
		orcRep.setOrcamentoDiferenciadoRejeitado(reparo.getOrcamentoDiferenciadoRejeitado());
		orcRep.setPrazoDevolucao(reparo.getOrdemServico().getCliente().getPrazoDevolucao());
		orcRep.setPropostaAprovada(reparo.getPropostaAprovada());
		orcRep.setPropostaReprovada(reparo.getPropostaReprovada());
		orcRep.setStatusString(reparo.getStatusString());
		orcRep.setUnidade(reparo.getOrdemServico().getUnidade().getNome());
		orcRep.setComponentePendente(reparo.getComponentePendente());
		orcRep.setCriadoFromOrcamento(reparo.getCriadoFromOrcamento());
		return orcRep;
	}
	
	public static OrcRepGenericoTO converterOrcamentoToOrcRep(Orcamento orcamento){
		OrcRepGenericoTO orcRep = new OrcRepGenericoTO();
		orcRep.setDataEntradaReparo(orcamento.getDataEntrada());
		orcRep.setDataPrioridade(orcamento.getPrioridade());
		orcRep.setIdOrcRep(orcamento.getId());
		orcRep.setDataChegadaServilogi(orcamento.getOrdemServico().getNotaFiscal().getDataChegada());
		orcRep.setDataLimite(orcamento.getDataLimite());
		orcRep.setFinalidade("Orçamento");
		if(orcamento.getTecnicoResponsavel() != null){
			orcRep.setIdTecnicoResponsavel(orcamento.getTecnicoResponsavel().getId());
			orcRep.setTecnico(orcamento.getTecnicoResponsavel().getUsuario());
		}
		orcRep.setLaboratorio(orcamento.getOrdemServico().getUnidade().getLaboratorio().getNome());
		orcRep.setIdLaboratorio(orcamento.getOrdemServico().getUnidade().getLaboratorio().getId());
		orcRep.setLaudoTecnicoAprovado(orcamento.getLaudoTecnicoAprovado());
		orcRep.setLaudoTecnicoReprovado(orcamento.getLaudoTecnicoReprovado());
		orcRep.setNumeroNotaFiscal(orcamento.getOrdemServico().getNotaFiscal().getNumero());
		orcRep.setNumeroOrdemServico(orcamento.getOrdemServico().getNumeroOrdemServico());
		if(orcamento.getOrdemServico().getUnidadePai() != null){
			orcRep.setNumeroOrdemServicoPai(orcamento.getOrdemServico().getUnidadePai().getNumeroOrdemServico());
		}
		orcRep.setBloqueado(orcamento.getOrdemServico().getBloqueado());
		orcRep.setCliente(orcamento.getOrdemServico().getCliente().getNomeSistema());
		orcRep.setNumeroSerieCliente(orcamento.getOrdemServico().getSerieCliente());
		orcRep.setNumeroSerieFabricante(orcamento.getOrdemServico().getSerieFabricante());
		orcRep.setPrazoDevolucao(orcamento.getOrdemServico().getCliente().getPrazoDevolucao());
		orcRep.setPropostaReprovada(orcamento.getPropostaReprovada());
		orcRep.setRejeitadoPeloLider(orcamento.getRejeitadoPeloLider());
		orcRep.setStatusString(orcamento.getStatusString());
				
		orcRep.setUnidade(orcamento.getOrdemServico().getUnidade().getNome());
		orcRep.setComponentePendente(orcamento.getComponentePendente());
		return orcRep;
	}
}
