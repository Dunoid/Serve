package org.dunoid.web.html;

import java.io.IOException;
import java.io.OutputStream;

import org.dunoid.web.http.HttpData;
import org.dunoid.web.http.HttpData.HttpCode;

public class HtmlPage extends HtmlArray {
	private static final long serialVersionUID = 1L;
	private HttpCode status;
	
	public 
	HtmlPage(HttpCode status,HtmlElement headContent, HtmlElement bodyContent){
		this.status = status;
		HtmlObject html = new HtmlObject("html", null);
		
		HtmlObject head = new HtmlObject("head", null);
		head.items().add(headContent);
		
		HtmlObject body = new HtmlObject("body", null);
		body.items().add(bodyContent);
		
		html.items().addAll( head, body);

		HtmlText docType = new HtmlText("<!DOCTYPE HTML>");
		docType.escapeText(false);
		
		addAll(docType, html);
	}
	@Override
	public void write(OutputStream stream, int tabs) throws IOException{
		stream.write(HttpData.httpHeader(status).getBytes());
		super.write(stream, tabs);
	}

}
