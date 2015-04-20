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
import br.com.sose.utils.PropertiesUtil;

@Service(value="relatorioCompra")
@RemotingDestination(value="relatorioCompra")
public class RelatorioCompra {

	@Autowired
	private ItemCompraService business;

	@Autowired
	private ArquivoUploadService arquivoUploadService;
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public String gerarRelatorio(Compra compra, String imgURL) throws Exception {
		try{
			System.out.println("Entrou no gerar relatorio");
			List<ItemCompra> listaItemCompra = business.listarItemCompraPorCompra(compra);
			List<RelatorioCompraVO> listaCompraVO = new ArrayList<RelatorioCompraVO>();
			HashSet<String> tecnicos;
			HashSet<String> origens;
			String origemAux;
			int origemIndex = 0;
			StringBuilder sb;
			ArquivoUpload arquivo = null;
			
			RelatorioCompraVO rcvo = null;
			for(ItemCompra ic : listaItemCompra){
				rcvo = new RelatorioCompraVO();
				rcvo.setComponente(ic.getComponente());
				rcvo.setData(new Date());
				
				arquivo = arquivoUploadService.buscarImagemPrincipalComponente(ic.getComponente());
				if(arquivo != null){
					rcvo.setImagem(imgURL + arquivo.getTipoEntidade() + "/" + arquivo.getIdentificadorEntidade() + "/" + arquivo.getTipoArquivo() + "/" + arquivo.getNome());
				}
								
				rcvo.setPossuiAmostra(ic.getPossuiAmostra());
				rcvo.setQtdEsperada(ic.getQtdEsperada());
				rcvo.setQtdPedido(ic.getQtdPedido());
				rcvo.setQtdTotalRequisitada(ic.getQtdTotalRequisitada());

				tecnicos = new HashSet<String>();
				origens = new HashSet<String>();
				for(PedidoCompra pc : ic.getListaPedidoCompra()){
					if(pc.getTecnico() != null)
						tecnicos.add(pc.getTecnico().getUsuario());

					if(pc.getOrigemPedido() != null)
						origens.add(pc.getOrigemPedido());		
				}

				if(tecnicos.size() > 0){
					sb = new StringBuilder();
					for(String s : tecnicos){
						sb.append(s);
						sb.append(";");
					}
					rcvo.setTecnicos(sb.toString());
				}
				

				if(origens.size() > 0){
					sb = new StringBuilder();

					if(origens.contains("Reposição")){
						sb.append("R|");
					}
					if(origens.contains("Compra avulsa")){
						sb.append("CA|");
					}
					if(origens.contains("Componente novo")){
						sb.append("CN|");
					}
					if(origens.contains("Estoque mínimo")){
						sb.append("EM|");
					}
					if(origens.contains("Estoque zerado")){
						sb.append("EZ|");
					}
					
					origemAux = sb.toString();
					origemIndex = origemAux.lastIndexOf("|");
					if(origemIndex != -1){
						origemAux = origemAux.substring(0, origemIndex);
					}
					rcvo.setOrigem(origemAux);
				}
				listaCompraVO.add(rcvo);
			}

			Map map = new HashMap();

			// Gerando Relatorio;
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/br/com/sose/relatorio/componente/itemCompra.jasper");
			JasperPrint jasperPrint = null;

			if(listaCompraVO.equals(null) || listaCompraVO.size() ==0) {
				jasperPrint = JasperFillManager.fillReport(is,map, new JREmptyDataSource());
			} else {
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaCompraVO);
				System.out.println("Preenchendo relatorio de compra...");
				jasperPrint = JasperFillManager.fillReport(is, map,ds);
				System.out.println("Relatorio de compra preenchido");
			}		

			System.out.println("Gerando relatorio de compra...");
			byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
			System.out.println("Relatorio de compra gerado");

			File dir = new File(PropertiesUtil.getProperty("upload.temporario.location"));
			if(!dir.exists()){
				dir.mkdirs();
			}

			String nomeArquivo = "compra_" + compra.getId() + "_" +new Date().getTime()+  ".pdf";
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
