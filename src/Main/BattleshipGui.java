/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Game.GameGui;
import Game.InstructionsGui;
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
public class BattleshipGui extends JFrame {

    BackgroundImagePanel backgroundImagePanel;

    MenuHandler menuHandler;

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
    int taskBarSize = scnMax.bottom;

    public BattleshipGui() {
        setTitle("Battleship");
        setSize(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
        setContentPane(backgroundImagePanel = new BackgroundImagePanel("Images\\background.jpg"));

        menuHandler = new MenuHandler();

        add(menuHandler);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
