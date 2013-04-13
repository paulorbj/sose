package br.com.sose.status.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service("aguardandoPropostaAplicacaoStatus")
public class AguardandoProposta extends StatusAplicacao {

	public static final String nome = "Aguardando proposta"; 

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	public AguardandoProposta() {
		// TODO Auto-generated constructor stub
	}
	
	public AguardandoProposta(OrdemServico o) {
		super.ordemServico = o;
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
