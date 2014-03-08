package entities.recebimento
{
	import entities.administrativo.ItemLpu;
	import entities.administrativo.Lpu;
	import entities.administrativo.Unidade;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.recebimento.ItemNotaFiscal")]	
	public class ItemNotaFiscal
	{
		private var _id:Number;	
		private var _idTemp:Number;
		private var _nome:String;
		private var _codigo:String;	
		private var _quantidade:Number;
		private var _valorUnitario:Number;
		private var _unidade:Unidade;
		private var _itemLpu:ItemLpu;
		private var _ordensServico:ArrayCollection;
		private var _notaFiscal:NotaFiscal;
		private var _ncm:String;
		private var _cst:String;
		private var _cfop:String;
		private var _unidadeMedida:String;
		private var _unidadeString:String;
		private var _lpuString:String;
		private var _ordemNaLista:int;
		
		public function ItemNotaFiscal()
		{
			_id = 0;
			_quantidade = 0;
			_valorUnitario = 0;
			_nome = "";
			_idTemp = new Date().time;
			_ordensServico = new ArrayCollection;
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get quantidade():Number
		{
			return _quantidade;
		}

		public function set quantidade(value:Number):void
		{
			_quantidade = value;
		}

		public function get valorUnitario():Number
		{
			return _valorUnitario;
		}

		public function set valorUnitario(value:Number):void
		{
			_valorUnitario = value;
		}

		public function get unidade():Unidade
		{
			return _unidade;
		}

		public function set unidade(value:Unidade):void
		{
			_unidade = value;
		}

		public function get ordensServico():ArrayCollection
		{
			return _ordensServico;
		}

		public function set ordensServico(value:ArrayCollection):void
		{
			_ordensServico = value;
		}

		public function get notaFiscal():NotaFiscal
		{
			return _notaFiscal;
		}

		public function set notaFiscal(value:NotaFiscal):void
		{
			_notaFiscal = value;
		}

		public function get codigo():String
		{
			return _codigo;
		}

		public function set codigo(value:String):void
		{
			_codigo = value;
		}

		public function get idTemp():Number
		{
			return _idTemp;
		}

		public function set idTemp(value:Number):void
		{
			_idTemp = value;
		}

		public function get ncm():String
		{
			return _ncm;
		}

		public function set ncm(value:String):void
		{
			_ncm = value;
		}

		public function get cst():String
		{
			return _cst;
		}

		public function set cst(value:String):void
		{
			_cst = value;
		}

		public function get cfop():String
		{
			return _cfop;
		}

		public function set cfop(value:String):void
		{
			_cfop = value;
		}

		public function get unidadeMedida():String
		{
			return _unidadeMedida;
		}

		public function set unidadeMedida(value:String):void
		{
			_unidadeMedida = value;
		}

		public function get unidadeString():String
		{
			return _unidadeString;
		}

		public function set unidadeString(value:String):void
		{
			_unidadeString = value;
		}

		public function get lpuString():String
		{
			return _lpuString;
		}

		public function set lpuString(value:String):void
		{
			_lpuString = value;
		}

		public function get ordemNaLista():int
		{
			return _ordemNaLista;
		}

		public function set ordemNaLista(value:int):void
		{
			_ordemNaLista = value;
		}

		public function get itemLpu():ItemLpu
		{
			return _itemLpu;
		}

		public function set itemLpu(value:ItemLpu):void
		{
			_itemLpu = value;
		}


	}
}