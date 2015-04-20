package modules.administrativo.encapsulamento.eventos
{
	import entities.administrativo.Encapsulamento;
	
	import flash.events.Event;
	
	public class EncapsulamentoEvent extends Event
	{
		public static const EDITAR_ENCAPSULAMENTO:String = "editarEncapsulamento";
		public static const EXCLUIR_ENCAPSULAMENTO:String = "excluirEncapsulamento";
		
		private	var _encapsulamento:Encapsulamento;

		public function get encapsulamento():Encapsulamento
		{
			return _encapsulamento;
		}

		public function set encapsulamento(value:Encapsulamento):void
		{
			_encapsulamento = value;
		}

		
		public function EncapsulamentoEvent(tipo:String,encapsulamento:Encapsulamento =null)
		{
			super(tipo, true, false);
			this._encapsulamento = encapsulamento;
		}		
	}
}