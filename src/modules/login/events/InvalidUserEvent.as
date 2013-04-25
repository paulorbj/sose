package modules.login.events
{
	import flash.events.Event;
	
	public class InvalidUserEvent extends Event
	{
		public static const INVALID_USER:String = "invalidUser";
		
		public function InvalidUserEvent()
		{
			super(INVALID_USER, true, false);
		}
	}
}