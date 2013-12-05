package entities.compra
{	
	import entities.administrativo.Componente;
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.compra.ItemCompra")]	
	public class ItemCompra
	{
		private var _id:Number;
		private var _compra:Compra;
		private var _listaPedidoCompra:ArrayCollection;
		private var _qtdTotalRequisitada:Number;
		private var _qtdEsperada:Number;
		private var _qtdComprada:Number;
		private var _qtdPedido:Number;
		private var _tecnicos:String;
		private var _nAmostra:String;
		private var _possuiAmostra:Boolean;
		private var _nNotaFiscal:String;
		private var _valorUnitario:Number;
		private var _valorUnitarioDolar:Number;
		private var _fornecedor:Pessoa;
		private var _componenteEquivalente:Componente;
		private var _componente:Componente;
		private var _dataEntrada:Date;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _status:String;
			
		public function ItemCompra(){
			_id = 0;
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get listaPedidoCompra():ArrayCollection
		{
			return _listaPedidoCompra;
		}

		public function set listaPedidoCompra(value:ArrayCollection):void
		{
			_listaPedidoCompra = value;
		}

		public function get qtdTotalRequisitada():Number
		{
			return _qtdTotalRequisitada;
		}

		public function set qtdTotalRequisitada(value:Number):void
		{
			_qtdTotalRequisitada = value;
		}

		public function get qtdEsperada():Number
		{
			return _qtdEsperada;
		}

		public function set qtdEsperada(value:Number):void
		{
			_qtdEsperada = value;
		}

		public function get qtdComprada():Number
		{
			return _qtdComprada;
		}

		public function set qtdComprada(value:Number):void
		{
			_qtdComprada = value;
		}

		public function get nNotaFiscal():String
		{
			return _nNotaFiscal;
		}

		public function set nNotaFiscal(value:String):void
		{
			_nNotaFiscal = value;
		}

		public function get valorUnitario():Number
		{
			return _valorUnitario;
		}

		public function set valorUnitario(value:Number):void
		{
			_valorUnitario = value;
		}

		public function get fornecedor():Pessoa
		{
			return _fornecedor;
		}

		public function set fornecedor(value:Pessoa):void
		{
			_fornecedor = value;
		}

		public function get componenteEquivalente():Componente
		{
			return _componenteEquivalente;
		}

		public function set componenteEquivalente(value:Componente):void
		{
			_componenteEquivalente = value;
		}

		public function get dataEntrada():Date
		{
			return _dataEntrada;
		}

		public function set dataEntrada(value:Date):void
		{
			_dataEntrada = value;
		}
		
		public function get dataEntradaString():String
		{
			if(_dataEntrada)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEntrada);
			else{
				return "";
			}
		}

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}

		public function get compra():Compra
		{
			return _compra;
		}

		public function set compra(value:Compra):void
		{
			_compra = value;
		}

		public function get qtdPedido():Number
		{
			return _qtdPedido;
		}

		public function set qtdPedido(value:Number):void
		{
			_qtdPedido = value;
		}

		public function get tecnicos():String
		{
			_tecnicos = "";
			for each(var pc:PedidoCompra in _listaPedidoCompra){
				if(pc.tecnico != null) {
					_tecnicos = _tecnicos + ";" + pc.tecnico.usuario;	
				}				
			}
			if(_tecnicos.length > 0) return _tecnicos.substring(1)
			return "";
		}

		public function set tecnicos(value:String):void
		{
			_tecnicos = value;
		}

		public function get nAmostra():String
		{
			_nAmostra = "";
			for each(var pc:PedidoCompra in _listaPedidoCompra){
				_nAmostra = _nAmostra + ";" + pc.id;
			}
			if(_nAmostra.length > 0) return _nAmostra.substring(1)
			return "";
		}

		public function set nAmostra(value:String):void
		{
			_nAmostra = value;
		}

		public function get possuiAmostra():Boolean
		{
			return _possuiAmostra;
		}

		public function set possuiAmostra(value:Boolean):void
		{
			_possuiAmostra = value;
		}

		public function get isSelected():Boolean
		{
			return _isSelected;
		}

		public function set isSelected(value:Boolean):void
		{
			_isSelected = value;
		}

		public function get isEnabled():Boolean
		{
			return _isEnabled;
		}

		public function set isEnabled(value:Boolean):void
		{
			_isEnabled = value;
		}

		public function get status():String
		{
			return _status;
		}

		public function set status(value:String):void
		{
			_status = value;
		}

		public function get valorUnitarioDolar():Number
		{
			return _valorUnitarioDolar;
		}

		public function set valorUnitarioDolar(value:Number):void
		{
			_valorUnitarioDolar = value;
		}

	
	}
}