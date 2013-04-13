package modules.administrativo.defeito.eventos
{
	import entities.administrativo.Defeito;
	
	import flash.events.Event;
	
	public class DefeitoEvent extends Event
	{
		public static const EDITAR_DEFEITO:String = "editarDefeito";
		public static const EXCLUIR_DEFEITO:String = "excluirDefeito";
		
		private	var _defeito:Defeito;

		public function get defeito():Defeito
		{
			return _defeito;
		}

		public function set defeito(value:Defeito):void
		{
			_defeito = value;
		}

		
		public function DefeitoEvent(tipo:String,defeito:Defeito =null)
		{
			super(tipo, true, false);
			this._defeito = defeito;
		}		
	}
}