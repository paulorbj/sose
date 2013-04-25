package components.messages
{
	import flash.events.Event;
	
	import mx.containers.HBox;
	import mx.controls.Image;
	import mx.controls.Text;
	import mx.effects.Effect;
	import mx.effects.Pause;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;

	/**
	 * Creates a container with icon and message.
	 * @see IconContainer
	 * @see MessageText
	 */
	public class TempMessage extends BaseMessage
	{
		/**
		 * Delay in miliseconds.
		 */
		private var delay:Number;
		
		/**
		 * Constructor.
		 */
		public function TempMessage(message:String,
									type:MessageType,
									delay:Number)
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
			
			this.delay = delay;
			
			// Image
			
			var iconContainer:IconContainer = new IconContainer(type.iconPath(), 50, 3);
			
			// Label
			
			var label:MessageText = new MessageText();
			
			label.text = message;
			
			// HBox
			
			this.addChild(iconContainer);
			
			this.addChild(label);
			
			//this.setStyle("backgroundColor", type.getColor());
			
			this.addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete);
		}
		
		/**
		 * Handle creation complete and start countdown (Pause effect).
		 * @see Pause
		 */
		private function onCreationComplete(event:FlexEvent):void
		{
			// Start time
			
			var pause:Pause = new Pause(this);
			
			pause.duration = delay;
			
			pause.addEventListener(EffectEvent.EFFECT_END, onEffectEnd);
			
			pause.play();
		}
		
		/**
		 * Dispatch close event.
		 * @see CloseMessageEvent
		 */
		private function onEffectEnd(event:Event):void
		{
			dispatchEvent(new CloseMessageEvent());
		}
	}
}