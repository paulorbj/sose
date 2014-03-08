package entities.administrativo
{
	import mx.collections.ArrayCollection;

	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.lpu.UnidadeItemLpu")]	
	public class UnidadeItemLpu
	{
		
		private var _id:Number;
		private var _lpu:Lpu;
		private var _unidadeServilogi:Unidade;
		private var _valorReparo:Number;
		private var _valorMinimo:Number;
		private var _valorMaximo:Number;
		private var _valorTeste:Number;
		private var _listaItemLpu:ArrayCollection;
		
		public function UnidadeItemLpu()
		{
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

		public function get unidadeServilogi():Unidade
		{
			return _unidadeServilogi;
		}

		public function set unidadeServilogi(value:Unidade):void
		{
			_unidadeServilogi = value;
		}

		public function get listaItemLpu():ArrayCollection
		{
			return _listaItemLpu;
		}

		public function set listaItemLpu(value:ArrayCollection):void
		{
			_listaItemLpu = value;
		}

		public function get valorReparo():Number
		{
			return _valorReparo;
		}

		public function set valorReparo(value:Number):void
		{
			_valorReparo = value;
		}

		public function get valorMinimo():Number
		{
			return _valorMinimo;
		}

		public function set valorMinimo(value:Number):void
		{
			_valorMinimo = value;
		}

		public function get valorMaximo():Number
		{
			return _valorMaximo;
		}

		public function set valorMaximo(value:Number):void
		{
			_valorMaximo = value;
		}

		public function get valorTeste():Number
		{
			return _valorTeste;
		}

		public function set valorTeste(value:Number):void
		{
			_valorTeste = value;
		}


	}
}