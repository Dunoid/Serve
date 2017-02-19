package org.dunoid.web.test;

import java.io.IOException;

import org.dunoid.web.html.HtmlArray;
import org.dunoid.web.html.HtmlObject;
import org.dunoid.web.html.HtmlPage;
import org.dunoid.web.http.BasicFileRequestHandler;
import org.dunoid.web.http.ExceptionHandler;
import org.dunoid.web.http.RequestHandler;
import org.dunoid.web.server.Server;
import org.dunoid.web.server.ThreadedServer;

public class FileServerTest {
	public static void main(String[] args){
		if(args.length == 0){
			throw new IllegalArgumentException("Provide a server root path");
		}
		RequestHandler req = new BasicFileRequestHandler(args[0]);
		
		final ExceptionHandler eHandler = (e, request)->{
			HtmlObject title = 
					new HtmlObject("title", null, "Error: "+e.getErrCode());
			HtmlArray body = new HtmlArray();
			HtmlArray head = new HtmlArray();
			head.add(title);
			
			body.addAll(
					new HtmlObject(
							"h1", null, "Error: "+e.getErrCode().toString()),
					new HtmlObject(
							"p", null, "There was an error: "+e.toString()) );
			
			return new HtmlPage(e.getErrCode(), head, body);
		};
		
		try {
			Server server = new ThreadedServer(4242, req, eHandler);
			server.loop();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
