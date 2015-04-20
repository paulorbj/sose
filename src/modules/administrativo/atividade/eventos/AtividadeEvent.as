package modules.administrativo.atividade.eventos
{
	import entities.administrativo.Atividade;
	
	import flash.events.Event;
	
	public class AtividadeEvent extends Event
	{
		public static const EDITAR_ATIVIDADE:String = "editarAtividade";
		public static const EXCLUIR_ATIVIDADE:String = "excluirAtividade";
		
		private	var _atividade:Atividade;

		public function get atividade():Atividade
		{
			return _atividade;
		}

		public function set atividade(value:Atividade):void
		{
			_atividade = value;
		}

		
		public function AtividadeEvent(tipo:String,atividade:Atividade =null)
		{
			super(tipo, true, false);
			this._atividade = atividade;
		}		
	}
}