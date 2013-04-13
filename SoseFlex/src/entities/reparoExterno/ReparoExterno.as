package entities.reparoExterno
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.reparoExterno.ReparoExterno")]	
	public class ReparoExterno
	{
		private var _id:Number;
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
	
	}
}