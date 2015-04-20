package components.messages
{
	import mx.containers.HBox;
	
	/**
	 * Base message class.
	 */
	public class BaseMessage extends HBox
	{
		
		private var _message:String;
		private var _type:MessageType;
				
		public function BaseMessage()
		{
			super();
		}

		public function get type():MessageType
		{
			return _type;
		}

		public function set type(value:MessageType):void
		{
			_type = value;
		}

		public function get message():String
		{
			return _message;
		}

		public function set message(value:String):void
		{
			_message = value;
		}

	}
}