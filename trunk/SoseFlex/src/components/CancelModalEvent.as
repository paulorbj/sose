package components
{
	import flash.events.Event;

	public class CancelModalEvent extends Event
	{
		public static const CANCEL_MODAL:String = "cancelModalEvent";
		
		public function CancelModalEvent()
		{
			super(CANCEL_MODAL, true, false);
		}
	}
}