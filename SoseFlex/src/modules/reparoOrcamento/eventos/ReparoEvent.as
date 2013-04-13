package modules.reparoOrcamento.eventos
{
	import entities.orcamentoreparo.Reparo;
	
	import flash.events.Event;
	
	public class ReparoEvent extends Event
	{
		public static const ATUALIZAR_REPARO:String = "atualizarReparo";
		public static const INICIAR_REPARO:String = "iniciarReparo";
		public static const SALVAR_REPARO:String = "salvarReparo";
		public static const FINALIZAR_REPARO:String = "finalizarReparo";
		public static const APROVAR_REPARO:String = "aprovarReparo";
		public static const REJEITAR_REPARO:String = "rejeitarReparo";
		public static const EXCLUIR_REPARO:String = "excluirReparo";
		public static const VOLTAR_REPARO:String = "voltarReparo";
		public static const DEVOLVER_SEM_REPARO:String = "devolverSemReparoReparo";
		public static const REQUISITAR_ORCAMENTO_DIFERENCIADO:String = "requisitarOrcamentoDiferenciado";
		public static const REQUISITAR_LAUDO_TECNICO:String = "requisitarLaudoTecnicoReparo";
		public static const REQUISITAR_REPARO_EXTERNO:String = "requisitarReparoExternoReparo";

		private	var _reparo:Reparo;

		
		public function ReparoEvent(tipo:String,reparo:Reparo)
		{
			super(tipo, true, false);
			this._reparo = reparo;
		}		

		public function get reparo():Reparo
		{
			return _reparo;
		}

		public function set reparo(value:Reparo):void
		{
			_reparo = value;
		}



	}
}