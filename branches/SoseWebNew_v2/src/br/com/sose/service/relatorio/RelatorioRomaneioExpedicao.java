package br.com.sose.service.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.comparators.NumeroOrdemServicoComparator;
import br.com.sose.entity.admistrativo.Observacao;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service(value="relatorioRomaneioExpedicao")
@RemotingDestination(value="relatorioRomaneioExpedicao")
public class RelatorioRomaneioExpedicao  {	

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	private String[] FailheaderNames = {
			"Nº OS","Cliente","Unidade","N/S Fabricante","N/S Cliente","Código","Cliente avaya","Case avaya","Nº NF Entrada","Valor unitário", "Condição","Em garantia","Observação Recebimento"};


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
		try{
			logger.info("Creating file with " + nomeRelatorio + " information");

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


			sheet = wb.createSheet( "Romaneio - ordens de serviço" );


			sheet.setDisplayGridlines(false);

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

			// Set column size.
			sheet.setColumnWidth((int)0,(int)6000);
			sheet.setColumnWidth((int)1,(int)6000);
			sheet.setColumnWidth((int)2,(int)6000);
			sheet.setColumnWidth((int)3,(int)6000);
			sheet.setColumnWidth((int)4,(int)6000);
			sheet.setColumnWidth((int)5,(int)6000);
			sheet.setColumnWidth((int)6,(int)6000);
			sheet.setColumnWidth((int)7,(int)6000);
			sheet.setColumnWidth((int)8,(int)6000);
			sheet.setColumnWidth((int)9,(int)6000);
			sheet.setColumnWidth((int)10,(int)6000);
			sheet.setColumnWidth((int)11,(int)6000);
			sheet.setColumnWidth((int)12,(int)6000);

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

			// Start to include the information.
			// Crate a line
			row = sheet.createRow((short)rowIndex);


			rowIndex = rowIndex + 5;
			row = sheet.createRow((short)rowIndex);
			// Cell for title
			headerCell = row.createCell(0);
			headerCell.setCellStyle(styleHeaderTemplate);


			// Merge cells.
			headerCell.setCellValue(new HSSFRichTextString("Romaneio expedição"));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,(short)0,(short)FailheaderNames.length-1));


			// add a line.
			rowIndex++;
			rowIndex++;
			// Add a row with column names
			row = sheet.createRow((short)rowIndex);

			int numberOfColumns = 0;

			numberOfColumns = FailheaderNames.length;


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

			//add a line.
			rowIndex++;

			// Filling with screen info
			while(valuesIterator.hasNext()) {
				// New line
				row = sheet.createRow((short)rowIndex);
				// Filling Cells

				OrdemServico rowValues = (OrdemServico)valuesIterator.next();	

				try{
					rowValues = ordemServicoService.buscarCompletoPorId(rowValues.getId());
				}catch(Exception e){
					e.printStackTrace(); logger.error(e);
				}

				HSSFCell cOrdemServico = row.createCell((int)0);
				HSSFCell cCliente = row.createCell((int)1);
				HSSFCell cUnidade = row.createCell((int)2);
				HSSFCell cSerieFabricante = row.createCell((int)3);
				HSSFCell cSerieCliente = row.createCell((int)4);
				HSSFCell cCodigo = row.createCell((int)5);
				HSSFCell cClienteAvaya = row.createCell((int)6);
				HSSFCell cCaseAvaya = row.createCell((int)7);
				HSSFCell cNFEntrada = row.createCell((int)8);
				HSSFCell cItemValorUnitario = row.createCell((int)9);
				HSSFCell cCondicao = row.createCell((int)10);
				HSSFCell cGarantia = row.createCell((int)11);
				HSSFCell cObservacaoRecebinto = row.createCell((int)12);


				cOrdemServico.setCellValue(new HSSFRichTextString(rowValues.getNumeroOrdemServico()));
				if(rowValues.getCliente() != null){
					cCliente.setCellValue(new HSSFRichTextString(rowValues.getCliente().getNomeSistema()));	
				}else{

				}
				if(rowValues.getUnidade() != null){
					cUnidade.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getNome()));
					cCodigo.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getCodigo()));	
				}else{

				}

				cSerieFabricante.setCellValue(new HSSFRichTextString(rowValues.getSerieFabricante()));	
				cSerieCliente.setCellValue(new HSSFRichTextString(rowValues.getSerieCliente()));	

				if(rowValues.getNotaFiscal() != null){
					cNFEntrada.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscal().getNumero()));
				}else{

				}

				cClienteAvaya.setCellValue(new HSSFRichTextString(rowValues.getClienteAvaya()));
				cCaseAvaya.setCellValue(new HSSFRichTextString(rowValues.getCaseAvaya()));

				if(rowValues.getItemNotaFiscal() != null && rowValues.getItemNotaFiscal().getValorUnitario() != null && rowValues.getUnidadePai() == null){
					cItemValorUnitario.setCellValue(rowValues.getItemNotaFiscal().getValorUnitario().doubleValue());
					cItemValorUnitario.setCellStyle(currencyStyle);
				}else{

				}

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

				try{
					List<Observacao> obsRecebimento = new ArrayList<Observacao>();
					List<Observacao> obsOrdemServico = observacaoService.listarObservacoesOrdemServico(rowValues);
					List<Observacao> obsNotaFiscal = observacaoService.listarObservacoesNotaFiscal(rowValues.getNotaFiscal());
					if(obsOrdemServico != null){
						obsRecebimento.addAll(obsOrdemServico);
					}
					if(obsNotaFiscal != null){
						obsRecebimento.addAll(obsNotaFiscal);
					}
					StringBuilder sb = new StringBuilder();
					int i =1;
					//				if(rowValues.getNotaFiscal().getObservacao() != null && !rowValues.getNotaFiscal().getObservacao().isEmpty())
					//					sb.append("OBS"+i+": " + rowValues.getNotaFiscal().getObservacao());
					if(obsRecebimento != null && !obsRecebimento.isEmpty()){
						for(Observacao obs : obsRecebimento){
							i++;
							sb.append("OBS"+i+": "+obs.getTexto());
						}
					}

					cObservacaoRecebinto.setCellValue(new HSSFRichTextString(sb.toString()));

				}catch(Exception e){

				}

				if(rowValues.getGarantia() != null && rowValues.getGarantia()){
					cGarantia.setCellValue(new HSSFRichTextString("SIM"));
				}else{
					cGarantia.setCellValue(new HSSFRichTextString("NÃO"));
				}

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
				sheet.autoSizeColumn((int)11);

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
			/************************************PICTURE**************************************/
			// Add picture data to this workbook.
			try{
				InputStream is = RelatorioRomaneioExpedicao.class.getResourceAsStream("logo_servilogi.png");
				byte[] bytes = IOUtils.toByteArray(is);
				int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
				is.close();

				CreationHelper helper = wb.getCreationHelper();

				// Create the drawing patriarch.  This is the top level container for all shapes. 
				Drawing drawing = sheet.createDrawingPatriarch();			
				//add a picture shape
				ClientAnchor anchor = helper.createClientAnchor();			
				//set top-left corner of the picture,
				//subsequent call of Picture#resize() will operate relative to it
				anchor.setCol1(0);			
				anchor.setRow1(0);			

				Picture pict = drawing.createPicture(anchor, pictureIdx);			
				//auto-size picture relative to its top-left corner
				pict.resize();
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
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}

	}


}