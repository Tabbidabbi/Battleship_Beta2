package Game;

import Gameobjects.Player.AiPlayer;
import Gameobjects.Player.HumanPlayer;
import Gameobjects.Player.Player;
import IO.IO;
import java.io.Serializable;
import java.util.ArrayList;
import Helper.Helper;
import SaveLoad.SaveLoad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Helper.*;

public class Game implements Serializable, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -4356896699088096722L;
    private ArrayList<Player> playerList;
    private GameGui gameGui;
    private Settings gameSettings;
    private boolean shipOrientation;
    private boolean shootOrientation;
    private int selectedShip;
    private int selectedPlayer;
    private int player = 0;
    private int roundNumber = 1;
    private int shipsPlaced = 0;
    //0 = Placement, 1 = Shoot
    private int gameState = 0;

    /**
     * Konstruktor der Klasse Game
     *
     * @param gameSettings Einstellungen zum Erstellen des Spiels
     */
    public Game(Settings gameSettings) {
        this.gameSettings = gameSettings;
        this.playerList = buildPlayerArray(gameSettings);
        this.gameGui = new GameGui(gameSettings);
        gamePreperation();

    }

    /**
     * Vorbereitung des Spiels und Prüfung ob ein Ki Spieler vorhanden ist.
     */
    private void gamePreperation() {
        System.out.println("################# Welcome to Battleship #################" + "\n");
        addPlayerToGameGui(playerList);
        addGameGui();

    }

    /**
     * Gibt gameGui zurück
     *
     * @return GameGui gameGui
     */
    public GameGui getGameGui() {
        return gameGui;
    }

    /**
     * Der GameGui wird das Spieldfeld des Spielers hinzugefügt und angezeigt.
     *
     * @param playerList
     */
    private void addPlayerToGameGui(ArrayList<Player> playerList) {
        gameGui.addPlayerView(player, playerList);
        gameGui.addOpponentView(player, playerList);
        gameGui.showPlayerPlayField(player);
    }

    /**
     * Der GameGui wird die Spieler- und die Schiffsliste hinzugefügt und
     * angezeigt.
     */
    private void addGameGui() {
        addPlayerViewMatrixListener();
        addOpponentViewMatrixListener();
        addStartGameListener();
        addNextPlayerButtonListener();
        addStartRoundListener();
        addNextRoundListener();
        gameGui.addPlayerButtonsToGameGui(playerList);
        addPlayerButtonsActionListener();
        gameGui.addShipButtonsToGameGui(player, playerList);
        addShipButtonsActionListener();
    }

    /**
     * Textinteraktion mit dem Spieler
     *
     * @param playerList
     */
    private void interactWithPlayer(ArrayList<Player> playerList) {
        System.out.println("Player " + playerList.get(player).getName() + ", " + " its your turn.");
        System.out.println("Please place all available ships." + "\n");
        System.out.println("Click on the playfield to place " + playerList.get(player).getShips().get(shipsPlaced).getName() + " : ");
    }

    /**
     * Textinteraktion mit dem Spieler
     */
    private void nextShipDialog() {
        System.out.println("Click on the playfield " + playerList.get(player).getShips().get(shipsPlaced).getName() + " : ");
    }

    /**
     * Gibt gameSettings zurück
     *
     * @return Settings gameSettings
     */
    public Settings getGameSettings() {
        return gameSettings;
    }

    /**
     * Gibt ArrayList vom Typ Player
     *
     * @return ArrayList<Player> playerList
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Erstellt die Spieler
     *
     * @param Settings gameSettings
     * @return ArrayList<Player> playerList
     */
    private ArrayList buildPlayerArray(Settings gameSettings) {
        this.playerList = new ArrayList<>();
        int playerNumber = 0;
        for (int i = 0; i < gameSettings.getAmountOfPlayer(); i++) {
            if (gameSettings.getAiArray()[i] == false) {
                Player player = new HumanPlayer(playerNumber, gameSettings.getPlayerNames()[i], gameSettings, false);
                playerList.add(player);
                playerNumber++;
            } else if (gameSettings.getAiArray()[i] == true) {
                AiPlayer player = new AiPlayer(playerNumber, gameSettings.getPlayerNames()[i], gameSettings, true);
                playerList.add(player);
                playerNumber++;
            }
        }
        return playerList;
    }

    /**
     * Setzt Schiffe
     *
     * @param ship Schiffobjet
     * @param input Koordinate zum Setzen
     * @param orientation Richtung des Schiffes
     * @param playfield Spielfeld des auf Sicht des Spielers
     * @param opponentfield playfield Spielfeld des auf Sicht des Gegners
     * @return boolean, ob Schiff gesetzt werden konnte
     */
    private boolean checkShipPlacement(ActionEvent e, boolean orientation) {

        String input = e.getActionCommand();

        String[] splitted = input.split("\\#");

        if (shipOrientation == true) {
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                try {
                    // Abfrage, welche prüft ob das Feld auf der das
                    // Schiff gesetzt werden soll, deaktiviert ist.
                    // Falls ja:
                    // gibt die ganze Methode "false zurück".
                    if (!playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(splitted[0])][Integer.parseInt(splitted[1]) + i]
                            .isActive()) {
                        System.out.println("Sorry the ship cant be placed here " + "\n" + " the ship´s must have one field space in between!");

                        return false;
                    }
                    // Falls das Schiff mit der Größe nicht in das
                    // Array passt, fange die Fehlermeldung ab und
                    // gib folgendes aus...
                } catch (ArrayIndexOutOfBoundsException ex) {
                    IO.println("The ship does not fit on the playfield");
                    return false;
                }
            }
        } else {
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                try {
                    // Abfrage, welche prüft ob das Feld auf der das
                    // Schiff gesetzt werden soll, deaktiviert ist.
                    // Falls ja:
                    // gibt die ganze Methode "false zurück".
                    if (!playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(splitted[0]) + i][Integer.parseInt(splitted[1])]
                            .isActive()) {
                        System.out.println("Sorry the ship cant be placed here " + "\n" + " the ships must have one field space in between!");
                        return false;
                    }
                    // Falls das Schiff mit der Größe nicht in das
                    // Array passt, fange die Fehlermeldung ab und
                    // gib folgendes aus...
                } catch (ArrayIndexOutOfBoundsException ex) {
                    IO.println("The ship does not fit on the playfield");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param e
     * @param orientation
     * @param playerList
     * @return
     */
    public boolean placeShip(ActionEvent e, boolean orientation,
            ArrayList<Player> playerList) {

        String placeShipCoordinateInput = e.getActionCommand();

        String[] placeShipCoordinateSplitted = placeShipCoordinateInput.split("\\#");
        // true = horizontal
        if (orientation == true) {
            // Setze Schiff
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setText(playerList.get(player).getShips().get(shipsPlaced).getSign());
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setIsWater(false);
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setHasShip(true);
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setShipNumber(playerList.get(player).getShips().get(shipsPlaced).getNumber());
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setIsWater(true);
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setHasShip(true);
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setShipNumber(playerList.get(player).getShips().get(shipsPlaced).getNumber());
//                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0])][Integer.parseInt(placeShipCoordinateSplitted[1]) + i].setText(playerList.get(player).getShips().get(shipsPlaced).getSign());

            }

//                         Deaktiviere Felder um das Schiff herum
            for (int i = (Integer.parseInt(placeShipCoordinateSplitted[1]) - 1); i <= playerList.get(player).getShips().get(shipsPlaced).getSize() + Integer.parseInt(placeShipCoordinateSplitted[1]); i++) {
                for (int j = (Integer.parseInt(placeShipCoordinateSplitted[0]) - 1); j < Integer.parseInt(placeShipCoordinateSplitted[0]) + 2; j++) {
                    try {
                        playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[j][i]
                                .setActive(false);
                        // Tetstweise eingebaut um zu sehen welche
                        // Felder deaktiviert werden
//                        playerList.get(player).getPlayerPlayFieldGui().getPlayfieldMatrix()[j][i].setText("D");
                    } catch (ArrayIndexOutOfBoundsException ex) {

                    }
                }
            }
//                    }
//                }
//            }

        } // false = vertikal
        else {
            // Setze Schiff
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setText(playerList.get(player).getShips().get(shipsPlaced).getSign());
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setIsWater(false);
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setHasShip(true);
                playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setShipNumber(playerList.get(player).getShips().get(shipsPlaced).getNumber());
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setIsWater(true);
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setHasShip(true);
                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setShipNumber(playerList.get(player).getShips().get(shipsPlaced).getNumber());
//                playerList.get(player).getOpponentViewGui().getOpponentViewMatrix()[Integer.parseInt(placeShipCoordinateSplitted[0]) + i][Integer.parseInt(placeShipCoordinateSplitted[1])].setText(playerList.get(player).getShips().get(shipsPlaced).getSign());

            }

            // Deaktiviere Felder um das Schiff herum
            for (int i = (Integer.parseInt(placeShipCoordinateSplitted[0]) - 1); i <= playerList.get(player).getShips().get(shipsPlaced).getSize() + Integer.parseInt(placeShipCoordinateSplitted[0]); i++) {
                for (int j = (Integer.parseInt(placeShipCoordinateSplitted[1]) - 1); j < Integer.parseInt(placeShipCoordinateSplitted[1]) + 2; j++) {
                    try {
                        playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[i][j]
                                .setActive(false);
                        // Tetstweise eingebaut um zu sehen welche
                        // Felder deaktiviert werden
//                        playerList.get(player).getPlayerPlayFieldGui().getPlayfieldMatrix()[i][j].setText("D");
                    } catch (ArrayIndexOutOfBoundsException exc) {

                    }
                }
            }
//                    }
//                }
//            }
        }
        return true;
    }

    private boolean checkAiShipPlacement(int player, boolean orientation, int yCoordinate, int xCoordinate) {

        boolean aiOrientation = orientation;

        if (aiOrientation == true) {
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                try {
                    // Abfrage, welche prÃ¼ft ob das Feld auf der das
                    // Schiff gesetzt werden soll, deaktiviert ist.
                    // Falls ja:
                    // gibt die ganze Methode "false zurÃ¼ck".
                    if (playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate][xCoordinate + i]
                            .isActive() == false) {
                        //System.out.println("Leider nicht moeglich," + "\n" + "das Schiff muss mindestens 1 Feld Abstand zum naechsten Schiff haben!");
                        return false;
                    }
                    // Falls das Schiff mit der GrÃ¶ÃŸe nicht in das
                    // Array passt, fange die Fehlermeldung ab und
                    // gib folgendes aus...
                } catch (ArrayIndexOutOfBoundsException ex) {
                    //IO.println("Das Schiff passt so nicht auf das Spielfeld, bitte neue koordinaten eingeben!!!");
                    return false;
                }
            }
        } else {
            for (int i = 0; i < playerList.get(player).getShips().get(shipsPlaced).getSize(); i++) {
                try {
                    // Abfrage, welche prÃ¼ft ob das Feld auf der das
                    // Schiff gesetzt werden soll, deaktiviert ist.
                    // Falls ja:
                    // gibt die ganze Methode "false zurÃ¼ck".
                    if (playerList.get(player).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate + i][xCoordinate]
                            .isActive() == false) {
                        //System.out.println("Leider nicht moeglich," + "\n" + "das Schiff muss mindestens 1 Feld Abstand zum naechsten Schiff haben!");
                        return false;
                    }
                    // Falls das Schiff mit der GrÃ¶ÃŸe nicht in das
                    // Array passt, fange die Fehlermeldung ab und
                    // gib folgendes aus...
                } catch (ArrayIndexOutOfBoundsException ex) {
                    //IO.println("Das Schiff passt so nicht auf das Spielfeld, bitte neue koordinaten eingeben!!!");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Platziert KI-Schiff
     *
     * @param player Spielerindex
     * @return Gibt Booleanwert zurÃ¼ck, ob das Schiff gesetzt werden kann
     */
    private boolean placeAiShip(int playerIndex, int shipsPlaced) {

        for (shipsPlaced = 0; shipsPlaced < playerList.get(player).getShips().size(); shipsPlaced++) {
            boolean orientation = false;
            boolean loop = true;
            int yCoordinate = 0;
            int xCoordinate = 0;

            while (loop) {
                orientation = ((AiPlayer) playerList.get(playerIndex)).getAiOrientation();
                yCoordinate = ((AiPlayer) playerList.get(playerIndex)).getAiRandomNumber(playerList, playerIndex);
                xCoordinate = ((AiPlayer) playerList.get(playerIndex)).getAiRandomNumber(playerList, playerIndex);
                if (checkAiShipPlacement(playerIndex, orientation, yCoordinate, xCoordinate)) {
                    loop = false;
                }
            }
            // true = horizontal
            if (orientation == true) {
                // 1. ALLE Felder sind active
                // Alle Felder liegen innerhalb des playfields
                // Setze Schiff

                for (int i = 0; i < playerList.get(playerIndex).getShips().get(shipsPlaced).getSize(); i++) {
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate][xCoordinate + i].setText(playerList.get(playerIndex).getShips().get(shipsPlaced).getSign());
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate][xCoordinate + i].setIsWater(false);
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate][xCoordinate + i].setHasShip(true);
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate][xCoordinate + i].setShipNumber(playerList.get(playerIndex).getShips().get(shipsPlaced).getNumber());
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate][xCoordinate + i].setIsWater(true);
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate][xCoordinate + i].setHasShip(true);
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate][xCoordinate + i].setShipNumber(playerList.get(playerIndex).getShips().get(shipsPlaced).getNumber());
                }
                // Deaktiviere Felder um das Schiff herum fÃ¼r Spieleransicht/ -matrix
                for (int i = (xCoordinate - 1); i <= playerList.get(playerIndex).getShips().get(shipsPlaced).getSize() + xCoordinate; i++) {
                    for (int j = (yCoordinate - 1); j < yCoordinate + 2; j++) {
                        try {
                            playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[j][i]
                                    .setActive(false);
                        } catch (ArrayIndexOutOfBoundsException ex) {

                        }
                    }
                }
            } // false = vertikal
            else {
                // Setze Schiff
                for (int i = 0; i < playerList.get(playerIndex).getShips().get(shipsPlaced).getSize(); i++) {
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate + i][xCoordinate].setText(playerList.get(playerIndex).getShips().get(shipsPlaced).getSign());
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate + i][xCoordinate].setIsWater(false);
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate + i][xCoordinate].setHasShip(true);
                    playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[yCoordinate + i][xCoordinate].setShipNumber(playerList.get(playerIndex).getShips().get(shipsPlaced).getNumber());
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate + i][xCoordinate].setIsWater(false);
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate + i][xCoordinate].setHasShip(true);
                    playerList.get(playerIndex).getOpponentViewGui().getOpponentViewMatrix()[yCoordinate + i][xCoordinate].setShipNumber(playerList.get(playerIndex).getShips().get(shipsPlaced).getNumber());
                }

                // Deaktiviere Felder um das Schiff herumfÃ¼r Spieleransicht/ -matrix
                for (int i = (yCoordinate - 1); i <= playerList.get(playerIndex).getShips().get(shipsPlaced).getSize() + yCoordinate; i++) {
                    for (int j = (xCoordinate - 1); j < xCoordinate + 2; j++) {
                        try {
                            playerList.get(playerIndex).getPlayerViewGui().getPlayerViewMatrix()[i][j]
                                    .setActive(false);
                            // Tetstweise eingebaut um zu sehen welche
                            // Felder deaktiviert werden
                            // playerList.get(player).getPlayerPlayFieldGui().getPlayfieldMatrix()[i][j].setText("F");
                        } catch (ArrayIndexOutOfBoundsException exc) {
                        }
                    }
                }
            }
        }
        return true;
    }

    private void setDownReloadTime(ArrayList<Player> playerList, int player) {
        for (int shipNumber = 0; shipNumber < playerList.get(player).getShips().size(); shipNumber++) {
            if (playerList.get(player).getShips().get(shipNumber).getCurrentReloadTime() >= 1) {
                playerList.get(player).getShips().get(shipNumber).setDownReloadTime();
            }
        }
    }

    private void aiPlayerTurn(ArrayList<Player> playerList, int playerCounter) {
        //1. Auswahl des Schiffes
        IO.println(playerList.get(playerCounter).getName() + " ist am Zug!");
        //Vorher casten
        int aiShipIndex = ((AiPlayer) playerList.get(playerCounter)).getRandomShip(playerList, playerCounter);
        IO.println("Schiff: " + aiShipIndex + playerList.get(playerCounter).getShips().get(aiShipIndex).getName());
        int shootRange = playerList.get(playerCounter).getShips().get(aiShipIndex).getShootRange();
        boolean orientation = false;
        if (shootRange > 1) {
            orientation = ((AiPlayer) playerList.get(playerCounter)).getAiOrientation();
        }

        // 2. Auswahl eines Gegners.
        int aiOpponentIndex;
        if (((AiPlayer) playerList.get(playerCounter)).getAiLastHitOpponentIndex() == 9) {
            aiOpponentIndex = ((AiPlayer) playerList.get(playerCounter)).getAiOpponent(playerList, playerCounter);
        } else {
            aiOpponentIndex = ((AiPlayer) playerList.get(playerCounter)).getAiLastHitOpponentIndex();
        }
        IO.println("Gegner: " + playerList.get(aiOpponentIndex).getName());
        //playerList.get(aiOpponentIndex).getOpponentField().printOpponentField();
        // Koordinate wird gewählt

        // 3. Koordinate auf dem Spielfeld auswählen.
        String aiCoordinateToShoot = ((AiPlayer) playerList.get(playerCounter)).getAiChooseCoordinate(playerList, aiOpponentIndex, ((AiPlayer) playerList.get(playerCounter)).getAiLastHitCoordinate());
        //String aiCoordinateToShoot = Helper.aiChooseCoordinate(playerList, playerCounter, playerList.get(playerCounter).getAiLastHitCoordinate());
        IO.println("Koordinate: " + aiCoordinateToShoot);

        // 4.Schiessen
        String lastHitCoordinate = ((AiPlayer) playerList.get(playerCounter)).aiShootOnPlayField(playerList, aiOpponentIndex, shootRange, orientation, aiCoordinateToShoot);
        gameGui.showOpponentView(aiOpponentIndex);

        ((AiPlayer) playerList.get(playerCounter)).setAiLastHitCoordinate(lastHitCoordinate);

        // 5. Rundenende.
        // Nachladezeiten werden gesetzt
        playerList.get(playerCounter).getShips().get(aiShipIndex).setCurrentReloadTime();
        // Es wird geprüft, ob der Gegner verloren hat.
        if (Helper.checkIfShipAvailable(playerList, aiOpponentIndex) == false) {
            playerList.get(aiOpponentIndex).setLost(true);
        }
        if (playerList.get(aiOpponentIndex).getisLost() == true) {
            IO.println(playerList.get(aiOpponentIndex).getName() + " hat verloren!");
        }
    }

