package utils
{
	[Bindable]	
	[RemoteClass(alias="br.com.sose.utils.TipoEmbalagem")]
	public class TipoEmbalagem
	{
		
		private var _id:Number;
		
		private var _nome:String;
		
		public function TipoEmbalagem(id:Number=0, nome:String="")
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