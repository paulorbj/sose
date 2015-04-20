package br.com.sose.utils;

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

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.proposta.ItemPropostaService;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service(value="exportXlsUtil")
@RemotingDestination(value="exportXlsUtil")
public class ExportXlsUtil {	

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	private static final String XML_ENCODING = "UTF-8";

	private EntityManager entityManager;

	@Autowired
	private ItemPropostaService itemPropostaService;

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			logger.info("Error: EntityManager cannot be null (UserDAO - getEntityManager)");
			throw new IllegalStateException("Error: EntityManager cannot be null.");
		}
		return entityManager;
	}

	public byte[] createFile(List<Object[]> values, Usuario usuario) throws Exception {

		try{

			// Step 1. Create a template file. Setup sheets and workbook-level objects such as
			// cell styles, number formats, etc.

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Relatório de ordens de serviço");

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
			generate(fw, styles,0,1,values);
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



	public ItemPropostaService getItemPropostaService() {
		return itemPropostaService;
	}

	public void setItemPropostaService(ItemPropostaService itemPropostaService) {
		this.itemPropostaService = itemPropostaService;
	}

	private static void generate(Writer out, Map<String, XSSFCellStyle> styles, int headerRow, int firstDataRow, List<Object[]> values) throws Exception {
		Date hoje = new Date();
		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		//insert header row
		sw.insertRow(headerRow);
		int styleIndex = styles.get("header").getIndex();
		sw.createCell(0, "Nº OS", styleIndex);
		sw.createCell(1, "Nº OS Pai", styleIndex);
		sw.createCell(2, "Status", styleIndex);
		sw.createCell(3, "Condição", styleIndex);
		sw.createCell(4, "Origem", styleIndex);
		sw.createCell(5, "Dias Servilogi", styleIndex);
		sw.createCell(6, "Cliente", styleIndex);
		sw.createCell(7, "Unidade", styleIndex);
		sw.createCell(8, "Código", styleIndex);
		sw.createCell(9, "N/S Fabricante", styleIndex);
		sw.createCell(10, "N/S Cliente", styleIndex);
		sw.createCell(11, "Nº OS Cliente", styleIndex);
		sw.createCell(12, "Fabricante", styleIndex);
		sw.createCell(13, "LPU", styleIndex);
		sw.createCell(14, "Laboratório", styleIndex);
		sw.createCell(15, "Prestador de serviço", styleIndex);
		sw.createCell(16, "Nº NF Entrada", styleIndex);
		sw.createCell(17, "DT NF Emissão", styleIndex);
		sw.createCell(18, "DT NF Abertura", styleIndex);
		sw.createCell(19, "DT Chegada", styleIndex);
		sw.createCell(20, "DT Fim Reparo", styleIndex);
		sw.createCell(21, "Valor unitário", styleIndex);
		sw.createCell(22, "Cliente avaya", styleIndex);
		sw.createCell(23, "Case avaya", styleIndex);
		sw.createCell(24, "Status estoque", styleIndex);
		sw.createCell(25, "Posição estoque", styleIndex);
		sw.createCell(26, "Nº proposta", styleIndex);
		sw.createCell(27, "DT Proposta", styleIndex);
		sw.createCell(28, "Aprovado/Reprovado", styleIndex);
		sw.createCell(29, "DT Aprovado/Reprovado", styleIndex);
		sw.createCell(30, "Nº NF saída", styleIndex);
		sw.createCell(31, "DT Emissão NF saída", styleIndex);
		sw.createCell(32, "DT Saída do material", styleIndex);
		sw.createCell(33, "Observação Nota Fiscal", styleIndex);
		sw.createCell(34, "Observação Faturamento", styleIndex);
		sw.createCell(35, "Observação Nota fiscal saída", styleIndex);
		sw.createCell(36, "Observação Orçamento", styleIndex);
		sw.createCell(37, "Observação Ordem serviço", styleIndex);
		sw.createCell(38, "Observação Reparo", styleIndex);
		sw.createCell(39, "Observação Proposta", styleIndex);
		sw.createCell(40, "Observação Consulta", styleIndex);
		sw.createCell(41, "DT Fim Orçamento", styleIndex);

		sw.endRow();

		//write data rows
		for(Object[] consultaGeralTO : values) {
			// New line
			sw.insertRow(firstDataRow);
			// Filling Cells

			if(consultaGeralTO[0] != null){
				//N OS
				sw.createCell(0,StringEscapeUtils.escapeXml((consultaGeralTO[0].toString())));
			}

			if(consultaGeralTO[1] != null){
				//N OS PAI
				sw.createCell(1,StringEscapeUtils.escapeXml((consultaGeralTO[1].toString())));
			}

			if(consultaGeralTO[2]  != null){
				//				Status
				sw.createCell(2,StringEscapeUtils.escapeXml((consultaGeralTO[2].toString())));
			}


			//Condicao
			if(consultaGeralTO[34] != null && consultaGeralTO[32] != null){
				if(consultaGeralTO[35] != null && consultaGeralTO[35].equals("Com condição de reparo")){
					sw.createCell(3,StringEscapeUtils.escapeXml("Reparado"));

				}else if(consultaGeralTO[35] != null && consultaGeralTO[35].equals("Sem condição de reparo")){
					sw.createCell(3,StringEscapeUtils.escapeXml("Irreparável"));
				}else{
					sw.createCell(3,StringEscapeUtils.escapeXml((String)consultaGeralTO[35]));
				}
			}else if(consultaGeralTO[34] != null && consultaGeralTO[32] == null){
				if(consultaGeralTO[35] != null){
					sw.createCell(3,StringEscapeUtils.escapeXml((String)consultaGeralTO[35]));
				}
			}
			else{
				if(consultaGeralTO[37] != null){
					sw.createCell(3,StringEscapeUtils.escapeXml((String)consultaGeralTO[37]));
				}
			}

			//Origem
			if(consultaGeralTO[39] != null && (Boolean)consultaGeralTO[39]){
				sw.createCell(4,StringEscapeUtils.escapeXml("Garantia"));
			}else{
				if(consultaGeralTO[38] != null && consultaGeralTO[38].equals("Orçamento diferenciado")){
					sw.createCell(4,StringEscapeUtils.escapeXml("Orçamento diferenciado"));
				}else if(consultaGeralTO[40] != null){
					sw.createCell(4,StringEscapeUtils.escapeXml("LPU"));
				}else{
					sw.createCell(4,StringEscapeUtils.escapeXml("Orçamento"));
				}
			}

			if(consultaGeralTO[15]  != null){
				//Dias servilogi
				Date dataChegada = (Date) consultaGeralTO[15];
				Long diferenca = hoje.getTime() - dataChegada.getTime();
				Integer tempoDia  = 1000 * 60 * 60 * 24;
				Long diasServilogi = diferenca/tempoDia;
				sw.createCell(5,diasServilogi);						
			}

			if(consultaGeralTO[3]  != null){
				//				Cliente
				sw.createCell(6,StringEscapeUtils.escapeXml((consultaGeralTO[3].toString())));
			}

			if(consultaGeralTO[4]  != null){
				//				Unidade
				sw.createCell(7,StringEscapeUtils.escapeXml((consultaGeralTO[4].toString())));
			}	
			
			if(consultaGeralTO[33]  != null){
				// Codigo
				sw.createCell(8,StringEscapeUtils.escapeXml((consultaGeralTO[33].toString())));
			}

			if(consultaGeralTO[5]  != null){
				// N/S Fabricante
				sw.createCell(9,StringEscapeUtils.escapeXml((consultaGeralTO[5].toString())));
			}

			if(consultaGeralTO[6]  != null){
				// N/S Cliente
				sw.createCell(10,StringEscapeUtils.escapeXml((consultaGeralTO[6].toString())));
			}
			if(consultaGeralTO[7]  != null){
				sw.createCell(11,StringEscapeUtils.escapeXml((consultaGeralTO[7].toString())));
			}

			if(consultaGeralTO[8]  != null){
				sw.createCell(12,StringEscapeUtils.escapeXml((consultaGeralTO[8].toString())));
			}

			if(consultaGeralTO[9]  != null){
				sw.createCell(13,StringEscapeUtils.escapeXml((consultaGeralTO[9].toString())));
			}

			if(consultaGeralTO[10]  != null){
				sw.createCell(14,StringEscapeUtils.escapeXml((consultaGeralTO[10].toString())));
			}

			if(consultaGeralTO[11]  != null){
				sw.createCell(15,StringEscapeUtils.escapeXml((consultaGeralTO[11].toString())));
			}

			if(consultaGeralTO[12]  != null){
				sw.createCell(16,StringEscapeUtils.escapeXml((consultaGeralTO[12].toString())));
			}

			if(consultaGeralTO[13]  != null){
				sw.createCell(17,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[13])));
			}else{
				sw.createCell(17,"");
			}

			if(consultaGeralTO[14]  != null){
				sw.createCell(18,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[14])));
			}else{
				sw.createCell(18,"");
			}

			if(consultaGeralTO[15]  != null){
				sw.createCell(19,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[15])));					
			}else{
				sw.createCell(19,"");
			}

			//Data fim reparo
			if(consultaGeralTO[32]  != null){
				sw.createCell(20,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[32])));
			}else{
				sw.createCell(20,"");
			}

			if(consultaGeralTO[16]  != null){
				// Valor unitário
				if(consultaGeralTO[1] == null){
					sw.createCell(21,StringEscapeUtils.escapeXml((consultaGeralTO[16].toString())));
				}
			}

			if(consultaGeralTO[17]  != null){
				sw.createCell(22,StringEscapeUtils.escapeXml((consultaGeralTO[17].toString())));
			}

			if(consultaGeralTO[18]  != null){
				sw.createCell(23,StringEscapeUtils.escapeXml((consultaGeralTO[18].toString())));
			}
			
			if(consultaGeralTO[41]  != null){
				sw.createCell(24,StringEscapeUtils.escapeXml((consultaGeralTO[41].toString())));
			}
			
			if(consultaGeralTO[42]  != null){
				sw.createCell(25,StringEscapeUtils.escapeXml((consultaGeralTO[42].toString())));
			}

			if(consultaGeralTO[19]  != null){
				sw.createCell(26,StringEscapeUtils.escapeXml((consultaGeralTO[19].toString())));
			}

			if(consultaGeralTO[20]  != null){
				sw.createCell(27,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[20])));
			}else{
				sw.createCell(27,"");
			}

			if(consultaGeralTO[21]  != null){
				if(new Boolean(consultaGeralTO[21].toString())){
					sw.createCell(28,"Aprovado");
				}else{
					sw.createCell(28,"Reprovado");
				}
			}

			if(consultaGeralTO[22]  != null){
				sw.createCell(29,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[22])));
			}else{
				sw.createCell(29,"");
			}

			if(consultaGeralTO[23]  != null){
				sw.createCell(30,StringEscapeUtils.escapeXml((consultaGeralTO[23].toString())));
			}

			if(consultaGeralTO[24]  != null){
				//Dt emissao nf saida
				sw.createCell(31,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[24])));
			}else{
				sw.createCell(31,"");
			}
			
			if(consultaGeralTO[43]  != null){
				//Dt saida material
				sw.createCell(32,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[43])));
			}else{
				sw.createCell(32,"");
			}

			//Observ. nota fiscal
			if(consultaGeralTO[25]  != null){
				sw.createCell(33,StringEscapeUtils.escapeXml((consultaGeralTO[25].toString())));
			}

			//Observ. faturamento
			if(consultaGeralTO[26]  != null){
				sw.createCell(34,StringEscapeUtils.escapeXml((consultaGeralTO[26].toString())));
			}

			//Observ. nota fiscal saida
			if(consultaGeralTO[27]  != null){
				sw.createCell(35,StringEscapeUtils.escapeXml((consultaGeralTO[27].toString())));
			}

			//Observ. orcamento
			if(consultaGeralTO[28]  != null){
				sw.createCell(36,StringEscapeUtils.escapeXml((consultaGeralTO[28].toString())));
			}

			//Observ. ordem servico
			if(consultaGeralTO[29]  != null){
				sw.createCell(37,StringEscapeUtils.escapeXml((consultaGeralTO[29].toString())));
			}

			//Observ. reparo
			if(consultaGeralTO[30]  != null){
				sw.createCell(38,StringEscapeUtils.escapeXml((consultaGeralTO[30].toString())));
			}

			//Observ. proposta
			if(consultaGeralTO[31]  != null){
				sw.createCell(39,StringEscapeUtils.escapeXml((consultaGeralTO[31].toString())));
			}
			
			//Observ. consulta
			if(consultaGeralTO[44]  != null){
				sw.createCell(40,StringEscapeUtils.escapeXml((consultaGeralTO[44].toString())));
			}
			
			//Data fim orçamento
			if(consultaGeralTO[45]  != null){
				sw.createCell(41,StringEscapeUtils.escapeXml(DateUtils.formatarDataDDMMYYYY((Date)consultaGeralTO[45])));
			}else{
				sw.createCell(41,"");
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

		return styles;
	}

}