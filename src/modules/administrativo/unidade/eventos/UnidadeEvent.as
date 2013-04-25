package modules.administrativo.unidade.eventos
{
	import entities.administrativo.Unidade;
	
	import flash.events.Event;
	
	public class UnidadeEvent extends Event
	{
		public static const EDITAR_UNIDADE:String = "editarUnidade";
		public static const EXCLUIR_UNIDADE:String = "excluirUnidade";
		
		private	var _unidade:Unidade;

		public function get unidade():Unidade
		{
			return _unidade;
		}

		public function set unidade(value:Unidade):void
		{
			_unidade = value;
		}

		
		public function UnidadeEvent(tipo:String,unidade:Unidade =null)
		{
			super(tipo, true, false);
			this._unidade = unidade;
		}		
	}
}