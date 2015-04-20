package br.com.sose.status.orcamentodiferenciado;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.orcamento.OrcamentoDiferenciadoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.OrcamentoDiferenciadoSendoRealizado;

@Service("naoIniciadooStatusOrcamentoDiferenciado")
public class NaoIniciado extends StatusOrcamentoDiferenciado {

	public static String nome = "Não iniciado";
	
	@Autowired
	private OrcamentoDiferenciadoService orcamentoDiferenciadoService;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public NaoIniciado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}

	public NaoIniciado() {
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado iniciarOrcamentoDiferenciado(Usuario usuario) throws Exception {
		orcamentoDiferenciado.setDataInicio(new Date());
		orcamentoDiferenciado.setStatusString(Iniciado.nome);		
		
		OrdemServico os = orcamentoDiferenciado.getOrdemServico();
		os.setStatusString(OrcamentoDiferenciadoSendoRealizado.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);
		observacaoService.log("Orçamento diferenciado", "Orçamento diferenciado iniciado", 2, new Date(),os, usuario);
		
		orcamentoDiferenciado = orcamentoDiferenciadoService.salvarOrcamentoDiferenciado(orcamentoDiferenciado);
		orcamentoDiferenciado = orcamentoDiferenciadoService.buscarPorId(orcamentoDiferenciado.getId());
		return orcamentoDiferenciado;
	}
	
	
	
}
