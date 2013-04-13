package br.com.sose.status.aplicacao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.recebimento.NotaFiscalService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.recebimento.Aberta;

@Service("naoProcessadaAplicacaoStatus")
public class NaoProcessada extends StatusAplicacao {

	public static final String nome = "Não processada"; 

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private NotaFiscalService notaFiscalService;
	
	public NaoProcessada(OrdemServico o) {
		super.ordemServico = o;
	}

	public NaoProcessada() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico processarOrdemServico(Usuario usuario) throws Exception {
		logger.info("Antes##INFORMAÇÕES OS: Nº OS: " + this.ordemServico.getNumeroOrdemServico() + " - S/N Fabricante: " + this.ordemServico.getSerieFabricante() + " - S/N Cliente: " + this.ordemServico.getSerieCliente() + " - OS Cliente: " + this.ordemServico.getOrdemServicoCliente());
		OrdemServico ordemServicoRetorno = ordemServicoService.processarOrdemServico(this.ordemServico,usuario);
		if(notaFiscalService.processamentoAberturaNotaFiscalFinalizado(ordemServicoRetorno.getNotaFiscal())){
			NotaFiscal notaFiscal = ordemServicoRetorno.getNotaFiscal();
			notaFiscal.setStatusString(Aberta.nome);
			notaFiscal = notaFiscalService.salvarNotaFiscal(notaFiscal);
			ordemServicoRetorno.setNotaFiscal(notaFiscal);
		}
		logger.info("Depois##INFORMAÇÕES OS: Nº OS: " + ordemServicoRetorno.getNumeroOrdemServico() + " - S/N Fabricante: " + ordemServicoRetorno.getSerieFabricante() + " - S/N Cliente: " + ordemServicoRetorno.getSerieCliente() + " - OS Cliente: " + ordemServicoRetorno.getOrdemServicoCliente());

		return ordemServicoRetorno;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrdemServico salvarOrdemServico() throws Exception {
		OrdemServico ordemServicoRetorno = ordemServicoService.salvarOrdemServico(this.ordemServico);
		return ordemServicoRetorno;
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

	public NotaFiscalService getNotaFiscalService() {
		return notaFiscalService;
	}

	public void setNotaFiscalService(NotaFiscalService notaFiscalService) {
		this.notaFiscalService = notaFiscalService;
	}

}
