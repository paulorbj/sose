package br.com.sose.status.orcamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;

@Service("finalizadoStatusOrcamento")
public class Finalizado extends StatusOrcamento {

	public static final String nome = "Finalizado"; 

	@Autowired
	private OrcamentoService orcamentoService;
	
	public Finalizado(Orcamento r) {
		super.orcamento = r;
	}
	
	public Finalizado() {
	}

	public String getNome(){
		return nome;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirPrioridade(Orcamento orcamento, Date date, Usuario usuario) throws Exception {
		return orcamentoService.atribuirPrioridade(orcamento,date, usuario);
	}
	
}
