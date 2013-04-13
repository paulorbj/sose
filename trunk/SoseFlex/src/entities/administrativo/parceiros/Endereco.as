package entities.administrativo.parceiros
{
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.parceiros.Endereco")]	
	public class Endereco
	{
		private var _id:Number;
		private var _logradouro:String;
		private var _numero:String;
		private var _complemento:String;
		private var _bairro:String;
		private var _pais:String;
		private var _cidade:String;
		private var _cep:String;
		private var _estado:String;		
		private var _telefone:String;
		private var _fax:String;	
		private var _enderecoComercial:Boolean;	
		private var _enderecoCobranca:Boolean;		
		private var _enderecoEntrega:Boolean;
		private var _cliente:Pessoa;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _nomeCliente:String;
		
		public function Endereco(){
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

		public function get logradouro():String
		{
			return _logradouro;
		}

		public function set logradouro(value:String):void
		{
			_logradouro = value;
		}

		public function get numero():String
		{
			return _numero;
		}

		public function set numero(value:String):void
		{
			_numero = value;
		}

		public function get complemento():String
		{
			return _complemento;
		}

		public function set complemento(value:String):void
		{
			_complemento = value;
		}

		public function get bairro():String
		{
			return _bairro;
		}

		public function set bairro(value:String):void
		{
			_bairro = value;
		}

		public function get pais():String
		{
			return _pais;
		}

		public function set pais(value:String):void
		{
			_pais = value;
		}

		public function get cidade():String
		{
			return _cidade;
		}

		public function set cidade(value:String):void
		{
			_cidade = value;
		}

		public function get cep():String
		{
			return _cep;
		}

		public function set cep(value:String):void
		{
			_cep = value;
		}

		public function get estado():String
		{
			return _estado;
		}

		public function set estado(value:String):void
		{
			_estado = value;
		}

		public function get enderecoComercial():Boolean
		{
			return _enderecoComercial;
		}

		public function set enderecoComercial(value:Boolean):void
		{
			_enderecoComercial = value;
		}

		public function get enderecoCobranca():Boolean
		{
			return _enderecoCobranca;
		}

		public function set enderecoCobranca(value:Boolean):void
		{
			_enderecoCobranca = value;
		}

		public function get enderecoEntrega():Boolean
		{
			return _enderecoEntrega;
		}

		public function set enderecoEntrega(value:Boolean):void
		{
			_enderecoEntrega = value;
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

		public function get fax():String
		{
			return _fax;
		}

		public function set fax(value:String):void
		{
			_fax = value;
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

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}


	}
}