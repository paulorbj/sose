package br.com.sose.relatorio.componente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.Cotacao;
import br.com.sose.entity.compra.ItemCompra;
import br.com.sose.service.compra.CotacaoService;
import br.com.sose.service.compra.ItemCompraService;
import br.com.sose.utils.DateUtils;

@Service(value="relatorioListagemComponenteCompra")
@RemotingDestination(value="relatorioListagemComponenteCompra")
public class RelatorioListagemComponenteCompra {

	@Autowired
	private ItemCompraService business;
	
	@Autowired
	private CotacaoService cotacaoService;

	@RemotingInclude
	@Transactional(readOnly = true)
	public String gerarRelatorio(Compra compra) throws Exception {
		try{
			System.out.println("Entrou no gerar relatorio");
			List<ItemCompra> listaItemCompra = business.listarItemCompraPorCompra(compra);
			List<RelatorioCompraVO> listaCompraVO = new ArrayList<RelatorioCompraVO>();

			RelatorioCompraVO rcvo = null;
			for(ItemCompra ic : listaItemCompra){
				rcvo = new RelatorioCompraVO();
				rcvo.setComponente(ic.getComponente());
				rcvo.setData(new Date());
				
				rcvo.setHistoricoCompra(formatarHistoricoCompra(business.listarUltimasCompras(ic.getComponente(), 5)));

				rcvo.setCotacoes(formatarCotacoes(cotacaoService.listarCotacaoPorComponente(ic.getComponente(), 5)));
				
				rcvo.setPossuiAmostra(ic.getPossuiAmostra());
				rcvo.setQtdEsperada(ic.getQtdEsperada());
				rcvo.setQtdPedido(ic.getQtdPedido());
				rcvo.setQtdTotalRequisitada(ic.getQtdTotalRequisitada());

				listaCompraVO.add(rcvo);
			}

			Map map = new HashMap();

			// Gerando Relatorio;
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/br/com/sose/relatorio/componente/listagemComponente.jasper");
			JasperPrint jasperPrint = null;

			if(listaCompraVO.equals(null) || listaCompraVO.size() ==0) {
				jasperPrint = JasperFillManager.fillReport(is,map, new JREmptyDataSource());
			} else {
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaCompraVO);
				System.out.println("Preenchendo relatorio de listagem componente...");
				jasperPrint = JasperFillManager.fillReport(is, map,ds);
				System.out.println("Relatorio de compra preenchido");
			}		

			System.out.println("Gerando relatorio de listagem componente...");
			byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
			System.out.println("Relatorio de listagem componente gerado");

			File dir = new File("C:\\arquivo_servilogi\\temporario");
			if(!dir.exists()){
				dir.mkdirs();
			}

			String nomeArquivo = "listagem_componente_" + compra.getId() + "_" +new Date().getTime()+  ".pdf";
			File file = new File(dir,nomeArquivo);

			FileOutputStream output = new FileOutputStream(file);
			output.write(report);
			output.flush();
			output.close();    
			System.out.println("Saiu no gerar relatorio");
			return nomeArquivo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private String formatarCotacoes(List<Cotacao> cotacoes){
		if(cotacoes != null && !cotacoes.isEmpty()){
			StringBuilder sb = new StringBuilder();
			String eol = System.getProperty("line.separator");
			int i = 0;
			for(Cotacao cotacao : cotacoes){



				sb.append(" Forn: ");
				if(cotacao.getFornecedor() == null){
					sb.append("--- ");
				}else{
					sb.append(cotacao.getFornecedor());
				}

				sb.append(" Dt: ");
				if(cotacao.getDataCotacao() == null){
					sb.append("--- ");
				}else{
					sb.append(DateUtils.formatarDataDDMMYYYY(cotacao.getDataCotacao()));
				}
				sb.append(" Valor: ");

				if(cotacao.getValor() == null){
					sb.append("--- ");
				}else{
					sb.append(cotacao.getValor());
				}

				i++;
				if(i < cotacoes.size()){
					sb.append("\n");
				}
			}
			return sb.toString();
		}else{
			return "";
		}
	}

	private String formatarHistoricoCompra(List<ItemCompra> itensCompra){

		if(itensCompra != null && !itensCompra.isEmpty()){
			StringBuilder sb = new StringBuilder();
			String eol = System.getProperty("line.separator");
			int i = 0;
			for(ItemCompra itemCompra : itensCompra){

				sb.append(" Forn: ");
				if(itemCompra.getFornecedor() == null){
					sb.append("--- ");
				}else{
					sb.append(itemCompra.getFornecedor());
				}

				sb.append(" Dt: ");
				if(itemCompra.getDataEntrada() == null){
					sb.append("--- ");
				}else{
					sb.append(DateUtils.formatarDataDDMMYYYY(itemCompra.getDataEntrada()));
				}
				
				sb.append(" Qtd: ");
				if(itemCompra.getQtdComprada() == null || itemCompra.getQtdComprada() == 0){
					sb.append("--- ");
				}else{
					sb.append(itemCompra.getQtdComprada());
				}
				
				sb.append(" Valor: ");

				if(itemCompra.getValorUnitario() == null && itemCompra.getValorUnitarioDolar()==null){
					sb.append("--- ");
				}else{
					if(itemCompra.getValorUnitario() != null)
						sb.append("R$" + itemCompra.getValorUnitario());
					else
						sb.append("USD$" + itemCompra.getValorUnitarioDolar());
				}

				i++;
				if(i < itensCompra.size()){
					sb.append("\n");
				}
			}
			return sb.toString();
		}else{
			return "";
		}
	}
}
