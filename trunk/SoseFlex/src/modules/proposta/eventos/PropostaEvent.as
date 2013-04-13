package modules.proposta.eventos
{
	
	import flash.events.Event;
	
	public class PropostaEvent extends Event
	{
		public static const COMBO_CLIENTE_POPULADO:String = "comboClientePopulado";
		
		public function PropostaEvent(tipo:String)
		{
			super(tipo, true, false);
		}		

	}
}