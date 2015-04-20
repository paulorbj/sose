package br.com.sose.service.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.comparators.NumeroOrdemServicoComparator;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;

@Service(value="relatorioNotaFiscalSaida")
@RemotingDestination(value="relatorioNotaFiscalSaida")
public class RelatorioNotaFiscalSaida {	

	private Logger logger = Logger.getLogger(this.getClass());

	private Hashtable<EspelhoNotaFiscalSaida, Integer> itensNotaFiscalSaida = new Hashtable<EspelhoNotaFiscalSaida, Integer>();

	private String[] FailheaderNames = {
			"Unidade","S/N Cliente","S/N Fabricante","Nº NF Entrada","Valor","Nº OS","Condição"};

	private String[] cabecalhoResumo = {
			"Cod.", "Descrição do item da NF", "NCM","CFOP","CST","Quantidade", "Valor unitário","Valor total","Unidade"};

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

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public byte[] gerarRelatorio(Object objeto, String nomeRelatorio, List<OrdemServico> valores) {
		logger.info("Creating file with " + nomeRelatorio + " information");

		itensNotaFiscalSaida = new Hashtable<EspelhoNotaFiscalSaida, Integer>();

		Collections.sort(valores, new NumeroOrdemServicoComparator());


		// Iterator Structures.
		Iterator<?> valuesIterator = valores.iterator();

		// To navigate throw sheet lines.
		int rowIndex = 0;
		int totalFeatures = 0;

		// Excel Structures. 
		// XLS file
		HSSFWorkbook wb = new HSSFWorkbook();

		// Sheet inside file
		HSSFSheet  sheet = null;

		HSSFSheet  planilhaResumo = null;


		sheet = wb.createSheet( "Ordens de serviço" );
		planilhaResumo = wb.createSheet("Espelho NF Saída");

		sheet.setDisplayGridlines(false);
		planilhaResumo.setDisplayGridlines(false);

		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.DARK_BLUE.index,
				(byte) 0,  //RGB red (0-255)
				(byte) 53,    //RGB green
				(byte) 97     //RGB blue
				);

		palette.setColorAtIndex(HSSFColor.LIGHT_BLUE.index,
				(byte) 193,  //RGB red (0-255)
				(byte) 218,    //RGB green
				(byte) 250     //RGB blue
				);



		// A single row
		HSSFRow row = null;
		// Initial info cells
		HSSFCell headerCell = null;

		// A single row
		HSSFRow linha = null;
		// Initial info cells
		HSSFCell cabecalho = null;

		// Set column size.
		sheet.setColumnWidth((int)0,(int)6000);
		sheet.setColumnWidth((int)1,(int)6000);
		sheet.setColumnWidth((int)2,(int)6000);
		sheet.setColumnWidth((int)3,(int)6000);
		sheet.setColumnWidth((int)4,(int)6000);
		sheet.setColumnWidth((int)5,(int)6000);
		sheet.setColumnWidth((int)6,(int)6000);

		planilhaResumo.setColumnWidth((int)0,(int)6000);
		planilhaResumo.setColumnWidth((int)1,(int)6000);
		planilhaResumo.setColumnWidth((int)2,(int)6000);
		planilhaResumo.setColumnWidth((int)3,(int)6000);
		planilhaResumo.setColumnWidth((int)4,(int)6000);
		planilhaResumo.setColumnWidth((int)5,(int)6000);
		planilhaResumo.setColumnWidth((int)6,(int)6000);

		// pattern font
		HSSFFont fontHeader = wb.createFont();   
		fontHeader.setFontName("Verdana");
		fontHeader.setItalic(true);
		fontHeader.setFontHeightInPoints((short)20);
		fontHeader.setColor(HSSFColor.WHITE.index);

		HSSFFont font = wb.createFont();   
		font.setFontName("Verdana");
		font.setItalic(true);
		font.setFontHeightInPoints((short)12); 

