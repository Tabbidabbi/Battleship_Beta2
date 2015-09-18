/*
 * To change this license headerLabel, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import Main.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author Tobias
 */
public class SettingsGui extends JPanel {
	
	private static final long serialVersionUID = 2804307014155123447L;
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

    JLabel headerLabel;
    JPanel headerPanel;
    JComboBox amountPlayerComboBox;
    String[] comboBoxItems = {"2", "3", "4", "5", "6"};
    JLabel playerComboBoxLabel;
    JPanel playerComboBoxPanel;
    JLabel[] ammountPlayersLabel = {new JLabel("Spieler 1:"), new JLabel("Spieler 2:"), new JLabel("Spieler 3:"),
        new JLabel("Spieler 4:"), new JLabel("Spieler 5:"), new JLabel("Spieler 6:")};
    JTextField[] playerTextFields = new JTextField[6];
    JCheckBox[] kiCheckboxes = new JCheckBox[6];
    JPanel[] singlePlayerPanel = new JPanel[6];
    JPanel playerPanel;
    JSpinner[] setAmmountOfShipsSpinner;
    JLabel[] shipLabel = {new JLabel("Anzahl der Zerstörer:"),
        new JLabel("Anzahl der Fregatten:"), new JLabel("Anzahl der Korvetten:"),
        new JLabel("Anzahl der U-Boote:")};
    JPanel[] singleShipPanel = new JPanel[4];
    JPanel shipFieldsPanel;
    JLabel playFieldSizeLabel;
    JSpinner playFieldSizeSpinner;
    JPanel playFieldSizePanel;
    JPanel categoriePanel;
    JPanel backPanel;
    JButton backButton, resetSettingsButton, SaveSettingsButton;
    JPanel buttonPanel;
    MainMenuGui mainMenuGUI;
    Settings gameSettings;
    

