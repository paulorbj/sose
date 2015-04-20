package modules.reparoOrcamento.eventos
{
	import entities.orcamentoreparo.Orcamento;
	
	import flash.events.Event;
	
	public class OrcamentoEvent extends Event
	{
		public static const ATUALIZAR_ORCAMENTO:String = "atualizarOrcamento";
		public static const INICIAR_ORCAMENTO:String = "iniciarOrcamento";
		public static const SALVAR_ORCAMENTO:String = "salvarOrcamento";
		public static const FINALIZAR_ORCAMENTO:String = "finalizarOrcamento";
		public static const EXCLUIR_ORCAMENTO:String = "excluirOrcamento";
		public static const VOLTAR_ORCAMENTO:String = "voltarOrcamento";
		public static const ENCAMINHAR_AO_LIDER:String = "encaminharAoLider";
		public static const DEVOLVER_SEM_REPARO:String = "devolverSemReparoOrcamento";
		public static const APROVAR_ORCAMENTO:String = "aprovarOrcamento";
		public static const REJEITAR_ORCAMENTO:String = "reprovarOrcamento";
		public static const REQUISITAR_LAUDO_TECNICO:String = "requisitarLaudoTecnicoOrcamento";
		public static const REQUISITAR_REPARO_EXTERNO:String = "requisitarReparoExternoOrcamento";

		private	var _orcamento:Orcamento;

		
		public function OrcamentoEvent(tipo:String,orcamento:Orcamento)
		{
			super(tipo, true, false);
			this._orcamento = orcamento;
		}		

		public function get orcamento():Orcamento
		{
			return _orcamento;
		}

		public function set orcamento(value:Orcamento):void
		{
			_orcamento = value;
		}



	}
}