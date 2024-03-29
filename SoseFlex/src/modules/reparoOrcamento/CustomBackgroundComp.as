package modules.reparoOrcamento
{
	import mx.controls.Label; 
	import mx.controls.DataGrid; 
	import mx.controls.dataGridClasses.*; 
	import flash.display.Graphics; 
	
	public class CustomBackgroundComp extends Label { 
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void 
		{ 
			super.updateDisplayList(unscaledWidth, unscaledHeight); 
			var g:Graphics = graphics; 
			g.clear(); 
			var grid1:DataGrid = DataGrid(DataGridListData(listData).owner); 
			if (grid1.isItemSelected(data) || grid1.isItemHighlighted(data)) 
				return; 
			if (data[DataGridListData(listData).dataField] < 0) 
			{ 
				g.beginFill(0xFF0000); 
				g.drawRect(0, 0, unscaledWidth, unscaledHeight); 
				g.endFill(); 
			} 
			
			if (data[DataGridListData(listData).dataField] >= 0 &&data[DataGridListData(listData).dataField] < 6) 
			{ 
				g.beginFill(0x00FF00); 
				g.drawRect(0, 0, unscaledWidth, unscaledHeight); 
				g.endFill(); 
			} 
		} 
	} 
}
