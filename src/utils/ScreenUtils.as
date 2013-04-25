package utils
{
	
	import entities.orcamentoreparo.Orcamento;
	import entities.orcamentoreparo.Reparo;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.KeyboardEvent;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.containers.Canvas;
	import mx.containers.FormItem;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.ColorPicker;
	import mx.controls.ComboBox;
	import mx.controls.DateField;
	import mx.controls.RadioButton;
	import mx.controls.TextArea;
	import mx.core.Container;
	import mx.core.UIComponentDescriptor;
	import mx.formatters.DateFormatter;
	import mx.formatters.SwitchSymbolFormatter;
	import mx.resources.ResourceManager;
	
	import spark.components.CheckBox;
	import spark.components.NumericStepper;
	import spark.components.TextArea;
	import spark.components.TextInput;
	
	import to.OrcRepGenericoTO;
	
	

	public class ScreenUtils
	{
		private static const millisecondsPerDay :int = 1000 * 60 * 60 * 24;
		
		[Bindable]
		public static var font_header_size:int = 15;
		
		[Bindable]
		public static var months:Array =  ['janeiro','fevereiro','março','abril','maio','junho','julho','agosto','setembro','outubro','novembro','dezembro'];				
		
		public static var dias:ArrayCollection = new ArrayCollection(['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30']);

		public static var horas:ArrayCollection = new ArrayCollection(['00','01','02','03','04','05','06','07']);
		
		public static var minutos:ArrayCollection = new ArrayCollection(['00','05','10','15','20','25','30','35','40','45','50','55']);
		
		public static var estados:Array =  [new Estado(1,"Acre","AC"),
			new Estado(2,"Alagoas","AL"),new Estado(3,"Amazonas","AM"),new Estado(4,"Amapá","AP"),
			new Estado(5,"Bahia","BA"),new Estado(6,"Ceará","CE"),new Estado(7,"Distrito Federal","DF"),
			new Estado(8,"Espírito Santo","ES"),new Estado(9,"Goiás","GO"),	new Estado(10,"Maranhão","MA"),
			new Estado(11,"Minas Gerais","MG"),new Estado(12,"Mato Grosso do Sul","MS"),
			new Estado(13,"Mato Grosso","MT"),new Estado(14,"Pará","PA"),new Estado(15,"Paraíba","PB"),
			new Estado(16,"Pernanbuco","PE"),new Estado(17,"Piauí","PI"),new Estado(18,"Paraná","PR"),
			new Estado(19,"Rio de Janeiro","RJ"),new Estado(20,"Rio Grande do Norte","RN"),
			new Estado(21,"Rondônia","RO"),new Estado(22,"Rorâima","RR"),new Estado(23,"Rio Grando do Sul","RS"),
			new Estado(24,"Santa Catarina","SC"),new Estado(25,"Sergipe","SE"),new Estado(26,"São Paulo","SP"),
			new Estado(27,"Tocantins","TO")];
		
		public static var tipoTelefones:Array =  [new TipoTelefone(1,"Comercial"),
			new TipoTelefone(2,"Residencial"),new TipoTelefone(3,"Celular"),new TipoTelefone(4,"Principal"),
			new TipoTelefone(5,"FAX Comercial"),new TipoTelefone(6,"FAX Residencial")];
			
		
		public static var TIPO_PESSOA_FISICA:TipoPessoa  = new TipoPessoa(1,"Física");
		public static var TIPO_PESSOA_JURIDICA:TipoPessoa  = new TipoPessoa(2,"Jurídica");

		public static var TIPO_EMBALAGEM_FONTE:TipoEmbalagem  = new TipoEmbalagem(1,"Fonte");
		public static var TIPO_EMBALAGEM_BASTIDOR:TipoEmbalagem  = new TipoEmbalagem(2,"Bastidor");
		public static var TIPO_EMBALAGEM_METER:TipoEmbalagem  = new TipoEmbalagem(3,"Meter");
		public static var TIPO_EMBALAGEM_3AL1:TipoEmbalagem  = new TipoEmbalagem(4,"3 AL Nº 01 - Maleta");
		public static var TIPO_EMBALAGEM_3AL2:TipoEmbalagem  = new TipoEmbalagem(5,"3 AL Nº 02 - Maleta");
		public static var TIPO_EMBALAGEM_ALCATEL:TipoEmbalagem  = new TipoEmbalagem(6,"Alcatel Bete");
		public static var TIPO_EMBALAGEM_AVAYA01:TipoEmbalagem  = new TipoEmbalagem(7,"Avaya Nº 01");
		public static var TIPO_EMBALAGEM_AVAYA02:TipoEmbalagem  = new TipoEmbalagem(8,"Avaya Nº 02");
		public static var TIPO_EMBALAGEM_AVAYA03:TipoEmbalagem  = new TipoEmbalagem(9,"Avaya Nº 03");
		public static var TIPO_EMBALAGEM_AVAYA04:TipoEmbalagem  = new TipoEmbalagem(10,"Avaya Nº 04");
		public static var TIPO_EMBALAGEM_AVAYA05:TipoEmbalagem  = new TipoEmbalagem(11,"Avaya Nº 05");
		public static var TIPO_EMBALAGEM_AVAYA06:TipoEmbalagem  = new TipoEmbalagem(12,"Avaya Nº 06");
		public static var TIPO_EMBALAGEM_AVAYA07:TipoEmbalagem  = new TipoEmbalagem(13,"Avaya Nº 07");
		public static var TIPO_EMBALAGEM_AVAYA08:TipoEmbalagem  = new TipoEmbalagem(14,"Avaya Nº 08");
		public static var TIPO_EMBALAGEM_AVAYA08B:TipoEmbalagem  = new TipoEmbalagem(15,"Avaya Nº 08B");
		public static var TIPO_EMBALAGEM_AVAYA09:TipoEmbalagem  = new TipoEmbalagem(16,"Avaya Nº 09");
		public static var TIPO_EMBALAGEM_AVAYA10:TipoEmbalagem  = new TipoEmbalagem(17,"Avaya Nº 10");
		public static var TIPO_EMBALAGEM_AVAYA11:TipoEmbalagem  = new TipoEmbalagem(18,"Avaya Nº 11");
		public static var TIPO_EMBALAGEM_AVAYA12:TipoEmbalagem  = new TipoEmbalagem(19,"Avaya Nº 12");
		public static var TIPO_EMBALAGEM_C1:TipoEmbalagem  = new TipoEmbalagem(20,"C1");
		public static var TIPO_EMBALAGEM_CM:TipoEmbalagem  = new TipoEmbalagem(21,"CM");
		public static var TIPO_EMBALAGEM_FONTE2:TipoEmbalagem  = new TipoEmbalagem(22,"Fonte2 (Nova)");
		public static var TIPO_EMBALAGEM_FONTE480:TipoEmbalagem  = new TipoEmbalagem(23,"Fonte (480x400x280)");
		public static var TIPO_EMBALAGEM_IPAVAYA:TipoEmbalagem  = new TipoEmbalagem(24,"IP Avaya");
		public static var TIPO_EMBALAGEM_MONITOR:TipoEmbalagem  = new TipoEmbalagem(25,"Monitor Serviços");
		public static var TIPO_EMBALAGEM_POWERMETER:TipoEmbalagem  = new TipoEmbalagem(26,"Power Meter");
		public static var TIPO_EMBALAGEM_S12:TipoEmbalagem  = new TipoEmbalagem(27,"S.12");
		public static var TIPO_EMBALAGEM_SUBBSTIDOR:TipoEmbalagem  = new TipoEmbalagem(28,"Sub Bastidor");
		public static var TIPO_EMBALAGEM_TRO:TipoEmbalagem  = new TipoEmbalagem(29,"TRO");
		public static var TIPO_EMBALAGEM_TROF:TipoEmbalagem  = new TipoEmbalagem(30,"TROF");
		public static var TIPO_EMBALAGEM_UCP:TipoEmbalagem  = new TipoEmbalagem(31,"UCP");
		public static var TIPO_EMBALAGEM_CAIXA_AVULSA:TipoEmbalagem  = new TipoEmbalagem(31,"Caixa avulsa");

		
		public static var ANALISE_REPARO_COM_CONDICAO:CondicaoReparo  = new CondicaoReparo(1,"Com condição de reparo");
		public static var ANALISE_REPARO_SEM_CONDICAO:CondicaoReparo  = new CondicaoReparo(2,"Sem condição de reparo");
		public static var ANALISE_REPARO_DEVOLUCAO_SEM_REPARO:CondicaoReparo  = new CondicaoReparo(3,"Devolução sem reparo");

		
		
		public static var TIPO_CLIENTE:Tipo  = new Tipo(1,"Cliente");
		public static var TIPO_FORNECEDOR:Tipo = new Tipo(2,"Fornecedor");
		public static var TIPO_PRESTADOR_SERVICO:Tipo = new Tipo(4,"Prestador Serviço");
		public static var TIPO_TRANSPORTADORA:Tipo = new Tipo(3,"Transportadora");
		
		public static var ESTADO_ACRE:Estado  = new Estado(1,"Acre","AC");
		public static var ESTADO_ALAGOAS:Estado  = new Estado(2,"Alagoas","AL");
		public static var ESTADO_AMAZONAS:Estado  = new Estado(3,"Amazonas","AM");
		public static var ESTADO_AMAPA:Estado  = new Estado(4,"Amapá","AP");
		public static var ESTADO_BAHIA:Estado  = new Estado(5,"Bahia","BA");
		public static var ESTADO_CEARA:Estado  = new Estado(6,"Ceará","CE");
		public static var ESTADO_DISTRITO_FEDERAL:Estado  = new Estado(7,"Distrito Federal","DF");
		public static var ESTADO_ESPIRITO_SANTO:Estado  = new Estado(8,"Espírito Santo","ES");
		public static var ESTADO_GOIAS:Estado  = new Estado(9,"Goiás","GO");
		public static var ESTADO_MARANHAO:Estado  = new Estado(10,"Maranhão","MA");
		public static var ESTADO_MINAS_GERAIS:Estado  = new Estado(11,"Minas Gerais","MG");
		public static var ESTADO_MATO_GROSSO_DO_SUL:Estado  = new Estado(12,"Mato Grosso do Sul","MS");
		public static var ESTADO_MATO_GROSSO:Estado  = new Estado(13,"Mato Grosso","MT");
		public static var ESTADO_PARA:Estado  = new Estado(14,"Pará","PA");
		public static var ESTADO_PARAIBA:Estado  = new Estado(15,"Paraíba","PB");
		public static var ESTADO_PERNANBUCO:Estado  = new Estado(16,"Pernanbuco","PE");
		public static var ESTADO_PIAUI:Estado  = new Estado(17,"Piauí","PI");
		public static var ESTADO_PARANA:Estado  = new Estado(18,"Paraná","PR");
		public static var ESTADO_RIO_DE_JANEIRO:Estado  = new Estado(19,"Rio de Janeiro","RJ");
		public static var ESTADO_RIO_GRANDE_DO_NORTE:Estado  = new Estado(20,"Rio Grande do Norte","RN");
		public static var ESTADO_RONDONIA:Estado  = new Estado(21,"Rondônia","RO");
		public static var ESTADO_RORAIMA:Estado  = new Estado(22,"Rorâima","RR");
		public static var ESTADO_RIO_GRANDE_DO_SUL:Estado  = new Estado(23,"Rio Grando do Sul","RS");
		public static var ESTADO_SANTA_CATARINA:Estado  = new Estado(24,"Santa Catarina","SC");
		public static var ESTADO_SERGIPE:Estado  = new Estado(25,"Sergipe","SE");
		public static var ESTADO_SAO_PAULO:Estado  = new Estado(26,"São Paulo","SP");
		public static var ESTADO_TOCANTINS:Estado  = new Estado(27,"Tocantins","TO");
		
		//List to combos
		public static var tipoList:ArrayCollection=new ArrayCollection;
		public static var tipoPessoaList:ArrayCollection=new ArrayCollection;
		public static var tipoNotaFiscalList:ArrayCollection=new ArrayCollection;
		public static var meses:ArrayCollection=new ArrayCollection;
		public static var estadoList:ArrayCollection=new ArrayCollection;
		public static var analiseReparoList:ArrayCollection=new ArrayCollection;
		public static var tipoEmbalagemList:ArrayCollection=new ArrayCollection;

		
		{
			tipoPessoaList.addItem(TIPO_PESSOA_FISICA);
			tipoPessoaList.addItem(TIPO_PESSOA_JURIDICA);

			tipoList.addItem(TIPO_CLIENTE);
			tipoList.addItem(TIPO_FORNECEDOR);
			tipoList.addItem(TIPO_PRESTADOR_SERVICO);
			tipoList.addItem(TIPO_TRANSPORTADORA);
			
			tipoNotaFiscalList.addItem(new TipoNotaFiscal(1,"Normal"));
			tipoNotaFiscalList.addItem(new TipoNotaFiscal(2,"Estoque"));
			tipoNotaFiscalList.addItem(new TipoNotaFiscal(3,"Substituição"));
			tipoNotaFiscalList.addItem(new TipoNotaFiscal(4,"Padrão"));
			tipoNotaFiscalList.addItem(new TipoNotaFiscal(5,"Avaya"));
			
			estadoList.addItem(ESTADO_ACRE);
			estadoList.addItem(ESTADO_ALAGOAS);
			estadoList.addItem(ESTADO_AMAZONAS);
			estadoList.addItem(ESTADO_AMAPA);
			estadoList.addItem(ESTADO_BAHIA);
			estadoList.addItem(ESTADO_CEARA);
			
			analiseReparoList.addItem(ANALISE_REPARO_COM_CONDICAO);
			analiseReparoList.addItem(ANALISE_REPARO_SEM_CONDICAO);
			analiseReparoList.addItem(ANALISE_REPARO_DEVOLUCAO_SEM_REPARO);
			
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_3AL1);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_3AL2);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_ALCATEL);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA01);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA02);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA03);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA04);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA05);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA06);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA07);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA08);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA08B);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA09);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA10);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA11);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_AVAYA12);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_BASTIDOR);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_C1);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_CAIXA_AVULSA);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_CM);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_FONTE);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_FONTE2);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_FONTE480);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_IPAVAYA);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_METER);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_MONITOR);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_POWERMETER);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_S12);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_SUBBSTIDOR);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_TRO);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_TROF);
			tipoEmbalagemList.addItem(TIPO_EMBALAGEM_UCP);
			
		}
		
		public static function getKeyAsString(key:String):String{
			return ResourceManager.getInstance().getString('ApplicationResource',key);
		}
		
		
		public static function isDateDDMMYYYYValid(value:String, minDate:Date, maxDate:Date):Boolean {
			
			const DDMMYYYY : String = "DDMMYYYY";
			
			if (value.length != DDMMYYYY.length) {
				trace ("IsDateDDMMYYYYValid wrong date length");
				return false;
			}
			
			var day:String = value.substr(0,2);
			var month:String = value.substr(2,2);
			var year:String	= value.substr(4,4);
			var date:Date;
			
			date = new Date(parseInt(year), parseInt(month)-1, parseInt(day));
			
			if (minDate != null) {
				
				minDate = new Date(minDate.fullYear,minDate.month, minDate.date);
				
				if (date < minDate) {
					trace ("IsDateDDMMYYYYValid invalid, date < minDate");
					return false;
				}
			}
			
			if (maxDate != null) {
				
				maxDate = new Date(maxDate.fullYear,maxDate.month, maxDate.date);
				
				if (date > maxDate) {
					trace ("IsDateDDMMYYYYValid invalid, date > minDate");
					return false
				};
			}
			
			return true;
			
		}

		
		
		public static function formatDateUsing(ev:KeyboardEvent, mask:String,
									  rxp:String):void{
			var switcher:SwitchSymbolFormatter = new
				SwitchSymbolFormatter('#');
			var pattern:RegExp = new RegExp(rxp,"gi");
			var input:TextInput = (ev.currentTarget as TextInput);
			var toFormat:String = input.text;
			var maskAr:Array = mask.match(new RegExp("[#]","gi"));
			input.text = toFormat.replace(pattern,"");
			if (input.text.length >= maskAr.length){
				input.text = switcher.formatValue(mask,input.text);
				
				input.selectRange(input.text.length,input.text.length+1);
			}
		} 
		
		public static function formatarMoeda(num:Number):String
		{
			var str:String = num.toString();
			var centsSeparator:String = ',';
			var thousandsSeparator:String = '.';
			var char:String="";
			var prefix:String="R$";
			var thousandsFormatted:String="";
			var isNumber:* = /[0-9]/;
			var formatted:String="";
			var charBol:Boolean = true;
			
			for(var i:int; i<str.length;i++){
				char = str.substr(i,1);
				if(formatted.length==0 && char == '0') charBol = false;
				if(charBol && char.match(isNumber)) formatted = formatted +char;
				charBol = true;
			}
			
			while (formatted.length<3) formatted = '0'+formatted;
			
			var centsVal:String;
			if(num.toString().indexOf(".") != -1){
				centsVal = formatted.substr(formatted.length-2,2);
			}else{
				centsVal = "00";
			}
			
			var integerVal:String
			if(num.toString().indexOf(".") != -1){
				integerVal = formatted.substr(0,formatted.length-2);
			}else{
				integerVal = formatted;
			}
			
			// apply cents pontuation
			formatted = integerVal+centsSeparator+centsVal;	
			
			if (thousandsSeparator) {
				var thousandsCount:Number = 0;
				for (var j:int=integerVal.length;j>0;j--) {
					char = integerVal.substr(j-1,1);
					thousandsCount++;
					if (thousandsCount%3==0) char = thousandsSeparator+char;
					thousandsFormatted = char+thousandsFormatted;
				}
				if (thousandsFormatted.substr(0,1)==thousandsSeparator) thousandsFormatted = thousandsFormatted.substring(1,thousandsFormatted.length);
				formatted = thousandsFormatted+centsSeparator+centsVal;
			}
			
			
			// apply the prefix
			if (prefix) formatted = prefix+formatted;
			
			return formatted;
		}
		
		
		public static function formatarMoeda4(num:Number):String
		{
			var str:String = num.toString();
			var centsSeparator:String = ',';
			var thousandsSeparator:String = '.';
			var char:String="";
			var prefix:String="R$";
			var thousandsFormatted:String="";
			var isNumber:* = /[0-9]/;
			var formatted:String="";
			var charBol:Boolean = true;
			
			str = str.replace("R$","");
			str = str.replace(" ","");
			str = str.replace(".",",");
//			var myPattern:RegExp = /\./g;
//			str = str.replace(myPattern,"");
						
			var inteiro:String = "";
			var inteiroFormatado:String = "";
			//Pega os centavos se esses existirem
			var centavos:String = "";
			var centavosFormatado:String = "";
			if(str !=null && str.length > 0){
				var indexVirgula:int = str.lastIndexOf(",");
				//possui virgula?
				if(indexVirgula != -1){
					centavos = str.substring(indexVirgula+1);
					inteiro = str.substring(0,indexVirgula);
				}else{
					inteiro = str;
				}
			}
			
			//Formata os centavos
			if(centavos.length > 0){
				if(centavos.length == 1){
					centavosFormatado = centavos.charAt(0)+"000";
				}else if(centavos.length == 2){
					centavosFormatado = centavos.charAt(0)+centavos.charAt(1)+"00";
				}else if(centavos.length == 3){
					centavosFormatado = centavos.charAt(0)+centavos.charAt(1)+centavos.charAt(2)+"0";
				}else if(centavos.length > 4){
					var char:String = centavos.charAt(4);
					var numero:int = parseInt(char);
					if(numero >= 5){
						var char1:String = centavos.charAt(3)
						var numero1:int = parseInt(char1);
						centavosFormatado = centavos.charAt(0)+centavos.charAt(1)+centavos.charAt(2) + (numero1+1).toString(); 
					}else{
						centavosFormatado = centavos.charAt(0)+centavos.charAt(1)+centavos.charAt(2)+centavos.charAt(3);
					}
				}else{
					centavosFormatado = centavos;
				}
			}else{
				centavosFormatado = "0000";
			}
			
			//Formatar valor inteiro
			var separador:int = 0;
			var char2:String = "";
			for(var i:int=inteiro.length-1;i!=-1;i--){
				char2 = inteiro.charAt(i);
				inteiroFormatado = char2 + inteiroFormatado;
				separador++;
				
				if(separador%3 == 0 && i != 0){
					inteiroFormatado = "." + inteiroFormatado;
				}
			}
			
			formatted = inteiroFormatado + "," + centavosFormatado;
			
			return formatted;
		}
		
		public static function converterReparoToOrcRep(reparo:Reparo):OrcRepGenericoTO
		{
			var orcRep:OrcRepGenericoTO = new OrcRepGenericoTO();
			orcRep.dataEntradaReparo = reparo.dataEntrada;
			orcRep.dataPrioridade = reparo.prioridade;
			orcRep.idOrcRep = reparo.id;
			orcRep.dataChegadaServilogi = reparo.ordemServico.notaFiscal.dataChegada;
			orcRep.dataLimite = reparo.dataLimite;
			orcRep.finalidade = "Reparo";
			if(reparo.tecnicoResponsavel != null){
				orcRep.idTecnicoResponsavel = reparo.tecnicoResponsavel.id;
				orcRep.tecnico  = reparo.tecnicoResponsavel.usuario;
			}
			orcRep.laboratorio = reparo.ordemServico.unidade.laboratorio.nome;
			orcRep.idLaboratorio = reparo.ordemServico.unidade.laboratorio.id;

			orcRep.laudoTecnicoAprovado = reparo.laudoTecnicoAprovado;
			orcRep.laudoTecnicoReprovado = reparo.laudoTecnicoReprovado;
			orcRep.numeroNotaFiscal = reparo.ordemServico.notaFiscal.numero;
			orcRep.numeroOrdemServico = reparo.ordemServico.numeroOrdemServico;
			if(reparo.ordemServico.unidadePai != null){
				orcRep.numeroOrdemServicoPai = reparo.ordemServico.unidadePai.numeroOrdemServico;
			}
			orcRep.numeroSerieCliente = reparo.ordemServico.serieCliente;
			orcRep.numeroSerieFabricante = reparo.ordemServico.serieFabricante;
			orcRep.orcamentoDiferenciadoRejeitado = reparo.orcamentoDiferenciadoRejeitado;
			orcRep.prazoDevolucao = reparo.ordemServico.cliente.prazoDevolucao;
			orcRep.propostaAprovada = reparo.propostaAprovada;
			orcRep.propostaReprovada = reparo.propostaReprovada;
			orcRep.statusString = reparo.statusString;
			
			orcRep.cliente = reparo.ordemServico.cliente.nomeSistema;
			orcRep.prazoDevolucao = reparo.ordemServico.cliente.prazoDevolucao;
			orcRep.bloqueado = reparo.ordemServico.bloqueado;
			
			orcRep.unidade = reparo.ordemServico.unidade.nome;
			orcRep.componentePendente = reparo.componentePendente;
			orcRep.criadoFromOrcamento = reparo.criadoFromOrcamento;
			return orcRep;
		}
		
		public static function converterOrcamentoToOrcRep(orcamento:Orcamento):OrcRepGenericoTO
		{
			var orcRep:OrcRepGenericoTO = new OrcRepGenericoTO();
			orcRep.dataEntradaReparo = orcamento.dataEntrada;
			orcRep.dataPrioridade = orcamento.prioridade;
			orcRep.idOrcRep = orcamento.id;
			orcRep.dataChegadaServilogi = orcamento.ordemServico.notaFiscal.dataChegada;
			orcRep.dataLimite = orcamento.dataLimite;
			orcRep.finalidade = "Orçamento";
			if(orcamento.tecnicoResponsavel != null){
				orcRep.idTecnicoResponsavel = orcamento.tecnicoResponsavel.id;
				orcRep.tecnico = orcamento.tecnicoResponsavel.usuario;
			}
			orcRep.laboratorio  = orcamento.ordemServico.unidade.laboratorio.nome;
			orcRep.idLaboratorio = orcamento.ordemServico.unidade.laboratorio.id;
			
			orcRep.laudoTecnicoAprovado = orcamento.laudoTecnicoAprovado;
			orcRep.laudoTecnicoReprovado = orcamento.laudoTecnicoReprovado;
			orcRep.numeroNotaFiscal = orcamento.ordemServico.notaFiscal.numero;
			orcRep.numeroOrdemServico = orcamento.ordemServico.numeroOrdemServico;
			if(orcamento.ordemServico.unidadePai != null){
				orcRep.numeroOrdemServicoPai = orcamento.ordemServico.unidadePai.numeroOrdemServico;
			}
			orcRep.numeroSerieCliente = orcamento.ordemServico.serieCliente;
			orcRep.numeroSerieFabricante = orcamento.ordemServico.serieFabricante;
			orcRep.prazoDevolucao = orcamento.ordemServico.cliente.prazoDevolucao;
			orcRep.propostaReprovada = orcamento.propostaReprovada;
			orcRep.rejeitadoPeloLider = orcamento.rejeitadoPeloLider;
			orcRep.statusString = orcamento.statusString;
			
			orcRep.cliente = orcamento.ordemServico.cliente.nomeSistema;
			orcRep.prazoDevolucao = orcamento.ordemServico.cliente.prazoDevolucao;
			orcRep.bloqueado = orcamento.ordemServico.bloqueado;
			orcRep.unidade = orcamento.ordemServico.unidade.nome;
			orcRep.componentePendente = orcamento.componentePendente;
			return orcRep;
		}

		
				
		public static function clearAllData(container:Container):void{
			for each(var baseComp:UIComponentDescriptor in container.childDescriptors){
				if (baseComp.id != null){
					if (baseComp.document[baseComp.id] is FormItem){
						search2Level(baseComp.document[baseComp.id]);
					}else{
						clearData(baseComp.document[baseComp.id]);
					}
				}
			}
		}		
		
		private static function search2Level(baseComp:*):void{
			for each(var secComp:UIComponentDescriptor in (baseComp.document[baseComp.id] as FormItem).childDescriptors){
				if (((secComp.type == HBox) || (secComp.type == VBox)) && (secComp.id != null)){
					search3Level(baseComp.document[baseComp.id], secComp.document[secComp.id]);
				}else{
					if (secComp.id != null){ 
						clearData(baseComp.document[baseComp.id].document[secComp.id]);
					}
				}
			}
		}
		
		private static function search3Level(baseComp:*, secComp:*):void{
			for each(var trComp:UIComponentDescriptor in (secComp.document[secComp.id] as HBox).childDescriptors){	
				if (trComp.id != null){
					clearData(baseComp.document[baseComp.id].document[secComp.id].document[trComp.id]);
				}			
			}				
		}
		
		public static function somarDias(data:Date, dias:int):Date{
			if(data != null){
			var dataDesejada:Date = new Date( data.getTime() + ( dias * millisecondsPerDay ));
			return dataDesejada;
			}
			return new Date();
		}
		
		public static function subtrairDias(data:Date, dias:int):Date{
			var dataDesejada:Date = new Date( data.getTime() - ( dias * millisecondsPerDay ));
			return dataDesejada;
		}
		
		public static function formatarDataDDMMYYYY(data:Date):String{
			
			var format:DateFormatter = new DateFormatter();
			format.formatString = "DD/MM/YYYY";
			return format.format(data);
			
		}
		
		public static function formatarDataDDMMYYYYHNNSS(data:Date):String{
			
			var format:DateFormatter = new DateFormatter();
			format.formatString = "DD/MM/YYYY H:NN:SS";
			return format.format(data);
			
		}
		
		public static function generateRandomNumber( start:Number, end:Number ):void
		{
			var randomNum:Number;
			if( end == 1 )
			{
				// Number is between 0 and 1, don't round.
				randomNum = Math.random();
			}
			else{
				// Number is between 0 and 100 or 1000, round the number
				randomNum = Math.round( Math.random() * end );
			}
		}

		
		private static function clearData(comp:*):void{
			if (comp.enabled == true){
				switch(comp.className){
					case "TextInput" :
						(comp as TextInput).text = '';
						break;
					case "TextArea" :
						//(comp as TextArea).text = '';
						//(comp as TextArea).htmlText = '';
						break;					
					case "NumericStepper" :
						(comp as NumericStepper).value = 0;
						break;
					case "RadioButton" :
						(comp as RadioButton).selected = false;
						break;
					case "ComboBox" :
						(comp as ComboBox).selectedIndex = -1;
						break;					
					case "ColorPicker" :
						(comp as ColorPicker).selectedColor = new uint(0xffffff);
						break;					
					case "CheckBox" :
						(comp as CheckBox).selected = false;
						break;								
				}
			}
		}
	}
	
}