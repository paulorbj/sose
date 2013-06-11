package entities.administrativo
{
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Lpu")]	
	public class Lpu
	{
		
		private var _id:Number;
		
		private var _lpu:Lpu;
		
		private var _unidade:String;
		private var _equipamento:String;
		private var _fabricante:String;
		private var _codigo1:String;
		private var _codigo2:String;	
		private var _valor:Number;
		
		private var _unidadeServilogi:Unidade;
		
		public function Lpu(){
		}
	
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get lpu():Lpu
		{
			return _lpu;
		}

		public function set lpu(value:Lpu):void
		{
			_lpu = value;
		}

		public function get unidade():String
		{
			return _unidade;
		}

		public function set unidade(value:String):void
		{
			_unidade = value;
		}

		public function get equipamento():String
		{
			return _equipamento;
		}

		public function set equipamento(value:String):void
		{
			_equipamento = value;
		}

		public function get fabricante():String
		{
			return _fabricante;
		}

		public function set fabricante(value:String):void
		{
			_fabricante = value;
		}

		public function get codigo1():String
		{
			return _codigo1;
		}

		public function set codigo1(value:String):void
		{
			_codigo1 = value;
		}

		public function get codigo2():String
		{
			return _codigo2;
		}

		public function set codigo2(value:String):void
		{
			_codigo2 = value;
		}

		public function get valor():Number
		{
			return _valor;
		}

		public function set valor(value:Number):void
		{
			_valor = value;
		}

		public function get unidadeServilogi():Unidade
		{
			return _unidadeServilogi;
		}

		public function set unidadeServilogi(value:Unidade):void
		{
			_unidadeServilogi = value;
		}


	}
}