package org.dunoid.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.dunoid.web.http.ExceptionHandler;
import org.dunoid.web.http.HttpException;
import org.dunoid.web.http.HttpRequest;
import org.dunoid.web.http.RequestHandler;
import org.dunoid.web.http.RequestURI;
import org.dunoid.web.http.HttpRequest.Method;
import org.dunoid.web.http.HttpData.HttpCode;

public class ThreadedServer implements Server, Runnable{
	private ServerSocket server;
	private final RequestHandler rHandler;
	private final ExceptionHandler eHandler;
	private Socket client;
	
	public 
	ThreadedServer(int port, final RequestHandler r, final ExceptionHandler e)
			throws IOException
	{
		server = new ServerSocket(port);
		rHandler = r;
		eHandler = e;
		client = null;
	}
	private ThreadedServer(ThreadedServer parent, Socket client){
		this.server = parent.server;
		this.rHandler = parent.rHandler;
		this.eHandler = parent.eHandler;
		this.client = client;
	}
	
	@Override
	public void loop() throws IOException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		while(true){
			client = server.accept();
			Future<?> future = threadPool.submit(new ThreadedServer(this, client));
			System.out.println("Added thread to pool: "+future.toString());
		}
	}
	
	@Override
	public final void run() {
		BufferedReader in = null;
		try{
			in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			
			OutputStream out = client.getOutputStream();
			
			String line;
			line = in.readLine();
			if(line == null){
				System.out.println("This is a null stream!");
				return;
			}
			String[] tokens = line.split(" ");
			HttpRequest request = null;
			try{
				if(tokens.length != 3){
					throw new HttpException(HttpCode.BAD_REQUEST, 
							"Request header should consist of the method, "
							+ "path, and protocol version: "+line);
				}
				Method m;
				if(tokens[0].equals("GET")){
					m = Method.GET;
				}
				else if(tokens[0].equals("POST")) {
					m = Method.POST;
				}
				else {
					throw new HttpException(HttpCode.BAD_REQUEST, 
							"Unknown HTTP method: "+tokens[0]);
				}
				RequestURI uri = new RequestURI(tokens[1]);
				request = new HttpRequest(m, uri);
				
				while((line = in.readLine()) != null ){
					if(line.length() == 0){
						break;
					}
					tokens = line.split(":");
					if(tokens.length < 2){
						throw new HttpException(HttpCode.BAD_REQUEST, 
								"Bad key/value pair: "+line);
					}
					if(tokens[0].equals("User-Agent")){
						request.setUserAgent(tokens[1]);
					}
					else if(tokens[0].equals("Accept")){
						request.setAcceptedFormats( 
								tokens[1].split(";")[0].split(","));
					}
				}
				if(!client.isClosed()){
					rHandler.respond(request).write(out, 0);
					out.flush();
				}
			}
			catch(Exception e){
				if(!client.isClosed()){
					HttpException httpEx;
					if(e instanceof HttpException){
						httpEx = (HttpException) e;
					}
					else {
						httpEx = new HttpException(HttpCode.INTERNAL_ERROR, e.toString());
					}
					eHandler.respond(httpEx, request).write(out,  0);
					e.printStackTrace();
					out.flush();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(client != null){
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
