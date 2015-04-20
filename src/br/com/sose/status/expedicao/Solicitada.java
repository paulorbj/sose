package br.com.sose.status.expedicao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.exceptions.NumeroNotaFiscalSaidaNaoDisponivelException;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.expedicao.NotaFiscalRemessaService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.utils.DateUtils;

@Service("solicitadaStatusExpedicao")
public class Solicitada extends StatusExpedicao {

	public static final String nome = "Emitir nota fiscal"; 

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private NotaFiscalRemessaService notaFiscalRemessaService;

	@Autowired
	private ObservacaoService observacaoService;
	
	@Autowired
	private OrdemServicoDao ordemServicoDao;
	
	@Autowired
	private OrdemServicoService ordemServicoService;

	public Solicitada(NotaFiscalRemessa nfr) {
		super.notaFiscalRemessa = nfr;
	}

	public Solicitada() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return "Emitir nota fiscal";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public NotaFiscalRemessa emitirNotaFiscalSaida(Usuario usuario) throws Exception {
		try{
		if(notaFiscalRemessaService.verificarDisponibilidadeNumeroOrdemServico(notaFiscalRemessa)){
			
			notaFiscalRemessa.setDtEmissao(new Date());
			notaFiscalRemessa = notaFiscalRemessaService.emitirNotaFiscalRemessa(notaFiscalRemessa);
			salvarObservacaoNotaFiscal(usuario);

			Set<OrdemServico> listaOrdemServico = new HashSet<OrdemServico>();

			Date dataFinalizacao = new Date();
			for(OrdemServico os :notaFiscalRemessa.getOrdensServico()){
				os = ordemServicoService.buscarCompletoPorId(os.getId());
				os.setDataFinalizacao(dataFinalizacao);
				os.setStatusString(br.com.sose.status.aplicacao.Finalizada.nome);
				if(os.getGarantia()){
					if(os.getEstenderGarantia() || os.getCliente().getEstenderGarantia()){
						os.setDataGarantiaAte(new Date(DateUtils.addDays(dataFinalizacao.getTime(), notaFiscalRemessa.getCliente().getTempoGarantia())));
					}else{
						if(os.getOsGarantia() != null){
							os.setDataGarantiaAte(os.getOsGarantia().getDataGarantiaAte());
						}else{
							os.setDataGarantiaAte(new Date(DateUtils.addDays(dataFinalizacao.getTime(), notaFiscalRemessa.getCliente().getTempoGarantia())));
						}
					}
				}else{
					os.setDataGarantiaAte(new Date(DateUtils.addDays(dataFinalizacao.getTime(), notaFiscalRemessa.getCliente().getTempoGarantia())));
				}

				//Computa o valor a ser faturado e a sua origem
				os = ordemServicoService.calcularValorFaturado(os);

				os = ordemServicoDao.update(os);

				listaOrdemServico.add(os);
			}
			
			notaFiscalRemessa = notaFiscalRemessaService.buscarPorId(notaFiscalRemessa.getId());
			
			notaFiscalRemessaService.calcularFretePorOrdemServico(notaFiscalRemessa);

			notaFiscalRemessa.setOrdensServico(listaOrdemServico);

			
			observacaoService.log("Expedição", "Nota fiscal de saída emitida com sucesso", 2, new Date(),notaFiscalRemessa, usuario);
			return notaFiscalRemessa;
		}else{
			throw new NumeroNotaFiscalSaidaNaoDisponivelException(notaFiscalRemessa.getNumero());
		}
		}catch(NumeroNotaFiscalSaidaNaoDisponivelException e){
			e.printStackTrace();
			throw new NumeroNotaFiscalSaidaNaoDisponivelException(notaFiscalRemessa.getNumero());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
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
