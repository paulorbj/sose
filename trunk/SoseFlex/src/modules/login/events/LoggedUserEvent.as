package modules.login.events
{

	import entities.administrativo.Usuario;
	
	import flash.events.Event;
	
	public class LoggedUserEvent extends Event
	{
		public static const LOGGED_USER:String = "loggedUser";
		
		private	var _sessionUser:Usuario;
		
		public function LoggedUserEvent(type:String,
										sessionUser:Usuario,
										bubbles:Boolean = true,
										cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
			this._sessionUser = sessionUser;
		}
		
		public override function clone():Event
		{
			return new LoggedUserEvent(this.type, this._sessionUser, this.bubbles, this.cancelable);
		}
		
		public function get sessionUser():Usuario
		{
			return _sessionUser;
		}
		
		public function set sessionUser(sessionUser:Usuario):void
		{
			_sessionUser = sessionUser;
		}
	}
}