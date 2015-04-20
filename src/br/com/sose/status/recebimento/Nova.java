package br.com.sose.status.recebimento;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.exceptions.NumeroNFExistenteParaClienteException;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.recebimento.NotaFiscalService;

@Service("novaRecebimentoStatus")
public class Nova extends StatusRecebimento {
	
	public static final String nome = "Nova"; 
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private NotaFiscalService notaFiscalService;
	
	@Autowired
	private ObservacaoService observacaoService;

	public Nova() {
	
	}
	
	public Nova(NotaFiscal nf) {
		super.notaFiscal = nf;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal processarNotaFiscal(Usuario usuario) throws Exception {
		try{
			List<NotaFiscal> nfs = notaFiscalService.buscarPorNumeroCliente(notaFiscal.getNumero(), notaFiscal.getCliente().getId());
			if(nfs != null && !nfs.isEmpty()){
				throw new NumeroNFExistenteParaClienteException(notaFiscal.getNumero());
			}
			this.notaFiscal.setStatusString(Processada.nome);
			NotaFiscal notaFiscalRetorno;
			notaFiscal = notaFiscalService.salvarNotaFiscal(notaFiscal);
			salvarObservacaoNotaFiscal(usuario);
			notaFiscalRetorno = notaFiscalService.procesarNotaFiscal(notaFiscal,usuario);
//			notaFiscalRetorno = notaFiscalService.buscarPorId(notaFiscalRetorno.getId());
			return notaFiscalRetorno;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			throw(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscal salvarNotaFiscal(Usuario usuario) throws Exception {
		NotaFiscal notaFiscalRetorno;
		notaFiscalRetorno = notaFiscalService.salvarNotaFiscal(notaFiscal);
		salvarObservacaoNotaFiscal(usuario);
//		notaFiscalRetorno = notaFiscalService.buscarPorId(notaFiscal.getId());
		return notaFiscalRetorno;
	}
	
	


	public String getNome(){
		return nome;
	}

	public NotaFiscalService getNotaFiscalService() {
		return notaFiscalService;
	}

	public void setNotaFiscalService(NotaFiscalService notaFiscalService) {
		this.notaFiscalService = notaFiscalService;
	}
	
}
