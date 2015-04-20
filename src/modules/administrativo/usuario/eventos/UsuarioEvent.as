package modules.administrativo.usuario.eventos
{
	import entities.administrativo.Usuario;
	
	import flash.events.Event;
	
	public class UsuarioEvent extends Event
	{
		public static const EDITAR_USUARIO:String = "editarUsuario";
		public static const EXCLUIR_USUARIO:String = "excluirUsuario";
		
		private	var _usuario:Usuario;

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
		}

		
		public function UsuarioEvent(tipo:String,usuario:Usuario =null)
		{
			super(tipo, true, false);
			this._usuario = usuario;
		}		
	}
}