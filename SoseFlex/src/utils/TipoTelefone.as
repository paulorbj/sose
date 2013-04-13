package utils
{
	[Bindable]	
	public class TipoTelefone
	{
		
		private var _id:Number;
		
		private var _nome:String;
		
		public function TipoTelefone(id:Number=0, nome:String="")
		{
			_id = id;
			_nome = nome;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}


	}
}