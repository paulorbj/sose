package components.messages
{
	import mx.containers.HBox;
	import mx.controls.Image;
	import mx.controls.Text;

	/**
	 * Creates a container with icon, message and close button.
	 * @see IconContainer
	 * @see MessageText
	 * @see CloseButton
	 */
	public class Message extends BaseMessage
	{
		/**
		 * Constructor.
		 */
		public function Message(message:String,
								type:MessageType)
		{
			this.message=message;
			this.type=type;
			
			this.percentWidth = 100;
			
			this.percentHeight = 100;
			
			this.verticalCenter = 0;
			
			this.horizontalCenter = 0;
			
			this.setStyle("verticalAlign", "middle");
			
			this.verticalScrollPolicy = "off";
			
			this.horizontalScrollPolicy = "off";
			
			this.setStyle("paddingLeft", "15");
			
			this.setStyle("paddingRight", "15");
			
			// Close button
			
			
			// Image
			
			var iconContainer:IconContainer = new IconContainer(type.iconPath(), 50, 3);
			
			// Label
			
			var label:MessageText = new MessageText();
			
			label.text = message;
			
			// HBox
			
			this.addChild(iconContainer);
			
			this.addChild(label);
			
			//this.setStyle("backgroundColor", type.getColor());
		}
	}
}