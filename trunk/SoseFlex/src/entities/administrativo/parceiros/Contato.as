package entities.administrativo.parceiros
{
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.parceiros.Contato")]	
	public class Contato
	{
		private var _id:Number;
		private var _nome:String;
		private var _cargo:String;
		private var _email:String;
		private var _departamento:String;
		private var _informacoesAdicionais:String;
		private var _telefone:String;
		private var _celular:String;
		private var _fax:String;
		private var _cliente:Pessoa;

		public function Contato(){
			_id = 0;
			_nome = "";
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

		public function get cargo():String
		{
			return _cargo;
		}

		public function set cargo(value:String):void
		{
			_cargo = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get departamento():String
		{
			return _departamento;
		}

		public function set departamento(value:String):void
		{
			_departamento = value;
		}

		public function get informacoesAdicionais():String
		{
			return _informacoesAdicionais;
		}

		public function set informacoesAdicionais(value:String):void
		{
			_informacoesAdicionais = value;
		}

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get telefone():String
		{
			return _telefone;
		}

		public function set telefone(value:String):void
		{
			_telefone = value;
		}

		public function get celular():String
		{
			return _celular;
		}

		public function set celular(value:String):void
		{
			_celular = value;
		}

		public function get fax():String
		{
			return _fax;
		}

		public function set fax(value:String):void
		{
			_fax = value;
		}


	}
}