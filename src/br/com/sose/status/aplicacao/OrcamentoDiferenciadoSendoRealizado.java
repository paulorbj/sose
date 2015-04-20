package br.com.sose.status.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service("orcamentoDiferenciadoSendoRealizadoAplicacaoStatus")
public class OrcamentoDiferenciadoSendoRealizado extends StatusAplicacao {

	public static final String nome = "Orçamento diferenciado sendo realizado"; 

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	public OrcamentoDiferenciadoSendoRealizado(OrdemServico o) {
		super.ordemServico = o;
	}
	
	public OrcamentoDiferenciadoSendoRealizado() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public OrdemServico salvarOrdemServico() throws Exception {
		// TODO Auto-generated method stub
		return super.ordemServico;
	}

	public String getNome(){
		return nome;
	}

	public OrdemServicoService getOrdemServicoService() {
		return ordemServicoService;
	}

	public void setOrdemServicoService(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}
	
}
