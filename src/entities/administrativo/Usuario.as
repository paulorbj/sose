package entities.administrativo
{
	import entities.administrativo.parceiros.Endereco;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Usuario")]	
	public class Usuario
	{
		private var _id:Number;
		private var _usuario:String;
		private var _senha:String;
		private var _perfil:Perfil;
		private var _nome:String;
		private var _rg:String;
		private var _cpf:String;
		private var _email:String;
		private var _dataNascimento:Date;
		private var _endereco:Endereco;
		private var _bloqueado:Boolean;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _nomePerfil:String;
		private var _telefone:String;
		private var _celular:String;
		
		public function Usuario(){
			_id = 0;
			_nome = "";
			_endereco = new Endereco();
			_bloqueado=false;
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get usuario():String
		{
			return _usuario;
		}

		public function set usuario(value:String):void
		{
			_usuario = value;
		}

		public function get senha():String
		{
			return _senha;
		}

		public function set senha(value:String):void
		{
			_senha = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get dataNascimento():Date
		{
			return _dataNascimento;
		}

		public function set dataNascimento(value:Date):void
		{
			_dataNascimento = value;
		}

		public function get bloqueado():Boolean
		{
			return _bloqueado;
		}

		public function set bloqueado(value:Boolean):void
		{
			_bloqueado = value;
		}

		public function get endereco():Endereco
		{
			return _endereco;
		}

		public function set endereco(value:Endereco):void
		{
			_endereco = value;
		}

		public function get rg():String
		{
			return _rg;
		}

		public function set rg(value:String):void
		{
			_rg = value;
		}

		public function get cpf():String
		{
			return _cpf;
		}

		public function set cpf(value:String):void
		{
			_cpf = value;
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

		public function get perfil():Perfil
		{
			return _perfil;
		}

		public function set perfil(value:Perfil):void
		{
			_perfil = value;
		}

		public function get nomePerfil():String
		{
			return _nomePerfil;
		}

		public function set nomePerfil(value:String):void
		{
			_nomePerfil = value;
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


	}
}