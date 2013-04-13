package components.messages
{
	/**
	 * Class that enumerate different types of message.
	 */
	public class MessageType
	{
		public static const ERROR:MessageType = new MessageType("error");
		
		public static const WARNING:MessageType = new MessageType("warning");
		
		public static const INFO:MessageType = new MessageType("info");
		
		public static const SUCCESS:MessageType = new MessageType("success");
		
		private var type:String;
		
		/**
		 * Constructor.
		 */
		public function MessageType(type:String)
		{
			this.type = type;
		}
				
		public function iconPath():String
		{
			switch (type) {
				
				case "error":
					return "assets/icons/notification/icon_error.gif";
					
				case "warning":
					return "assets/icons/notification/icon_warning.png";
					
				case "info":
					return "assets/icons/notification/icon_info.png";
					
				case "success":
					return "assets/icons/notification/icon_success.png";
					
				default:
					return "assets/icons/notification/icon_warning.png";
			}
		}
		
		/**
		 * Method that returns the message color based on the current type.
		 */
		public function getColor():String
		{
			/*switch (type) {
				
				case "error":
					return "#EE7D7D";
					
				case "warning":
					return "#FFFF00";
					
				case "info":
					return "#CCCCCC";
					
				default:
					return "#CCCCCC";
			}*/
			return "#ECEDFF";
		}
	}
}