package Gameobjects.Player;

import Game.Settings;
import Gameobjects.Playfield.*;
import Gameobjects.Ships.*;
import IO.IO;

import java.io.Serializable;
import java.util.ArrayList;

import Gameobjects.Playfield.PlayerViewGui;

public  abstract class Player implements Serializable {

	private static final long serialVersionUID = -3542755719003023085L;
	private Settings gameSettings;
	private int number;
	private String name;
	private ArrayList<Ship> ships;
	private PlayerViewGui playerViewGui;
	private OpponentViewGui opponentViewGui;
	private boolean lost;
	private boolean isAi;

	/**
	 * Konstruktor für den Spieler
	 *
	 * @param number Nummer des Spielers
	 * @param gameSettings spieleinstellungen
	 */
	public Player(int number, String name, Settings gameSettings, boolean isAi) {
		this.number = number;
		this.name = name;
		this.gameSettings = gameSettings;
		this.isAi = isAi;
		buildShipArray(gameSettings);
		this.playerViewGui = new PlayerViewGui(gameSettings);
		this.opponentViewGui = new OpponentViewGui(gameSettings);
		// playfield = new
		// Playfield(gameSettings.getPlayfieldSize(),gameSettings.getPlayfieldSize());
		// playfield.printPlayField();
		// opponentField = new
		// Playfield(gameSettings.getPlayfieldSize(),gameSettings.getPlayfieldSize());
	}
	
	/**
	 * Gibt Nummer des Spielers zurück
	 *
	 * @return int number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Setzt Spielernummmer
	 *
	 * @param int number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Gibt Name des Spielers zurück
	 *
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt Name des Spielers
	 *
	 * @param String
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gibt ArrayList vom Typ Ship zurück
	 *
	 * @return ArrayList<Ship> ships
	 */
	public ArrayList<Ship> getShips() {
		return ships;
	}

