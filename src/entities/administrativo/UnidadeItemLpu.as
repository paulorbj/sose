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
		private var _valor:Number;
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

		public function get valor():Number
		{
			return _valor;
		}

		public function set valor(value:Number):void
		{
			_valor = value;
		}

		public function get listaItemLpu():ArrayCollection
		{
			return _listaItemLpu;
		}

		public function set listaItemLpu(value:ArrayCollection):void
		{
			_listaItemLpu = value;
		}


	}
}