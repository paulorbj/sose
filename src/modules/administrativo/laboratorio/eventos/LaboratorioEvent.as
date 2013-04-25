package modules.administrativo.laboratorio.eventos
{
	import entities.administrativo.Laboratorio;
	
	import flash.events.Event;
	
	public class LaboratorioEvent extends Event
	{
		public static const EDITAR_LABORATORIO:String = "editarLaboratorio";
		public static const EXCLUIR_LABORATORIO:String = "excluirLaboratorio";
		
		private	var _laboratorio:Laboratorio;

		public function get laboratorio():Laboratorio
		{
			return _laboratorio;
		}

		public function set laboratorio(value:Laboratorio):void
		{
			_laboratorio = value;
		}

		
		public function LaboratorioEvent(tipo:String,laboratorio:Laboratorio =null)
		{
			super(tipo, true, false);
			this._laboratorio = laboratorio;
		}		
	}
}