package utils
{
	import components.messages.MessageType;
	
	import entities.administrativo.Usuario;
	
	import flash.events.EventDispatcher;
	
	import modules.login.events.BlockedUserEvent;
	import modules.login.events.ForgotPasswordEvent;
	import modules.login.events.InvalidUserEvent;
	import modules.login.events.LoggedUserEvent;
	
	import mx.core.FlexGlobals;
	import mx.managers.CursorManager;
	import mx.resources.ResourceManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	
	/**
	 * Class responsible for all authentication operations
	 */
	public class AuthenticationUtils
	{
		
		
		/** Remote service responsible for retrieve the user information */
		private static var userService:RemoteObject = null;
		
		/** Current user object */
		public static var _currentUser:Usuario = null;
		
		/** The last EventDispatcher that made an authentication call */
		private static var lastCaller:EventDispatcher = null;
		
		
		/**
		 * Get the current logged User
		 */
		public static function get currentUser():Usuario
		{
			return _currentUser;
		}
		
		public static function set currentUser(user:Usuario):void
		{
			_currentUser = user;
		}
		
		/**
		 * Initialize the remote objects parameters
		 */
		private static function initializeUserService():void
		{
			userService = new RemoteObject();
			
			// Set the remote destination
			userService.destination = "usuarioService";
			userService.endpoint =  ResourceManager.getInstance().getString('ConfigurationResource','amf');
			
			// Add result and fault listeners
			userService.logar.addEventListener(ResultEvent.RESULT, handleLoginUserResult);
			userService.logar.addEventListener(FaultEvent.FAULT, handleLoginUserException);
			
			userService.sendPassword.addEventListener(ResultEvent.RESULT, handlesendPasswordResult);
			userService.sendPassword.addEventListener(FaultEvent.FAULT, handlesendPasswordException);
		}
		
		/*
		* Begin Event Handlers
		*/
		
		private static function handleLoginUserResult(event:ResultEvent):void
		{
			currentUser = event.result as Usuario;
			
			if(currentUser.id != 0 && !currentUser.bloqueado){
				lastCaller.dispatchEvent(new LoggedUserEvent(LoggedUserEvent.LOGGED_USER,currentUser, true, false));
			} else if (currentUser.bloqueado) {
				lastCaller.dispatchEvent(new BlockedUserEvent());
			} else{
				lastCaller.dispatchEvent(new InvalidUserEvent());
			}
		}
		
		private static function handleLoginUserException(event:FaultEvent):void
		{
			
			// Return the cursor to its original state
			CursorManager.removeAllCursors();
			
			// Debug output
			trace("[LoginFault] [" +
				event.fault.faultCode + "] " + event.fault.faultString + " (" +
				event.fault.faultDetail + ")");
			
		}
		
		private static function handlesendPasswordResult(event:ResultEvent):void
		{
			currentUser = event.result as Usuario;
			
			if(currentUser.id != 0){
				lastCaller.dispatchEvent(new ForgotPasswordEvent());
			}else{
				lastCaller.dispatchEvent(new InvalidUserEvent());
			}
		}
		
		private static function handlesendPasswordException(event:FaultEvent):void
		{
			
			// Return the cursor to its original state
			CursorManager.removeAllCursors();
			
			FlexGlobals.topLevelApplication.messageFactory.addMessage(ResourceManager.getInstance().getString('ApplicationResource','EMAIL_ERROR'), MessageType.ERROR);
			
			// Debug output
			trace("[SendEmailFault] [" +
				event.fault.faultCode + "] " + event.fault.faultString + " (" +
				event.fault.faultDetail + ")");
			
		}
		
		/*
		* End Event Handlers
		*/
		
		public static function doLogin(caller:EventDispatcher,username:String = "",password:String = ""):void
		{
			// Set the last EventDispatcher caller
			lastCaller = caller;			
			initializeUserService();
			userService.loginUser(username,password);
		}
		
		
		
		/**
		 * Start the logout process
		 *
		 * <p>Call the logout remote method and informs the application</p>
		 *
		 * @param caller:EventDispatcher The event dispatcher calling the authentication process.<br>
		 * 								 It MUST be a valid, not null object
		 */
		public static function invalidateUser(caller:EventDispatcher):void
		{
			// Set the last EventDispatcher caller
			lastCaller = caller;

			currentUser = null;
		}
		
		public static function sendPassword(caller:EventDispatcher,username:String = ""):void{
			// Set the last EventDispatcher caller
			lastCaller = caller;			
			initializeUserService();
			userService.sendPassword(username);
		} 
	}
}