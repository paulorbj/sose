package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.status.aplicacao.OrcamentoSendoRealizado;

@Service("naoIniciadoStatusOrcamento")
public class NaoIniciado extends StatusOrcamento {

	public static final String nome = "Não Iniciado"; 
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public NaoIniciado(Orcamento r) {
		super.orcamento = r;
	}
	
	public NaoIniciado() {
	}

	public String getNome(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento iniciarOrcamento(Orcamento orcamento, Usuario realizadoPor) throws Exception {
		orcamento.setStatusString(Iniciado.nome);
		orcamento.setDataInicio(new Date());
		orcamento = orcamentoService.salvarOrcamento(orcamento);
		
		orcamento.getOrdemServico().setStatusString(OrcamentoSendoRealizado.nome);
		ordemServicoService.salvarOrdemServico(orcamento.getOrdemServico());
		
		observacaoService.log("Orçamento", "Orçamento iniciado", 2, new Date(),orcamento, realizadoPor);
		return orcamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		return orcamentoService.reAtribuirTecnico(orcamento, usuario, atribuidoPor);	
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario usuario) throws Exception {
		return orcamentoService.atribuirPrioridade(orcamento,date, usuario);
	}
	
}
