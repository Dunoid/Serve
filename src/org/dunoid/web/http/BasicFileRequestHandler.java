package org.dunoid.web.http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dunoid.web.http.HttpData.HttpCode;

/**
 * Exactly what it says on the tin, this is the most basic file
 * server possible.  It automatically searches for an <code>index.html</code>
 * when it encounters a directory, otherwise it returns the file and 
 * {@link HttpCode#OK}/200.  If it can't find the file, it just
 * throws a 404 {@link HttpException}
 * @author Devin Hastings
 */
public class BasicFileRequestHandler implements RequestHandler {
	private Path home;
	/**
	 * Create a new BasicFileRequestHandler, using homeDirectory as 
	 * the root of the file system.
	 * @param homeDirectory The absolute path to your website's files
	 */
	public BasicFileRequestHandler(Path homeDirectory){
		home = homeDirectory;
	}
	/**
	 * The same as {@link #BasicFileRequestHandler(Path)}, but it parses
	 * a String using {@link Paths#get(String, String...)}
	 * @param path The absolute path to your website's files
	 * @param folders More folders that can extend the path.  
	 * These are optional
	 */
	public BasicFileRequestHandler(String path, String... folders) {
		this(Paths.get(path, folders));
	}
	@Override
	public HttpResponse respond(HttpRequest request) throws HttpException {
		Path requestPath = home.resolve(request.uri.toString());
		if(Files.isDirectory(requestPath)){
			requestPath = requestPath.resolve("index.html");
		}
		if(!Files.exists(requestPath)){
			throw new HttpException(HttpCode.NOT_FOUND);
		}
		else {
			try {
				String name=requestPath.getFileName().toString();
				String mimeType=HttpData.MIMEfromFilename(name);
				
				InputStream in = new FileInputStream(requestPath.toFile());
				return new HttpFileResponse(mimeType, in);
			} catch (FileNotFoundException e) {
				throw new HttpException(HttpCode.NOT_FOUND);
			}
		}
	}

}
