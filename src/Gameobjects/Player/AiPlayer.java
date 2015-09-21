package Gameobjects.Player;

import java.io.Serializable;
import java.util.ArrayList;

import Game.Settings;
import Gameobjects.Ships.Ship;

public class AiPlayer extends Player implements Serializable {

	private static final long serialVersionUID = -2046818718759120112L;
	private int aiLastHitOpponentIndex;
	private String aiLastHitCoordinate;

	/**
	 * Konstruktor
	 * 
	 * @param number
	 *            Spielernummer
	 * @param name
	 *            Spielername
	 * @param gameSettings
	 *            Spieleinstellungen
	 * @param isAi
	 *            KI
	 */
	public AiPlayer(int number, String name, Settings gameSettings, boolean isAi) {
		super(number, name, gameSettings, isAi);
		setAiLastHitCoordinate(null);
		// 9 steht für keinen Gegner
		setAiLastHitOpponentIndex(9);
	}

	/**
	 * Gibt letzten getroffenen Gegner zurück
	 * 
	 * @return int lastHitOpponentNumber
	 */
	public int getAiLastHitOpponentIndex() {
		return aiLastHitOpponentIndex;
	}

	/**
	 * Setzt letzten getroffenen Gegner
	 * 
	 * @param int lastHitOpponentNumber
	 */
	public void setAiLastHitOpponentIndex(int aiLastHitOpponentIndex) {
		this.aiLastHitOpponentIndex = aiLastHitOpponentIndex;
	}

	/**
	 * Gibt letzte getroffene Koordinate zurück
	 * 
	 * @return String aiLastHitCoordinate
	 */
	public String getAiLastHitCoordinate() {
		return aiLastHitCoordinate;
	}

	/**
	 * Setzt letzte getroffene Koordinate
	 * 
	 * @param String
	 *            aiLastHitCoordinate
	 */
	public void setAiLastHitCoordinate(String aiLastHitCoordinate) {
		this.aiLastHitCoordinate = aiLastHitCoordinate;
	}

	/**
	 * Gibt einen Gegner-Index zurück
	 * 
	 * @param playerList
	 *            ArrayList vom Typ Player
	 * @return aiOpponent Zufällig berechneten Index
	 */
	public int getAiOpponent(ArrayList<Player> playerList, int playerIndex) {
		int aiOpponentIndex = 9;
		int lastHitOpponentIndex = getAiLastHitOpponentIndex();
		if (lastHitOpponentIndex == 9) {
			boolean error = false;
			do {
				aiOpponentIndex = (int) (Math.random() * playerList.size());
				if (playerIndex != aiOpponentIndex
						&& playerList.get(aiOpponentIndex).getisLost() == false) {
					error = false;
				} else {
					error = true;
				}
			} while (error);
		} else {
			aiOpponentIndex = lastHitOpponentIndex;
		}
		return aiOpponentIndex;
	}

	/**
	 * Gibt eine belibige Schiffsindex zurück. Schiff darf nicht gesunken sein
	 * oder nachladen.
	 * 
	 * @param playerList
	 * @param playerIndex
	 *            , der an der Reihe ist.
	 * @return Schiffsindex
	 */
	public int getRandomShip(ArrayList<Player> playerList, int playerIndex) {
		int randomShipIndex;
		boolean error = false;
		do {
			randomShipIndex = (int) (Math.random() * playerList
					.get(playerIndex).getShips().size());
			if (playerList.get(playerIndex).getShips().get(randomShipIndex)
					.getIsSunk() == false
					&& playerList.get(playerIndex).getShips()
							.get(randomShipIndex).getCurrentReloadTime() == 0) {
				error = false;
			} else {
				error = true;
			}
		} while (error);
		return randomShipIndex;
	}

	/**
	 * Berechnet eine zufällige Orientierung
	 * 
	 * @return boolean orientation
	 */
	public boolean getAiOrientation() {
		boolean orientation = false;
		int orient = (int) (Math.random() * 2);
		if (orient == 1) {
			orientation = true;
		} else {
			orientation = false;
		}
		return orientation;
	}

	/**
	 * Wählt eine zufällige Zahl aus dem Pool aus int pool ist die Menge an
	 * Ganzzahlen, die gewählt werden dürfen.
	 * 
	 * @param ArrayList
	 *            <Player>playerList
	 * @param int playerIndex
	 * @return int Integer-Coordinate
	 */
	public int getAiRandomNumber(ArrayList<Player> playerList, int playerIndex) {
		int pool = playerList.get(playerIndex).getPlayerViewGui()
				.getPlayerViewMatrix().length - 1;
		return (int) (Math.random() * pool) + 1;
	}

