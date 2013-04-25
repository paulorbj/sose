package utils
{
	import components.messages.MensagemEvent;
	import components.messages.MessageType;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.FileReference;
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.FlexGlobals;
	import mx.events.CloseEvent;
	import mx.formatters.DateFormatter;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
	import mx.resources.ResourceManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	

	public class GeradorRelatorios extends EventDispatcher
	{
		
		private var relatorioService:RemoteObject;
		
		private var fileRef:FileReference;
		
		public function GeradorRelatorios()
		{
			fileRef = new FileReference();
			fileRef.addEventListener(Event.OPEN, openHandler);
		}
		
		public  function gerarRelatorio(objeto:Object, nomeRelatorio:String, valores:ArrayCollection,resultFunction:Function, faultFunction:Function):void {
			var relatorioService:RemoteObject;
			relatorioService = new RemoteObject();
			relatorioService.endpoint = ResourceManager.getInstance().getString('ConfigurationResource','amf');
			relatorioService.destination = "relatorioService";
			relatorioService.gerarRelatorio.addEventListener("result", resultFunction);
			relatorioService.addEventListener("fault", faultFunction);
			relatorioService.channelSet = FlexGlobals.topLevelApplication.channelSet;
			relatorioService.showBusyCursor = true;
			relatorioService.gerarRelatorio(objeto,nomeRelatorio,valores);
		}
		
		private function openHandler(event:Event):void
		{
			dispatchEvent(new MensagemEvent("Texto openHandler gerador", MessageType.INFO, 3000));
		}		
		
		public static function getFormattedDateToWriteFile(date:Date):String
		{
			var date:Date = date;
			var dateF:DateFormatter = new DateFormatter();
			dateF.formatString = "DDMMYYYY - LLNNSS";
			return dateF.format(date);
		}
		
		public  function saveFile(arquivo:ByteArray,nomeArquivo:String):void
		{
			fileRef.save(arquivo, nomeArquivo + " " + getFormattedDateToWriteFile(new Date()) +	".xls");
		}
		

	}
}