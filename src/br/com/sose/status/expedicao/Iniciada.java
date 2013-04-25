package br.com.sose.status.expedicao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;
import br.com.sose.service.recebimento.NotaFiscalService;

@Service("iniciadaStatusExpedicao")
public class Iniciada extends StatusExpedicao {

	public static final String nome = "Iniciada"; 
	
	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;
	
	@Autowired
	private NotaFiscalService notaFiscalService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public Iniciada(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}
	
	public Iniciada() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return "Iniciada";
	}

	@Override
	public NotaFiscalRemessa cancelarNotaFiscalSaida() throws Exception {
		// TODO Auto-generated method stub
		return super.cancelarNotaFiscalSaida();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa solicitarNotaFiscalSaida(Usuario usuario) throws Exception {
		notaFiscalRemessa.setSolicitacaoRegistradaEm(new Date());
		notaFiscalRemessa.setStatusString(Solicitada.nome);
		notaFiscalRemessa = notaFiscalRemessaService.salvarNotaFiscalRemessa(notaFiscalRemessa);
		salvarObservacaoNotaFiscal(usuario);
//		notaFiscalRemessa = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());
		for(OrdemServico os : notaFiscalRemessa.getOrdensServico()){
			notaFiscalService.finalizarNotaFiscal(os.getNotaFiscal());
		}
		observacaoService.log("Expedição", "Nota fiscal de saída emitida com sucesso", 2, new Date(),notaFiscalRemessa, usuario);
		return notaFiscalRemessa;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa salvarNotaFiscalSaida(Usuario usuario) throws Exception {
		notaFiscalRemessa = notaFiscalRemessaService.salvarNotaFiscalRemessa(notaFiscalRemessa);
		salvarObservacaoNotaFiscal(usuario);
//		notaFiscalRemessa = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());
		return notaFiscalRemessa;
	}
	
} 
