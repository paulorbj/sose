package components.imageviewer
{
	import entities.ArquivoUpload;
	
	import flash.events.Event;
	
	import mx.containers.TitleWindow;

	public class ImagemViewerEvent extends Event
	{
		public static const ABRIR_IMAGEM_VIEWER:String = "abrirImagemViewerEvent";
		public static const FECHAR_IMAGEM_VIEWER:String = "fecharImagemViewerEvent";
		
		private	var _idEntidade:Number;
		private var _tipoEntidade:String;
		private var _modalCorrente:TitleWindow;
		private var _imagemSelecionada:ArquivoUpload;

		public function get modalCorrente():TitleWindow
		{
			return _modalCorrente;
		}

		public function set modalCorrente(value:TitleWindow):void
		{
			_modalCorrente = value;
		}

		public function get idEntidade():Number
		{
			return _idEntidade;
		}

		public function set idEntidade(value:Number):void
		{
			_idEntidade = value;
		}

		public function get tipoEntidade():String
		{
			return _tipoEntidade;
		}

		public function set tipoEntidade(value:String):void
		{
			_tipoEntidade = value;
		}

		public function get imagemSelecionada():ArquivoUpload
		{
			return _imagemSelecionada;
		}

		public function set imagemSelecionada(value:ArquivoUpload):void
		{
			_imagemSelecionada = value;
		}

		
		public function ImagemViewerEvent(tipo:String,tpEnt:String=null,idEnt:Number=0,imgSelec:ArquivoUpload=null,modal:TitleWindow=null)
		{
			super(tipo, true, false);
			this._idEntidade = idEnt;
			this._tipoEntidade = tpEnt;
			this._modalCorrente = modal;
			this._imagemSelecionada = imgSelec;
		}

	}
}