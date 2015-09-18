package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import IO.IO;

public class Client {

	public static void main(String[] args){
		
		//System-in = Tastatur
		Scanner eingabe = new Scanner(System.in);
		
		try{
			//Erstellt Socket
			Socket client = new Socket("localhost", 5555);
			IO.println("Client gestartet");
			
			//Streams
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			 
			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			//--------------------------------------------
			
			IO.print("Eingabe: ");
			String anServer = eingabe.nextLine();
						
			writer.write(anServer + "\n");
			writer.flush();
			
			String s = null;
			
			while((s = reader.readLine()) != null){
				IO.println("Empfang vom Server: "  + s);
				
			}
			
			reader.close();
			writer.close();
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
			
		}
	}
}
