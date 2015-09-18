/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.util.ArrayList;

import IO.IO;
import Gameobjects.Player.Player;

/**
 * 
 * @author Tobias
 */
public class Helper {

	private static int input;

	/**
	 * 
	 * @param message Hint
	 * @param min Minimum Value
	 * @param max Maximum Value
	 * @return
	 */
	public static int checkUserInput(String message, int min, int max) {
		boolean error = false;
		do {
			IO.println(message);
			input = IO.readInt();
			if (input < min || input > max) {
				IO.println("Eingabe, außerhalb des gültigen Bereiches (" + min + "-" + max + ")");
				error = true;
			} else {
				error = false;
			}
		} while (error);
		return input;
	}

	public static int checkUserInput(int min, int max) {
		boolean error = false;
		do {
			input = IO.readInt();
			if (input < min || input > max) {
				IO.println("Eingabe, außerhalb des gültigen Bereiches (" + min
						+ "-" + max + ")");
				error = true;
			} else {
				error = false;

			}
		} while (error);
		return input;
	}

	/**
	 * Gibt Summe der noch im Spiel befindenen Spieler zurueck
	 * 
	 * @param player Spielerarray
	 * @return Summe der noch im Spiel befindenen Spieler
	 */
	public static int getAmountOfLivingPlayers(ArrayList<Player> playerList) {
		int result = 0;
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getisLost() == false) {
				result++;
			}
		}
		return result;

	}

	/**
	 * Prüft, ob Spieler mindestens ein Schiff hat.
	 * 
	 * @param player Spielerarray
	 * @param opponent Nummer des gegnerischen Spielers
	 * @return Booleanwert, ob Schiff vorhanden ist
	 */
	public static boolean checkIfShipAvailable(ArrayList<Player> playerList, int opponentIndex) {
		boolean result = false;
		for (int i = 0; i < playerList.get(opponentIndex).getShips().size(); i++) {
			if (playerList.get(opponentIndex).getShips().get(i).getIsSunk() == false) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Gibt Gewinner aus
	 *
	 * @param playerList Spielerliste
	 */
	public static void printWinner(ArrayList<Player> playerList) {
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getisLost() == false) {
				IO.println("Spieler " + playerList.get(i).getName()	+ " hat gewonnen!");
			}
		}
	}

}