	/**
	 * Wählt und validiert eine zufällige oder eine logisch ausgewählte
	 * Integer-Koordinate auf dem Spielfeld aus Benötigt für das Setzen von
	 * Schiffen und zum Schiessen
	 * 
	 * @param playerList
	 *            ArrayList vom Typ Player
	 * @param playerIndex
	 *            Index des aktuellen Spielers
	 * @return coordinate Koordinate vom Type String
	 */
	public String getAiChooseCoordinate(ArrayList<Player> playerList,
			int opponentIndex, String lastHitCoordinate) {
		String aiCoordinate;
		if (lastHitCoordinate == null) {
			aiCoordinate = getRandomCoordinate(playerList, opponentIndex);
		} else {
			aiCoordinate = lastHitCoordinate;
			int[] lastHitCoordinateArray = splitCoordinate(aiCoordinate);
			int yCoordinate = lastHitCoordinateArray[0];
			int xCoordinate = lastHitCoordinateArray[1];
			int range = playerList.get(opponentIndex).getPlayerViewGui()
					.getPlayerViewMatrix().length - 1;
			// Entpricht oben
			if (yCoordinate - 1 <= range
					&& yCoordinate - 1 > 0
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate - 1][xCoordinate]
							.getIsShot() == false
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate - 1][xCoordinate]
							.getHasShip() == true) {
				aiCoordinate = Integer.toString(yCoordinate - 1) + "#"
						+ Integer.toString(xCoordinate);
			}
			// Entspricht rechts
			else if (xCoordinate + 1 <= range
					&& xCoordinate + 1 > 0
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate + 1][xCoordinate]
							.getIsShot() == false
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate + 1][xCoordinate]
							.getHasShip() == true) {
				aiCoordinate = Integer.toString(yCoordinate + 1) + "#"
						+ Integer.toString(xCoordinate);
			}
			// Entspricht unten
			else if (yCoordinate + 1 <= range
					&& yCoordinate + 1 > 0
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate + 1][xCoordinate]
							.getIsShot() == false
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate + 1][xCoordinate]
							.getHasShip() == true) {
				aiCoordinate = Integer.toString(yCoordinate + 1) + "#"
						+ Integer.toString(xCoordinate);

			}
			// Entspricht links
			else if (xCoordinate - 1 <= range
					&& xCoordinate - 1 > 0
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate][xCoordinate - 1]
							.getIsShot() == false
					&& playerList.get(opponentIndex).getPlayerViewGui()
							.getPlayerViewMatrix()[yCoordinate][xCoordinate - 1]
							.getHasShip() == true) {

				aiCoordinate = Integer.toString(yCoordinate) + "#"
						+ Integer.toString(xCoordinate - 1);

			} else {
				aiCoordinate = getRandomCoordinate(playerList, opponentIndex);
			}
		}
		System.out.println("getAiChooseCoordinate: " + aiCoordinate);
		return aiCoordinate;
	}

	/**
	 * Wählt eine zufällige Koorindate
	 * 
	 * @param playerList
	 * @param opponentIndex
	 * @return
	 */
	public String getRandomCoordinate(ArrayList<Player> playerList,
			int opponentIndex) {
		boolean error;
		String aiCoordinate;
		do {
			int yCoordinate = getAiRandomNumber(playerList, opponentIndex);
			int xCoordinate = getAiRandomNumber(playerList, opponentIndex);
			aiCoordinate = Integer.toString(yCoordinate) + "#"
					+ Integer.toString(xCoordinate);
			error = false;
			if (playerList.get(opponentIndex).getPlayerViewGui()
					.getPlayerViewMatrix()[yCoordinate][xCoordinate].getIsHit() == true) {
				error = true;
			}
		} while (error);
		return aiCoordinate;
	}

	/**
	 * Methode convertiert einen Kordinaten-String in ein int-Array.
	 * 
	 * @param stringCoordinate
	 *            Koordinate mit Typ String
	 * @return int[] intCoordinates
	 */
	public int[] splitCoordinate(String stringCoordinate) {
		System.out.println("splaiCoordinate: " + stringCoordinate);
		int[] intCoordinates = new int[2];
		String[] splitted = stringCoordinate.split("\\#");
		intCoordinates[0] = Integer.parseInt(splitted[0]);
		intCoordinates[1] = Integer.parseInt(splitted[1]);
		return intCoordinates;
	}

	/**
	 * Schussfunktion
	 * 
	 * @param playerList
	 *            ArrayList vom Typ Player
	 * @param aiOpponentIndex
	 *            Gegner-Index
	 * @param shootRange
	 *            Schussreichweite
	 * @param orientation
	 *            Schussrichtung
	 * @param coordinate
	 *            Koordinate, die beschossen werden soll
	 * @return hitCoordinate Coordinate mit Treffer
	 */
	public String aiShootOnPlayField(ArrayList<Player> playerList,
			int aiOpponentIndex, int shootRange, boolean orientation,
			String coordinate) {
		ArrayList<Integer> hitShips;
		String hitCoordinate = null;
		int[] tempIntCoordinates = splitCoordinate(coordinate);
		int yCoordinate = tempIntCoordinates[0];
		int xCoordinate = tempIntCoordinates[1];
		// Getroffene Schiffsnummern werden in das Array geschrieben
		hitShips = playerList.get(aiOpponentIndex).getPlayerViewGui()
				.setAiShot(yCoordinate, xCoordinate, shootRange, orientation);
		playerList.get(aiOpponentIndex).getOpponentViewGui()
				.setAiShot(yCoordinate, xCoordinate, shootRange, orientation);
		// Prüft, ob schiffe getroffen wurden und setzt Hitpoints
		for (int i = 0; i < hitShips.size(); i++) {
			for (Ship ship : playerList.get(aiOpponentIndex).getShips()) {
				if (ship.getNumber() == hitShips.get(i)) {
					ship.setHitpoints();
				}
			}
		}
		if (playerList.get(aiOpponentIndex).getPlayerViewGui()
				.getPlayerViewMatrix()[yCoordinate][xCoordinate].getHasShip() == true) {
			hitCoordinate = coordinate;
			setAiLastHitOpponentIndex(aiOpponentIndex);
		} else {
			setAiLastHitOpponentIndex(9);
		}
		System.out.println("aiShootOnPlayfield - hitCoordinate: "
				+ hitCoordinate);
		return hitCoordinate;
	}
}
