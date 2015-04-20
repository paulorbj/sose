package br.com.sose.status.expedicao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.utils.DateUtils;

@Service("emitidaStatusExpedicao")
public class Emitida extends StatusExpedicao {

	public static final String nome = "Emitida"; 

	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ObservacaoService observacaoService;

	public Emitida(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}

	public Emitida() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return "Emitida";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa finalizarNotaFiscalSaida(Usuario usuario) throws Exception {
		notaFiscalRemessa.setDtFinalizacao(new Date());
		notaFiscalRemessa.setStatusString(Finalizada.nome);
		notaFiscalRemessa = notaFiscalRemessaService.salvarNotaFiscalRemessa(notaFiscalRemessa);
//		notaFiscalRemessa = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());
		observacaoService.log("Expedição", "O material foi recebido por " + notaFiscalRemessa.getRecebidoPor() + " em " + notaFiscalRemessa.getDataRecebimentoMaterial(), 2, new Date(),notaFiscalRemessa, usuario);
		return notaFiscalRemessa;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa registrarDataSaidaNotaFiscalSaida(Date dataSaida,Usuario usuario) throws Exception {
		NotaFiscalRemessa notaFiscalRemessaRetorno = null;
		notaFiscalRemessa.setDtSaida(dataSaida);
		notaFiscalRemessa.setSaidaRegistradaEm(new Date());

		notaFiscalRemessaRetorno = notaFiscalRemessaService.salvarNotaFiscalRemessa(notaFiscalRemessa);
		salvarObservacaoNotaFiscal(usuario);
//		notaFiscalRemessaRetorno = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());

		observacaoService.log("Expedição", "O material foi retirado por " + notaFiscalRemessaRetorno.getNomeMotorista() +" portador do documento de número " + notaFiscalRemessaRetorno.getNumeroDocumento() + " e veículo de placa " +notaFiscalRemessaRetorno.getPlacaVeiculo()+ " na data " + notaFiscalRemessaRetorno.getDtSaida(), 2, new Date(),notaFiscalRemessaRetorno, usuario);
		return notaFiscalRemessaRetorno;
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
