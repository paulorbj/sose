package utils
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	
	import mx.resources.ResourceBundle;
	import mx.resources.ResourceManager;
	

	public class ConfigurationUtil
	{
		/** The main resource bundle that holds the localized strings */
		private static var resourceBundle:ResourceBundle;
		
		/** Dispatcher to inform the language change */
		private static var dispatcher:EventDispatcher = null;
		
		/** The current location */
		[Bindable]
		public static var currentLocale:String = "pt_BR";
		
		/**
		 * Loads a new location from an XML file based on the language code
		 * 
		 * @param locale:String The new location (default is brazilian portuguese)
		 * @param callerDispatcher:EventDispatcher The event dispatcher of the caller object,
		 *                                         used to dispatches the language changed event
		 */ 
		public static function loadLocale(locale:String = "pt_BR", callerDispatcher:EventDispatcher = null):void
		{
			// Set the new dispatcher and the new location
			dispatcher = callerDispatcher;
			currentLocale = locale;
			
			// Loads the XML file
			var xmlLoader:URLLoader = new URLLoader();
			xmlLoader.addEventListener(Event.COMPLETE, createBundle);
			xmlLoader.load(new URLRequest("assets/configuration/configuration.xml"));
		}
		
		/**
		 * Create the new resource bundle with the localized strings
		 * 
		 * @param event:Event The event informing that the XML file load is complete
		 */  
		private static function createBundle(event:Event):void
		{
			// Create "core" resource bundle to prevent 'null' instead of '...' when trucation is applied
			var r:ResourceBundle = new ResourceBundle(currentLocale, "core");
			r.content["truncationIndicator"] = '...';
			ResourceManager.getInstance().addResourceBundle(r);
			
			// Loads the XML data
			var propertyList:XMLList = new XML(event.target.data).property;
			
			// Create a new resource bundle for the location
			resourceBundle = new ResourceBundle(currentLocale, "ConfigurationResource");
			
			// Load each property from the XML to the resource bundle
			for each (var property:XML in propertyList)
			{
				resourceBundle.content[property.@key] = property.@value;
			}
			
			// Configure the resource manager
			ResourceManager.getInstance().addResourceBundle(resourceBundle);
			ResourceManager.getInstance().update();
			ResourceManager.getInstance().localeChain = [ currentLocale ];
			
			// Dispatches the event informing the language change
			if (dispatcher != null)
			{
				dispatcher.dispatchEvent(new Event("languageChanged"));
			}
			
			
		}
	}
}