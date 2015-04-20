package components.events
{
	import entities.administrativo.Usuario;
	
	import flash.events.Event;
	
	public class UserRegisterNumberModalEvent extends Event
	{
		public static const USER_REGISTER_NUMBER_MODAL:String = "userRegisterNumberModal";
		
		private	var _usuario:Usuario;

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
		}

		
		public function UserRegisterNumberModalEvent(type:String,
													 usuario:Usuario,
													 bubbles:Boolean = true,
													 cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
			this._usuario = usuario;
		}
		
		public override function clone():Event
		{
			return new UserRegisterNumberModalEvent(this.type, this._usuario, this.bubbles, this.cancelable);
		}
		
	}
}