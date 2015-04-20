package br.com.sose.service.relatorio;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.consulta.Consulta;
import br.com.sose.entity.consulta.ConsultaResultado;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.to.OrcRepGenericoTO;
import br.com.sose.utils.ExportXlsUtil;

@Service(value="relatorioService")
@RemotingDestination(value="relatorioService")
public class RelatorioService {

	private IRelatorioService relatorio;
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RelatorioConferenciaFaturamento relatorioConferenciaFaturamento;
	
	@Autowired
	private RelatorioNotaFiscalSaida relatorioNotaFiscalSaida;
	
	@Autowired
	private RelatorioRomaneioExpedicao relatorioRomaneioExpedicao;
	
	@Autowired
	private RelatorioListagemGeral relatorioListagemGeral;
	
	@Autowired
	private RelatorioListagemLaudoTecnico relatorioListagemLaudoTecnico;
	
	private ExportXlsUtil exportXlsUtil = new ExportXlsUtil();

	@RemotingInclude
	@Transactional(readOnly=true)
	public ConsultaResultado realizarConsultaGeral(Consulta consulta) throws Exception {
		ConsultaResultado consultaResultado = new ConsultaResultado();
		try {
			
			//Busca todas as notas fiscais de entrada, saída e todas as os's associadas ao cliente
//			List<OrdemServico> os = ordemServicoDao.realizarConsultaGeral(consulta);
//			consultaResultado.setListaOrdemServico(os);

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return consultaResultado;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public byte[] gerarRelatorio(Object objeto,String nomeRelatorio, List<?> valores) throws Exception {
		try {
			if(nomeRelatorio.equals("relatorioConferenciaFaturamento")){
				Date inicio = new Date();
				byte[] relatorio = relatorioConferenciaFaturamento.gerarRelatorio(objeto,nomeRelatorio, (List<OrdemServico>)valores);
				Date fim = new Date();
				logger.info("Tempo geração relatório conferencia faturamento (segundos): " + (fim.getTime() - inicio.getTime())/1000);
				return relatorio;
			}else if(nomeRelatorio.equals("relatorioNotaFiscalSaida")){
				Date inicio = new Date();
				byte[] relatorio = relatorioNotaFiscalSaida.gerarRelatorio(objeto,nomeRelatorio, (List<OrdemServico>)valores);
				Date fim = new Date();
				logger.info("Tempo geração relatório nota fiscal saída (segundos): " + (fim.getTime() - inicio.getTime())/1000);
				return relatorio;
			}else if(nomeRelatorio.equals("relatorioRomaneioExpedicao")){
				Date inicio = new Date();
				byte[] relatorio = relatorioRomaneioExpedicao.gerarRelatorio(objeto,nomeRelatorio, (List<OrdemServico>)valores);
				Date fim = new Date();
				logger.info("Tempo geração relatório romaneio expedição (segundos): " + (fim.getTime() - inicio.getTime())/1000);
				return relatorio;
			}else if(nomeRelatorio.equals("relatorioListagemGeral")){
				Date inicio = new Date();
				byte[] relatorio = relatorioListagemGeral.gerarRelatorio(objeto,nomeRelatorio, (List<OrcRepGenericoTO>)valores);
				Date fim = new Date();
				logger.info("Tempo geração relatório listagem geral (segundos): " + (fim.getTime() - inicio.getTime())/1000);
				return relatorio;
			}else if(nomeRelatorio.equals("relatorioListagemLaudoTecnico")){
				Date inicio = new Date();
				byte[] relatorio = relatorioListagemLaudoTecnico.gerarRelatorio(objeto,nomeRelatorio, (List<LaudoTecnico>)valores);
				Date fim = new Date();
				logger.info("Tempo geração relatório listagem laudo técnico (segundos): " + (fim.getTime() - inicio.getTime())/1000);
				return relatorio;
			}else{
				return null;
			}
		} catch(Exception e){
			throw e;
		}
	}
	
//	@RemotingInclude
//	public byte[] createFile(List<?> values, Usuario usuario)  throws Exception {
//		try {			
//			return exportXlsUtil.createFile(values,usuario);
//		} catch (Exception e){ 
//			throw e;
//		}		
//	}


}
