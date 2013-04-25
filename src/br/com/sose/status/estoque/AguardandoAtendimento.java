package br.com.sose.status.estoque;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;

@Service("aguardandoAtendimentoStatusEstoque")
public class AguardandoAtendimento extends StatusEstoque {

	public static final String nome = "Aguardando atendimento"; 
	
	@Autowired
	private RequisicaoComponenteService requisicaoComponenteService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public AguardandoAtendimento(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public AguardandoAtendimento() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente atenderRequisicao(Usuario usuario) throws Exception {
		requisicaoComponente.setDataAtendimento(new Date());
		requisicaoComponente.setStatusString(ColetandoMaterial.nome);
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" atendida", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		return requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(Usuario usuario) throws Exception {
		requisicaoComponente = requisicaoComponenteService.cancelarRequisicaoRestituirComponentes(requisicaoComponente,usuario);
		//observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" cancelada ", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		return requisicaoComponente;
	}

	
}
