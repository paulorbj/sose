package utils
{
    import flash.events.IOErrorEvent;
    import flash.events.SecurityErrorEvent;
    import flash.net.URLLoader;
    import flash.net.URLRequest;
    import flash.net.URLVariables;

    import mx.logging.AbstractTarget;
    import mx.logging.ILogger;
    import mx.logging.LogEvent;
    import mx.utils.StringUtil;

    /**
     * ILoggingTarget implementation that sends logging details
     * to a remote service.  The following details are sent as
     * request parameters:
     *  - level: the log level as an integer (see LogEventLevel)
     *  - category: the log category
     *  - timestamp: as milliseconds from 1st Jan 1970
     *  - message: the log message
     */
    public class RemoteTarget extends AbstractTarget
    {
        /**
         * Flag to indicate whether remote logging is enabled
         * or not.  The RemoteTarget will automatically disable
         * itself if it encounters any errors.
         */
        private var enabled:Boolean = true;

        //------------------------------------
        //
        //           Constructor
        //
        //------------------------------------

        /**
         * Constructor
         */
        public function RemoteTarget()
        {
            super();
        }


        //------------------------------------
        //
        //           Properties
        //
        //------------------------------------

        //----------------------------------
        //  url
        //----------------------------------

        private var _url:String;
        /**
         * The URL of the service to which to log.
         */
        public function get url():String
        {
            return _url;
        }
        /**
         * @private
         */
        public function set url(value:String):void
        {
            _url = value;
        }


        //------------------------------------
        //
        //        Overridden Functions
        //
        //------------------------------------

        /**
         * @private
         */
        override public function logEvent(event:LogEvent):void
        {
            // if there has been any issue with remote logging
            if (!enabled)
                return;
            // if no url is set then do nothing
            if (!_url || StringUtil.trim(_url).length == 0)
                return;

            // attempt to log to the server
            try
            {
                var variables:URLVariables = new URLVariables();
                variables.level = event.level;
                variables.category = ILogger(event.target).category;
                variables.timestamp = new Date().time;
                variables.message = event.message;
                var request:URLRequest = new URLRequest(_url);
                request.method = "POST";
                request.data = variables;
                var loader:URLLoader = createLoader();
                loader.load(request);
            }
            catch (e:Error)
            {
                enabled = false;
                trace(e.message);
                trace(e.getStackTrace());
            }
        }


        //------------------------------------
        //
        //            Functions
        //
        //------------------------------------

        /**
         * Creates a URLLoader with all the appropriate event listeners.
         */
        private function createLoader():URLLoader
        {
            var loader:URLLoader = new URLLoader();
            loader.addEventListener(IOErrorEvent.IO_ERROR,
                                    ioErrorHandler,
                                    false, 0, true);
            loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR,
                                    securityErrorHandler,
                                    false, 0, true);
            return loader;
        }


        //------------------------------------
        //
        //           Event Handlers
        //
        //------------------------------------

        /**
         * Disables the remote logging and traces debug information
         * when there is an IO error during logging to the remote server.
         */
        private function ioErrorHandler(event:IOErrorEvent):void
        {
            enabled = false;
            trace("ERROR - IO error in RemoteTarget");
            trace(event.text);
        }

        /**
         * Disables the remote logging and traces debug information
         * when there is a security error during logging to the remote
         * server.
         */
        private function securityErrorHandler(event:SecurityErrorEvent):void
        {
            enabled = false;
            trace("ERROR - Security error in RemoteTarget");
            trace(event.text);
        }
    }
}