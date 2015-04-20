package modules.login.events
{
	import flash.events.Event;
	
	public class ForgotPasswordEvent extends Event
	{
		public static const FORGOT_PASSWORD:String = "forgotpassword";
		
		public function ForgotPasswordEvent()
		{
			super(FORGOT_PASSWORD, true, false);
		}
	}
}