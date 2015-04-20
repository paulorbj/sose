package entities.grafico
{
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.grafico.RetornoGraficoPizza")]	
	public class RetornoGraficoPizza
	{
		private var _categoria:String;
		private var _valor:Number;

		public function RetornoGraficoPizza()
		{
		}

		public function get categoria():String
		{
			return _categoria;
		}

		public function set categoria(value:String):void
		{
			_categoria = value;
		}

		public function get valor():Number
		{
			return _valor;
		}

		public function set valor(value:Number):void
		{
			_valor = value;
		}


	}
}