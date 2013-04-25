package entities.relatorio
{
	import entities.administrativo.Usuario;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.relatorio.LogSistema")]	
	public class LogSistema
	{
		private var _id:Number;
		private var _usuarioRealizador:Usuario;
		private var _dataAlteracao:Date;
		private var _tipoAlteracao:Number;
	
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get usuarioRealizador():Usuario
		{
			return _usuarioRealizador;
		}

		public function set usuarioRealizador(value:Usuario):void
		{
			_usuarioRealizador = value;
		}

		public function get dataAlteracao():Date
		{
			return _dataAlteracao;
		}

		public function set dataAlteracao(value:Date):void
		{
			_dataAlteracao = value;
		}

		public function get tipoAlteracao():Number
		{
			return _tipoAlteracao;
		}

		public function set tipoAlteracao(value:Number):void
		{
			_tipoAlteracao = value;
		}


	}
}