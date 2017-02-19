package org.dunoid.web.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dunoid.web.html.HtmlArray;
import org.dunoid.web.html.HtmlObject;
import org.dunoid.web.html.HtmlPage;
import org.dunoid.web.http.ExceptionHandler;
import org.dunoid.web.http.HttpException;
import org.dunoid.web.http.RequestHandler;
import org.dunoid.web.http.HttpData.HttpCode;
import org.dunoid.web.server.Server;
import org.dunoid.web.server.ThreadedServer;

public class ServerTest {
	public static void main(String[] args){
		
		final RequestHandler rHandler = (request)-> {
			String name = null;
			if( request.getUri().matches("hello", "*", "say") ){
				name = request.getUri().get(1).getFileName();
			}
			else if(request.getUri().matches("")){
				name = "root";
			}
			else {
				throw new HttpException(HttpCode.NOT_FOUND,
						request.getUri().toString());
			}

			Map<String, String> bodyProps = new HashMap<>();
			bodyProps.put("class", "object-class");
			
			HtmlArray body = new HtmlArray();
			body.addAll(
					new HtmlObject(
							"h1", null, "Hello, "+name+"!"),
					new HtmlObject(
							"p", null, "I don't know what else to do"));
			
			return new HtmlPage(
					HttpCode.OK, 
					new HtmlObject("title", null, "Hello, "+name+"!"), 
					body);
		};
		
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
							"p", null, "There was an error: "+e.toString()));
			
			return new HtmlPage(e.getErrCode(), head, body);
		};
		try {
			Server server = new ThreadedServer(4242, rHandler, eHandler);
			server.loop();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
