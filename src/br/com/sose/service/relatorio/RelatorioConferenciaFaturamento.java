package br.com.sose.service.relatorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.proposta.ItemPropostaService;
import br.com.sose.service.recebimento.OrdemServicoService;

@Service(value="relatorioConferenciaFaturamento")
@RemotingDestination(value="relatorioConferenciaFaturamento")
public class RelatorioConferenciaFaturamento {	

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ItemPropostaService itemPorpostaService;

	private String[] FailheaderNames = {"OS","Nº OS Cliente","N/S Cliente","N/S Fabricante","Dt Abertura","Nome no sistema","Nº NF Entrada","DT NF Entrada","DT NF Emissão","Cliente Avaya","Case Avaya","Laboratório","Unidade","Código","Valor unitário","Descrição do produto da NF","Fabricante","Condição Reparo","Nº NF Saida","DT emissão NF Saída","DT Saída do material","Nº Proposta","Dt Proposta","Status proposta","Dt status proposta","Fornecedor","Origem","Valor","Observação Recebimento","Observação Orçamento","Observação Proposta","Observação Reparo","Observação Expedicao", "Observação Faturamento","Observação consulta"};

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

		Collections.sort(valores, new NumeroOrdemServicoComparator());

		// Iterator Structures.
		Iterator<?> valuesIterator = valores.iterator();

		// To navigate throw sheet lines.
		int rowIndex = 0;
		int totalFeatures = 0;

		double totalFaturado = 0;
		double totalSistema = 0;
		double totalFrete = 0;
		Calendar cal = Calendar.getInstance(); // locale-specific


		// Excel Structures. 
		// XLS file
		HSSFWorkbook wb = new HSSFWorkbook();

		// Sheet inside file
		HSSFSheet  sheet = null;


		sheet = wb.createSheet( "Faturamento - relatório de controle" );


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

