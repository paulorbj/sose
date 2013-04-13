package entities.consulta
{
	import entities.administrativo.parceiros.Pessoa;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.consulta.Consulta")]	
	public class Consulta
	{
		
		private var _numeroOS:String;
		private var _numeroNotaFiscal:String;
		private var _numeroNotaFiscalSaida:String;
		private var _cliente:Pessoa;
		private var _dataDe:Date;
		private var _dataAte:Date;
		
		public function get numeroOS():String
		{
			return _numeroOS;
		}

		public function set numeroOS(value:String):void
		{
			_numeroOS = value;
		}

		public function get numeroNotaFiscal():String
		{
			return _numeroNotaFiscal;
		}

		public function set numeroNotaFiscal(value:String):void
		{
			_numeroNotaFiscal = value;
		}

		public function get numeroNotaFiscalSaida():String
		{
			return _numeroNotaFiscalSaida;
		}

		public function set numeroNotaFiscalSaida(value:String):void
		{
			_numeroNotaFiscalSaida = value;
		}

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get dataDe():Date
		{
			return _dataDe;
		}

		public function set dataDe(value:Date):void
		{
			_dataDe = value;
		}

		public function get dataAte():Date
		{
			return _dataAte;
		}

		public function set dataAte(value:Date):void
		{
			_dataAte = value;
		}


	}
}