		HSSFCellStyle style = wb.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.BLACK.index);
		style.setFillForegroundColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);

		// Style for header info cells.
		HSSFCellStyle styleHeaderTemplate = wb.createCellStyle();
		styleHeaderTemplate.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
		styleHeaderTemplate.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeaderTemplate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeaderTemplate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeaderTemplate.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeaderTemplate.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeaderTemplate.setFont(fontHeader);

		// Style for columns division cells.
		HSSFCellStyle styleHeaderFeature = wb.createCellStyle();
		styleHeaderFeature.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		styleHeaderFeature.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeaderFeature.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHeaderFeature.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeaderFeature.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeaderFeature.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeaderFeature.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeaderFeature.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleHeaderFeature.setFont(font);

		// Style for columns division cells.
		HSSFCellStyle styleFooterFeature = wb.createCellStyle();
		styleHeaderFeature.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		styleHeaderFeature.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeaderFeature.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHeaderFeature.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleHeaderFeature.setFont(font);

		HSSFCellStyle currencyStyle = wb.createCellStyle();
		HSSFDataFormat df = wb.createDataFormat();
		currencyStyle.setDataFormat(df.getFormat("R$#,##0.00;R$#,##0.00"));
		currencyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		//		currencyStyle.setDataFormat((short)8);

		// Start to include the information.
		// Crate a line
		row = sheet.createRow((short)rowIndex);
		linha = planilhaResumo.createRow((short)rowIndex);

		rowIndex = rowIndex + 5;
		row = sheet.createRow((short)rowIndex);
		linha = planilhaResumo.createRow((short)rowIndex);

		// Cell for title
		headerCell = row.createCell(0);
		headerCell.setCellStyle(styleHeaderTemplate);
		cabecalho = linha.createCell(0);
		cabecalho.setCellStyle(styleHeaderTemplate);

		// Merge cells.
		headerCell.setCellValue(new HSSFRichTextString("Lista de ordens de serviço"));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,(short)0,(short)FailheaderNames.length-1));
		cabecalho.setCellValue(new HSSFRichTextString("Itens da nota fiscal de saída"));
		planilhaResumo.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,(short)0,(short)cabecalhoResumo.length-1));

		// add a line.
		rowIndex++;
		HSSFRow rowNF = sheet.createRow((short)rowIndex);

		rowNF.createCell(0).setCellValue("Nossa DANFE Nº");

		rowIndex++;
		// Add a row with column names
		row = sheet.createRow((short)rowIndex);
		linha = planilhaResumo.createRow((short)rowIndex);

		int numberOfColumns = 0;
		int numeroColunas = 0;

		numberOfColumns = FailheaderNames.length;
		numeroColunas = cabecalhoResumo.length;

		try{
			for(int j=0;j<numberOfColumns;j++){
				// A single cell for each column name  
				headerCell = row.createCell((int)j);
				// Set style
				headerCell.setCellStyle(styleHeaderFeature);
				HSSFRichTextString cellName = null;

				cellName = new HSSFRichTextString((String)FailheaderNames[j]);

				headerCell.setCellValue(cellName);
			}
		}catch (Exception e) {
			e.getMessage();
		}

		NotaFiscalRemessa nfr = (NotaFiscalRemessa)objeto;
		//		try{
		//			 nfr = notaFiscalRemessaService.buscarPorId(((NotaFiscalRemessa)objeto).getId());	
		//		}catch(Exception e){
		//			e.printStackTrace(); logger.error(e);
		//		}


		Endereco enderecoEntrega = null;
		if(nfr != null){
			enderecoEntrega = nfr.getEnderecoEntrega();
		}

		if(enderecoEntrega != null){
			String e = enderecoEntrega.getLogradouro();
			String numero = enderecoEntrega.getNumero();
			String cep = enderecoEntrega.getCep();
			String cidade = enderecoEntrega.getCidade();
			String estado = enderecoEntrega.getEstado();
			String telefone = enderecoEntrega.getTelefone();
			String enderecoCompleto = "";

			if(e != null && !e.isEmpty() && numero != null && !numero.isEmpty()){
				enderecoCompleto = e +", " + numero +" CEP: " + cep +" " + cidade + "-"+estado +  " Telefone: " + telefone;;
			}

			HSSFCell endEntrega = linha.createCell(0);
			endEntrega.setCellValue("Endereço de entrega: " + enderecoCompleto);
		}else{
			HSSFCell endEntrega = linha.createCell(0);
			endEntrega.setCellValue("Endereço de entrega:");
		}



		rowIndex++;
		rowIndex++;

		linha = planilhaResumo.createRow((short)rowIndex);

		try{
			for(int j=0;j<numeroColunas;j++){
				// A single cell for each column name  
				cabecalho = linha.createCell((int)j);
				// Set style
				cabecalho.setCellStyle(styleHeaderFeature);
				HSSFRichTextString cellName = null;

				cellName = new HSSFRichTextString((String)cabecalhoResumo[j]);

				cabecalho.setCellValue(cellName);
			}
		}catch (Exception e) {
			e.getMessage();
		}

		//add a line.
		rowIndex++;

		int linhaIndice = rowIndex;

		OrdemServico rowValues = null;
		NotaFiscalRemessa nfSaida = null;
		// Filling with screen info
		while(valuesIterator.hasNext()) {
			// New line
			row = sheet.createRow((short)rowIndex);
			// Filling Cells

			rowValues = (OrdemServico)valuesIterator.next();		

			//			try{
			//				rowValues = ordemServicoService.buscarCompletoPorId(rowValues.getId());
			//			}catch(Exception e){
			//				e.printStackTrace(); logger.error(e);
			//			}

			if(nfSaida == null){
				nfSaida = rowValues.getNotaFiscalSaida();
			}

			EspelhoNotaFiscalSaida espelhoNFSaida;
			if(rowValues.getUnidadePai() == null){
				espelhoNFSaida = new EspelhoNotaFiscalSaida();
				espelhoNFSaida.setIdItemNotaFiscal(rowValues.getItemNotaFiscal().getId());
				espelhoNFSaida.setItemNotaFiscal(rowValues.getItemNotaFiscal());
				espelhoNFSaida.setTextoEspelho(rowValues.getItemNotaFiscal().getNome());
				if(itensNotaFiscalSaida.containsKey(espelhoNFSaida)){
					Integer val = itensNotaFiscalSaida.get(espelhoNFSaida);
					val++;
					itensNotaFiscalSaida.put(espelhoNFSaida, val);
				}else{
					itensNotaFiscalSaida.put(espelhoNFSaida, 1);
				}
			}

			//"Unidade","S/N Cliente","S/N Fabricante","Nº NF Entrada","Valor","Nº OS","Condição"

			HSSFCell cUnidade = row.createCell((int)0);
			HSSFCell cSerieCliente = row.createCell((int)1);
			HSSFCell cSerieFabricante = row.createCell((int)2);
			HSSFCell cNFEntrada = row.createCell((int)3);
			HSSFCell cValor = row.createCell((int)4);
			HSSFCell cOrdemServico = row.createCell((int)5);
			HSSFCell cCondicao = row.createCell((int)6);

			cOrdemServico.setCellValue(new HSSFRichTextString(rowValues.getNumeroOrdemServico()));

			cUnidade.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getNome()));

			cSerieFabricante.setCellValue(new HSSFRichTextString(rowValues.getSerieFabricante()));	
			cSerieCliente.setCellValue(new HSSFRichTextString(rowValues.getSerieCliente()));	

			if(rowValues.getItemNotaFiscal() != null && rowValues.getUnidadePai()==null){
				cValor.setCellValue(rowValues.getItemNotaFiscal().getValorUnitario().doubleValue());	
				cValor.setCellStyle(currencyStyle);
			}

			cNFEntrada.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscal().getNumero()));

			if(rowValues.getReparo() != null){
				if(rowValues.getReparo().getCondicao().equals("Com condição de reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Reparado"));
				}
				if(rowValues.getReparo().getCondicao().equals("Sem condição de reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Irreparável"));
				}
				if(rowValues.getReparo().getCondicao().equals("Devolução sem reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Devolução sem reparo"));
				}
			}else if(rowValues.getOrcamento() != null){
				if(rowValues.getOrcamento().getCondicao().equals("Com condição de reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Reparado"));
				}
				if(rowValues.getOrcamento().getCondicao().equals("Sem condição de reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Irreparável"));
				}
				if(rowValues.getOrcamento().getCondicao().equals("Devolução sem reparo")){
					cCondicao.setCellValue(new HSSFRichTextString("Devolução sem reparo"));
				}
			}

			if(nfSaida != null)
				rowNF.createCell(1).setCellValue(nfSaida.getNumero());

			sheet.autoSizeColumn((int)0);
			sheet.autoSizeColumn((int)1);
			sheet.autoSizeColumn((int)2);
			sheet.autoSizeColumn((int)3);
			sheet.autoSizeColumn((int)4);
			sheet.autoSizeColumn((int)5);
			sheet.autoSizeColumn((int)6);
			sheet.autoSizeColumn((int)7);
			sheet.autoSizeColumn((int)8);
			sheet.autoSizeColumn((int)9);
			sheet.autoSizeColumn((int)10);

			rowIndex++;
			totalFeatures++;
		}

		rowIndex++;

		//Total Lines
		row = sheet.createRow((short)rowIndex);
		HSSFCell totalFeaturesFailed = row.createCell((int)0);		
		totalFeaturesFailed.setCellValue(new HSSFRichTextString("Total de os's: " + totalFeatures));
		totalFeaturesFailed.setCellStyle(styleFooterFeature);
		totalFeaturesFailed = row.createCell((int)1);
		//totalFeaturesFailed.setCellValue(totalFeatures);

		sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,(short)0,(short)FailheaderNames.length-1));
		totalFeaturesFailed.setCellStyle(styleFooterFeature);

		//"Cod.", "Descrição do item da NF", "NCM","CFOP","CST","Quantidade", "Valor unitário","Valor total","Unidade"
		int totalItens = 0;
		if(!itensNotaFiscalSaida.isEmpty()){
			for(EspelhoNotaFiscalSaida inf : itensNotaFiscalSaida.keySet()){

				linha = planilhaResumo.createRow((short)linhaIndice);

				HSSFCell cCodigo = linha.createCell((int)0);
				HSSFCell cDescricao = linha.createCell((int)1);
				HSSFCell cNCM = linha.createCell((int)2);
				HSSFCell cCFOP = linha.createCell((int)3);
				HSSFCell cUnidadeMedida = linha.createCell((int)4);
				HSSFCell cQuantidade = linha.createCell((int)5);
				HSSFCell cValorUnitario = linha.createCell((int)6);
				HSSFCell cValorTotal = linha.createCell((int)7);
				HSSFCell cUnidade = linha.createCell((int)8);

				cCodigo.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getCodigo()));
				cDescricao.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getNome()));
				if(inf.getItemNotaFiscal().getNcm() != null){
					cNCM.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getNcm()));
				}

				if(inf.getItemNotaFiscal().getCfop() != null){
					cCFOP.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getCfop()));
				}

				if(inf.getItemNotaFiscal().getUnidadeMedida() != null){
					cUnidadeMedida.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getUnidadeMedida()));
				}

				totalItens = totalItens + itensNotaFiscalSaida.get(inf);
				cQuantidade.setCellValue(new HSSFRichTextString(itensNotaFiscalSaida.get(inf).toString()));
				cValorUnitario.setCellValue(inf.getItemNotaFiscal().getValorUnitario().doubleValue());
				cValorUnitario.setCellStyle(currencyStyle);
				cValorTotal.setCellValue(inf.getItemNotaFiscal().getValorUnitario().multiply(new BigDecimal(itensNotaFiscalSaida.get(inf))).doubleValue());
				cValorTotal.setCellStyle(currencyStyle);
				cUnidade.setCellValue(new HSSFRichTextString(inf.getItemNotaFiscal().getUnidade().getNome()));

				planilhaResumo.autoSizeColumn((int)0);
				planilhaResumo.autoSizeColumn((int)1);
				planilhaResumo.autoSizeColumn((int)2);
				planilhaResumo.autoSizeColumn((int)3);
				planilhaResumo.autoSizeColumn((int)4);
				planilhaResumo.autoSizeColumn((int)5);
				planilhaResumo.autoSizeColumn((int)6);
				planilhaResumo.autoSizeColumn((int)7);
				planilhaResumo.autoSizeColumn((int)8);

				linhaIndice++;

			}
			//Total Lines
			linha = planilhaResumo.createRow((short)linhaIndice);
			HSSFCell totalItensCell = linha.createCell((int)0);		
			totalItensCell.setCellValue(new HSSFRichTextString("Total de itens da nota fiscal" ));
			totalItensCell.setCellStyle(styleFooterFeature);
			totalItensCell = linha.createCell((int)5);
			totalItensCell.setCellValue(totalItens);
			totalItensCell.setCellStyle(styleFooterFeature);
		}



		/************************************PICTURE**************************************/
		// Add picture data to this workbook.
		try{
			InputStream is = RelatorioRomaneioExpedicao.class.getResourceAsStream("logo_servilogi.png");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			int pictureIdx2 = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// Create the drawing patriarch.  This is the top level container for all shapes. 
			Drawing drawing = sheet.createDrawingPatriarch();	
			Drawing drawing2 = planilhaResumo.createDrawingPatriarch();	
			//add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();			
			//set top-left corner of the picture,
			//subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(0);			
			anchor.setRow1(0);		

			ClientAnchor anchor2 = helper.createClientAnchor();			
			//set top-left corner of the picture,
			//subsequent call of Picture#resize() will operate relative to it
			anchor2.setCol1(0);			
			anchor2.setRow1(0);	

			Picture pict = drawing.createPicture(anchor, pictureIdx);	
			Picture pict2 = drawing2.createPicture(anchor2, pictureIdx2);			

			//auto-size picture relative to its top-left corner
			pict.resize();
			pict2.resize();
		}catch (Exception e) {
			e.getMessage();
		}
		/************************************PICTURE - END**********************************/

		File dir = new File("C:\\arquivos");;
		if(!dir.exists()){
			dir.mkdir();
		}
		File file = new File(dir,"workbook.xls");
		System.out.println(file.canWrite());
		System.out.println(file.getAbsolutePath());

		try{
			FileOutputStream output = new FileOutputStream(file);
			wb.write( output );     
			output.close();    
			return returnByteArray(file.getAbsolutePath());
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}

	}


}