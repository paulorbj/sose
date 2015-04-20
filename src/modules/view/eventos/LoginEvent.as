package modules.view.eventos
{

		
	import flash.events.Event;
	
	public class LoginEvent extends Event
	{
		public static const INVALIDAR_USUARIO_AUTENTICADO:String = "invalidarUsuarioAutenticado";
		
		public function LoginEvent(tipo:String)
		{
			super(tipo, true, false);
		}		

	}
}