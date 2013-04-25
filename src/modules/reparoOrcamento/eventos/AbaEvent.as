package modules.recebimento.eventos
{

		
	import flash.events.Event;
	
	public class AbaEvent extends Event
	{
		public static const ALTEROU_ABA:String = "alterouAba";
		
		private	var _indiceAba:int;

		
		public function AbaEvent(tipo:String,indice:int)
		{
			super(tipo, true, false);
			this._indiceAba = indice;
		}		

		public function get indiceAba():int
		{
			return _indiceAba;
		}

		public function set indiceAba(value:int):void
		{
			_indiceAba = value;
		}

	}
}