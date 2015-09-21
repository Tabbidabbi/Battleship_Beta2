/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Gameobjects.Player.Player;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Gameobjects.Playfield.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author Tobias
 */
public class GameGui extends JPanel {

    private BoxLayout boxLayout;
    private FieldGui[][] emptyMatrix;
    private JPanel emptyMatrixPanel;
    private CardLayout playFieldCardLayout;
    private JPanel playerPlayFieldPanel;
    private JPanel[] playerPlayFieldArray;
    private Settings gameSettings;
    private JLabel playerListLabel;
    private JButton[] playerButton;
    private JPanel playerListPanel;
    private JTextArea textOutputArea;
    private JScrollPane textOutputPanel;
    private JPanel midPanel;
    private JLabel shipListLabel;
    private JButton[] shipListButtons;
    private JPanel shipListPanel;
    private JPanel componentPanel;
    private JButton menuButton, startGameButton, nextPlayerButton, startRoundButton, nextRoundButton;
    private JPanel buttonPanel;
    private PrintStream standardOut;

    //Konstruktor
    public GameGui(Settings gameSettings) {
        this.gameSettings = gameSettings;
        setOpaque(false);
        GroupLayout gameGuiLayout = new GroupLayout(this);

        //Panel wird initialisiert
        playerPlayFieldPanel = new JPanel();
        playFieldCardLayout = new CardLayout();
        playerPlayFieldPanel.setLayout(playFieldCardLayout);
        playerPlayFieldPanel.setOpaque(false);

        //Label wird initialisier
        playerListLabel = new JLabel("Spieler: ");
        playerListPanel = new JPanel();
        playerListPanel.add(playerListLabel);
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));

        //TextArea wird initisiert
        textOutputArea = new JTextArea();
        textOutputArea.setEditable(false);
        textOutputArea.setLineWrap(true);
        textOutputArea.setFont(new Font("Serif", Font.BOLD, 12));
        PrintStream printStream = new PrintStream(new CustomOutputStream(textOutputArea), true);
        standardOut = System.out;
        System.setOut(printStream);

        //ScrollPane wird initialisert
        textOutputPanel = new JScrollPane(textOutputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        shipListLabel = new JLabel("Schiffe: ");
        shipListPanel = new JPanel();
        shipListPanel.add(shipListLabel);
        shipListPanel.setLayout(new BoxLayout(shipListPanel, BoxLayout.Y_AXIS));

        //Buttons werden initialisiert
        startGameButton = new JButton("Start-Game");
        startGameButton.setActionCommand("Game-StartGame");
        startGameButton.setFont(new Font("Serif", 10, 13));
        startGameButton.setBackground(Color.white);
        startGameButton.setForeground(Color.black);
        nextRoundButton = new JButton("Next-Round");
        nextRoundButton.setActionCommand("Game-StartGame");
        nextRoundButton.setFont(new Font("Serif", 10, 13));
        nextRoundButton.setBackground(Color.white);
        nextRoundButton.setForeground(Color.black);
        nextRoundButton.setVisible(false);
        nextPlayerButton = new JButton("NextPlayer");
        nextPlayerButton.setActionCommand("Game-NextPlayer");
        nextPlayerButton.setFont(new Font("Serif", 10, 13));
        nextPlayerButton.setBackground(Color.white);
        nextPlayerButton.setForeground(Color.black);
        nextPlayerButton.setVisible(false);
        startRoundButton = new JButton("Start-Round");
        startRoundButton.setActionCommand("Game-StartRound");
        startRoundButton.setFont(new Font("Serif", 10, 13));
        startRoundButton.setBackground(Color.white);
        startRoundButton.setForeground(Color.black);
        startRoundButton.setVisible(false);
        menuButton = new JButton("Main-Menu");
        menuButton.setActionCommand("Game-MainMenu");
        menuButton.setFont(new Font("Serif", 10, 13));
        menuButton.setBackground(Color.white);
        menuButton.setForeground(Color.black);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(menuButton);

        //Leere Matrix für die neutrale Ansicht des Spielfeldes
        emptyMatrix = new FieldGui[gameSettings.getPlayfieldSize() + 1][gameSettings.getPlayfieldSize() + 1];
        emptyMatrixPanel = new JPanel();
        emptyMatrixPanel.setLayout(new GridLayout(gameSettings.getPlayfieldSize() + 1, gameSettings.getPlayfieldSize() + 1));

        for (int i = 0; i < emptyMatrix.length; i++) {
            for (int j = 0; j < emptyMatrix[i].length; j++) {
                emptyMatrix[i][j] = new FieldGui();
                emptyMatrix[i][0].setText("" + i);
                emptyMatrix[i][0].setActive(false);
                emptyMatrix[0][j].setText("" + j);
                emptyMatrix[0][j].setActive(false);
                emptyMatrix[0][0].setText("Y  /  X");
                emptyMatrix[0][0].setFont(new Font("Serif", Font.BOLD, 10));
                emptyMatrix[i][j].setBackground(Color.gray);
                emptyMatrix[i][j].setEnabled(false);
                emptyMatrixPanel.add(emptyMatrix[i][j]);
            }
        }

        setLayout(gameGuiLayout);
        gameGuiLayout.setVerticalGroup(gameGuiLayout.createSequentialGroup()
                .addGroup(gameGuiLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(gameGuiLayout.createSequentialGroup()
                                .addComponent(playerPlayFieldPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(startGameButton)
                                .addComponent(nextPlayerButton)
                                .addComponent(startRoundButton)
                                .addComponent(nextRoundButton)
                        )
                        .addGroup(gameGuiLayout.createSequentialGroup()
                                .addComponent(textOutputPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(gameGuiLayout.createParallelGroup()
                                        .addComponent(playerListPanel)
                                        .addComponent(shipListPanel))
                        )
                )
                .addComponent(buttonPanel)
        );
        gameGuiLayout.setHorizontalGroup(gameGuiLayout.createSequentialGroup()
                .addGroup(gameGuiLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(playerPlayFieldPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startGameButton)
                        .addComponent(nextPlayerButton)
                        .addComponent(startRoundButton)
                        .addComponent(nextRoundButton)
                )
                .addGroup(gameGuiLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(textOutputPanel, 0, GroupLayout.DEFAULT_SIZE, 350)
                        .addGroup(gameGuiLayout.createSequentialGroup()
                                .addComponent(playerListPanel)
                                .addComponent(shipListPanel)
                        )
                        .addComponent(buttonPanel))
        );

        playerPlayFieldPanel.add(emptyMatrixPanel, "emptyMatrix");

        gameGuiLayout.setAutoCreateGaps(true);
        gameGuiLayout.setAutoCreateContainerGaps(true);

        setPreferredSize(new Dimension(1024, 768));
        setVisible(true);
    }

    /**
     * playerView wird hinzugefügt.
     *
     * @param playerNumber
     * @param playerList
     */
    public void addPlayerView(int playerNumber, ArrayList<Player> playerList) {
        playerPlayFieldPanel.add(playerList.get(playerNumber).getPlayerViewGui(), "Player" + playerNumber);
    }

    /**
     * opponentView wird hinzugefügt.
     *
     * @param playerNumber
     * @param playerList
     */
    public void addOpponentView(int playerNumber, ArrayList<Player> playerList) {
        playerPlayFieldPanel.add(playerList.get(playerNumber).getOpponentViewGui(), "" + playerList.get(playerNumber).getNumber());
    }

    /**
     * Zeigt Ansicht für die Gegner. Darstellung ohne Schiffe.
     *
     * @param enemyNumber
     */
    public void showOpponentView(int enemyNumber) {
        playFieldCardLayout.show(playerPlayFieldPanel, "" + enemyNumber);
    }

    /**
     * Zeigt emptyMatrix
     */
    public void showEmptyMatrix() {

        playFieldCardLayout.show(playerPlayFieldPanel, "emptyMatrix");

    }

    /**
     * Zeigt playerPlayFieldPanel
     *
     * @param playerNumber
     */
    public void showPlayerPlayField(int playerNumber) {
        playFieldCardLayout.show(playerPlayFieldPanel, "Player" + playerNumber);
//        repaint();
    }

    /**
     * Schiffsbuttons werden zur Gui hinzugefügt
     */
    public void addShipButtonsToGameGui(int playerNumber, ArrayList<Player> playerList) {
        shipListButtons = new JButton[playerList.get(playerNumber).getShips().size()];
        Dimension maxButtonSize = new Dimension(130, 27);
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i] = new JButton(playerList.get(playerNumber).getShips().get(i).getName() + "(S. " + playerList.get(playerNumber).getShips().get(i).getSize() + ")");
            shipListButtons[i].setActionCommand(Integer.toString(playerList.get(playerNumber).getShips().get(i).getNumber()));
            shipListButtons[i].setEnabled(false);
            shipListButtons[i].setSelected(false);
            shipListButtons[i].setMaximumSize(maxButtonSize);
            shipListPanel.add(shipListButtons[i]);
        }
    }

    /**
     * ActionListener wird zu den Schiffsbuttons hinzugefügt.
     *
     * @param l
     */
    public void setShipButtonsActionListener(ActionListener l) {
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].addActionListener(l);

        }

    }

    /**
     * //Spielerbuttons werden zur Gui hinzugefügt
     *
     * @param playerList
     */
    public void addPlayerButtonsToGameGui(ArrayList<Player> playerList) {
        playerButton = new JButton[playerList.size()];
        Dimension maxButtonSize = new Dimension(100, 27);

        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i] = new JButton(playerList.get(i).getName());
            playerButton[i].setActionCommand(Integer.toString(playerList.get(i).getNumber()));
            playerButton[i].setMaximumSize(maxButtonSize);
            playerButton[i].setEnabled(false);
            playerListPanel.add(playerButton[i]);

        }

    }

    /**
     * ActionListener wird den Spielerbuttons hinzugefügt
     *
     * @param l
     */
    public void setPlayerButtonsActionListener(ActionListener l) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].addActionListener(l);

        }

    }

    /**
     * Aktiviert einen Spielerbutton
     *
     * @param player
     */
    public void activatePlayerButton(int player) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setEnabled(false);
            playerButton[player].setEnabled(true);
        }
    }

    /**
     * Prüft, ob ein Schiffsbutton selected ist
     *
     * @return boolean
     */
    public boolean checkShipButtonSelection() {
        for (int i = 0; i < shipListButtons.length; i++) {
            if (shipListButtons[i].isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ändert die Farbe eines Spielerbuttons
     *
     * @param enemyPlayer
     */
    public void changePlayerButtonColor(int enemyPlayer) {

        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setBackground(new JButton().getBackground());
            playerButton[enemyPlayer].setBackground(Color.red);

        }

    }

    /**
     * Ändert die Farbe eines Schiffsbuttons
     *
     * @param shipNumber
     */
    public void changeShipButtonColor(int shipNumber) {

        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].setBackground(new JButton().getBackground());
            shipListButtons[i].setSelected(false);
            shipListButtons[shipNumber].setBackground(Color.red);
            shipListButtons[shipNumber].setSelected(true);
        }

    }

    /**
     * Aktiviert verfügbare Playerbuttons
     *
     * @param player
     */
    public void activateEnemyPlayerButton(int player) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setEnabled(true);
            playerButton[player].setEnabled(false);
        }
    }

    /**
     * Aktiviert einzelnen Schiffsbutton
     *
     * @param int ship
     */
    public void activateSingleShipButton(int ship) {
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].setEnabled(false);
            shipListButtons[ship].setEnabled(true);
        }
    }

    /**
     * Aktiviert Schiffsbuttons
     *
     * @param playerlist
     * @param player
     * @return boolean
     */
    public boolean activateShipButtons(ArrayList<Player> playerlist, int player) {

        int counter = 0;
        for (int i = 0; i < shipListButtons.length; i++) {
            if (playerlist.get(player).getShips().get(i).getIsSunk() == false
                    && playerlist.get(player).getShips().get(i).getCurrentReloadTime() == 0) {
                shipListButtons[i].setEnabled(true);
                counter++;
            }
        }
        if (counter == 0) {
            return false;
        }
        return true;
    }

    //Deaktivier Spieler- und Schiffsbuttons
    public void deActivatePlayerAndShipButtons() {
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].setBackground(new JButton().getBackground());
            shipListButtons[i].setEnabled(false);
        }
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setBackground(new JButton().getBackground());
            playerButton[i].setEnabled(false);

        }
    }

    /**
     * Aktiviert startRoundButton
     */
    public void activateStartRoundButton() {
        this.startRoundButton.setVisible(true);
    }

    /**
     * Deaktiviert startRoundButton
     */
    public void deActivateStartRoundButton() {
        this.startRoundButton.setVisible(false);
    }

    /**
     * Aktiviert nextRoundButton
     */
    public void activateNextRoundButton() {
        this.nextRoundButton.setVisible(true);
    }

    /**
     * Deaktiviert nextRoundButton
     */
    public void deActivateNextRoundButton() {
        this.nextRoundButton.setVisible(false);
    }

    /**
     * nextRoundButton wird der Gui hintugefügt
     *
     * @param ActionListener l
     */
    public void setNextRoundButtonListener(ActionListener l) {
        this.nextRoundButton.addActionListener(l);
    }

    /**
     * nextPlayerButton wird unsichtbar gemacht
     */
    public void activateNextPlayerButton() {
        this.nextPlayerButton.setVisible(true);
    }

    /**
     * startRoundButton wird der Gui hinzugefügt
     *
     * @param ActionListener l
     */
    public void setStartRoundButtonListener(ActionListener l) {
        this.startRoundButton.addActionListener(l);
    }

    /**
     * nextPlayerButton wird deaktiviert
     */
    public void deActivateNextPlayerButton() {
        this.nextPlayerButton.setVisible(false);
    }

    /**
     * nextPlayerButton wird der Gui hinzugefügt
     *
     * @param ActionListener l
     */
    public void setNextPlayerButtonListener(ActionListener l) {
        this.nextPlayerButton.addActionListener(l);
    }

    /**
     * menuButton wird der Gui hinzugefügt
     *
     * @param ActionListener l
     */
    public void setGameButtonListener(ActionListener l) {
        this.menuButton.addActionListener(l);
    }

    /**
     * startGameButton wird der Gui hinzugefügt
     *
     * @param ActionListener l
     */
    public void setStartGameButtonListener(ActionListener l) {
        this.startGameButton.addActionListener(l);
    }

    /**
     * startGameButton wird unsichtbar gemacht
     *
     * @param ActionListener l
     */
    public void disableStartGameButton() {
        this.startGameButton.setVisible(false);
    }

    /**
     * CustomOutputStream wird implementiert
     */
    public class CustomOutputStream extends OutputStream {

        private JTextArea textArea;

        //Konstruktor
        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        //Befüllen der TextArea
        public void write(int b) throws IOException {
            // redirects data to the text area
            textArea.append(String.valueOf((char) b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
