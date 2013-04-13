package br.com.sose.status.faturamento;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.faturamento.FaturamentoService;

@Service("emitidaStatusFaturamento")
public class Emitida extends StatusFaturamento {

	public static final String nome = "Emitida"; 
	
	@Autowired
	private FaturamentoService faturamentoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public Emitida(Faturamento nfr) {
		super.faturamento = nfr;
	}
	
	public Emitida() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento salvarFatura(Usuario usuario) throws Exception {
		faturamento = faturamentoService.editarFaturamento(faturamento);
		return faturamento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento finalizarFatura(Usuario usuario) throws Exception {
		faturamento.setStatusString(Finalizada.nome);
		faturamento = faturamentoService.editarFaturamento(faturamento);
		observacaoService.log("Faturamento", "Fatura finalizada com sucesso", 2, new Date(),faturamento, usuario);
		return faturamento;
	}
	
	
	
}
