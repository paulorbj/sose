package entities.administrativo
{
	
	[Bindable]	
	public class Etiqueta
	{
		
		private var _id:Number;
		private var _quantidade:Number;
		private var _primeiroNumeroSequencia:Number;
		private var _usuario:Usuario;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		
		public function Fabricante(){
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

		public function get quantidade():Number
		{
			return _quantidade;
		}

		public function set quantidade(value:Number):void
		{
			_quantidade = value;
		}

		public function get primeiroNumeroSequencia():Number
		{
			return _primeiroNumeroSequencia;
		}

		public function set primeiroNumeroSequencia(value:Number):void
		{
			_primeiroNumeroSequencia = value;
		}

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
		}


	}
}