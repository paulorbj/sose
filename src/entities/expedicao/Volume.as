package entities.expedicao
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.expedicao.Volume")]	
	public class Volume
	{
		
		private var _id:Number;
		private var _tipoEmbalagem:String;
		private var _totalItens:Number;
		private var _pesoBruto:Number;
		private var _notaFiscalSaida:NotaFiscalRemessa;

		public function Volume(){
			_totalItens = 0;
			_pesoBruto = 0;
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get tipoEmbalagem():String
		{
			return _tipoEmbalagem;
		}

		public function set tipoEmbalagem(value:String):void
		{
			_tipoEmbalagem = value;
		}

		public function get totalItens():Number
		{
			return _totalItens;
		}

		public function set totalItens(value:Number):void
		{
			_totalItens = value;
		}

		public function get pesoBruto():Number
		{
			return _pesoBruto;
		}

		public function set pesoBruto(value:Number):void
		{
			_pesoBruto = value;
		}

		public function get notaFiscalSaida():NotaFiscalRemessa
		{
			return _notaFiscalSaida;
		}

		public function set notaFiscalSaida(value:NotaFiscalRemessa):void
		{
			_notaFiscalSaida = value;
		}
		
	
	}
}