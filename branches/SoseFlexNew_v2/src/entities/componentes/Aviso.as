package entities.componentes
{
	import entities.administrativo.Usuario;

	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.componentes.Aviso")]	
	public class Aviso
	{
		
		private var _id:Number;
		private var _mensagem:String;
		private var _titulo:String;
		private var _dataCriacao:Date;
		private var _dataVisivelAlterado:Date;
		private var _visivel:Boolean;
		private var _criadoPor:Usuario;
		
		public function Aviso()
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

		public function get mensagem():String
		{
			return _mensagem;
		}

		public function set mensagem(value:String):void
		{
			_mensagem = value;
		}

		public function get dataCriacao():Date
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:Date):void
		{
			_dataCriacao = value;
		}

		public function get dataVisivelAlterado():Date
		{
			return _dataVisivelAlterado;
		}

		public function set dataVisivelAlterado(value:Date):void
		{
			_dataVisivelAlterado = value;
		}

		public function get visivel():Boolean
		{
			return _visivel;
		}

		public function set visivel(value:Boolean):void
		{
			_visivel = value;
		}

		public function get criadoPor():Usuario
		{
			return _criadoPor;
		}

		public function set criadoPor(value:Usuario):void
		{
			_criadoPor = value;
		}

		public function get titulo():String
		{
			return _titulo;
		}

		public function set titulo(value:String):void
		{
			_titulo = value;
		}


	}
}