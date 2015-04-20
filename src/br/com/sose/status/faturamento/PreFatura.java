package br.com.sose.status.faturamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.service.faturamento.FaturamentoService;

@Service("preFaturaStatusFaturamento")
public class PreFatura extends StatusFaturamento {

	public static final String nome = "Realizando pré-faturamento"; 
	
	@Autowired
	private FaturamentoService faturamentoService;
	
	public PreFatura(Faturamento nfr) {
		super.faturamento = nfr;
	}
	
	public PreFatura() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento criarFatura(Usuario usuario) throws Exception {
		faturamento.setStatusString(NaoIniciado.nome);
		faturamento.setDataCriacaoFatura(new Date());
		faturamento = faturamentoService.gerarFaturamento(faturamento);

		return faturamento;
	}

	
	
}
