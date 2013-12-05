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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;

@Service(value="relatorioLaudoTecnico")
@RemotingDestination(value="relatorioLaudoTecnico")
public class RelatorioLaudoTecnico {

	@Autowired
	private LaudoTecnicoService business;

	
	@RemotingInclude
	@Transactional(readOnly = true)
	public String gerarRelatorio(LaudoTecnico laudoTecnico) throws Exception {
		try{
		System.out.println("Entrou no gerar relatorio");
		LaudoTecnico arquivoEncontrado = business.buscarPorId(laudoTecnico.getId());
		List<LaudoTecnico> laudosTecnicos = new ArrayList<LaudoTecnico>();
		laudosTecnicos.add(arquivoEncontrado);
		Map map = new HashMap();

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
		
		String nomeArquivo = "laudo_tecnico_" + laudoTecnico.getControle() + ".pdf";
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
