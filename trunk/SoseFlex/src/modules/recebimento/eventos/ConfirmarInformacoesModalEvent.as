package modules.recebimento.eventos
{
	import entities.recebimento.OrdemServico;
	
	import flash.events.Event;
	
	public class ConfirmarInformacoesModalEvent extends Event
	{
		public static const RETORNAR_ORDEM_SERVICO_CONFIRMAR_MODAL:String = "retornarOrdemServicoConfirmarModal";
		
		private	var _ordemServico:OrdemServico;

		public function ConfirmarInformacoesModalEvent(type:String,
													 ordemServico:OrdemServico,
													 bubbles:Boolean = true,
													 cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
			this._ordemServico = ordemServico;
		}
		
		public override function clone():Event
		{
			return new ConfirmarInformacoesModalEvent(this.type, this._ordemServico, this.bubbles, this.cancelable);
		}

		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}

		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}

		
	}
}