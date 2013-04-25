package entities.administrativo
{
	import entities.expedicao.NotaFiscalRemessa;
	import entities.faturamento.Faturamento;
	import entities.laudoTecnico.LaudoTecnico;
	import entities.orcamentoreparo.Orcamento;
	import entities.orcamentoreparo.Reparo;
	import entities.proposta.ItemProposta;
	import entities.proposta.Proposta;
	import entities.recebimento.NotaFiscal;
	import entities.recebimento.OrdemServico;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Observacao")]	
	public class Observacao
	{
		
		private var _id:Number;
		private var _notaFiscal:NotaFiscal;
		private var _ordemServico:OrdemServico;
		private var _proposta:Proposta;
		private var _itemProposta:ItemProposta;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		private var _laudoTecnico:LaudoTecnico;
		private var _notaFiscalSaida:NotaFiscalRemessa;
		private var _faturamento:Faturamento;
		private var _data:Date;
		private var _usuario:Usuario;
		private var _origem:String;
		private var _texto:String;
		private var _relevancia:Number;
		private var _escopo:Number;
		private var _sigiloso:Boolean;
		
		public function Observacao(){
			_id = 0;
			_texto = "";
			_origem = "";
			_relevancia = 0;
			_escopo = 0;
			_sigiloso = false;
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get notaFiscal():NotaFiscal
		{
			return _notaFiscal;
		}

		public function set notaFiscal(value:NotaFiscal):void
		{
			_notaFiscal = value;
		}

		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}

		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}

		public function get data():Date
		{
			return _data;
		}
		
		public function get dataString():String
		{
			if(_data){
				return ScreenUtils.formatarDataDDMMYYYYHNNSS(_data);
			}else{
				return "";
			}
			return _data;
		}

		public function set data(value:Date):void
		{
			_data = value;
		}

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
		}

		public function get origem():String
		{
			return _origem;
		}

		public function set origem(value:String):void
		{
			_origem = value;
		}

		public function get texto():String
		{
			return _texto;
		}

		public function set texto(value:String):void
		{
			_texto = value;
		}

		public function get relevancia():Number
		{
			return _relevancia;
		}

		public function set relevancia(value:Number):void
		{
			_relevancia = value;
		}

		public function get escopo():Number
		{
			return _escopo;
		}

		public function set escopo(value:Number):void
		{
			_escopo = value;
		}

		public function get proposta():Proposta
		{
			return _proposta;
		}

		public function set proposta(value:Proposta):void
		{
			_proposta = value;
		}

		public function get reparo():Reparo
		{
			return _reparo;
		}

		public function set reparo(value:Reparo):void
		{
			_reparo = value;
		}

		public function get orcamento():Orcamento
		{
			return _orcamento;
		}

		public function set orcamento(value:Orcamento):void
		{
			_orcamento = value;
		}

		public function get laudoTecnico():LaudoTecnico
		{
			return _laudoTecnico;
		}

		public function set laudoTecnico(value:LaudoTecnico):void
		{
			_laudoTecnico = value;
		}

		public function get sigiloso():Boolean
		{
			return _sigiloso;
		}

		public function set sigiloso(value:Boolean):void
		{
			_sigiloso = value;
		}

		public function get itemProposta():ItemProposta
		{
			return _itemProposta;
		}

		public function set itemProposta(value:ItemProposta):void
		{
			_itemProposta = value;
		}

		public function get notaFiscalSaida():NotaFiscalRemessa
		{
			return _notaFiscalSaida;
		}

		public function set notaFiscalSaida(value:NotaFiscalRemessa):void
		{
			_notaFiscalSaida = value;
		}

		public function get faturamento():Faturamento
		{
			return _faturamento;
		}

		public function set faturamento(value:Faturamento):void
		{
			_faturamento = value;
		}
		
	
	}
}