	/**
	 * Setzt ArrayList vom Typ Ship
	 *
	 * @param ArrayList
	 *            <Ship> ships
	 */
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}
	
	/**
	 * Gibt SpielerGUI zurück
	 *
	 * @return playerPlayFieldGui SpielerGui
	 */
	public PlayerViewGui getPlayerViewGui() {
		return playerViewGui;
	}

	/**
	 * Setzt SpielerGUI
	 *
	 * @param playerPlayFieldGui
	 *            SpielerGUI
	 */
	public void setPlayerPlayFieldGui(PlayerViewGui playerPlayFieldGui) {
		this.playerViewGui = playerPlayFieldGui;
	}
	
	/**
	 * Gibt GegnerGUI zurück
	 *
	 * @return playerPlayFieldGui GegnerGui
	 */
	public OpponentViewGui getOpponentViewGui() {
		return opponentViewGui;
	}

	/**
	 * Setzt GegnerGUI
	 *
	 * @param playerPlayFieldGui
	 *            GegnerGUI
	 */
	public void setOpponentPlayFieldGui(OpponentViewGui opponentPlayFieldGui) {
		this.opponentViewGui = opponentPlayFieldGui;
	}
	
	/**
	 * Gibt zurück, ob Gegner verloren hat
	 *
	 * @return boolean lost
	 */
	public boolean getisLost() {
		return lost;
	}

	/**
	 * Setzt, dass Gegner verloren hat
	 *
	 * @param booelan
	 *            lost
	 */
	public void setLost(boolean lost) {
		this.lost = lost;
	}
	
	/**
	 * Gibt boolean-Wert zurück, ob Player KI ist.
	 * @return boolean isAi
	 */
	public boolean getIsAi() {
		return isAi;
	}

	/**
	 * Setzt Attribut auf einen boolean Wert
	 * @param boolean isAI
	 */
	public void setAI(boolean isAi) {
		this.isAi = isAi;
	}

	/**
	 * Erzeugt Schiffsarray
	 *
	 * @param cSettings
	 *            Spieleinstellungen
	 */
	private void buildShipArray(Settings cSettings) {
		ships = new ArrayList<>();
		int shipNumber = 0;
		for (int i = 1; i <= cSettings.getAmountOfDestroyer(); i++) {
			Ship ship = new Destroyer(shipNumber);
			ships.add(ship);
			shipNumber++;
		}
		for (int i = 1; i <= cSettings.getAmountOfFrigate(); i++) {
			Ship ship = new Frigate(shipNumber);
			ships.add(ship);
			shipNumber++;
		}
		for (int i = 1; i <= cSettings.getAmountOfCorvette(); i++) {
			Ship ship = new Corvette(shipNumber);
			ships.add(ship);
			shipNumber++;
		}
		for (int i = 1; i <= cSettings.getAmountOfSubmarine(); i++) {
			Ship ship = new Submarine(shipNumber);
			ships.add(ship);
			shipNumber++;
		}
	}
	
	/**
	 * 
	 * @param playerList
	 * @param selectedPlayer
	 * @param shootRange
	 * @param orientation
	 * @param coordinate
	 */
	public void humanShootOnPlayfield(ArrayList<Player> playerList, int selectedPlayer, int shootRange, boolean orientation, String[] coordinate) {
		ArrayList<Integer> hitShips;
		hitShips = playerList.get(selectedPlayer).getPlayerViewGui().setShot(coordinate, shootRange, orientation);
		playerList.get(selectedPlayer).getOpponentViewGui().setShot(coordinate, shootRange, orientation);
		// Prüfen ob schiffe getroffen
		for (int i = 0; i < hitShips.size(); i++) {
			for (int shipIndex = 0; shipIndex < playerList.get(selectedPlayer).getShips().size(); shipIndex++) {
				if (playerList.get(selectedPlayer).getShips().get(shipIndex).getNumber() == hitShips.get(i)) {
					playerList.get(selectedPlayer).getShips().get(shipIndex).setHitpoints();
				}
			}
		}
	}

	/**
	 * Gibt Liste der Schiffe aus, die zur Verfügung stehen
	 *
	 * @param player Playerarray
	 * @param playerN Index des Spielers in Player-Array
	 */
	public int[] listOfAvalableShips(ArrayList<Player> playerList,
		int playerindex) {
		int[] tempShipArray;
		int arrayLength = 0;
		for (int ships = 1; ships < playerList.get(playerindex).getShips().size(); ships++) {
			if (playerList.get(playerindex).getShips().get(ships).getIsSunk() == false
					&& playerList.get(playerindex).getShips().get(ships).getCurrentReloadTime() == 0) {
				arrayLength = arrayLength++;
			}
		}
		tempShipArray = new int[arrayLength];
		for (int ships = 1; ships < tempShipArray.length; ships++) {
			if (playerList.get(playerindex).getShips().get(ships).getIsSunk() == false
					&& playerList.get(playerindex).getShips().get(ships).getCurrentReloadTime() == 0) {
				tempShipArray[ships] = playerList.get(playerindex).getShips().get(ships).getNumber();
			}
		}
		return tempShipArray;
	}

	/**
	 * Gibt Schiffindex zurück, mit dem angegriffen werden soll
	 * 
	 * @param player Spielerarray
	 * @param playerN Spielernummer
	 * @return shipIndex
	 */
	public int getAvailableShipToShoot(ArrayList<Player> playerList, int playerindex) {
		boolean error = true;
		int shipIndex;
		int[] tempShipArray;
		IO.println("Mit welchem Schiff willst du schiessen?");
		tempShipArray = listOfAvalableShips(playerList, playerindex);
		IO.println("Gib die Nummer des Schiffs ein: ");
		do {
			shipIndex = IO.readInt() - 1;
			for (int counter = 0; counter > tempShipArray.length; counter++) {
				if (tempShipArray[counter] == shipIndex) {
					error = false;
				}
			}
		} while (error);
		IO.println("Sie haben das Schiff mit der Nummer "
				+ playerList.get(playerindex).getShips().get(shipIndex).getNumber()
				+ " vom Typ "
				+ playerList.get(playerindex).getShips().get(shipIndex).getName() + " ausgewaehlt!");
		return shipIndex;
	}
	
	/**
	 * Gibt Liste der Gegner aus, die zur Verfügung stehen
	 *
	 * @param player Playerarray
	 * @param playerIndex Index des Spielers in Player-Array
	 */
	public int[] listOfAvalableOpponents(ArrayList<Player> playerList, int playerindex) {
		int[] tempOpponentArray;
		int arrayLength = 0;
		for (int opponents = 1; opponents < playerList.size(); opponents++) {
			if (playerList.get(opponents).getNumber() == playerList.get(playerindex).getNumber()
					&& playerList.get(opponents).getisLost() == false) {
				arrayLength = arrayLength++;
			}
		}
		tempOpponentArray = new int[arrayLength];
		for (int opponents = 1; opponents < playerList.size(); opponents++) {
			if (playerList.get(opponents).getNumber() == playerList.get(playerindex).getNumber()
					&& playerList.get(opponents).getisLost() == false) {
				tempOpponentArray[opponents] = playerList.get(opponents).getNumber();
			}
		}
		return tempOpponentArray;
	}
	
	/**
	 * Gibt Schiffindex zurück, mit dem angegriffen werden soll
	 * 
	 * @param player Spielerarray
	 * @param playerN Spielernummer
	 * @return shipIndex
	 */
	public int getAvailableOpponentsToShoot(ArrayList<Player> playerList, int playerindex) {		
		boolean error = true;
		int opponentIndex;
		int[] tempOpponentpArray;
		IO.println("Auf welchen Gegner willst du schiessen?");
		tempOpponentpArray = listOfAvalableOpponents(playerList, playerindex);
		IO.println("Gib die Nummer des Schiffs ein: ");
		do {
			opponentIndex = IO.readInt() - 1;
			for (int counter = 0; counter > tempOpponentpArray.length; counter++) {
				if (tempOpponentpArray[counter] == opponentIndex) {
					error = false;
				}
			}
		} while (error);
		IO.println("Sie haben das Schiff mit der Nummer "
				+ playerList.get(playerindex).getShips().get(opponentIndex).getNumber()
				+ " vom Typ "
				+ playerList.get(playerindex).getShips().get(opponentIndex).getName() + " ausgewaehlt!");
		return opponentIndex;
	}
}
