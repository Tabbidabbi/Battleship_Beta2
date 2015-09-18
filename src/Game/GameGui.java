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

    public GameGui(Settings gameSettings) {
        this.gameSettings = gameSettings;
        setOpaque(false);
        GroupLayout gameGuiLayout = new GroupLayout(this);

        playerPlayFieldPanel = new JPanel();
        playFieldCardLayout = new CardLayout();
        playerPlayFieldPanel.setLayout(playFieldCardLayout);
        playerPlayFieldPanel.setOpaque(false);

        playerListLabel = new JLabel("Spieler: ");
        playerListPanel = new JPanel();
        playerListPanel.add(playerListLabel);
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));

        textOutputArea = new JTextArea();
        textOutputArea.setEditable(false);
        textOutputArea.setLineWrap(true);
        textOutputArea.setFont(new Font("Serif", Font.BOLD, 12));
        PrintStream printStream = new PrintStream(new CustomOutputStream(textOutputArea), true);
        standardOut = System.out;
        System.setOut(printStream);

        textOutputPanel = new JScrollPane(textOutputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        shipListLabel = new JLabel("Schiffe: ");
        shipListPanel = new JPanel();
        shipListPanel.add(shipListLabel);
        shipListPanel.setLayout(new BoxLayout(shipListPanel, BoxLayout.Y_AXIS));

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

    public void addPlayerView(int playerNumber, ArrayList<Player> playerList) {

        playerPlayFieldPanel.add(playerList.get(playerNumber).getPlayerViewGui(), "Player" + playerNumber);

    }

    public void addOpponentView(int playerNumber, ArrayList<Player> playerList) {
        playerPlayFieldPanel.add(playerList.get(playerNumber).getOpponentViewGui(), "" + playerList.get(playerNumber).getNumber());
    }

    public void showOpponentView(int enemyNumber) {
        playFieldCardLayout.show(playerPlayFieldPanel, "" + enemyNumber);
    }

    public void showEmptyMatrix() {

        playFieldCardLayout.show(playerPlayFieldPanel, "emptyMatrix");

    }

    public void showPlayerPlayField(int playerNumber) {
        playFieldCardLayout.show(playerPlayFieldPanel, "Player" + playerNumber);
//        repaint();

    }

    public void addShipButtonsToGameGui(int playerNumber, ArrayList<Player> playerList) {
        shipListButtons = new JButton[playerList.get(playerNumber).getShips().size()];
        Dimension maxButtonSize;
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i] = new JButton(playerList.get(playerNumber).getShips().get(i).getName() + "(S. " + playerList.get(playerNumber).getShips().get(i).getSize() + ")");
//            maxButtonSize = shipListButtons[0].getMaximumSize();
            shipListButtons[i].setActionCommand(Integer.toString(playerList.get(playerNumber).getShips().get(i).getNumber()));
            shipListButtons[i].setEnabled(false);
            shipListButtons[i].setSelected(false);
//            shipListButtons[i].setMaximumSize(maxButtonSize);
            shipListPanel.add(shipListButtons[i]);
        }
    }

    public void setShipButtonsActionListener(ActionListener l) {
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].addActionListener(l);

        }

    }

    public void addPlayerButtonsToGameGui(ArrayList<Player> playerList) {
        playerButton = new JButton[playerList.size()];
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i] = new JButton(playerList.get(i).getName());
            playerButton[i].setActionCommand(Integer.toString(playerList.get(i).getNumber()));
            playerButton[i].setEnabled(false);
            playerListPanel.add(playerButton[i]);

        }

    }

    public void setPlayerButtonsActionListener(ActionListener l) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].addActionListener(l);

        }

    }

    public void activatePlayerButton(int player) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setEnabled(false);
            playerButton[player].setEnabled(true);
        }
    }

    public boolean checkShipButtonSelection() {
        for (int i = 0; i < shipListButtons.length; i++) {
            if (shipListButtons[i].isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void changePlayerButtonColor(int enemyPlayer) {

        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setBackground(new JButton().getBackground());
            playerButton[enemyPlayer].setBackground(Color.red);

        }

    }

    public void changeShipButtonColor(int shipNumber) {

        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].setBackground(new JButton().getBackground());
            shipListButtons[i].setSelected(false);
            shipListButtons[shipNumber].setBackground(Color.red);
            shipListButtons[shipNumber].setSelected(true);
        }

    }

    public void activateEnemyPlayerButton(int player) {
        for (int i = 0; i < playerButton.length; i++) {
            playerButton[i].setEnabled(true);
            playerButton[player].setEnabled(false);
        }
    }

    public void activateSingleShipButton(int ship) {
        for (int i = 0; i < shipListButtons.length; i++) {
            shipListButtons[i].setEnabled(false);
            shipListButtons[ship].setEnabled(true);
        }
    }

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

    public void activateStartRoundButton() {
        this.startRoundButton.setVisible(true);
    }

    public void deActivateStartRoundButton() {
        this.startRoundButton.setVisible(false);
    }

    public void activateNextRoundButton() {
        this.nextRoundButton.setVisible(true);
    }

    public void deActivateNextRoundButton() {
        this.nextRoundButton.setVisible(false);
    }

    public void setNextRoundButtonListener(ActionListener l) {
        this.nextRoundButton.addActionListener(l);
    }

    public void activateNextPlayerButton() {
        this.nextPlayerButton.setVisible(true);
    }

    public void setStartRoundButtonListener(ActionListener l) {
        this.startRoundButton.addActionListener(l);
    }

    public void deActivateNextPlayerButton() {
        this.nextPlayerButton.setVisible(false);
    }

    public void setNextPlayerButtonListener(ActionListener l) {
        this.nextPlayerButton.addActionListener(l);
    }

    public void setGameButtonListener(ActionListener l) {
        this.menuButton.addActionListener(l);
    }

    public void setStartGameButtonListener(ActionListener l) {
        this.startGameButton.addActionListener(l);
    }

    public void disableStartGameButton() {
        this.startGameButton.setVisible(false);
    }

    public class CustomOutputStream extends OutputStream {

        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            // redirects data to the text area
            textArea.append(String.valueOf((char) b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
