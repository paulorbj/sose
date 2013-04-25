package modules.administrativo.perfil.eventos
{
	import entities.administrativo.Perfil;
	
	import flash.events.Event;
	
	public class PerfilEvent extends Event
	{
		public static const EDITAR_PERFIL:String = "editarPerfil";
		public static const EXCLUIR_PERFIL:String = "excluirPerfil";
		
		private	var _perfil:Perfil;

		public function get perfil():Perfil
		{
			return _perfil;
		}

		public function set perfil(value:Perfil):void
		{
			_perfil = value;
		}

		
		public function PerfilEvent(tipo:String,perfil:Perfil =null)
		{
			super(tipo, true, false);
			this._perfil = perfil;
		}		
	}
}