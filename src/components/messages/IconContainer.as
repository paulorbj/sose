package components.messages
{
	import mx.containers.HBox;
	import mx.controls.Image;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	
	import spark.effects.Scale3D;
	import spark.effects.easing.Bounce;
	
	/**
	 * Class used to wrap message icon container and effects.
	 */
	public class IconContainer extends HBox
	{
		private var iconImage:Image;
		
		private var scaleIn:Scale3D;
		
		private var scaleOut:Scale3D;
		
		private var alertCount:int = 0;
		
		private const scalePercent:Number = 0.6;
		
		/**
		 * Constructor.
		 */
		public function IconContainer(iconPath:String, containerWidth:int, alertCount:int = 0)
		{
			super();
			
			iconImage = new Image();
			
			iconImage.load(iconPath);
			
			this.setStyle("horizontalAlign", "center");
			
			this.width = containerWidth;
			
			this.addChild(iconImage);
			
			if (alertCount > 0) {
				
				this.alertCount = alertCount;
				
				this.addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete);
			}
			
			// Scale in effect
			
			scaleIn = new Scale3D(iconImage);
			
			scaleIn.autoCenterTransform = true;
			
			scaleIn.duration = 700;
			
			scaleIn.scaleXFrom = 1.0;
			
			scaleIn.scaleXTo = scalePercent;
			
			scaleIn.scaleYFrom = 1.0;
			
			scaleIn.scaleYTo = scalePercent;
			
			scaleIn.scaleZFrom = 1.0;
			
			scaleIn.scaleZTo = scalePercent;
			
			scaleIn.easer = new Bounce();
			
			scaleIn.addEventListener(EffectEvent.EFFECT_END, onScaleInEnd);
			
			// Scale out effect
			
			scaleOut = new Scale3D(iconImage);
			
			scaleOut.autoCenterTransform = true;
			
			scaleOut.duration = 700;
			
			scaleOut.scaleXTo = 1.0;
			
			scaleOut.scaleXFrom = scalePercent;
			
			scaleOut.scaleYTo = 1.0;
			
			scaleOut.scaleYFrom = scalePercent;
			
			scaleOut.scaleZTo = 1.0;
			
			scaleOut.scaleZFrom = scalePercent;
			
			scaleOut.easer = new Bounce();
		}
		
		/**
		 * Method responsable for play scale in effect for the first time.
		 */
		private function onCreationComplete(event:FlexEvent):void
		{
			scaleIn.play();
		}
		
		/**
		 * Method responsable for play scale out effect.
		 */
		private function onScaleInEnd(event:EffectEvent):void
		{
			scaleOut.addEventListener(EffectEvent.EFFECT_END, onScaleOutEnd);
			
			scaleOut.play();
		}
		
		/**
		 * Method responsable for play scale in effect.
		 */
		private function onScaleOutEnd(event:EffectEvent):void
		{
			alertCount--;
			
			if (alertCount > 0) {
				
				scaleIn.play();
			}
		}
	}
}