package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.service.orcamento.OrcamentoService;

@Service("aguardandoPropostaStatusOrcamento")
public class AguardandoProposta extends StatusOrcamento {

	public static final String nome = "Aguardando proposta"; 
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	public AguardandoProposta(Orcamento r) {
		super.orcamento = r;
	}
	
	public AguardandoProposta() {
	}

	public String getNome(){
		return nome;
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
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento salvarOrcamento(Orcamento orcamento) throws Exception {
		return orcamentoService.salvarOrcamento(orcamento);
	}
	
}
