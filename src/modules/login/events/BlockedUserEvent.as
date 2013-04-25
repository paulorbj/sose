package modules.login.events
{
	import flash.events.Event;
	
	public class BlockedUserEvent extends Event
	{
		public static const BLOCKED_USER:String = "blockedUser";
		
		public function BlockedUserEvent()
		{
			super(BLOCKED_USER, true, false);
		}
	}
}
