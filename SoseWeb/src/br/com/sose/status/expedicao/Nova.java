package br.com.sose.status.expedicao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;

@Service("novaStatusExpedicao")
public class Nova extends StatusExpedicao {

	public static final String nome = "Nova"; 
	
	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public Nova(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}
	
	public Nova() {
	}

	public String getStatus(){
		return "Nova";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa iniciarNotaFiscalSaida(Usuario usuario) throws Exception {
		notaFiscalRemessa.setStatusString(Iniciada.nome);
		notaFiscalRemessa.setDtIniciacao(new Date());
		notaFiscalRemessa = notaFiscalRemessaService.salvarNotaFiscalRemessa(notaFiscalRemessa);
//		notaFiscalRemessa = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());
		observacaoService.log("Expedição", "Nota fiscal de saída iniciada com sucesso", 2, new Date(),notaFiscalRemessa, usuario);
		return notaFiscalRemessa;
	}
	
	
	
}
