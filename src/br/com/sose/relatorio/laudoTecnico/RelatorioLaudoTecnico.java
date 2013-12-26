package br.com.sose.relatorio.laudoTecnico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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

import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.utils.ArquivoUpload;

@Service(value="relatorioLaudoTecnico")
@RemotingDestination(value="relatorioLaudoTecnico")
public class RelatorioLaudoTecnico {

	@Autowired
	private LaudoTecnicoService business;


	@RemotingInclude
	@Transactional(readOnly = true)
	public String gerarRelatorio(LaudoTecnico laudoTecnico, List<ArquivoUpload> imagens, String imgURL) throws Exception {
		try{
			System.out.println("Entrou no gerar relatorio");
			LaudoTecnico laudoEncontrado = business.buscarPorId(laudoTecnico.getId());
			List<LaudoTecnico> laudosTecnicos = new ArrayList<LaudoTecnico>();

			if(imagens != null && !imagens.isEmpty()){
				int i = 0;
				while(i < imagens.size() && i < 3){
					if(i == 0){
						laudoEncontrado.setImagem1(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
					}
					if(i == 1){
						laudoEncontrado.setImagem2(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
					}
					if(i == 2){
						laudoEncontrado.setImagem3(imgURL + imagens.get(i).getTipoEntidade() + "/" + imagens.get(i).getIdentificadorEntidade() + "/" + imagens.get(i).getTipoArquivo() + "/" + imagens.get(i).getNome());
					}
					i++;
				}
			}


			laudosTecnicos.add(laudoEncontrado);
			Map map = new HashMap();

			BufferedImage image = ImageIO.read(getClass().getResource("/br/com/sose/relatorio/laudoTecnico/logo_servilogi.jpg"));
			map.put("logo", image );
			
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

			File dir = new File("C:\\arquivo_servilogi\\temporario");
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
