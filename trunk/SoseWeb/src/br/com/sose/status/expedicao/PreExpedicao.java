package br.com.sose.status.expedicao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;
import br.com.sose.status.aplicacao.ExpedicaoSendoRealizada;

@Service("preExpedicaoStatusExpedicao")
public class PreExpedicao extends StatusExpedicao {

	public static final String nome = "Realizando pré-expedição"; 
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	@Autowired
	private OrdemServicoDao ordemServicoDao;
	
	public PreExpedicao(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}
	
	public PreExpedicao() {
	}

	public String getStatus(){
		return "Nova";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa criarNotaFiscal(Usuario usuario) throws Exception {
		try{
			notaFiscalRemessa.setDtCriacao(new Date());
			for(OrdemServico os : notaFiscalRemessa.getOrdensServico()){
//				OrdemServico osRecuperada = ordemServicoDao.buscarPorId2(os.getId());
				notaFiscalRemessa.setCliente(os.getCliente());
				break;
			}
			
			for(OrdemServico os : notaFiscalRemessa.getOrdensServico()){
				os.setNotaFiscalSaida(notaFiscalRemessa);
				os.setStatusString(ExpedicaoSendoRealizada.nome);
				ordemServicoDao.update(os);
			}
			
			notaFiscalRemessa.setStatusString(Nova.nome);
			notaFiscalRemessa = notaFiscalRemessaService.editarNotaFiscal(notaFiscalRemessa);
			observacaoService.log("Expedição", "Nota fiscal de saída criada", 2, new Date(),notaFiscalRemessa, usuario);
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return notaFiscalRemessa;
	}
	
	
	
}
