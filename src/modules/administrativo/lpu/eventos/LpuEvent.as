package modules.administrativo.lpu.eventos
{
	import entities.administrativo.Lpu;
	
	import flash.events.Event;
	
	public class LpuEvent extends Event
	{
		public static const EDITAR_LPU:String = "editarLpu";
		public static const EXCLUIR_LPU:String = "excluirLpu";
		
		private	var _lpu:Lpu;

		public function get lpu():Lpu
		{
			return _lpu;
		}

		public function set lpu(value:Lpu):void
		{
			_lpu = value;
		}

		
		public function LpuEvent(tipo:String,lpu:Lpu =null)
		{
			super(tipo, true, false);
			this._lpu = lpu;
		}		
	}
}