//    private void humanPlayerTurn(ArrayList<Player> playerList, int playerCounter) {
//        IO.println("Spieler " + playerList.get(playerCounter).getNumber()
//                + ": " + playerList.get(playerCounter).getName() + " ist am Zug!");
//
//        // 1. Auswahl eines verfuegbaren Schiffes.
//        int shipIndex = playerList.get(playerCounter).getAvailableShipToShoot(playerList, playerCounter);
//        int shootRange = playerList.get(playerCounter).getShips().get(shipIndex).getShootRange();
//        boolean orientation = false;
//        if (shootRange > 1) {
//            HelperOrientationDialog orientationDialog = new HelperOrientationDialog("Bitte geben Sie die Ausrichtung ein");
//            orientation = orientationDialog.getOrientation();
//        }
//
//        // 2. Auswahl eines Gegners.
//        int opponent = playerList.get(playerCounter).getAvailableOpponentsToShoot(playerList, playerCounter);
//
//        // 3. Koordinate auf dem Spielfeld auswählen.
//        String koordinate = playerList.get(playerCounter).coordinateToShoot();
//
//        // 4.Schiessen
//        playerList.get(playerCounter).shootOnPlayField(playerList, opponent, shootRange, orientation, koordinate);
//        playerList.get(playerCounter).getShips().get(shipIndex).setCurrentReloadTime();
//
//        if (Helper.checkIfShipAvailable(playerList, opponent) == false) {
//            playerList.get(opponent).setLost(true);
//        }
//
//        if (playerList.get(opponent).getisLost() == true) {
//            IO.println(playerList.get(opponent).getName() + " hat verloren!");
//        }
//    }
    
    /**
     * Dem Spielfeld "PlayerView wird ein Actionlistener hinzugefügt.
     * Wenn das Feld aktiviert wurde, kann man per Maus-Klick ein Event auslösen, welches die verfügbaren Schiffe platziert.
     */
    private void addPlayerViewMatrixListener() {

        playerList.get(player).getPlayerViewGui().setPlayerViewButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                HelperOrientationDialog placeShipOrientationDialog = new HelperOrientationDialog("Please choose what orientation the ship should have: ", "ship");
                shipOrientation = placeShipOrientationDialog.getOrientation();
                if (!checkShipPlacement(e, shipOrientation)) {
                    System.out.println("The Ship cant be placed please try again.");
                } else {
                    placeShip(e, shipOrientation, playerList);
                    shipsPlaced++;
                    if (shipsPlaced < playerList.get(player).getShips().size()) {
                        gameGui.activateSingleShipButton(shipsPlaced);
                        nextShipDialog();
                    } else {
                        shipsPlaced = 0;
                        showNextPlayerOrRoundButton();
                    }

                }
            }
        });
