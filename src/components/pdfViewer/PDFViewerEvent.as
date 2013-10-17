package components.pdfViewer
{
	import flash.events.Event;
	
	import mx.containers.TitleWindow;

	public class PDFViewerEvent extends Event
	{
		public static const ABRIR_VIEWER:String = "abrirViewerEvent";
		public static const FECHAR_VIEWER:String = "fecharViewerEvent";
		
		private	var _urlString:String;
		private var _modalCorrente:TitleWindow;
		
		public function get urlString():String
		{
			return _urlString;
		}
		
		public function set urlString(value:String):void
		{
			_urlString = value;
		}

		public function get modalCorrente():TitleWindow
		{
			return _modalCorrente;
		}

		public function set modalCorrente(value:TitleWindow):void
		{
			_modalCorrente = value;
		}

		
		public function PDFViewerEvent(tipo:String,urlString:String =null,modal:TitleWindow = null)
		{
			super(tipo, true, false);
			this._urlString = urlString;
			this._modalCorrente = modal;
		}

	}
}