package org.dunoid.web.http;

public class HttpData {
	public static enum HttpCode {
		/**
		 * 100 Continue
		 */
		CONTINUE(100),
		/**
		 * 101 Switching protocols
		 */
		SWITCHING_PROTOCOLS(101),
		/**
		 * 102 Processing
		 */
		PROCESSING(102),
		
		/**
		 * 200 OK
		 */
		OK(200),
		/**
		 * 201 Created
		 */
		CREATED(201),
		/**
		 * 202 Accepted
		 */
		ACCEPTED(202),
		/**
		 * 203 Non authoritative
		 */
		NON_AUTHORITATIVE(203),
		/**
		 * 204 No content
		 */
		NO_CONTENT(204),
		/**
		 * 205 Reset Connection
		 */
		RESET_CONTENT(205),
		/**
		 * 206 Partial content
		 */
		PARTIAL_CONTENT(206),
		/**
		 * 207 Multi Status
		 */
		MULTI_STATUS(207),
		/**
		 * 208 Already Reported
		 */
		ALREADY_REPORTED(208),
		
		/**
		 * 200 Multiple Choices
		 */
		MULTIPLE_CHOICES(300),
		
		/**
		 * 400 Bad Request
		 */
		BAD_REQUEST(400),
		/**
		 * 401 Unauthorized
		 */
		UNAUTHORIZED(401),
		/**
		 * 402 Payment required
		 */
		PAYMENT_REQUIRED(402),
		/**
		 * 403 Forbidden
		 */
		FORBIDDEN(403),
		/**
		 * 404 Not found
		 */
		NOT_FOUND(404),
		/**
		 * 405 Method not allowed
		 */
		METHOD_NOT_ALLOWED(405),
		
		/**
		 * 500 Internal error
		 */
		INTERNAL_ERROR(500);
		
		private int code;
		HttpCode(int code){
			this.code = code;
		}
		public int code(){
			return code;
		}
		@Override
		public String toString(){
			return Integer.toString(code)+"/"+super.name();
		}
	}
	public static final String httpHeader(HttpCode status, String mime){
		return("HTTP/1.1 "+status
				+"\nContent-Type: "+mime+" \n\n");
	}
	
	public static String[] img_extensions = {
		"png",
		"svg",
		"jpg",
		"bmp",
		"jpeg",
		"tif", 
		"gif"
	};
	public static String MIMEfromFilename(String file){
		file = file.toLowerCase();
		for(String s : img_extensions){
			if(file.endsWith('.'+s)){
				if(s.equals("svg")){
					s = "svg+xml";
				}
				return "image/"+s;
			}
		}
		if(file.endsWith(".html")){
			return "text/html";
		}
		else if(file.endsWith(".css")) {
			return "text/css";
		}
		else if(file.endsWith(".json")){
			return "text/json";
		}
		else {
			return "text/plain";
		}
	}
	
}
