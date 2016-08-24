package org.dunoid.web.http;

import java.net.Socket;

/**
 * An HTTP Request.  It will always have a method and URI, 
 * but the other attributes are optional
 * @author devin
 *
 */
public class HttpRequest {
	public enum Method {
		POST,
		GET
	}
	Method method;
	RequestURI uri;
	String userAgent;
	String[] acceptedFormats;
	
	public static HttpRequest fromClient(Socket client) throws Exception{
		//TODO implement this
		return null;
	}
	public HttpRequest(Method method, RequestURI path){
		this.uri = path;
		this.method = method;
	}
	
	public Method getMethod() {
		return method;
	}
	public RequestURI getUri() {
		return uri;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String[] getAcceptedFormats() {
		return acceptedFormats;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setAcceptedFormats(String[] acceptedFormats) {
		this.acceptedFormats = acceptedFormats;
	}
	
	
	
}
