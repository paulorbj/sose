package modules.recebimento.eventos
{

		
	import entities.recebimento.NotaFiscal;
	
	import flash.events.Event;
	
	public class NotaFiscalEvent extends Event
	{
		public static const EDITAR_NOTA_FISCAL:String = "editarNotaFiscal";
		public static const EXCLUIR_NOTA_FISCAL:String = "editarNotaFiscal";
		public static const VISUALIZAR_NOTA_FISCAL:String = "visualizarNotaFiscal";
		
		private	var _notaFiscal:NotaFiscal;

		
		public function NotaFiscalEvent(tipo:String,notaFiscal:NotaFiscal)
		{
			super(tipo, true, false);
			this._notaFiscal = notaFiscal;
		}		

		public function get notaFiscal():NotaFiscal
		{
			return _notaFiscal;
		}

		public function set notaFiscal(value:NotaFiscal):void
		{
			_notaFiscal = value;
		}



	}
}