package org.dunoid.web.http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.dunoid.web.http.HttpData.HttpCode;

public class BasicFileRequestHandler implements RequestHandler {
	private Path home;
	public BasicFileRequestHandler(Path homeDirectory){
		home = homeDirectory;
	}
	public BasicFileRequestHandler(String path) {
		this(Paths.get(path));
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
				InputStream in = new FileInputStream(requestPath.toFile());
				return new HttpFileResponse(in);
			} catch (FileNotFoundException e) {
				throw new HttpException(HttpCode.NOT_FOUND);
			}
		}
	}

}
