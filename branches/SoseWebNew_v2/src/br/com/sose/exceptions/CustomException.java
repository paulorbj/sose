package br.com.sose.exceptions;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;  

	public CustomException(String msg){  
		super(msg);  
		this.msg = msg;  
	}  
	public String getMessage(){  
		return msg;  
	}  
}
