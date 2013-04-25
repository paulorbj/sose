package components.messages
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class NotificationEvent extends Event
	{
		public static const LIMPAR_NOTIFICACOES:String = "limparNotificacoes";
		
		public function NotificationEvent(acao:String)
		{
			super(acao, true, false);
		}
	}
}