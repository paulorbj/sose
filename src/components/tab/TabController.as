package components.tab
{
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.core.UIComponent;
	import mx.effects.Fade;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	
	/**
	 * This class is responsable for controll all tabs.
	 */
	public class TabController extends Canvas
	{
		/** Initial X position where the tabs will be draw */
		private const firstTabPosition:int = 15;
		
		/** Tab stack effect */
		private const tabStack:int = 7;
		
		/**
		 * Control variable for tab drawing.
		 * Keep the X position of the next tab to be draw
		 */
		private var startNextTab:int = firstTabPosition;
		
		/**
		 * Map used for control tab drawing. Keep the X position of a given tab
		 */
		private var tabMap:Dictionary = new Dictionary();
		
		/** Current tab */
		private var currentTabObject:TabModule;
		
		/** Creation order used for redraw tabs and keep the same order */
		private var creationOrder:ArrayCollection;
		
		/** Default contructor */
		public function TabController()
		{
			this.addEventListener(FlexEvent.CREATION_COMPLETE, onCreationComplete);
		}
		
		/**
		 * Handles creation complete event. It's responsable for draw the tabs.
		 */
		private function onCreationComplete(event:FlexEvent):void
		{
			creationOrder = new ArrayCollection();
			
			for (var i:int = 0; i < this.numChildren; i++) {
				
				var tab:TabModule = this.getChildAt(i) as TabModule;
				
				if (tab == null) continue;
				
				tab.width = this.width;
				
				if (i != 0) {
					
					startNextTab -= tabStack;
				}
				
				tab.draw(startNextTab);
				
				tabMap[tab] = startNextTab;	// Save X position
				
				startNextTab = tab.getFinalTabPosition();
				
				tab.addEventListener(MouseEvent.CLICK, onTabClick);
				
				if (tab.isCurrent) {
					
					currentTabObject = tab;
				}
				else {	// Not current
					
					this.setChildIndex(tab, 0);
				}
				
				creationOrder.addItem(tab);
			}
		}
		
		/**
		 * This method is responsable for refresh (redraw) tabs after a click.
		 * Returns the current tab.
		 */
		private function refreshTabs():TabModule
		{
			var currentTab:TabModule = null;
			
			for (var i:int = 0; i < creationOrder.length; i++) {
				
				var tab:TabModule = creationOrder.getItemAt(i) as TabModule;
				
				if (tab == null) continue;
				
				tab.draw(tabMap[tab]);
				
				if (!tab.isCurrent) {
					
					this.setChildIndex(tab, 0);
				}
				else {
					
					currentTab = tab;
				}
			}
			return currentTab;
		}
		
		/**
		 * Handles tab click.
		 */
		private function onTabClick(event:MouseEvent):void
		{
			var target:Tab = event.target as Tab;
			
			if (target == null || target.getIsCurrent() || !target.getIsEnabled()) return;
			
			// Search TabModule
			
			for (var i:int = 0; i < creationOrder.length; i++) {
				
				var tab:TabModule = creationOrder.getItemAt(i) as TabModule;
				
				if (tab.getTab() == target) {
					
					tab.setIsCurrent(true);
					
					currentTabObject.setIsCurrent(false);
					
					currentTabObject = tab;
					
					refreshTabs();
					
					break;
				}
			}
		}
	}
}