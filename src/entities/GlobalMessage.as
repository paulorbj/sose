package entities	
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.utils.GlobalMessage")]
	public class GlobalMessage		
	{

		private var _mensagem : String;
		private var _tipo: String;
		private var _conteudo : Object;
		

		public function get mensagem():String
		{
			return _mensagem;
		}

		public function set mensagem(value:String):void
		{
			_mensagem = value;
		}

		public function get tipo():String
		{
			return _tipo;
		}

		public function set tipo(value:String):void
		{
			_tipo = value;
		}

		public function get conteudo():Object
		{
			return _conteudo;
		}

		public function set conteudo(value:Object):void
		{
			_conteudo = value;
		}


	}
	
}

