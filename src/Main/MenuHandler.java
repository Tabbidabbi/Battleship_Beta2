/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Game.Game;
import Game.GameGui;
import Game.InstructionsGui;
import Game.Settings;
import Game.SettingsGui;
import Gameobjects.Playfield.PlayerViewGui;
import Multimedia.BackgroundImagePanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Tobias
 */
public class MenuHandler extends JPanel implements ActionListener {

    JPanel panelContainer;
    MainMenuGui mainMenuGui;
    SettingsGui settingsGui;
    InstructionsGui instructionsGui;
    GameGui gameGui;
    PlayerViewGui playfieldGui;

    GridBagLayout gameGuiLayout;
    GridBagConstraints gridBagConstraints;

    CardLayout cardLayout;

    Settings gameSettings;

    Game newGame;

    public MenuHandler() {

        setOpaque(false);
        setBackground(Color.red);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        mainMenuGui = new MainMenuGui();
        mainMenuGui.setOpaque(false);

        add(mainMenuGui, "menu");

        cardLayout.show(this, "menu");

        addMenuListener();

        setPreferredSize(null);
        setVisible(true);

    }

    private void addMenuListener() {
        this.mainMenuGui.setListener(this);
    }

    private void addSettingsGuiListener() {
        this.settingsGui.setListener(this);
    }

    private void addInstructionGuiListener() {
        this.instructionsGui.setListener(this);
    }

    private void addGameGuiListener() {
        this.newGame.getGameGui().setGameButtonListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {

            case "Menu-NewGame":
                this.settingsGui = new SettingsGui();
                add(settingsGui, "settings");
                settingsGui.setPreferredSize(new Dimension(1024, 768));
                addSettingsGuiListener();
                cardLayout.show(this, "settings");
                break;
            case "Menu-LoadGame":
                break;
            case "Menu-Instructions":
                instructionsGui = new InstructionsGui();
                add(instructionsGui, "instructions");
                addInstructionGuiListener();
                cardLayout.show(this, "instructions");
                break;
            case "Menu-ExitGame":
                System.exit(0);
                break;
            case "Settings-MainMenu":
                cardLayout.show(this, "menu");
                break;
            case "Settings-SaveSettings":
                settingsGui.setSettings();
                this.gameSettings = new Settings(settingsGui);
                this.newGame = new Game(gameSettings);
                remove(settingsGui);
                add(newGame.getGameGui(), "newGame");
                cardLayout.show(this, "newGame");
                addGameGuiListener();
                break;
            case "Instructions-MainMenuButton":
                cardLayout.show(this, "menu");
                break;
            case "Game-MainMenu":
                cardLayout.show(this, "menu");
                break;
            case "Game-SaveGame":
                System.out.println("Halllo");
                break;

        }

    }

}
