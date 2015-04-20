package utils
{
	[Bindable]	
	public class Estado
	{
		
		private var _id:Number;

		private var _nome:String;
		
		private var _sigla:String;
		
		public function Estado(id:Number=0,nome:String="",sigla:String="")
		{
			_id = id;
			_nome = nome;
			_sigla = sigla;
			
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

		public function get sigla():String
		{
			return _sigla;
		}

		public function set sigla(value:String):void
		{
			_sigla = value;
		}


	}
}