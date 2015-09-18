package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import IO.IO;

public class Server {

	/**
	 * 
	 */
	public static void main(String[] args){
		
		try{
			//Erstellt ServerSocket
			//5555 ist der Port
			ServerSocket server = new ServerSocket(5555);
			IO.println("Server gestartet");
			
			Socket client = server.accept();
			
			//Streams
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			 
			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			//---------------------------------------------------------------
			
			String s = null;
			
			while((s = reader.readLine()) != null){
				writer.write(s + "\n");
				writer.flush();
				IO.println("Empfang vom Client: "  + s);
				
			}
			
			writer.close();
			reader.close();
			server.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
