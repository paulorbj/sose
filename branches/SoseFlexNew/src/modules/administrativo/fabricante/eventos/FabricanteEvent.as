package modules.administrativo.fabricante.eventos
{
	import entities.administrativo.Fabricante;
	
	import flash.events.Event;
	
	public class FabricanteEvent extends Event
	{
		public static const EDITAR_FABRICANTE:String = "editarFabricante";
		public static const EXCLUIR_FABRICANTE:String = "excluirFabricante";
		
		private	var _fabricante:Fabricante;

		public function get fabricante():Fabricante
		{
			return _fabricante;
		}

		public function set fabricante(value:Fabricante):void
		{
			_fabricante = value;
		}

		
		public function FabricanteEvent(tipo:String,fabricante:Fabricante =null)
		{
			super(tipo, true, false);
			this._fabricante = fabricante;
		}		
	}
}