package entities.compra
{	
	import entities.administrativo.Componente;
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.compra.Cotacao")]	
	public class Cotacao
	{
		private var _id:Number;
		private var _componente:Componente;
		private var _dataCotacao:Date;
		private var _fornecedor:String;
		private var _valor:Number;
		private var _cotadoPor:Usuario;
			
		public function Cotacao(){
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

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}

		public function get dataCotacao():Date
		{
			return _dataCotacao;
		}

		public function set dataCotacao(value:Date):void
		{
			_dataCotacao = value;
		}
		
		public function get dataCotacaoString():String
		{
			if(_dataCotacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dataCotacao);
			else{
				return "";
			}
		}

		public function get fornecedor():String
		{
			return _fornecedor;
		}

		public function set fornecedor(value:String):void
		{
			_fornecedor = value;
		}

		public function get valor():Number
		{
			return _valor;
		}

		public function set valor(value:Number):void
		{
			_valor = value;
		}

		public function get cotadoPor():Usuario
		{
			return _cotadoPor;
		}

		public function set cotadoPor(value:Usuario):void
		{
			_cotadoPor = value;
		}

	
	}
}