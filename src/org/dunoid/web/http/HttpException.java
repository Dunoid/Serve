package org.dunoid.web.http;

import org.dunoid.web.http.HttpData.HttpCode;

public class HttpException extends Exception {
	private static final long serialVersionUID = 1L;
	private HttpCode errCode;
	
	public HttpException(HttpCode errCode, String message){
		super(message);
		this.errCode = errCode;
	}
	
	public HttpException(HttpCode errCode){
		super();
		this.errCode = errCode;
	}
	
	public HttpCode getErrCode(){
		return errCode;
	}
	@Override
	public String toString(){
		return String.format("%s: %s - %s", 
				getClass().getName(), errCode.toString(), getMessage());
	}
}
