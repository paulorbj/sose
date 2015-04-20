package modules.administrativo.parceiros.pessoa.eventos
{

	
	import entities.administrativo.parceiros.Pessoa;
	
	import flash.events.Event;
	
	public class PessoaEvent extends Event
	{
		public static const EDITAR_PESSOA:String = "editarPessoa";
		public static const EXCLUIR_PESSOA:String = "excluirPessoa";
		
		private	var _pessoa:Pessoa;

		public function get pessoa():Pessoa
		{
			return _pessoa;
		}

		public function set pessoa(value:Pessoa):void
		{
			_pessoa = value;
		}

		
		public function PessoaEvent(tipo:String,pessoa:Pessoa =null)
		{
			super(tipo, true, false);
			this._pessoa = pessoa;
		}		
	}
}