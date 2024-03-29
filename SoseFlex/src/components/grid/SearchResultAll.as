package components.grid
{
	import components.SearchInput;
	
	import flash.display.DisplayObjectContainer;
	import flash.text.TextFormat;
	
	import modules.administration.ImportBOM;
	
	import mx.controls.AdvancedDataGrid;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridGroupItemRenderer;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridListData;
	import mx.controls.textClasses.TextRange;
	import mx.core.UIComponent;
	
	/**
	 * This class extends a Label and is used for render MantisTable cell search
	 * result.
	 * @see MantisTable
	 */
	public class SearchResultAll extends AdvancedDataGridGroupItemRenderer

	{
		/**
		 * Default constructor.
		 */
		public function SearchResultAll()
		{
			super();
		}
		
		/**
		 * Text range used to highlight search result.
		 */
		private var textRange:TextFormat;
		
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
			
			if (null == listData) {
				return;
			}
			var listData1:AdvancedDataGridListData = listData as AdvancedDataGridListData;
			var advancedDataGrid:AdvancedDataGrid = listData1.owner as AdvancedDataGrid;

			
			var component:Object = this.parentDocument as Object;
			
			if (component == null) return;
			
			var searchComponent:SearchInput = component.searchInput;
			
			var token:String = searchComponent.searchInput.text;
			
			// Return if it's empty
			if (token == "" || token == "Buscar...") return;
			
			// Where it is
//			var begin:int = this.text.toLowerCase().indexOf(token.toLowerCase());
			var begin:int = this.label.text.toLowerCase().indexOf(token.toLowerCase());
			
			if (begin > -1) {
				
				// Highlight it
				textRange = this.label.getTextFormat( begin, begin + token.length);
				
				//textRange.color = "green"; 
				
				textRange.bold  = true;
				
				textRange.underline = true;
				this.label.setTextFormat(textRange, begin, begin + token.length);
				validateProperties();
			}
			
		}

	}
}