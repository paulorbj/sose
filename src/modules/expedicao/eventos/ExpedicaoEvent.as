package modules.expedicao.eventos
{
	
	import flash.events.Event;
	
	public class ExpedicaoEvent extends Event
	{
		public static const COMBO_CLIENTE_POPULADO:String = "comboClientePopulado";
		
		public function ExpedicaoEvent(tipo:String)
		{
			super(tipo, true, false);
		}		

	}
}