//        }
    }

    /**
     * Dem Spielfeld "OpponentView" wird ein Actionlistener hinzugefügt.
     * Auf dieses Spielfeld kann geschossen werden.
     * Die Koordinate wird gespeichert und an die Schießen Methode weitergegeben.

     */
    private void addOpponentViewMatrixListener() {
        for (Player pl : playerList) {
            pl.getOpponentViewGui().setOpponentViewButtonListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameGui.checkShipButtonSelection()) {
                        int shootRange = playerList.get(player).getShips().get(selectedShip).getShootRange();
                        if (shootRange > 1) {
                            HelperOrientationDialog shootOrientationDialog = new HelperOrientationDialog("Please choose the shoot alignment: ", "shoot");
                            shootOrientation = shootOrientationDialog.getOrientation();
                        }
                        String shootCoordinate = e.getActionCommand();
                        String[] shootCoordinateSplitted = shootCoordinate.split("\\#");
                        playerList.get(player).humanShootOnPlayfield(playerList, selectedPlayer, shootRange, shootOrientation, shootCoordinateSplitted);
                        playerList.get(player).getShips().get(selectedShip).setCurrentReloadTime();
                        playerList.get(selectedPlayer).getOpponentViewGui().disableOpponentView();
                        if (Helper.checkIfShipAvailable(playerList, selectedPlayer) == false) {
                            playerList.get(selectedPlayer).setLost(true);
                            System.out.println(playerList.get(selectedPlayer).getName() + " lost!");
                        } else {
                            showNextPlayerOrRoundButton();

                        }
                    }
                }
            });
        }
    }
