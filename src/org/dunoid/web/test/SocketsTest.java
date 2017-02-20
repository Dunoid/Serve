package org.dunoid.web.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.dunoid.web.http.HttpData;
import org.dunoid.web.http.HttpData.HttpCode;

public class SocketsTest {
	public static void main(String[] args){
		int portNum;
		ServerSocket server = null;
		
		if(args.length >= 1){
			portNum = Integer.parseInt(args[0]);
		}
		else {
			portNum = 4242;
		}
		
		try {
			server = new ServerSocket(portNum);
			
			while(true){
				System.out.println("Waiting for client...");
				Socket client = server.accept();
				System.out.print("Client found");
				
				PrintWriter out = new PrintWriter(client.getOutputStream());
				System.out.println("Getting output");
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(client.getInputStream()));
				System.out.println("Getting input");
				
				String line;
				out.write(HttpData.httpHeader(HttpCode.OK, "text/html"));
				
				while((line = in.readLine()) != null ){
					if(line.length() == 0){
						break;
					}
					System.out.println("Sending data: \n\t"+line);
					out.println(line);
				}
				out.flush();
				out.close();
				in.close();
				client.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
	}
}
