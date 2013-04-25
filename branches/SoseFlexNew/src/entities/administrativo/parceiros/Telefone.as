package entities.administrativo.parceiros
{
	import entities.administrativo.Usuario;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.parceiros.Telefone")]	
	public class Telefone
	{
		private var _id:Number;
		private var _ddd:String;
		private var _numero:String;
		private var _tipo:String;
		private var _cliente:Pessoa;
		private var _endereco:Endereco;
		private var _contato:Contato;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;

		public function Telefone(){
			_id = 0;
			_ddd = "";
			_numero = "";
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get ddd():String
		{
			return _ddd;
		}

		public function set ddd(value:String):void
		{
			_ddd = value;
		}

		public function get numero():String
		{
			return _numero;
		}

		public function set numero(value:String):void
		{
			_numero = value;
		}

		public function get tipo():String
		{
			return _tipo;
		}

		public function set tipo(value:String):void
		{
			_tipo = value;
		}

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get endereco():Endereco
		{
			return _endereco;
		}

		public function set endereco(value:Endereco):void
		{
			_endereco = value;
		}

		public function get contato():Contato
		{
			return _contato;
		}

		public function set contato(value:Contato):void
		{
			_contato = value;
		}

		public function get cadastroSistemaRealizadoEm():Date
		{
			return _cadastroSistemaRealizadoEm;
		}

		public function set cadastroSistemaRealizadoEm(value:Date):void
		{
			_cadastroSistemaRealizadoEm = value;
		}

		public function get cadastroSistemaRealizadoPor():Usuario
		{
			return _cadastroSistemaRealizadoPor;
		}

		public function set cadastroSistemaRealizadoPor(value:Usuario):void
		{
			_cadastroSistemaRealizadoPor = value;
		}

		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}

		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}


	}
}