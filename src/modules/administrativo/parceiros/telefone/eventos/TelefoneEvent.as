package modules.administrativo.parceiros.telefone.eventos
{

	
	import entities.administrativo.parceiros.Telefone;
	
	import flash.events.Event;
	
	public class TelefoneEvent extends Event
	{
		public static const EDITAR_TELEFONE:String = "editarTelefone";
		public static const EXCLUIR_TELEFONE:String = "excluirTelefone";
		
		private	var _telefone:Telefone;

		public function get telefone():Telefone
		{
			return _telefone;
		}

		public function set telefone(value:Telefone):void
		{
			_telefone = value;
		}

		
		public function TelefoneEvent(tipo:String,telefone:Telefone =null)
		{
			super(tipo, true, false);
			this._telefone = telefone;
		}		
	}
}