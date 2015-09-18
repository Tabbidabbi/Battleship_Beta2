/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Multimedia.BackgroundImagePanel;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Tobias
 */
public class BattleshipGui extends JFrame {

    private BackgroundImagePanel backgroundImagePanel;
    private MenuHandler menuHandler;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
    private int taskBarSize = scnMax.bottom;

    /**
     * Konstruktor
     */
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
