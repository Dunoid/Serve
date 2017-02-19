package org.dunoid.web.http;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dunoid.web.http.HttpData.HttpCode;

public class HttpFileResponse implements HttpResponse {
	private InputStream in;
	public HttpFileResponse(InputStream in) {
		this.in = in;
	}
	@Override
	public void write(OutputStream out, int tabulation) throws IOException{
		out.write(HttpData.httpHeader(HttpCode.ACCEPTED).getBytes());
		byte[] buffer = new byte[2048];
		int count;
		while((count = in.read(buffer)) > 0){
			out.write(buffer, 0, count);
		}
		in.close();
	}
}