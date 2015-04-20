package components
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.controls.ComboBox;
	
	public class SmartCombo extends ComboBox
	{
		private var _trackText:String = "";
		private var _caseSensitiveSearching : Boolean = true;
		
		public function SmartCombo()
		{
			//TODO: implement function
			super();   
		}
		
		public function set caseSensitiveSearching (bool : Boolean) : void {
			_caseSensitiveSearching = bool;      
		}
		
		public function get caseSensitiveSearching () : Boolean {
			return _caseSensitiveSearching;      
		}
		
		override protected function textInput_changeHandler(event:Event):void {
			_trackText = this.textInput.text;
			
			// variables used in the loop
			var label : String = null;
			var matchingIdx : int = 0;
			var foundMatch : Boolean = false;
			var searchString : String = this.textInput.text;
			
			if ( caseSensitiveSearching == false ) searchString = searchString.toLowerCase();
			
			
			// using a for each loop on dataProvider does not strongly 
			// couple to it only being an ArrayCollection... simlar 
			// with weak typing on the items in the set
			for each ( var item : Object in this.dataProvider ) 
			{
				// using itemToLabel() checks a few things like
				// if the item is a String, or it there's a 
				// labelFunction being used
				label = this.itemToLabel( item );
				
				
				// if searching should not be case sensitive
				// do a toLowerCase() on label
				if ( this.caseSensitiveSearching == false )
				{
					label = label.toLowerCase();
				}
				
				
				// find the first item that starts with searchString
				// if there's a match, break out of the loop
				if ( label.substr( 0, searchString.length ) == searchString )
				{
					this.dropdown.selectedIndex = matchingIdx;
					this.dropdown.scrollToIndex( matchingIdx );
					foundMatch = true
					break;
				}
				matchingIdx++;
			}
			
			
			// if there was no match found set selectedIndex to -1 
			// (unselect the list)
			if ( foundMatch == false ) 
			{
				this.dropdown.selectedIndex = -1;
			}
		}
		
		override public function close(trigger:Event=null):void{ 
			super.close(trigger);
			if (this.text == "")
			{
				this.selectedIndex = 0;
			}
			this.editable = false;
		}
		
		override protected function focusOutHandler(event:FocusEvent):void
		{
			super.focusOutHandler(event);
			if (this.text == "")
			{
				this.selectedIndex = 0;
			}
			this.textInput.selectionEndIndex = this.textInput.width;
			this.editable = false;     
		}
		
		override protected function focusInHandler(event:FocusEvent):void
		{
			super.focusInHandler(event);
			this.editable = true;
			this.textInput.setFocus();
			this.open();
		}
		
		
	}
}