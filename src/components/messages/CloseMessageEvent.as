package components.messages
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class CloseMessageEvent extends Event
	{
		public static const CLOSE_MESSAGE:String = "closeMessageEvent";
		
		public function CloseMessageEvent()
		{
			super(CLOSE_MESSAGE, true, false);
		}
	}
}