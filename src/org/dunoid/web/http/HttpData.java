package org.dunoid.web.http;

import java.util.HashMap;
import java.util.Map;


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
	public static final String httpHeader(HttpCode status, MIME_Type mime){
		return("HTTP/1.1 "+status
				+"\nContent-Type: "+mime.toString()+" \n\n");
	}
	
	public static Map<String, MIME_Type>  MIME_types;
	
	static{
		MIME_types = new HashMap<>();
	}
	
	public static enum MIME_Type{
		HTML("text/html", "html", "htm", "htx"),
		CSS("text/css", "css"),
		JSON("text/json", "json"),
		CALENDAR("text/calendar", "ics"),
		CSV("text/csv", "csv"),
		TEXT_PLAIN("text/plain", "txt", "js"),
		
		SVG("image/svg+xml", "svg"),
		PNG("image/png", "png"),
		JPEG("image/jpeg", "jpg", "jpeg"),
		TIFF("image/tiff", "tiff"),
		BTIF("image/prs.btif", "btif"),
		ICON("image/x-icon", "ico"),
		GIF("image/gif", "gif"),
		
		MP4("audio/mp4", "mp4"),
		MPEG("audio/mpeg", "mpeg"),
		OGG("audio/ogg", "ogg"),
		MIDI("audio/midi", "mid", "midi");
		
		private String type;
		MIME_Type(String s, String... extensions){
			type = s;
			for(String ext : extensions){
				MIME_types.put(ext, this);
			}
		}
		@Override
		public String toString(){
			return type;
		}
	}
	public static MIME_Type MIMEfromFilename(String file){
		String[] stringlist = file.split(".");
		String ext = stringlist[stringlist.length-1];
		if(stringlist.length > 1 && MIME_types.containsKey(ext)){
			return MIME_types.get(ext);
		}
		else {
			return MIME_Type.TEXT_PLAIN;
		}
	}
	
}
