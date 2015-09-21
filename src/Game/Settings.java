package Game;

import Gameobjects.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable{
    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1029641823135222581L;
	private int amountOfPlayer;
    private String[] playerNames;
    private int amountOfKIPlayer;
    private boolean[] aiArray;
    private int amountOfDestroyer;
    private int amountOfFrigate;
    private int amountOfCorvette;
    private int amountOfSubmarine;
    private int amountOfAllShips;
    private int playfieldSize;
    private SettingsGui gameSettings;
    
	// Konstanten
    final int SIZE_DESTRIOYER = 5;
    final int SIZE_FRIGATE = 4;
    final int SIZE_CORVETTE = 3;
    final int SIZE_SUBMARINE = 2;
    final int MIN_PLAYFIELD_SIZE = 8;
    final int MAX_PLAYFIELD_SIZE = 26;
    
    /**
     * Konstruktor
     * @param gameGuiSettings
     */
    public Settings(SettingsGui gameGuiSettings) {
        this.gameSettings = gameGuiSettings;
        this.amountOfPlayer = gameGuiSettings.getAmountOfPlayer();
        this.playerNames = gameGuiSettings.getPlayerNames();
        this.amountOfKIPlayer = gameGuiSettings.getAmountOfKIPlayer();
        this.aiArray = gameGuiSettings.getAiArray();
        this.amountOfDestroyer = gameGuiSettings.getAmountOfDestroyer();
        this.amountOfFrigate = gameGuiSettings.getAmountOfFrigate();
        this.amountOfCorvette = gameGuiSettings.getAmountOfCorvette();
        this.amountOfSubmarine = gameGuiSettings.getAmountOfSubmarine();
        this.amountOfAllShips = amountOfDestroyer + amountOfFrigate + amountOfCorvette + amountOfSubmarine;
        this.playfieldSize = gameGuiSettings.getPlayfieldSize();
//        calculateMinPlayfieldSize();
    }
    
    /**
     * Gibt Anzahl aller Schiffe zurück
     * @return
     */
    public int getAmountOfAllShips() {
        return amountOfAllShips;
    }
    
    /**
     * Gibt Stringarray mit Namen zurück
     * @return
     */
    public String[] getPlayerNames() {
        return playerNames;
    }

    /**
     * Setzt Spielernamen
     * @param playerNames
     */
    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    /**
     * Gibt Anzahl der Spieler zurück
     * @return
     */
    public int getAmountOfPlayer() {
        return amountOfPlayer;
    }

    /**
     * Setzt Anzahl an Spielern
     * @param amountOfPlayer
     */
    public void setAmountOfPlayer(int amountOfPlayer) {
        this.amountOfPlayer = amountOfPlayer;
    }

    /**
     * Gibt Anzahl der KI-Spieler zurück
     * @return
     */
    public int getAmountOfKIPlayer() {
        return amountOfKIPlayer;
    }

    /**
     * Setzt Anzahl der KI-Spieler
     * @param amountOfKIPlayer
     */
    public void setAmountOfKIPlayer(int amountOfKIPlayer) {
        this.amountOfKIPlayer = amountOfKIPlayer;
    }
    
    /**
     * Gibt Array mit boolean-Werte entsprechend der KI-Spieler zurück
     * @return
     */
    public boolean[] getAiArray() {
		return aiArray;
	}

    /**
     * Setzt KI-Array
     * @param aiArray
     */
	public void setAiArray(boolean[] aiArray) {
		this.aiArray = aiArray;
	}

	/**
	 * Gibt Anzahl der Destroyer zurück
	 * @return
	 */
	public int getAmountOfDestroyer() {
        return amountOfDestroyer;
    }

	/**
	 * Setzt Anzahl der Destroyer 
	 * @return
	 */
    public void setAmountOfDestroyer(int amountOfDestroyer) {
        this.amountOfDestroyer = amountOfDestroyer;
        calculateMinPlayfieldSize();
    }

    /**
	 * Gibt Anzahl der Frigate zurück
	 * @return
	 */
    public int getAmountOfFrigate() {
        return amountOfFrigate;
    }

    /**
	 * Setzt Anzahl der Frigate 
	 * @return
	 */
    public void setAmountOfFrigate(int amountOfFrigate) {
        this.amountOfFrigate = amountOfFrigate;
        calculateMinPlayfieldSize();
    }

    /**
	 * Gibt Anzahl der Corvette zurück
	 * @return
	 */
    public int getAmountOfCorvette() {
        return amountOfCorvette;
    }

    /**
	 * Setzt Anzahl der Corvette 
	 * @return
	 */
    public void setAmountOfCorvette(int amountOfCorvette) {
        this.amountOfCorvette = amountOfCorvette;
        calculateMinPlayfieldSize();
    }

    /**
	 * Gibt Anzahl der Submarines zurück
	 * @return
	 */ 
    public int getAmountOfSubmarine() {
        return amountOfSubmarine;
    }

	 /**
		 * Setzt Anzahl der Submarines 
		 * @return
		 */
    public void setAmountOfSubmarine(int amountOfSubmarine) {
        this.amountOfSubmarine = amountOfSubmarine;
        calculateMinPlayfieldSize();
    }

    /**
	 * Gibt Spielfeldgroesse zurück
	 * @return
	 */
    public int getPlayfieldSize() {
        return playfieldSize;
    }

    /**
	 * Setzt Spielfeldgröße
	 * @return
	 */
    public void setPlayfieldSize(int playfieldSize) {
        if (playfieldSize < MIN_PLAYFIELD_SIZE) {
            this.playfieldSize = MIN_PLAYFIELD_SIZE;
        } else if (playfieldSize > MAX_PLAYFIELD_SIZE) {
            this.playfieldSize = MAX_PLAYFIELD_SIZE;
        }
        this.playfieldSize = playfieldSize;
    }
    
    /**
	 * Berechnet Minimalgröße des Feldes
	 * @return
	 */
    private void calculateMinPlayfieldSize(){
        this.playfieldSize = (int) Math.ceil(Math.sqrt(3*(this.getAmountOfDestroyer() * SIZE_DESTRIOYER + this.getAmountOfFrigate() * SIZE_FRIGATE 
                + this.getAmountOfCorvette() * SIZE_CORVETTE + this.getAmountOfSubmarine() * SIZE_SUBMARINE)));
    }
}
