package modules.login.events
{
	import flash.events.Event;
	
	public class LoggedOutUserEvent extends Event
	{
		public static const LOGGED_OUT_USER:String = "loggedOutUser";
		
		public function LoggedOutUserEvent()
		{
			super(LOGGED_OUT_USER, true, false);
		}
	}
}