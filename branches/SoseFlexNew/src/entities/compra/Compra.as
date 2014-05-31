package entities.compra
{
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.RequisicaoComponente;
	import entities.recebimento.OrdemServico;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.compra.Compra")]	
	public class Compra
	{
		private var _id:Number;
		private var _nome:String;
		private var _listaItemCompra:ArrayCollection;
		private var _statusString:String;
		private var _dataCriacaoCompra:Date;
			
		public function Compra(){
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

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get listaItemCompra():ArrayCollection
		{
			return _listaItemCompra;
		}

		public function set listaItemCompra(value:ArrayCollection):void
		{
			_listaItemCompra = value;
		}

		public function get dataCriacaoCompra():Date
		{
			return _dataCriacaoCompra;
		}

		public function set dataCriacaoCompra(value:Date):void
		{
			_dataCriacaoCompra = value;
		}

		public function get dataCriacaoCompraString():String
		{
			if(_dataCriacaoCompra)
				return ScreenUtils.formatarDataDDMMYYYY(_dataCriacaoCompra);
			else{
				return "";
			}
		}

	}
}