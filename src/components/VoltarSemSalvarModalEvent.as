package components
{
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class VoltarSemSalvarModalEvent extends Event
	{
		public static const VOLTAR_SEM_SALVAR_MODAL:String = "voltarSemSalvarModalEvent";
		
		public function VoltarSemSalvarModalEvent()
		{
			super(VOLTAR_SEM_SALVAR_MODAL, true, false);
		}
	}
}