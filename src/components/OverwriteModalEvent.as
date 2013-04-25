package components
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class OverwriteModalEvent extends Event
	{
		public static const OVERWRITE_MODAL:String = "overwriteModalEvent";
		
		public function OverwriteModalEvent()
		{
			super(OVERWRITE_MODAL, true, false);
		}
	}
}