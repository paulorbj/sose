package components.grid
{
	
	import components.grid.filtros.controles.DataGridColumnFilter;
	
	import flash.display.Sprite;
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.AdvancedDataGrid;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.core.ClassFactory;
		
	import utils.CopyCell;
	
	/**
	 * This is an extension of the DataGrid component used for change row
	 * colors.
	 */
	public class MantisTable extends DataGrid
	{
		/**
		 * Exposed function that defines the row color. It must have the follow
		 * parameters:
		 * private function getRowColor(item:Object, rowIndex:int,
		 *                              dataIndex:int, color:uint):uint {...}
		 */
		public var rowColorFunction:Function;
				
		public function MantisTable()
		{
			super();
			this.itemRenderer =new ClassFactory(CopyCell);
		}
		
		/**
		 * Function responsable for draw the row background color.
		 */
		override protected function drawRowBackground(s:Sprite,
													  rowIndex:int,
													  y:Number,
													  height:Number,
													  color:uint,
													  dataIndex:int):void
		{
			
			if (rowColorFunction != null && dataProvider != null) {
				
				var item:Object;
				
				if (dataIndex < dataProvider.length) {
					
					item = dataProvider[dataIndex];
				}
				
				if (item)
				{
					color = rowColorFunction(item, rowIndex, dataIndex, color);
				}
			}
			
			super.drawRowBackground(s, rowIndex, y, height, color, dataIndex);
		}
		
		/**
		 * 
		 * Filter the DataGrid content.
		 * @see #filterItensByColumns()
		 * 
		 */
		public function filter():void 
		{
			if (dataProvider is ICollectionView)
			{
				dataProvider.filterFunction = filterItensByColumns;
				dataProvider.refresh();
				verticalScrollPosition = 0;
			}
			dispatchEvent(new Event("atualizarLegenda"));
		}
		
		/**
		 * @private
		 * 
		 * This method is called by each DataGrid's row. If it returns 'true', the row will be displayed, 
		 * otherwise it won't.
		 * 
		 * It returns 'true' if the row is in accordance with all columns criteria. That's an important point. It might have 
		 * a criteria in one column, another criteria in other column, and so on. But it's necessary just one column criteria 
		 * "not ok" to return 'false', and so, doesn't display the row.
		 * 
		 * @param item It is a row
		 * 
		 * @return Returns <code>true</code> to displayed rows and <code>false</code> to hidden rows
		 * 
		 * @see DataGridColumnFilter
		 * 
		 */
		private function filterItensByColumns(item:Object):Boolean 
		{
			var displayItem:Boolean = true;
			for each (var column:Object in columns)
			{
				if (column is DataGridColumnFilter && column.hasFilter) 
				{
					displayItem = displayItem && column.filter(item);
				}
			}
			return displayItem;
		}
		
		/**
		 * 
		 * Clean all filters.
		 * 
		 */
		public function cleanFilters():void 
		{
			for each(var column:Object in columns)
			{
				if (column is DataGridColumnFilter) 
				{
					column.hasFilter = false;
				}
			}
			filter();		
		}
		
		/**
		 * 
		 * Filters the columns to return an array with no repeated item labels.
		 * 
		 * @param column The column whose the labels will be returned.
		 * 
		 * @return An ArrayCollection with no repeated item labels in a column.
		 * 
		 */
		public function getExclusiveItemLabels(column:DataGridColumnFilter):ArrayCollection 
		{
			var exclusiveItemLabels:ArrayCollection = new ArrayCollection();
			for each (var item:Object in dataProvider)
			{
				var itemLabel:String = column.getItemLabel(item);
				if (exclusiveItemLabels.contains(itemLabel) == false)
				{
					exclusiveItemLabels.addItem(itemLabel);
				}
			}
			return exclusiveItemLabels;
		}
	}
}