		HSSFCellStyle dateStyle = wb.createCellStyle();
		HSSFDataFormat dateFormat = wb.createDataFormat();
		dateStyle.setDataFormat(dateFormat.getFormat("dd-mm-yyyy"));
		dateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);


		// Start to include the information.
		// Crate a line
		row = sheet.createRow((short)rowIndex);


		rowIndex = rowIndex + 5;
		row = sheet.createRow((short)rowIndex);
		// Cell for title
		headerCell = row.createCell(0);
		headerCell.setCellStyle(styleHeaderTemplate);


		// Merge cells.
		headerCell.setCellValue(new HSSFRichTextString("Relatório de controle faturamento"));
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
				rowValues = ordemServicoService.buscarPorId(rowValues.getId());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
			}

			HSSFCell cOrdemServico = row.createCell((int)0);
			HSSFCell cNumeroOSCliente = row.createCell((int)1);
			HSSFCell cSerieCliente = row.createCell((int)2);
			HSSFCell cSerieFabricante = row.createCell((int)3);
			HSSFCell cDtAbertura = row.createCell((int)4);
			HSSFCell cCliente = row.createCell((int)5);
			HSSFCell cNFEntrada = row.createCell((int)6);
			HSSFCell cDtNFEntrada = row.createCell((int)7);
			HSSFCell cDtNFEmissao = row.createCell((int)8);
			HSSFCell cClienteAvaya = row.createCell((int)9);
			HSSFCell cCaseAvaya = row.createCell((int)10);
			HSSFCell cLaboratorio = row.createCell((int)11);
			HSSFCell cUnidade = row.createCell((int)12);
			HSSFCell cCodigo = row.createCell((int)13);
			HSSFCell cValorUnitario = row.createCell((int)14);
			HSSFCell cDescProdutoNF = row.createCell((int)15);
			HSSFCell cFabricante = row.createCell((int)16);
			HSSFCell cCondicaoReparo = row.createCell((int)17);
			HSSFCell cNumeroNFSaida = row.createCell((int)18);
			HSSFCell cDtEmissaoNFSaida = row.createCell((int)19);
			HSSFCell cDtNFSaida = row.createCell((int)20);
			HSSFCell cNumeroProposta = row.createCell((int)21);
			HSSFCell cDtProposta = row.createCell((int)22);
			HSSFCell cAprovadoReprovado = row.createCell((int)23);
			HSSFCell cDtAprovadoReprovado = row.createCell((int)24);
			HSSFCell cFornecedor = row.createCell((int)25);
			HSSFCell cOrigem = row.createCell((int)26);
			HSSFCell cValor = row.createCell((int)27);
			HSSFCell cObsRecebimento = row.createCell((int)28);
			HSSFCell cObsOrcamento = row.createCell((int)29);
			HSSFCell cObsProposta = row.createCell((int)30);
			HSSFCell cObsReparo = row.createCell((int)31);
			HSSFCell cObsExpedicao = row.createCell((int)32);
			HSSFCell cObsFaturamento = row.createCell((int)33);
			HSSFCell cObsConsulta = row.createCell((int)34);

			cOrdemServico.setCellValue(new HSSFRichTextString(rowValues.getNumeroOrdemServico()));
			cNumeroOSCliente.setCellValue(new HSSFRichTextString(rowValues.getOrdemServicoCliente()));
			cSerieCliente.setCellValue(new HSSFRichTextString(rowValues.getSerieCliente()));
			cSerieFabricante.setCellValue(new HSSFRichTextString(rowValues.getSerieFabricante()));

			if(rowValues.getDataAbertura() != null){
				cal.setTime(rowValues.getDataAbertura());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cDtAbertura.setCellValue(cal.getTime());
				cDtAbertura.setCellStyle(dateStyle);
			}else{
				cDtAbertura.setCellValue("");
			}
			cCliente.setCellValue(new HSSFRichTextString(rowValues.getCliente().getNomeSistema()));
			cNFEntrada.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscal().getNumero()));
			if(rowValues != null && rowValues.getNotaFiscal()!= null && rowValues.getNotaFiscal().getDataChegada() != null){
				cal.setTime(rowValues.getNotaFiscal().getDataChegada());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cDtNFEntrada.setCellValue(cal.getTime());
				cDtNFEntrada.setCellStyle(dateStyle);

			}else{
				cDtNFEntrada.setCellValue("");
			}

			if(rowValues != null && rowValues.getNotaFiscal()!= null && rowValues.getNotaFiscal().getDataNotaFiscal() != null){
				cDtNFEmissao.setCellValue(rowValues.getNotaFiscal().getDataNotaFiscal());
				cDtNFEmissao.setCellStyle(dateStyle);

			}else{
				cDtNFEmissao.setCellValue("");
			}
			cClienteAvaya.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscal().getClienteAvaya()));
			cCaseAvaya.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscal().getCaseAvaya()));
			cLaboratorio.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getLaboratorio().getNome()));
			cUnidade.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getNome()));
			cCodigo.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getCodigo()));
			cValorUnitario.setCellValue(rowValues.getItemNotaFiscal().getValorUnitario().doubleValue());
			cValorUnitario.setCellStyle(currencyStyle);
			cDescProdutoNF.setCellValue(new HSSFRichTextString(rowValues.getItemNotaFiscal().getNome()));
			cFabricante.setCellValue(new HSSFRichTextString(rowValues.getUnidade().getFabricante().getNome()));

			if(rowValues.getReparo() != null && rowValues.getReparo().getCondicao() != null){
				if(rowValues.getReparo().getCondicao().equals("Com condição de reparo")){
					cCondicaoReparo.setCellValue(new HSSFRichTextString("Reparado"));
				}
				if(rowValues.getReparo().getCondicao().equals("Sem condição de reparo")){
					cCondicaoReparo.setCellValue(new HSSFRichTextString("Irreparável"));
				}
				if(rowValues.getReparo().getCondicao().equals("Devolução sem reparo")){
					cCondicaoReparo.setCellValue(new HSSFRichTextString("Devolução sem reparo"));
				}
			}else{
				cCondicaoReparo.setCellValue("");
			}

			if(rowValues.getNotaFiscalSaida() != null && rowValues.getNotaFiscalSaida().getNumero() != null){
				cNumeroNFSaida.setCellValue(new HSSFRichTextString(rowValues.getNotaFiscalSaida().getNumero()));
			}else{
				cNumeroNFSaida.setCellValue("");
			}
			
			if(rowValues.getNotaFiscalSaida() != null && rowValues.getNotaFiscalSaida().getDtEmissao() != null){
				//TODO - formatar as datas new HSSFDataFormatter().
				cal.setTime(rowValues.getNotaFiscalSaida().getDtEmissao());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cDtEmissaoNFSaida.setCellValue(cal.getTime());
				cDtEmissaoNFSaida.setCellStyle(dateStyle);
			}else{
				cDtEmissaoNFSaida.setCellValue("");
			}

			if(rowValues.getNotaFiscalSaida() != null && rowValues.getNotaFiscalSaida().getDtSaida() != null){
				//TODO - formatar as datas new HSSFDataFormatter().
				cDtNFSaida.setCellValue(rowValues.getNotaFiscalSaida().getDtSaida());
				cDtNFSaida.setCellStyle(dateStyle);
			}else{
				cDtNFSaida.setCellValue("");
			}

			if(rowValues.getProposta() != null && rowValues.getProposta().getNumero() != null){
				cNumeroProposta.setCellValue(new HSSFRichTextString(rowValues.getProposta().getNumero()));	
			}else{
				cNumeroProposta.setCellValue("");
			}

			if(rowValues.getProposta() != null && rowValues.getProposta().getDataEnvioCliente() != null){
				cDtProposta.setCellValue(rowValues.getProposta().getDataEnvioCliente());
				cDtProposta.setCellStyle(dateStyle);
			}else{
				cDtProposta.setCellValue("");
			}

			try{
				ItemProposta ip = null;
				if(rowValues.getProposta() != null){
					ip = itemPorpostaService.buscarPorOrdemServico(rowValues, rowValues.getProposta());
					if(ip != null){
						if(ip.getIsAprovado()){
							cAprovadoReprovado.setCellValue(new HSSFRichTextString("Aprovado"));
						}else{
							cAprovadoReprovado.setCellValue(new HSSFRichTextString("Reprovado"));
						}

						if(ip.getDataAprovacao() != null){
							cDtAprovadoReprovado.setCellValue(ip.getDataAprovacao());
							cDtAprovadoReprovado.setCellStyle(dateStyle);
						}else{
							cDtAprovadoReprovado.setCellValue(ip.getDataLiberacao());
							cDtAprovadoReprovado.setCellStyle(dateStyle);

							cAprovadoReprovado.setCellValue(new HSSFRichTextString("Liberado"));

						}
					}else{

					}
				}else{
					cAprovadoReprovado.setCellValue(new HSSFRichTextString(""));
					cDtAprovadoReprovado.setCellValue("");

				}

			}catch(Exception e){

			}

			cFornecedor.setCellValue("");

			cOrigem.setCellValue(new HSSFRichTextString(rowValues.getOrigemFaturamento()));

			cValor.setCellValue(rowValues.getValorFaturado().doubleValue());
			cValor.setCellStyle(currencyStyle);
			totalFaturado = totalFaturado + rowValues.getValorFaturado().doubleValue();
			totalSistema = totalSistema + rowValues.getValorSistema().doubleValue();

			if(rowValues.getFreteUnitarioProposta() != null && rowValues.getFreteUnitarioExpedicao() != null){
				if(rowValues.getFreteUnitarioProposta().compareTo(rowValues.getFreteUnitarioExpedicao()) == 1){
					totalFrete = totalFrete + rowValues.getFreteUnitarioProposta().doubleValue();
				}else{
					totalFrete = totalFrete + rowValues.getFreteUnitarioExpedicao().doubleValue();
				}
			}else if(rowValues.getFreteUnitarioProposta() != null && rowValues.getFreteUnitarioExpedicao() == null){
				totalFrete = totalFrete + rowValues.getFreteUnitarioProposta().doubleValue();
			}else if(rowValues.getFreteUnitarioProposta() == null && rowValues.getFreteUnitarioExpedicao() != null){
				totalFrete = totalFrete + rowValues.getFreteUnitarioExpedicao().doubleValue();
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

				if(obsRecebimento != null && !obsRecebimento.isEmpty()){
					for(Observacao obs : obsRecebimento){
						sb.append("OBS"+i+": "+obs.getTexto()+" ");
						i++;
					}
				}

				cObsRecebimento.setCellValue(new HSSFRichTextString(sb.toString()));

			}catch(Exception e){

			}

			try{
				if(rowValues.getProposta() != null){
					List<Observacao> obsProposta = observacaoService.listarObservacoesProposta(rowValues.getProposta());
					if(obsProposta != null && !obsProposta.isEmpty()){
						StringBuilder sb = new StringBuilder();
						int i =1;
						for(Observacao obs : obsProposta){
							sb.append("OBS"+i+": "+obs.getTexto()+" ");
							i++;
						}
						cObsProposta.setCellValue(new HSSFRichTextString(sb.toString()));
					}else{
						cObsProposta.setCellValue(new HSSFRichTextString(""));
					}
				}else{
					cObsProposta.setCellValue(new HSSFRichTextString(""));
				}
			}catch(Exception e){

			}

			try{
				if(rowValues.getReparo() != null){
					List<Observacao> obsReparo = observacaoService.listarObservacoesReparo(rowValues.getReparo());
					if(obsReparo != null && !obsReparo.isEmpty()){
						StringBuilder sb = new StringBuilder();
						int i =1;
						for(Observacao obs : obsReparo){
							sb.append("OBS"+i+": "+obs.getTexto() + " ");
							i++;
						}
						cObsReparo.setCellValue(new HSSFRichTextString(sb.toString()));
					}else{
						cObsReparo.setCellValue(new HSSFRichTextString(""));
					}
				}else{
					cObsReparo.setCellValue(new HSSFRichTextString(""));
				}
			}catch(Exception e){

			}


			try{
				List<Observacao> obsProposta = new ArrayList<Observacao>();
				List<Observacao> obsItemPropostaAux = observacaoService.listarObservacoesItemProposta(rowValues);
				if(obsItemPropostaAux != null){
					obsProposta.addAll(obsItemPropostaAux);
				}

				if(rowValues.getProposta() != null){
					List<Observacao> obsPropostaAux = observacaoService.listarObservacoesProposta(rowValues.getProposta());
					if(obsPropostaAux != null){
						obsProposta.addAll(obsPropostaAux);
					}

				}

				StringBuilder sb = new StringBuilder();
				int i =1;
				if(obsProposta != null && !obsProposta.isEmpty()){
					for(Observacao obs : obsProposta){
						sb.append("OBS"+i+": "+obs.getTexto()+" ");
						i++;
					}
				}

				cObsProposta.setCellValue(new HSSFRichTextString(sb.toString()));
			}catch(Exception e){

			}

			try{
				if(rowValues.getOrcamento() != null){
					List<Observacao> obsOrcamento = observacaoService.listarObservacoesOrcamento(rowValues.getOrcamento());
					if(obsOrcamento != null && !obsOrcamento.isEmpty()){
						StringBuilder sb = new StringBuilder();
						int i =1;
						for(Observacao obs : obsOrcamento){
							sb.append("OBS"+i+": "+obs.getTexto()+" ");
							i++;
						}
						cObsOrcamento.setCellValue(new HSSFRichTextString(sb.toString()));
					}else{
						cObsOrcamento.setCellValue(new HSSFRichTextString(""));
					}
				}else{
					cObsOrcamento.setCellValue(new HSSFRichTextString(""));
				}
			}catch(Exception e){

			}

			try{
				List<Observacao> obsExpedicao = new ArrayList<Observacao>();
				List<Observacao> obsExpedicaoAux2 = observacaoService.listarObservacoesExpedicao(rowValues);

				if(obsExpedicaoAux2 != null){
					obsExpedicao.addAll(obsExpedicaoAux2);
				}

				if(rowValues.getNotaFiscalSaida() != null){
					List<Observacao> obsExpedicaoAux = observacaoService.listarObservacoesNotaFiscalSaida(rowValues.getNotaFiscalSaida());

					if(obsExpedicaoAux != null){
						obsExpedicao.addAll(obsExpedicaoAux);
					}
				}

				StringBuilder sb = new StringBuilder();
				int i =1;
				if(obsExpedicao != null && !obsExpedicao.isEmpty()){
					for(Observacao obs : obsExpedicao){
						sb.append("OBS"+i+": "+obs.getTexto()+" ");
						i++;
					}
				}

				cObsExpedicao.setCellValue(new HSSFRichTextString(sb.toString()));

			}catch(Exception e){

			}

			try{
				if(objeto != null){
					List<Observacao> obsFaturamento = observacaoService.listarObservacoesFaturamento((Faturamento)objeto);
					if(obsFaturamento != null && !obsFaturamento.isEmpty()){
						StringBuilder sb = new StringBuilder();
						int i =1;
						for(Observacao obs : obsFaturamento){
							sb.append("OBS"+i+": "+obs.getTexto()+" ");
							i++;
						}
						cObsFaturamento.setCellValue(new HSSFRichTextString(sb.toString()));
					}else{
						cObsFaturamento.setCellValue(new HSSFRichTextString(""));
					}
				}else{
					cObsFaturamento.setCellValue(new HSSFRichTextString(""));
				}
			}catch(Exception e){

			}

			
				try{
					rowValues.setObservacaoConsulta(ordemServicoService.observacaoConsolidadaConsulta(rowValues));
				}catch(Exception e){
					e.printStackTrace();
				}

			if(rowValues.getObservacaoConsulta() != null){
				cObsConsulta.setCellValue(new HSSFRichTextString(rowValues.getObservacaoConsulta()));
			}else{
				cObsConsulta.setCellValue(new HSSFRichTextString(""));
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
			sheet.autoSizeColumn((int)12);
			sheet.autoSizeColumn((int)13);
			sheet.autoSizeColumn((int)14);
			sheet.autoSizeColumn((int)15);
			sheet.autoSizeColumn((int)16);
			sheet.autoSizeColumn((int)17);
			sheet.autoSizeColumn((int)18);
			sheet.autoSizeColumn((int)19);
			sheet.autoSizeColumn((int)20);
			sheet.autoSizeColumn((int)21);
			sheet.autoSizeColumn((int)22);
			sheet.autoSizeColumn((int)23);
			sheet.autoSizeColumn((int)24);
			sheet.autoSizeColumn((int)25);
			sheet.autoSizeColumn((int)26);
			sheet.autoSizeColumn((int)27);
			sheet.autoSizeColumn((int)28);
			sheet.autoSizeColumn((int)29);
			sheet.autoSizeColumn((int)30);
			sheet.autoSizeColumn((int)31);
			sheet.autoSizeColumn((int)32);
			sheet.autoSizeColumn((int)33);
			sheet.autoSizeColumn((int)34);
			sheet.autoSizeColumn((int)35);
			sheet.autoSizeColumn((int)36);
			sheet.autoSizeColumn((int)37);
			sheet.autoSizeColumn((int)38);
			sheet.autoSizeColumn((int)39);
			sheet.autoSizeColumn((int)40);
			sheet.autoSizeColumn((int)41);
			sheet.autoSizeColumn((int)42);
			sheet.autoSizeColumn((int)43);
			sheet.autoSizeColumn((int)44);
			sheet.autoSizeColumn((int)45);
			sheet.autoSizeColumn((int)46);
			sheet.autoSizeColumn((int)47);
			sheet.autoSizeColumn((int)48);
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
		totalFeaturesFailed.setCellStyle(styleFooterFeature);

		rowIndex++;
		
		//Total do sistema
		row = sheet.createRow((short)rowIndex);
		HSSFCell totalSistemaC = row.createCell((int)0);		
		totalSistemaC.setCellValue(new HSSFRichTextString("Valor total sistema: "));
		totalSistemaC.setCellStyle(styleFooterFeature);

		HSSFCell valorTotalSistema = row.createCell((int)1);		
		valorTotalSistema.setCellValue(totalSistema);
		valorTotalSistema.setCellStyle(currencyStyle);
		//totalFeaturesFailed.setCellValue(totalFeatures);

		rowIndex++;

		//Total da fatura
		row = sheet.createRow((short)rowIndex);
		HSSFCell totalFatura = row.createCell((int)0);		
		totalFatura.setCellValue(new HSSFRichTextString("Valor total da fatura: "));
		totalFatura.setCellStyle(styleFooterFeature);

		HSSFCell valorTotalFaturado = row.createCell((int)1);		
		valorTotalFaturado.setCellValue(totalFaturado);
		valorTotalFaturado.setCellStyle(currencyStyle);
		//totalFeaturesFailed.setCellValue(totalFeatures);

		rowIndex++;

		//Total da fatura
		row = sheet.createRow((short)rowIndex);
		HSSFCell totalFreteColuna = row.createCell((int)0);		
		totalFreteColuna.setCellValue(new HSSFRichTextString("Valor total do frete: "));
		totalFreteColuna.setCellStyle(styleFooterFeature);

		HSSFCell valorTotalFrete = row.createCell((int)1);		
		valorTotalFrete.setCellValue(totalFrete);
		valorTotalFrete.setCellStyle(currencyStyle);

		sheet.autoSizeColumn((int)0);

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
		File file = new File(dir,"faturamento.xls");
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