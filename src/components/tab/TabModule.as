package components.tab
{
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	/**
	 * This class extends a Canvas and is reponsable for control the content of
	 * a Tab.
	 * @see Canvas
	 * @see Tab
	 */
	public class TabModule extends Canvas
	{
		//
		// Exposed attributes
		//
		
		/** Tab width */
		public var tabWidth:int = 50;
		
		/** Tab height */
		public var tabHeight:int = 20;
		
		/** Tab left and right angle */
		public var tabAngle:int = 10;
		
		/** Tab caption */
		public var tabCaption:String = "???";
		
		/** Path for tab icon */
		public var tabIconPath:String = "";
		
		/** Indicates if this is the current tab */
		public var isCurrent:Boolean = false;
		
		/** Indicates if the tab is enabled */
		private var isEnabled:Boolean = true;
		
		
		//
		// Components
		//
		
		/** Tab component */
		private var tab:Tab;
		
		/**
		 * Default constructor.
		 */
		public function TabModule()
		{
			super();
			
			this.addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete);
		}
		
		/**
		 * Handles creation complete event. Is responsable for add a tab child
		 * and resize the content component.
		 */
		private function onCreationComplete(event:FlexEvent):void
		{
			tab = new Tab(tabWidth, tabHeight, tabAngle, tabCaption, tabIconPath, isCurrent);
			
			this.addChild(tab);
			
			// Get content
			
			for (var i:int = 0; i < this.numChildren; i++) {
				
				var tabChild:Tab = this.getChildAt(i) as Tab;
				
				if (tabChild != null) continue;
				
				var content:UIComponent = this.getChildAt(i) as UIComponent;
				
				if (content == null) return;
				
				content.y = tab.height;
				
				content.percentWidth = 100;
				
				content.height = parent.height - tab.height;
				
				//this.addChild(content);
			}
			
			this.setIsEnabled(isEnabled);			
			
			this.setIsCurrent(isCurrent);
		}
		
		//
		// Wrappers of Tab class
		//
		
		/**
		 * Wrapper of Tab.getFinalTabPosition() method.
		 * @see Tab
		 */
		public function getFinalTabPosition():int
		{
			return tab.getFinalTabPosition();
		}
		
		/**
		 * Wrapper of Tab.draw() method.
		 * @see Tab
		 */
		public function draw(startTabPosition:int):void
		{
			tab.width = this.width;
			
			tab.draw(startTabPosition);
		}
		
		/**
		 * Wrapper of Tab.setIsCurrent() method.
		 * @see Tab
		 */
		public function setIsCurrent(isCurrent:Boolean):void
		{
			this.isCurrent = isCurrent;
			
			if (tab == null) return;
			
			tab.setIsCurrent(isCurrent);
		}
		
		/**
		 * Return the Tab component.
		 * @see Tab
		 */
		public function getTab():Tab
		{
			return tab;
		}
		
		public function getContent():UIComponent
		{
			for (var i:int = 0; i < this.numChildren; i++) {
				
				var tabChild:Tab = this.getChildAt(i) as Tab;
				
				if (tabChild != null) continue;
				
				var content:UIComponent = this.getChildAt(i) as UIComponent;
				
				return content;
			}
			return null;
		}
		
		/**
		 * Change tab enable style.
		 * @see Tab
		 */
		public function setIsEnabled(isEnabled:Boolean):void
		{
			this.isEnabled = isEnabled;
				
			
			if (tab == null) return;
			
			tab.setIsEnabled(isEnabled);
			tab.buttonMode =  isEnabled;
			
			if (isEnabled) {
				
				tab.caption.alpha = 1.0;
				tab.tabIcon.alpha = 1.0;
			}
			else {
				
				tab.caption.alpha = 0.5;
				tab.tabIcon.alpha = 0.5;
			}
		}
	}
}