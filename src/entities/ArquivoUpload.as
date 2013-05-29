package entities	
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.utils.ArquivoUpload")]
	public class ArquivoUpload		
	{

		private var _id:Number;	
		private var _nome:String;
		private var _nomeOriginal:String;
		private var _caminho:String;
		private var _identificadorEntidade:Number;
		private var _tipoEntidade:String;
		private var _dataUpload:Date;
		private var _tipoArquivo:String;
		private var _caminhoImagem:String;
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get nomeOriginal():String
		{
			return _nomeOriginal;
		}

		public function set nomeOriginal(value:String):void
		{
			_nomeOriginal = value;
		}

		public function get caminho():String
		{
			return _caminho;
		}

		public function set caminho(value:String):void
		{
			_caminho = value;
		}

		public function get identificadorEntidade():Number
		{
			return _identificadorEntidade;
		}

		public function set identificadorEntidade(value:Number):void
		{
			_identificadorEntidade = value;
		}

		public function get tipoEntidade():String
		{
			return _tipoEntidade;
		}

		public function set tipoEntidade(value:String):void
		{
			_tipoEntidade = value;
		}

		public function get dataUpload():Date
		{
			return _dataUpload;
		}

		public function set dataUpload(value:Date):void
		{
			_dataUpload = value;
		}

		public function get tipoArquivo():String
		{
			return _tipoArquivo;
		}

		public function set tipoArquivo(value:String):void
		{
			_tipoArquivo = value;
		}

		public function get caminhoImagem():String
		{
			_caminhoImagem =  "http://localhost:8080/SoseWeb/arquivo_servilogi/" + _tipoEntidade + "/" + _identificadorEntidade + "/" + _nome;
			return _caminhoImagem;
		}

		public function set caminhoImagem(value:String):void
		{
			_caminhoImagem = value;
		}


	}
	
}

