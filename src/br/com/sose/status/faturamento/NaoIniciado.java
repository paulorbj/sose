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

@Service("naoIniciadoStatusFaturamento")
public class NaoIniciado extends StatusFaturamento {

	public static String nome = "Não iniciado";
	
	@Autowired
	private FaturamentoService faturamentoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public NaoIniciado(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

	public NaoIniciado() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Faturamento iniciarFatura(Usuario usuario) throws Exception {
		faturamento.setStatusString(Iniciada.nome);
		faturamento = faturamentoService.salvarFaturamento(faturamento);
		observacaoService.log("Faturamento", "Fatura iniciada com sucesso", 2, new Date(),faturamento, usuario);
		return faturamento;
	}
	

	
	
	
}
