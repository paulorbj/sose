package br.com.sose.relatorio.laudoTecnico;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.utils.ArquivoUpload;
import br.com.sose.utils.PropertiesUtil;

@Service(value="relatorioLaudoTecnico")
@RemotingDestination(value="relatorioLaudoTecnico")
public class RelatorioLaudoTecnico {

	@Autowired
	private LaudoTecnicoService business;

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public String gerarRelatorio(LaudoTecnico laudoTecnico, List<ArquivoUpload> imagens, String imgURL) throws Exception {
		try{
			String location = PropertiesUtil.getProperty("upload.temporario.location");
			System.out.println("Entrou no gerar relatorio");
			LaudoTecnico laudoEncontrado = business.buscarPorId(laudoTecnico.getId());
			List<LaudoTecnico> laudosTecnicos = new ArrayList<LaudoTecnico>();

			if(imagens != null && !imagens.isEmpty()){
				int i = 0;
				
				laudoEncontrado.setIdImagem1(null);
				laudoEncontrado.setIdImagem2(null);
				laudoEncontrado.setIdImagem3(null);
				
				while(i < imagens.size() && i < 3){
					if(i == 0){
						laudoEncontrado.setImagem1(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
						laudoEncontrado.setIdImagem1(imagens.get(i).getId());
					}
					if(i == 1){
						laudoEncontrado.setImagem2(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
						laudoEncontrado.setIdImagem2(imagens.get(i).getId());
					}
					if(i == 2){
						laudoEncontrado.setImagem3(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
						laudoEncontrado.setIdImagem3(imagens.get(i).getId());
					}
					i++;
				}
			}

			business.salvarLaudoTecnico(laudoEncontrado);

			laudosTecnicos.add(laudoEncontrado);
			Map map = new HashMap();
			
			map.put("logo", PropertiesUtil.getProperty("url.servidor.sose") + PropertiesUtil.getProperty("url.logo.servilogi"));
						
			// Gerando Relatorio;
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("/br/com/sose/relatorio/laudoTecnico/relatorioLaudoTecnico.jasper");
			JasperPrint jasperPrint = null;

			if(laudosTecnicos.equals(null) || laudosTecnicos.size() ==0) {
				jasperPrint = JasperFillManager.fillReport(is,map, new JREmptyDataSource());
			} else {
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(laudosTecnicos);
				System.out.println("Preenchendo laudo tecnico...");
				jasperPrint = JasperFillManager.fillReport(is, map,ds);
				System.out.println("Relatorio laudo tecnico preenchido");
			}		

			System.out.println("Gerando relatorio laudo tecnico...");
			byte[] report = JasperExportManager.exportReportToPdf(jasperPrint);
			System.out.println("Relatorio laudo tecnico gerado");

			File dir = new File(PropertiesUtil.getProperty("upload.temporario.location"));
			if(!dir.exists()){
				dir.mkdirs();
			}

			String nomeArquivo = "laudo_tecnico_" + laudoTecnico.getControle() + "_" +new Date().getTime()+  ".pdf";
			File file = new File(dir,nomeArquivo);

			FileOutputStream output = new FileOutputStream(file);
			output.write(report);
			output.flush();
			output.close();    
			System.out.println("Local do PDF: " + file.getAbsolutePath());
			System.out.println("Saiu no gerar relatorio");
			return nomeArquivo;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
