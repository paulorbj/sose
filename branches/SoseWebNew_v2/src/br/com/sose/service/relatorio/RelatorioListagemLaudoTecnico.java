package br.com.sose.service.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.utils.ExcelUtils;
import br.com.sose.utils.SpreadsheetWriter;

@Service(value="relatorioListagemLaudoTecnico")
@RemotingDestination(value="relatorioListagemLaudoTecnico")
public class RelatorioListagemLaudoTecnico  {	

	private Logger logger = Logger.getLogger(this.getClass());

	private String[] FailheaderNames = {
			"Controle", "Status","Nº OS", "Nº OS pai","Cliente","Unidade","N/S Fabricante","N/S Cliente","Laboratório","Técnico"};
	
	private static final String XML_ENCODING = "UTF-8";

	// Converting to Byte Array
	private byte[] returnByteArray(String fileAbsolutePath) throws Exception {
		byte[] data = null;
		try {
			FileInputStream fis = new FileInputStream(fileAbsolutePath);
			FileChannel fc = fis.getChannel();
			data = new byte[(int) (fc.size())];
			ByteBuffer bb = ByteBuffer.wrap(data);
			fc.read(bb);
			return data;
		} catch (Exception e) {
			logger.info("Error:" + e.getMessage());
			throw new Exception(e);
		}
	}
	
	private static void generate(Writer out, Map<String, XSSFCellStyle> styles, int headerRow, int firstDataRow, List<LaudoTecnico> values) throws Exception {
		Date hoje = new Date();
		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		//insert header row
		sw.insertRow(headerRow);
		int styleIndex = styles.get("header").getIndex();
		int dateStyle = styles.get("date").getIndex();
		sw.createCell(0, "Controle", styleIndex);
		sw.createCell(1, "Status", styleIndex);
		sw.createCell(2, "Nº OS", styleIndex);
		sw.createCell(3, "Nº OS Pai", styleIndex);
		sw.createCell(4, "Cliente", styleIndex);
		sw.createCell(5, "Unidade", styleIndex);
		sw.createCell(6, "N/S Fabricante", styleIndex);
		sw.createCell(7, "N/S Cliente", styleIndex);
		sw.createCell(8, "Laboratório", styleIndex);
		sw.createCell(9, "Técnico", styleIndex);
		sw.endRow();
		
		//write data rows
		for(LaudoTecnico laudo : values) {
			// New line
			sw.insertRow(firstDataRow);
			// Filling Cells

			if(laudo.getControle() != null){
				sw.createCell(0,StringEscapeUtils.escapeXml((laudo.getControle().toString())));
			}
			
			if(laudo.getStatusString() != null){
				sw.createCell(1,StringEscapeUtils.escapeXml((laudo.getStatusString().toString())));
			}

			if(laudo.getNumeroOrdemServico() != null){
				sw.createCell(2,StringEscapeUtils.escapeXml((laudo.getNumeroOrdemServico().toString())));
			}

			if(laudo.getNumeroOrdemServicoPai() != null){
				sw.createCell(3,StringEscapeUtils.escapeXml((laudo.getNumeroOrdemServicoPai().toString())));
			}

			if(laudo.getCliente() != null){
				sw.createCell(4,StringEscapeUtils.escapeXml((laudo.getCliente().toString())));	
			}
			
			if(laudo.getUnidade() != null){
				sw.createCell(5,StringEscapeUtils.escapeXml((laudo.getUnidade().toString())));
			}
			if(laudo.getSerieFabricante() != null){
				sw.createCell(6,StringEscapeUtils.escapeXml((laudo.getSerieFabricante().toString())));
			}

			if(laudo.getSerieCliente() != null){
				sw.createCell(7,StringEscapeUtils.escapeXml((laudo.getSerieCliente().toString())));
			}

			if(laudo.getLaboratorio() != null){
				sw.createCell(8,StringEscapeUtils.escapeXml((laudo.getLaboratorio().toString())));
			}

			if(laudo.getTecnico() != null){
				sw.createCell(9,StringEscapeUtils.escapeXml((laudo.getTecnico().toString())));
			}
			sw.endRow();
			firstDataRow++;

		}
		sw.endSheet();
	}

	private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();


		XSSFCellStyle style5 = wb.createCellStyle();
		XSSFFont headerFont = wb.createFont();
		headerFont.setBold(true);
		style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style5.setFont(headerFont);
		styles.put("header", style5);

		
		XSSFCellStyle dateStyle = wb.createCellStyle();
		XSSFDataFormat dateFormat = wb.createDataFormat();
		dateStyle.setDataFormat(dateFormat.getFormat("dd-mm-yyyy"));
		dateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styles.put("date", dateStyle);

		return styles;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public byte[] gerarRelatorio(Object objeto, String nomeRelatorio, List<LaudoTecnico> valores) {
		try{

			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Listagem geral");

			Map<String, XSSFCellStyle> styles = createStyles(wb);
			//name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
			String sheetRef = sheet.getPackagePart().getPartName().getName();

			//save the template
			File dir2 = new File("C:\\arquivos");;
			if(!dir2.exists()){
				dir2.mkdir();
			}
			File file2 = new File(dir2,"template.xlsx");
			FileOutputStream os = new FileOutputStream(file2);
			wb.write(os);
			os.close();

			//Step 2. Generate XML file.
			File tmp = File.createTempFile("sheet", ".xml",dir2);
			Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
			generate(fw, styles,0,1,valores);
			fw.close();

			//Step 3. Substitute the template entry with the generated data
			File dir = new File("C:\\arquivos");;
			if(!dir.exists()){
				dir.mkdir();
			}
			File file = new File(dir,"workbook.xlsx");
			FileOutputStream out = new FileOutputStream(file);
			ExcelUtils.substitute(new File(dir,"template.xlsx"), tmp, sheetRef.substring(1), out);
			out.close();

			return returnByteArray(file.getAbsolutePath());
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}

}