package modules.estoque.componente.eventos
{
	import entities.compra.ItemCompra;
	
	import flash.events.Event;
	
	/**
	 * Event used to close a message when the close button is clicked or when
	 * the pause event is finished.
	 * @see CloseButton
	 * @see TempMessage
	 */
	public class ModalItemCompraEvent extends Event
	{
		public static const REMOVE_MODAL_ITEM_COMPRA:String = "removeModalItemCompraEvent";
		public static const SALVAR_ITEM_COMPRA:String = "salvarItemCompraEvent";
		public static const NOTIFICAR_ESTOQUE_ITEM_COMPRA:String = "notificarEstoqueItemCompraEvent";

		public var itemCompra:ItemCompra;
		
		public var tipoEvento:String;
		
		
		
		public function ModalItemCompraEvent(tipoEvento:String,
											 itemCompra:ItemCompra,
											 bubbles:Boolean = true,
											 cancelable:Boolean = false)
		{
			super(tipoEvento, true, false);
		}
	}
}