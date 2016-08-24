package org.dunoid.web.http;

public interface RequestHandler {
	public HttpResponse respond(HttpRequest request) throws HttpException;
}
