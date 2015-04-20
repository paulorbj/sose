package entities.administrativo
{
	import entities.administrativo.parceiros.Pessoa;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.lpu.Lpu")]	
	public class Lpu
	{
		
		private var _id:Number;
		private var _descricao:String;
		private var _cliente:Pessoa;
		private var _unidade:String;
		private var _codigo1:String;
		private var _codigo2:String;
		private var _fabricante:Fabricante;
		private var _equipamento:Equipamento;
		private var _moeda:String;
		private var _valorReparo:Number;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		
		public function Lpu(){
			_unidade = "";
			_id = 0;
			_cadastroSistemaAtivo = true;
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
		public function get descricao():String
		{
			return _descricao;
		}
		
		public function set descricao(value:String):void
		{
			_descricao = value;
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

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get unidade():String
		{
			return _unidade;
		}

		public function set unidade(value:String):void
		{
			_unidade = value;
		}

		public function get codigo1():String
		{
			return _codigo1;
		}

		public function set codigo1(value:String):void
		{
			_codigo1 = value;
		}

		public function get codigo2():String
		{
			return _codigo2;
		}

		public function set codigo2(value:String):void
		{
			_codigo2 = value;
		}

		public function get fabricante():Fabricante
		{
			return _fabricante;
		}

		public function set fabricante(value:Fabricante):void
		{
			_fabricante = value;
		}

		public function get equipamento():Equipamento
		{
			return _equipamento;
		}

		public function set equipamento(value:Equipamento):void
		{
			_equipamento = value;
		}

		public function get moeda():String
		{
			return _moeda;
		}

		public function set moeda(value:String):void
		{
			_moeda = value;
		}

		public function get valorReparo():Number
		{
			return _valorReparo;
		}

		public function set valorReparo(value:Number):void
		{
			_valorReparo = value;
		}


	}
}