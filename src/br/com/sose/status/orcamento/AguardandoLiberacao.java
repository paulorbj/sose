package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.service.orcamento.OrcamentoService;

@Service("aguardandoLiberacaoStatusOrcamento")
public class AguardandoLiberacao extends StatusOrcamento {

	public static final String nome = "Aguardando liberação"; 
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	public AguardandoLiberacao(Orcamento r) {
		super.orcamento = r;
	}
	
	public AguardandoLiberacao() {
	}

	public String getNome(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception {
		orcamento.setStatusString(NaoIniciado.nome);
		return orcamentoService.atribuirTecnico(orcamento, usuario,atribuidoPor);	
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario usuario) throws Exception {
		return orcamentoService.atribuirPrioridade(orcamento,date, usuario);
	}
	
}