/**
 * Den Spieler Buttons wird ein Actionlistener hinzugefügt.
 * Wird ein Button betätigt, wird der Button makiert und ein Event wird ausgelöst.
 */
    private void addPlayerButtonsActionListener() {
        gameGui.setPlayerButtonsActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == 1) {
                    selectedPlayer = Integer.parseInt(e.getActionCommand());
                    gameGui.showOpponentView(selectedPlayer);
                    System.out.println("You choosed player " + playerList.get(selectedPlayer).getName() + ".");
                    gameGui.changePlayerButtonColor(selectedPlayer);
                    if (gameGui.activateShipButtons(playerList, player)) {
                        System.out.println("Choose the ship you want to shoot with: ");
                        playerList.get(selectedPlayer).getOpponentViewGui().enableOpponentView();
                    } else {
                        System.out.println("All ships have a reload time.");
                        showNextPlayerOrRoundButton();
                    }
                }
            }
        });
    }

    private void addShipButtonsActionListener() {
        gameGui.setShipButtonsActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == 1) {
                    selectedShip = Integer.parseInt(e.getActionCommand());
                    System.out.println(playerList.get(player).getShips().get(selectedShip).getDescription());
                    gameGui.changeShipButtonColor(selectedShip);
                    System.out.println("\n" + "Click on the playfield where you want to shoot: ");

                }
            }
        });
    }

    private void showNextPlayerOrRoundButton() {
        if (gameState == 0) {
            if (player < playerList.size() - 1) {
                System.out.println("All ships placed!" + "\n");
                gameGui.deActivatePlayerAndShipButtons();
                playerList.get(player).getPlayerViewGui().disablePlayfield();
                gameGui.activateNextPlayerButton();
                gameGui.showEmptyMatrix();
            } else {

                System.out.println("Click on start round to start the first round." + "\n");
                gameGui.showEmptyMatrix();
                gameGui.deActivatePlayerAndShipButtons();
                playerList.get(player).getPlayerViewGui().disablePlayfield();
                gameGui.activateStartRoundButton();
                player = 0;

            }
        }
        if (gameState == 1) {
            if (player == playerList.size() - 1) {
                player = 0;
                System.out.println("Round " + roundNumber + " finished." + "\n");
                gameGui.activateNextRoundButton();


            } else {
                player++;
                gameGui.deActivatePlayerAndShipButtons();
                gameGui.activateNextPlayerButton();
            }
        }
    }

    private void addStartRoundListener() {
        gameGui.setStartRoundButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameState = 1;
                gameGui.deActivateStartRoundButton();
                if (playerList.get(player) instanceof AiPlayer) {
                    gameGui.showPlayerPlayField(player);
                    aiPlayerTurn(playerList, player);
                    gameGui.activateNextPlayerButton();
                } else {
                    gameGui.showPlayerPlayField(player);
                    gameGui.activateEnemyPlayerButton(player);
                    System.out.println(playerList.get(player).getName() + ", please choose the player you want to attack: ");
                }
            }
        });
    }
    
    private void addNextRoundListener() {
        gameGui.setNextRoundButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameGui.deActivateNextRoundButton();
                gameGui.deActivatePlayerAndShipButtons();
                
                if (playerList.get(player) instanceof AiPlayer) {
                    gameGui.showPlayerPlayField(player);
                    aiPlayerTurn(playerList, player);
                } else {
                    gameGui.showPlayerPlayField(player);
                    gameGui.activateEnemyPlayerButton(player);
                    System.out.println(playerList.get(player).getName() + ", please choose the player you want to attack: ");
                }
            }
        });
    }

    private void addNextPlayerButtonListener() {
        gameGui.setNextPlayerButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == 0) {
                    if (player < playerList.size() - 1) {
                        gameGui.deActivateNextPlayerButton();
                        player++;
                        gameGui.activateSingleShipButton(shipsPlaced);

                        addPlayerToGameGui(playerList);
                        playerList.get(player).getPlayerViewGui().enablePlayfield();
                        for(int s = 0; s < playerList.get(player).getShips().size(); s++){
                        	playerList.get(player).getShips().get(s).setDownReloadTime();
                        }
                        if (playerList.get(player) instanceof AiPlayer) {
                            placeAiShip(player, shipsPlaced);
                            gameGui.activatePlayerButton(player);
                            showNextPlayerOrRoundButton();
                        } else {
                            interactWithPlayer(playerList);
                            gameGui.activatePlayerButton(player);

                            addPlayerViewMatrixListener();

                        }

                    }
                }
                if (gameState == 1) {
                    gameGui.deActivateNextPlayerButton();
                    if (playerList.get(player) instanceof AiPlayer) {
                        gameGui.showPlayerPlayField(player);
                        aiPlayerTurn(playerList, player);
                    } else {
                        gameGui.showPlayerPlayField(player);
                        gameGui.activateEnemyPlayerButton(player);
                        System.out.println(playerList.get(player).getName() + ", please choose the player you want to attack: ");
                    }

                }
            }
        });

    }

    /**
     * Listener für das Starten des Spiels wird hinzugefügt.
     * 
     */
    private void addStartGameListener() {
        gameGui.setStartGameButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                playerList.get(player).getPlayerViewGui().enablePlayfield();
                gameGui.disableStartGameButton();
                if (playerList.get(player) instanceof AiPlayer) {
                    placeAiShip(player, shipsPlaced);
                    gameGui.activatePlayerButton(player);
                    gameGui.activateNextPlayerButton();
                    showNextPlayerOrRoundButton();

                } else {
                    interactWithPlayer(playerList);
                    gameGui.activatePlayerButton(player);
                    gameGui.activateSingleShipButton(shipsPlaced);

                }

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//
    }
}
