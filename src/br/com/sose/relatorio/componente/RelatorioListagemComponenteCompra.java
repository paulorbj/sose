package br.com.sose.relatorio.componente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import br.com.sose.entity.compra.ItemCompra;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.service.ArquivoUploadService;
import br.com.sose.service.compra.ItemCompraService;
import br.com.sose.utils.ArquivoUpload;

@Service(value="relatorioListagemComponenteCompra")
@RemotingDestination(value="relatorioListagemComponenteCompra")
public class RelatorioListagemComponenteCompra {

	@Autowired
	private ItemCompraService business;
	
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

			String nomeArquivo = "listagem_componente_" + compra.getId() + ".pdf";
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
}
