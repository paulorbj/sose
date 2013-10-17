package components.grid
{
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	
	public class AdvancedDataGridColumnNested extends AdvancedDataGridColumn
	{
		public function AdvancedDataGridColumnNested(columnName:String=null)
		{
			super(columnName);
		}
		
		private function applyFormatting(data:String):String
		{
			if (formatter != null && data != null)
			{
				var label:String = formatter.format(data);
				
				// Silently ignore formatter errors. For example, errors occur when
				// the property corresponding to the dataField is not present in the
				// row object i.e. it'll be empty and it's not anybody's fault that
				// it's an error.
				if (label)
					return label;
				
				return null;
			}
			else
			{
				return data;
			}
		}
		
		override public function itemToLabel(data:Object, withFormatting:Boolean=true):String
		{
			if (dataField != null && dataField.indexOf(".") != -1)
			{ 
				
				var fields:Array = dataField.split(".");
				try
				{
					var currentData:Object = data;
					for each(var field:String in fields)
					currentData = currentData[field];
					
					var label:String;
					if (currentData is String)
						label = String(currentData);
					
					label = currentData.toString();
				}
				catch(e:Error)
				{
					return "";
				}
				
				if (withFormatting)
					return applyFormatting(label);
				return label;
			}else{
				return "";
			}
			
			return super.itemToLabel(data, withFormatting);
		}
		
	}
}