package org.dunoid.web.http;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpResponse {
	/**
	 * Write the object as HTTP text
	 * @param stream The stream to write to
	 * @param tabulation The level of tabs to indent in
	 * @throws IOException Thrown by the stream if there was a problem
	 */
	public abstract void 
	write(OutputStream stream, int tabulation) throws IOException;
}
