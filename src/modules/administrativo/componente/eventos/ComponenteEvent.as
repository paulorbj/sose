package modules.administrativo.componente.eventos
{
	import entities.administrativo.Componente;
	
	import flash.events.Event;
	
	public class ComponenteEvent extends Event
	{
		public static const EDITAR_COMPONENTE:String = "editarComponente";
		public static const EXCLUIR_COMPONENTE:String = "excluirComponente";
		
		private	var _componente:Componente;

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}

		
		public function ComponenteEvent(tipo:String,componente:Componente =null)
		{
			super(tipo, true, false);
			this._componente = componente;
		}		
	}
}