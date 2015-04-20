package components.tab
{
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	import spark.filters.GlowFilter;
	
	/**
	 * Tab class responsable for draw a tab using graphics.
	 */
	public class Tab extends Canvas
	{
		// Exposed attributes
		
		protected var tabWidth:int = 100;
		
		protected var tabHeight:int = 20;
		
		protected var tabAngle:int = 10;
		
		protected var tabCaption:String = "???";
		
		protected var tabIconPath:String = "";
		
		protected var isCurrent:Boolean = false;
		
		// Control attributes
		
		protected var startTabPosition:int = 15;
		
		protected var glowFilter:GlowFilter;
		
		public var caption:Label;
		
		public var tabIcon:Image;
		
		protected const verticalSpace:int = 25;
		
		protected var isEnabled:Boolean = true;
		
		/**
		 * Constructor used for Action Script creation.
		 */
		public function Tab(tabWidth:int, tabHeight:int, tabAngle:int, tabCaption:String, tabIconPath:String, isCurrent:Boolean)
		{
			super();
			
			glowFilter = new GlowFilter(0x000000, 1, 8.0, 8.0, 1, 1, false, false);
			
			filters = [glowFilter];
			
			// Variables
			
			this.tabWidth = tabWidth;
			
			this.tabHeight = tabHeight;
			
			this.tabAngle = tabAngle;
			
			this.tabCaption = tabCaption;
			
			this.tabIconPath = tabIconPath;
			
			this.isCurrent = isCurrent;
			
			// Hand cursor
			
			this.useHandCursor = true;
			
			this.mouseChildren = false;
			
			this.buttonMode = true;
			
			// Add label and icon
			
			addLabel(tabCaption);
			
			addIcon(tabIconPath);
			
			// Refresh height
			
			this.height = verticalSpace + tabHeight + 10;
		}
		
		/**
		 * Adds label child.
		 */
		private function addLabel(caption:String):void
		{
			this.caption = new Label();
			
			this.tabCaption = caption;
			
			this.caption.text = caption;
			
			this.caption.setStyle("fontFamily", "Verdana");
			
			this.caption.addEventListener(MouseEvent.CLICK, onComponentClick);
			
			this.addChild(this.caption);
		}
		
		/**
		 * Adds icon child.
		 */
		private function addIcon(tabIconPath:String):void
		{
			this.tabIcon = new Image();
			
			this.tabIconPath = tabIconPath;
			
			this.tabIcon.load(tabIconPath);
			
			this.addChild(this.tabIcon);
			
			this.tabIcon.addEventListener(MouseEvent.CLICK, onComponentClick);
		}
		
		/**
		 * Handles label and icon click.
		 */
		private function onComponentClick(event:MouseEvent):void
		{
			this.dispatchEvent(event);
		}
		
		/**
		 * This method triggers the tab drawing.
		 */
		public function draw(startTabPosition:int):void
		{
			this.startTabPosition = startTabPosition;
			
			if (!isCurrent) {
				
				drawTab();
			}
			else {
				
				drawCurrentTab();
			}
		}
		
		/**
		 * Function responsable for draw this tab (this tab isn't the current
		 * one).
		 */
		protected function drawTab():void
		{
			graphics.clear();
			
			graphics.lineStyle(1, 0x003461, 1);
			
			graphics.beginFill(0xC1DAFA, 1);
			
			graphics.moveTo(startTabPosition, 0);
			
			graphics.lineTo(startTabPosition, verticalSpace);
			
			
			// Tab
			
			startTabPosition += tabAngle;	// Angle
			
			caption.y = verticalSpace + 2;
			
			caption.x = startTabPosition + 25;
			
			tabIcon.y = verticalSpace + 1;
			
			tabIcon.x = startTabPosition + 5;
			
			graphics.lineTo(startTabPosition, verticalSpace + tabHeight);
			
			startTabPosition += tabWidth;	// Tab size
			
			graphics.lineTo(startTabPosition, verticalSpace + tabHeight);
			
			startTabPosition += tabAngle;	// Angle
			
			graphics.lineTo(startTabPosition, verticalSpace);
			
			
			graphics.lineTo(startTabPosition, 0);
		}
		
		/**
		 * Function responsable for draw this tab (this tab is the current one).
		 */
		protected function drawCurrentTab():void
		{
			graphics.clear();
			
			graphics.lineStyle(1, 0x003461, 1);
			
			graphics.beginFill(0xC1DAFA, 1);
			
			graphics.moveTo(0, 0);
			
			graphics.lineTo(0, verticalSpace);
			
			
			// Tab
			
			graphics.lineTo(startTabPosition, verticalSpace);
			
			startTabPosition += tabAngle;	// Angle
			
			caption.y = verticalSpace + 1;
			
			caption.x = startTabPosition + 25;
			
			tabIcon.y = verticalSpace;
			
			tabIcon.x = startTabPosition + 5;
			
			graphics.lineTo(startTabPosition, verticalSpace + tabHeight);
			
			startTabPosition += tabWidth;	// Tab size
			
			graphics.lineTo(startTabPosition, verticalSpace + tabHeight);
			
			startTabPosition += tabAngle;	// Angle
			
			graphics.lineTo(startTabPosition, verticalSpace);
			
			
			graphics.lineTo(this.width, verticalSpace);
			
			graphics.lineTo(this.width, 0);
			
			graphics.lineTo(0, 0);
		}
		
		/**
		 * Returns the final X position after drawing.
		 */
		public function getFinalTabPosition():int
		{
			return startTabPosition;
		}
		
		/**
		 * Check if this tab is the current one.
		 */
		public function getIsCurrent():Boolean
		{
			return isCurrent;
		}
		
		/**
		 * Change "current tab" status.
		 */
		public function setIsCurrent(isCurrent:Boolean):void
		{
			this.isCurrent = isCurrent;
		}
		
		/**
		 * Change enabled status.
		 */
		public function setIsEnabled(isEnabled:Boolean):void
		{
			this.isEnabled = isEnabled;
		}
		
		/**
		 * Returns enabled status.
		 */
		public function getIsEnabled():Boolean
		{
			return isEnabled;
		}
	}
}