package modules.administrativo.parceiros.endereco.eventos
{
	
	import entities.administrativo.parceiros.Endereco;
	
	import flash.events.Event;
	
	public class EnderecoEvent extends Event
	{
		public static const ADICIONAR_ENDERECO:String = "adicionarEndereco";
		public static const EDITAR_ENDERECO:String = "editarEndereco";
		public static const EXCLUIR_ENDERECO:String = "excluirEndereco";
		
		private	var _endereco:Endereco;
		private var _indice:int;

		public function get endereco():Endereco
		{
			return _endereco;
		}

		public function set endereco(value:Endereco):void
		{
			_endereco = value;
		}

		public function get indice():int
		{
			return _indice;
		}

		public function set indice(value:int):void
		{
			_indice = value;
		}

		
		public function EnderecoEvent(tipo:String,endereco:Endereco =null,indiceSelecionado:int=-1)
		{
			super(tipo, true, false);
			this._endereco = endereco;
			this._indice = indiceSelecionado;
		}		
	}
}