    public SettingsGui() {
        setLayout(new GridLayout());
        this.amountOfAllShips = amountOfDestroyer + amountOfFrigate + amountOfCorvette + amountOfSubmarine;

        headerLabel = new JLabel("Einstellungen");
        headerLabel.setFont(new Font("Serif", 25, 25));
        headerPanel = new JPanel();
        headerPanel.add(headerLabel);
        headerPanel.setOpaque(false);

        playerComboBoxPanel = new JPanel();
        playerComboBoxLabel = new JLabel("Anzahl der Spieler");
        amountPlayerComboBox = new JComboBox(comboBoxItems);
        amountPlayerComboBox.addItemListener(new ComboBoxHandler());
        amountPlayerComboBox.setSelectedItem(comboBoxItems[0]);
        playerComboBoxPanel.add(playerComboBoxLabel);
        playerComboBoxPanel.add(amountPlayerComboBox);

        playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(250, 150));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < playerTextFields.length; i++) {
            playerTextFields[i] = new JTextField("Spieler" + (i + 1), 10);
            singlePlayerPanel[i] = new JPanel();
            kiCheckboxes[i] = new JCheckBox("KI");
            singlePlayerPanel[i].add(ammountPlayersLabel[i]);
            singlePlayerPanel[i].add(playerTextFields[i]);
            singlePlayerPanel[i].add(kiCheckboxes[i]);
            playerPanel.add(singlePlayerPanel[i]);
            if (i > 1) {
                playerTextFields[i].setEditable(false);
                kiCheckboxes[i].setEnabled(false);
            }

        }
        setAmmountOfShipsSpinner = new JSpinner[4];
        shipFieldsPanel = new JPanel();
        shipFieldsPanel.setLayout(new BoxLayout(shipFieldsPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 4; i++) {
            setAmmountOfShipsSpinner[i] = new JSpinner();
            singleShipPanel[i] = new JPanel();
            singleShipPanel[i].add(shipLabel[i]);
            singleShipPanel[i].add(setAmmountOfShipsSpinner[i]);
//            setAmmountOfShipsSpinner[i].addChangeListener(new SetShipSpinnerHandler() );
            shipFieldsPanel.add(singleShipPanel[i]);
        }
        setAmmountOfShipsSpinner[0].setModel(new SpinnerNumberModel(1, 0, 2, 1));
        setAmmountOfShipsSpinner[1].setModel(new SpinnerNumberModel(0, 0, 3, 1));
        setAmmountOfShipsSpinner[2].setModel(new SpinnerNumberModel(0, 0, 3, 1));
        setAmmountOfShipsSpinner[3].setModel(new SpinnerNumberModel(0, 0, 4, 1));

        playFieldSizeLabel = new JLabel("Spielfeldgröße:");
        playFieldSizeSpinner = new JSpinner();
//        playFieldSizeSpinner.addChangeListener(new PlayfieldSizeHandler());
        playFieldSizeSpinner.setModel(new SpinnerNumberModel(8, 6, 18, 1));

        playFieldSizePanel = new JPanel();
        playFieldSizePanel.add(playFieldSizeLabel);
        playFieldSizePanel.add(playFieldSizeSpinner);
        shipFieldsPanel.add(playFieldSizePanel);

        categoriePanel = new JPanel();

        categoriePanel.setLayout(new BoxLayout(categoriePanel, BoxLayout.X_AXIS));
        categoriePanel.add(playerComboBoxPanel);
        categoriePanel.add(playerPanel);
        categoriePanel.add(shipFieldsPanel);

        backButton = new JButton("Hauptmenü");
        backButton.setActionCommand("Settings-MainMenu");
        backButton.setFont(new Font("Serif", 10, 13));
        backButton.setBackground(Color.white);
        backButton.setForeground(Color.black);
        SaveSettingsButton = new JButton("Speichern");
        SaveSettingsButton.setActionCommand("Settings-SaveSettings");

        SaveSettingsButton.setFont(new Font("Serif", 10, 13));
        SaveSettingsButton.setBackground(Color.white);
        SaveSettingsButton.setForeground(Color.black);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(SaveSettingsButton);
        buttonPanel.add(Box.createHorizontalGlue());

        backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
        backPanel.add(headerPanel);
        backPanel.add(categoriePanel);
        backPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        backPanel.add(buttonPanel);
        backPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        add(backPanel);

    }

    public String[] getPlayerNames() {
        return playerNames;
    }

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
    }

    public int getAmountOfFrigate() {
        return amountOfFrigate;
    }

    public void setAmountOfFrigate(int amountOfFrigate) {
        this.amountOfFrigate = amountOfFrigate;
    }

    public int getAmountOfCorvette() {
        return amountOfCorvette;
    }

    public void setAmountOfCorvette(int amountOfCorvette) {
        this.amountOfCorvette = amountOfCorvette;
    }

    public int getAmountOfSubmarine() {
        return amountOfSubmarine;
    }

    public void setAmountOfSubmarine(int amountOfSubmarine) {
        this.amountOfSubmarine = amountOfSubmarine;
    }

    public int getAmountOfAllShips() {
        return amountOfAllShips;
    }

    public void setAmountOfAllShips(int amountOfAllShips) {
        this.amountOfAllShips = amountOfAllShips;
    }

    public int getPlayfieldSize() {
        return playfieldSize;
    }

    public void setPlayfieldSize(int playfieldSize) {
        this.playfieldSize = playfieldSize;
    }

    public void setListener(ActionListener l) {
        this.backButton.addActionListener(l);
        this.SaveSettingsButton.addActionListener(l);
    }


    private class ComboBoxHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            int command = amountPlayerComboBox.getSelectedIndex();

            if (e.getStateChange() == ItemEvent.SELECTED) {

                switch (command) {
                    case 0:
                        playerTextFields[2].setEditable(false);
                        playerTextFields[3].setEditable(false);
                        playerTextFields[4].setEditable(false);
                        playerTextFields[5].setEditable(false);

                        kiCheckboxes[2].setEnabled(false);
                        kiCheckboxes[3].setEnabled(false);
                        kiCheckboxes[4].setEnabled(false);
                        kiCheckboxes[5].setEnabled(false);
                        break;
                    case 1:
                        playerTextFields[2].setEditable(true);
                        playerTextFields[3].setEditable(false);
                        playerTextFields[4].setEditable(false);
                        playerTextFields[5].setEditable(false);

                        kiCheckboxes[2].setEnabled(true);
                        kiCheckboxes[3].setEnabled(false);
                        kiCheckboxes[4].setEnabled(false);
                        kiCheckboxes[5].setEnabled(false);

                        break;
                    case 2:
                        playerTextFields[2].setEditable(true);
                        playerTextFields[3].setEditable(true);
                        playerTextFields[4].setEditable(false);
                        playerTextFields[5].setEditable(false);

                        kiCheckboxes[2].setEnabled(true);
                        kiCheckboxes[3].setEnabled(true);
                        kiCheckboxes[4].setEnabled(false);
                        kiCheckboxes[5].setEnabled(false);

                        break;
                    case 3:
                        playerTextFields[2].setEditable(true);
                        playerTextFields[3].setEditable(true);
                        playerTextFields[4].setEditable(true);
                        playerTextFields[5].setEditable(false);

                        kiCheckboxes[2].setEnabled(true);
                        kiCheckboxes[3].setEnabled(true);
                        kiCheckboxes[4].setEnabled(true);
                        kiCheckboxes[5].setEnabled(false);

                        break;
                    case 4:
                        playerTextFields[2].setEditable(true);
                        playerTextFields[3].setEditable(true);
                        playerTextFields[4].setEditable(true);
                        playerTextFields[5].setEditable(true);

                        kiCheckboxes[2].setEnabled(true);
                        kiCheckboxes[3].setEnabled(true);
                        kiCheckboxes[4].setEnabled(true);
                        kiCheckboxes[5].setEnabled(true);

                        break;
                }
            }
        }
    }

    public void setSettings() {
        String userInput = (String) amountPlayerComboBox.getSelectedItem();
        amountOfPlayer = Integer.parseInt(userInput);
        
        playerNames = new String[amountOfPlayer];
        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = playerTextFields[i].getText();
        }
        //Setzt KI-Spieler in das Array
        aiArray = new boolean[amountOfPlayer];
        for (int i = 0; i < amountOfPlayer; i++) {
            if (kiCheckboxes[i].isSelected()){
            	aiArray[i] = true;
                //amountOfKIPlayer++;
            }
            else{
            	aiArray[i] = false;
            }
        }
        amountOfDestroyer = (int)setAmmountOfShipsSpinner[0].getValue();
        amountOfFrigate = (int)setAmmountOfShipsSpinner[1].getValue();
        amountOfCorvette = (int)setAmmountOfShipsSpinner[2].getValue();
        amountOfSubmarine = (int)setAmmountOfShipsSpinner[3].getValue();
        
        playfieldSize = (int)playFieldSizeSpinner.getValue();
    }
}
