package br.com.sose.status.expedicao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Observacao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;

public abstract class StatusExpedicao {

	protected NotaFiscalRemessa notaFiscalRemessa;
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;
	
	public abstract String getStatus();

	public NotaFiscalRemessa getNotaFiscalRemessa() {
		return notaFiscalRemessa;
	}

	public void setNotaFiscalRemessa(NotaFiscalRemessa notaFiscalRemessa) {
		this.notaFiscalRemessa = notaFiscalRemessa;
	}

	public NotaFiscalRemessa iniciarNotaFiscalSaida(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscalRemessa salvarNotaFiscalSaida(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");	
	}

	public NotaFiscalRemessa emitirNotaFiscalSaida(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscalRemessa editarNotaFiscalSaida() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");	
	}

	public NotaFiscalRemessa cancelarNotaFiscalSaida() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscalRemessa duplicarNotaFiscalSaida() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscalRemessa finalizarNotaFiscalSaida(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");	
	}
	
	public NotaFiscalRemessa solicitarNotaFiscalSaida(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");	
	}

	public NotaFiscalRemessa gerarEtiquetas() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public NotaFiscalRemessa criarNotaFiscal(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	public NotaFiscalRemessa registrarDataSaidaNotaFiscalSaida(Date dataSaida,Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void salvarObservacaoNotaFiscal(Usuario usuario)
	{
		if(notaFiscalRemessa.getObservacao() != null && !notaFiscalRemessa.getObservacao().isEmpty()){
			if(notaFiscalRemessa.getObservacaoAnterior() == null || !notaFiscalRemessa.getObservacao().equals(notaFiscalRemessa.getObservacaoAnterior())){
				Observacao obs = new Observacao();
				obs.setUsuario(usuario);
				obs.setTexto(notaFiscalRemessa.getObservacao());
				obs.setNotaFiscalSaida(notaFiscalRemessa);
				obs.setOrigem("Expedição");
				obs.setEscopo(3);
				obs.setData(new Date());
				try{
					observacaoService.salvarObservacao(obs);
					notaFiscalRemessa.setObservacaoAnterior(notaFiscalRemessa.getObservacao());
					notaFiscalRemessaService.salvarNotaFiscalRemessaSimples(notaFiscalRemessa);
				}catch(Exception e){
					e.printStackTrace(); logger.error(e);
				}
			}
		}
	}

}
