package modules.administrativo.equipamento.eventos
{
	import entities.administrativo.Equipamento;
	
	import flash.events.Event;
	
	public class EquipamentoEvent extends Event
	{
		public static const EDITAR_EQUIPAMENTO:String = "editarEquipamento";
		public static const EXCLUIR_EQUIPAMENTO:String = "excluirEquipamento";
		
		private	var _equipamento:Equipamento;

		public function get equipamento():Equipamento
		{
			return _equipamento;
		}

		public function set equipamento(value:Equipamento):void
		{
			_equipamento = value;
		}

		
		public function EquipamentoEvent(tipo:String,equipamento:Equipamento =null)
		{
			super(tipo, true, false);
			this._equipamento = equipamento;
		}		
	}
}