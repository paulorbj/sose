package entities.administrativo
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.TipoComponente")]	
	public class TipoComponente
	{
		
		private var _id:Number;
		private var _nome:String;
		private var _descricao:String;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		
		public function TipoComponente(){
			_id = 0;
			_nome = "";
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
		
		public function get nome():String
		{
			return _nome;
		}
		
		public function set nome(value:String):void
		{
			_nome = value;
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

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

	}
}