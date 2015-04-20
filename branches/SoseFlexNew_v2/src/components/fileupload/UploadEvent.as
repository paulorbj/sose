package components.fileupload
{
	
	import flash.events.Event;
	
	public class UploadEvent extends Event
	{
		public static const UPLOAD_COMPLETO:String = "uploadCompleto";
		public static const UPLOAD_CANCELADO:String = "uploadCancelado";
		public static const UPLOAD_ERRO:String = "uploadErro";
		
		public function UploadEvent(tipo:String)
		{
			super(tipo, true, false);
		}		
		
	}
}