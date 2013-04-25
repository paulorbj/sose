package br.com.sose.service.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;
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

import br.com.sose.to.OrcRepGenericoTO;
import br.com.sose.utils.DateUtils;
import br.com.sose.utils.ExcelUtils;
import br.com.sose.utils.SpreadsheetWriter;

@Service(value="relatorioListagemGeral")
@RemotingDestination(value="relatorioListagemGeral")
public class RelatorioListagemGeral  {	

	private Logger logger = Logger.getLogger(this.getClass());

	private String[] FailheaderNames = {
			"Finalidade", "Status", "Técnico", "Laboratório","Nº OS","Nº OS pai","Cliente","Unidade","Case avaya","Cliente avaya","Nº NF Entrada","Tempo na servilogi", "Tempo no laboratório","Prazo","Prioridade"};

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
	
	private static void generate(Writer out, Map<String, XSSFCellStyle> styles, int headerRow, int firstDataRow, List<OrcRepGenericoTO> values) throws Exception {
		Date hoje = new Date();
		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		//insert header row
		sw.insertRow(headerRow);
		int styleIndex = styles.get("header").getIndex();
		int dateStyle = styles.get("date").getIndex();
		sw.createCell(0, "Finalidade", styleIndex);
		sw.createCell(1, "Status", styleIndex);
		sw.createCell(2, "Técnico", styleIndex);
		sw.createCell(3, "Laboratório", styleIndex);
		sw.createCell(4, "Nº OS", styleIndex);
		sw.createCell(5, "Nº OS Pai", styleIndex);
		sw.createCell(6, "Cliente", styleIndex);
		sw.createCell(7, "Unidade", styleIndex);
		sw.createCell(8, "Case avaya", styleIndex);
		sw.createCell(9, "Cliente avaya", styleIndex);
		sw.createCell(10, "Nº NF Entrada", styleIndex);
		sw.createCell(11, "Tempo na servilogi", styleIndex);
		sw.createCell(12, "Tempo no laboratório", styleIndex);
		sw.createCell(13, "Prazo", styleIndex);
		sw.createCell(14, "Prioridade", styleIndex);
				
		sw.endRow();

		Date dataFinal = new Date();
		Long diferenca = new Long(0);
		Long tempoDia =  new Long(1000) * new Long(60) * new Long(60) * new Long(24) ;
		Long dias= new Long(0);
		Calendar calendar = Calendar.getInstance();
		
		//write data rows
		for(OrcRepGenericoTO orcRepTO : values) {
			// New line
			sw.insertRow(firstDataRow);
			// Filling Cells

			if(orcRepTO.getFinalidade() != null){
				//Finalidade
				sw.createCell(0,StringEscapeUtils.escapeXml((orcRepTO.getFinalidade().toString())));
			}
			
			if(orcRepTO.getStatusString() != null){
				//Status
				sw.createCell(1,StringEscapeUtils.escapeXml((orcRepTO.getStatusString().toString())));
			}
			
			if(orcRepTO.getTecnico() != null){
				//Tecnico
				sw.createCell(2,StringEscapeUtils.escapeXml((orcRepTO.getTecnico().toString())));
			}
			
			if(orcRepTO.getLaboratorio() != null){
				//Laboratorio
				sw.createCell(3,StringEscapeUtils.escapeXml((orcRepTO.getLaboratorio().toString())));
			}
			
			if(orcRepTO.getNumeroOrdemServico() != null){
				//N OS
				sw.createCell(4,StringEscapeUtils.escapeXml((orcRepTO.getNumeroOrdemServico().toString())));
			}
			
			if(orcRepTO.getNumeroOrdemServicoPai() != null){
				//N OS PAI
				sw.createCell(5,StringEscapeUtils.escapeXml((orcRepTO.getNumeroOrdemServicoPai().toString())));
			}
			
			if(orcRepTO.getCliente() != null){
				//Cliente
				sw.createCell(6,StringEscapeUtils.escapeXml((orcRepTO.getCliente().toString())));
			}
			
			if(orcRepTO.getUnidade() != null){
				//Unidade
				sw.createCell(7,StringEscapeUtils.escapeXml((orcRepTO.getUnidade().toString())));
			}
			
			if(orcRepTO.getCaseAvaya() != null){
				//Case avaya
				sw.createCell(8,StringEscapeUtils.escapeXml((orcRepTO.getCaseAvaya().toString())));
			}
			
			if(orcRepTO.getClienteAvaya() != null){
				//Cliente avaya
				sw.createCell(9,StringEscapeUtils.escapeXml((orcRepTO.getClienteAvaya().toString())));
			}
			
			if(orcRepTO.getNumeroNotaFiscal() != null){
				//Numero NF entrada
				sw.createCell(10,StringEscapeUtils.escapeXml((orcRepTO.getNumeroNotaFiscal().toString())));
			}

			// Calcula a diferença entre hoje e da data de inicio
			if(orcRepTO.getDataChegadaServilogi() == null){
				orcRepTO.setDataChegadaServilogi(new Date());
			}

			diferenca = dataFinal.getTime() - orcRepTO.getDataChegadaServilogi().getTime();
			// Quantidade de milissegundos em um dia		

			dias = diferenca / tempoDia;
			sw.createCell(11,StringEscapeUtils.escapeXml((dias.toString())));

			// Calcula a diferença entre hoje e da data de inicio
			if(orcRepTO.getDataEntradaReparo() == null){
				orcRepTO.setDataEntradaReparo(orcRepTO.getDataChegadaServilogi());
			}

			diferenca = dataFinal.getTime() - orcRepTO.getDataEntradaReparo().getTime();
			// Quantidade de milissegundos em um dia		

			dias = diferenca / tempoDia;
			sw.createCell(12,StringEscapeUtils.escapeXml((dias.toString())));

			if(orcRepTO.getDataPrioridade() != null){
				diferenca =orcRepTO.getDataPrioridade().getTime() - new Date().getTime();
				dias = diferenca/tempoDia;
			}else{
				if(orcRepTO.getDataLimite() == null){
					System.out.println(orcRepTO.getNumeroOrdemServico());
					dias = new Long(0);
				}else{
				diferenca = DateUtils.addDays(orcRepTO.getDataLimite().getTime(),orcRepTO.getPrazoDevolucao()) - new Date().getTime();
				}
				dias = diferenca/tempoDia;
			}

			sw.createCell(13,StringEscapeUtils.escapeXml((dias.toString())));


			if(orcRepTO.getDataPrioridade() != null){
				calendar.setTime(orcRepTO.getDataPrioridade());
				sw.createCell(14,calendar,dateStyle);
//				cPrioridade.setCellStyle(dateStyle);
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
	public byte[] gerarRelatorio(Object objeto, String nomeRelatorio, List<OrcRepGenericoTO> valores) {
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