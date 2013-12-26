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
		private var _raizURL:String;
		private var _caminhoMiniatura:String;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		
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
			return _caminhoImagem;
		}

		public function set caminhoImagem(value:String):void
		{
			_caminhoImagem = value;
		}

		public function montarURLRelativa():String
		{
			return _tipoEntidade + "/" + _identificadorEntidade + "/" + _tipoArquivo + "/" + _nome;
		}
		
		public function get montarURLCompleta():String
		{
			return _caminho + _tipoEntidade + "/" + _identificadorEntidade + "/" + _tipoArquivo + "/" + _nome;
		}
		
		public function set montarURLCompleta(value:String):void
		{
			_caminhoImagem = value;
		}

		public function get raizURL():String
		{
			return _raizURL;
		}

		public function set raizURL(value:String):void
		{
			_raizURL = value;
		}

		public function get caminhoMiniatura():String
		{
			_caminhoMiniatura = _caminho + montarURLRelativa();
			return _caminhoMiniatura;
		}

		public function set caminhoMiniatura(value:String):void
		{
			_caminhoMiniatura = value;
		}

		public function get isSelected():Boolean
		{
			return _isSelected;
		}

		public function set isSelected(value:Boolean):void
		{
			_isSelected = value;
		}

		public function get isEnabled():Boolean
		{
			return _isEnabled;
		}

		public function set isEnabled(value:Boolean):void
		{
			_isEnabled = value;
		}
		
		

	}
	
}

