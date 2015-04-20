package br.com.sose.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionToFile extends SimpleMappingExceptionResolver   {

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
	    this.logger.error(buildLogMessage(ex, request), ex);
	}

}
