package modules.reparoOrcamento.eventos
{
	import entities.orcamentoreparo.Reparo;
	import entities.orcamentoreparo.RequisicaoComponente;
	
	import flash.events.Event;
	
	public class ComponenteEvent extends Event
	{
		public static const ADICIONAR_REQUISICAO:String = "adicionarRequisicao";
		

		private	var _requisicao:RequisicaoComponente;

		
		public function ComponenteEvent(tipo:String,requisicao:RequisicaoComponente)
		{
			super(tipo, true, false);
			this._requisicao = requisicao;
		}		


		public function get requisicao():RequisicaoComponente
		{
			return _requisicao;
		}

		public function set requisicao(value:RequisicaoComponente):void
		{
			_requisicao = value;
		}

	}
}