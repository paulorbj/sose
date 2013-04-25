package br.com.sose.status.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service("propostaSendoRealizadoAplicacaoStatus")
public class PropostaSendoRealizada extends StatusAplicacao {

	public static final String nome = "Proposta sendo realizada"; 

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	public PropostaSendoRealizada(OrdemServico o) {
		super.ordemServico = o;
	}
	
	public PropostaSendoRealizada() {
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
