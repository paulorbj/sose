package modules.administrativo.tipocomponente.eventos
{
	import entities.administrativo.TipoComponente;
	
	import flash.events.Event;
	
	public class TipoComponenteEvent extends Event
	{
		public static const EDITAR_TIPOCOMPONENTE:String = "editarTipoComponente";
		public static const EXCLUIR_TIPOCOMPONENTE:String = "excluirTipoComponente";
		
		private	var _tipoComponente:TipoComponente;

		public function get tipoComponente():TipoComponente
		{
			return _tipoComponente;
		}

		public function set tipoComponente(value:TipoComponente):void
		{
			_tipoComponente = value;
		}

		
		public function TipoComponenteEvent(tipo:String,tipoComponente:TipoComponente =null)
		{
			super(tipo, true, false);
			this._tipoComponente = tipoComponente;
		}		
	}
}