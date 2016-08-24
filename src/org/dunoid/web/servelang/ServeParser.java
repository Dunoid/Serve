package org.dunoid.web.servelang;

import org.dunoid.web.html.*;

public class ServeParser {
	private enum State {
		BASE_TEXT,
		READING_OBJECT,
		READING_ATTRIBUTES;
	}
	private static final String WHITESPACE = " \t\r\n";
	private static final String OPEN = "[({<";
	private static final String CLOSE = ">})]";
	private static final String NONSTOP = WHITESPACE+OPEN+CLOSE+"|";
	//TODO implement parser
	public static HtmlElement 
	serveToHtml(String format) throws ServeParseException {
		State state = State.BASE_TEXT;
		HtmlArray objects = new HtmlArray();
		int len = format.length();
		char x;
		for(int i = 0; i < len; i++){
			x = format.charAt(i); 
			switch(state){
			case BASE_TEXT:
				break;
			case READING_OBJECT:
				break;
			case READING_ATTRIBUTES:
				 break;
			 default:
				 break;
			}
		}
		return objects;
	}
	
	private static boolean 
	match(char s, final String sequence){
		for(int i = 0; i < sequence.length(); i++){
			if(s == sequence.charAt(i)){
				return true;
			}
		}
		return false;
	}
	
	private static int
	firstOfSequence(String format, int after, final String sequence){
		int out = -1;
		int len = format.length();
		for(int i = after; i < len; i++){
			if( match(format.charAt(i), sequence) ){
				out = i;
			}
		}
		return out;
	}
}
