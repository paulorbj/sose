package br.com.sose.status.recebimento;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Observacao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.recebimento.NotaFiscalService;

@Service
public abstract class StatusRecebimento {

	private Logger logger = Logger.getLogger(this.getClass());

	protected NotaFiscal notaFiscal;

	public abstract String getNome();

	@Autowired
	private ObservacaoService observacaoService;
	
	@Autowired
	private NotaFiscalService notaFiscalService;

	public NotaFiscal salvarNotaFiscal(Usuario usuario) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscal editarNotaFiscal() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscal cancelarNotaFiscal() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscal processarNotaFiscal(Usuario usurio) throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscal duplicarNotaFiscal() throws Exception{
		throw new Exception("Essa não é uma operação válida nesse status");
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void salvarObservacaoNotaFiscal(Usuario usuario)
	{
		if(notaFiscal.getObservacao() != null && !notaFiscal.getObservacao().isEmpty()){
			if(notaFiscal.getObservacaoAnterior() == null || !notaFiscal.getObservacao().equals(notaFiscal.getObservacaoAnterior())){
				Observacao obs = new Observacao();
				obs.setUsuario(usuario);
				obs.setTexto(notaFiscal.getObservacao());
				obs.setNotaFiscal(notaFiscal);
				obs.setOrigem("Recebimento");
				obs.setEscopo(3);
				obs.setData(new Date());
				try{
					observacaoService.salvarObservacao(obs);
					notaFiscal.setObservacaoAnterior(notaFiscal.getObservacao());
					notaFiscalService.salvarNotaFiscalSimples(notaFiscal);
				}catch(Exception e){
					e.printStackTrace(); logger.error(e);
				}
			}
		}
	}
}
