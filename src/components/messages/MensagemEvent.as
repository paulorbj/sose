package components.messages
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class MensagemEvent extends Event
	{
		public static const EXIBIR_MENSAGEM:String = "exibirMensagemEvente";
		public static const LIMPAR_NOTIFICACOES:String = "limparNotificacoes";
		public var mensagem:String;
		public var tipo:MessageType;
		public var delay:Number = -1;
		
		public function MensagemEvent(msg:String,t:MessageType,d:Number)
		{
			super(EXIBIR_MENSAGEM, true, false);
			mensagem = msg;
			tipo = t;
			delay = d;
		}
	}
}