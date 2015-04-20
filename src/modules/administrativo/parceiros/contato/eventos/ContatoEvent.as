package modules.administrativo.parceiros.contato.eventos
{
	import entities.administrativo.parceiros.Contato;
	
	import flash.events.Event;
	
	public class ContatoEvent extends Event
	{
		public static const EDITAR_CONTATO:String = "editarContato";
		public static const EXCLUIR_CONTATO:String = "excluirContato";
		public static const ADICIONAR_CONTATO:String = "adicionarContato";
		public static const COMBO_CONTATO_POPULADO:String = "comboContatoPopulado";
		
		private	var _contato:Contato;
		private var _indice:int;

		public function get contato():Contato
		{
			return _contato;
		}

		public function set contato(value:Contato):void
		{
			_contato = value;
		}

		public function get indice():int
		{
			return _indice;
		}

		public function set indice(value:int):void
		{
			_indice = value;
		}

		
		public function ContatoEvent(tipo:String,contato:Contato = null,indiceSelecionado:int=-1)
		{
			super(tipo, true, false);
			this._contato = contato;
			this._indice = indiceSelecionado;
		}		
	}
}