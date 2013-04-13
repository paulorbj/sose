package br.com.sose.status.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service("reparoSendoRealizadoAplicacaoStatus")
public class ReparoSendoRealizado extends StatusAplicacao {

	public static final String nome = "Reparo sendo realizado"; 

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	public ReparoSendoRealizado(OrdemServico o) {
		super.ordemServico = o;
	}
	
	public ReparoSendoRealizado() {
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
