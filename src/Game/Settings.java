package Game;

import Gameobjects.Player.Player;
import java.util.ArrayList;

public class Settings {
    

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
    
    public int getAmountOfAllShips() {
        return amountOfAllShips;
    }
    
    public String[] getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }
    //Instanzvariable ki spieler und im menu ki abfrage

    public int getAmountOfPlayer() {
        return amountOfPlayer;
    }

    public void setAmountOfPlayer(int amountOfPlayer) {
        this.amountOfPlayer = amountOfPlayer;
    }

    public int getAmountOfKIPlayer() {
        return amountOfKIPlayer;
    }

    public void setAmountOfKIPlayer(int amountOfKIPlayer) {
        this.amountOfKIPlayer = amountOfKIPlayer;
    }
    
    public boolean[] getAiArray() {
		return aiArray;
	}

	public void setAiArray(boolean[] aiArray) {
		this.aiArray = aiArray;
	}

	public int getAmountOfDestroyer() {
        return amountOfDestroyer;
    }

    public void setAmountOfDestroyer(int amountOfDestroyer) {
        this.amountOfDestroyer = amountOfDestroyer;
        calculateMinPlayfieldSize();
    }

    public int getAmountOfFrigate() {
        return amountOfFrigate;
    }

    public void setAmountOfFrigate(int amountOfFrigate) {
        this.amountOfFrigate = amountOfFrigate;
        calculateMinPlayfieldSize();
    }

    public int getAmountOfCorvette() {
        return amountOfCorvette;
    }

    public void setAmountOfCorvette(int amountOfCorvette) {
        this.amountOfCorvette = amountOfCorvette;
        calculateMinPlayfieldSize();
    }

    public int getAmountOfSubmarine() {
        return amountOfSubmarine;
    }

    public void setAmountOfSubmarine(int amountOfSubmarine) {
        this.amountOfSubmarine = amountOfSubmarine;
        calculateMinPlayfieldSize();
    }

    public int getPlayfieldSize() {
        return playfieldSize;
    }

    public void setPlayfieldSize(int playfieldSize) {
        if (playfieldSize < MIN_PLAYFIELD_SIZE) {
            this.playfieldSize = MIN_PLAYFIELD_SIZE;
        } else if (playfieldSize > MAX_PLAYFIELD_SIZE) {
            this.playfieldSize = MAX_PLAYFIELD_SIZE;
        }
        this.playfieldSize = playfieldSize;
    }
    
    private void calculateMinPlayfieldSize(){
        this.playfieldSize = (int) Math.ceil(Math.sqrt(3*(this.getAmountOfDestroyer() * SIZE_DESTRIOYER + this.getAmountOfFrigate() * SIZE_FRIGATE 
                + this.getAmountOfCorvette() * SIZE_CORVETTE + this.getAmountOfSubmarine() * SIZE_SUBMARINE)));
    }
}
