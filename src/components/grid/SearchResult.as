package components.grid
{
	import components.SearchInput;
	
	import flash.display.DisplayObjectContainer;
	
	import mx.controls.Label;
	import mx.controls.textClasses.TextRange;
	import mx.core.UIComponent;
	
	/**
	 * This class extends a Label and is used for render MantisTable cell search
	 * result.
	 * @see MantisTable
	 */
	public class SearchResult extends Label
	{
		/**
		 * Default constructor.
		 */
		public function SearchResult()
		{
			super();
		}
		
		/**
		 * Text range used to highlight search result.
		 */
		private var textRange:TextRange;
		
		/**
		 * This method is responsable for highlight the result in this cell.
		 */
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			// Still run the function we're overriding
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			// What we're looking for
			//			var mantisTable:Object = this.parent.parent;
			//			
			//			var component:Object = mantisTable.parent as Object;	// UI component
			
			var component:Object = this.parentDocument as Object;
			
			if (component == null) return;
			
			var searchComponent:SearchInput = component.searchInput;
			
			if(searchComponent == null) return;
			
			var token:String = searchComponent.searchInput.text;
			var tokens:Array = token.split(" ");
			var tokensEncontrados:Array = new Array();
			
			for each(var str:String in tokens){
				if(str != ""){
					tokensEncontrados.push(str);
				}
			}
			// Reset format
			textRange = new TextRange(this, false, -1, -1);	// Select all
			
			if (textRange.beginIndex == textRange.endIndex) return;
			
			textRange.color = this.getStyle("color");
			
			textRange.fontWeight = this.getStyle("fontWeight");
			
			textRange.textDecoration = this.getStyle("textDecoration");
			
			for each (var strToken:String in tokensEncontrados){
				// Return if it's empty
				if (strToken == "" || strToken == "Buscar...") return;
				
				// Where it is
				var begin:int = this.text.toLowerCase().indexOf(strToken.toLowerCase());
				
				if (begin > -1) {
					
					// Highlight it
					textRange = new TextRange(this, true, begin, begin + strToken.length);
					
					//textRange.color = "green"; 
					
					textRange.fontWeight = "bold";
					
					textRange.textDecoration = "underline";
				}
			}
		}
		
	}
}