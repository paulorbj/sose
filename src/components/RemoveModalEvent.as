package components
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class RemoveModalEvent extends Event
	{
		public static const REMOVE_MODAL:String = "removeModalEvent";
		
		public function RemoveModalEvent()
		{
			super(REMOVE_MODAL, true, false);
		}
	}
}