package components.pdfViewer
{
	import flash.events.Event;

	public class PDFViewerEvent extends Event
	{
		public static const ABRIR_VIEWER:String = "abrirViewerEvent";
		public static const FECHAR_VIEWER:String = "fecharViewerEvent";
		
		private	var _urlString:String;
		
		public function get urlString():String
		{
			return _urlString;
		}
		
		public function set urlString(value:String):void
		{
			_urlString = value;
		}
		
		public function PDFViewerEvent(tipo:String,urlString:String =null)
		{
			super(tipo, true, false);
			this._urlString = urlString;
		}

	}
}