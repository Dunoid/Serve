package org.dunoid.web.http;

public interface ExceptionHandler {
	public HttpResponse respond(HttpException exception, HttpRequest request);
}
