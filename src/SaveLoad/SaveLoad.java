package SaveLoad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Game.Game;
import IO.IO;

public class SaveLoad {
	
	/**
	 * Speichert das Spiel
	 * @param Game game, welches gespeichert werden soll
	 */
	public static void save(Game game){
		try{
			//Erzeugt Datei
			FileOutputStream fileOut = new FileOutputStream("save.txt");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			//Schreibt in Datei
			objectOut.writeObject(game);
			IO.println("Save game was successful.");
			//Schliesst Datei
			objectOut.close();
		}
		catch(IOException e){
			e.printStackTrace();			
		}
	}
	
	public static Game load() {
		Game game = null;
		try{
			//Lädt Datei
			FileInputStream fileIn = new FileInputStream("save.txt");
			//Liesst Datei
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			try {
				//Gameobjekt aus der Datei wird dem Gameobjekt game zugeordnet
				game = (Game)objectIn.readObject();
				
				IO.println("The game was successful loaded.");
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//Schliesst Datei
			objectIn.close();			
		}
		catch(IOException e){
			e.printStackTrace();			
		}	
		//Gibt Spiel zurück
		return game;
	}
}
