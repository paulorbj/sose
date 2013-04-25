package entities.administrativo
{
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Laboratorio")]	
	public class Laboratorio
	{
		
		private var _id:Number;
		private var _descricao:String;
		private var _nome:String;
		private var _tecnicos:ArrayCollection;
		private var _lider:Usuario;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		private var _nomeLider:String;
		
		public function Laboratorio(){
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
		
		public function get descricao():String
		{
			return _descricao;
		}
		
		public function set descricao(value:String):void
		{
			_descricao = value;
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

		public function get tecnicos():ArrayCollection
		{
			return _tecnicos;
		}

		public function set tecnicos(value:ArrayCollection):void
		{
			_tecnicos = value;
		}

		public function get lider():Usuario
		{
			return _lider;
		}

		public function set lider(value:Usuario):void
		{
			_lider = value;
		}

		public function get nomeLider():String
		{
			return _nomeLider;
		}

		public function set nomeLider(value:String):void
		{
			_nomeLider = value;
		}


	}
}