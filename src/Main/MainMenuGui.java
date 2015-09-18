/*
 * To change this license headerLabel, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Multimedia.BackgroundImagePanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Game.*;

/**
 *
 * @author Tobias
 */
public class MainMenuGui extends JPanel {

    public JPanel backGroundIMGPanel;
    Box buttonBox;
    JLabel headerLabel;
    JPanel menuButtonPanel;
    JPanel headerPanel;
    public JPanel menuPanel;
    JButton[] menuButtons = {new JButton("Neues Spiel"), new JButton("Spiel Laden"),
        new JButton("Anleitung"), new JButton("Spiel Verlassen")};
    
    public CardLayout cardLayout = new CardLayout();

    final Dimension MAXBUTTONSIZE = menuButtons[3].getMaximumSize();
    

    public MainMenuGui() {
        
        headerLabel = new JLabel("Schiffeversenken Alpha_5");
        headerLabel.setFont(new Font("Serif", 25, 25));
        headerPanel = new JPanel();
        headerPanel.add(headerLabel);
        headerPanel.setOpaque(false);

        buttonBox = new Box(BoxLayout.Y_AXIS);
        buttonBox.add(Box.createRigidArea(new Dimension(1, 35)));

        for (int i = 0; i < menuButtons.length; i++) {
            buttonBox.add(menuButtons[i]);
            buttonBox.add(Box.createRigidArea(new Dimension(0, 10)));
            menuButtons[i].setMaximumSize(MAXBUTTONSIZE);
            menuButtons[i].setBackground(Color.white);
            menuButtons[i].setForeground(Color.black);
            menuButtons[i].setFont(new Font("Serif", 10, 13));
        }
        menuButtons[0].setActionCommand("Menu-NewGame");
        menuButtons[1].setActionCommand("Menu-LoadGame");
        menuButtons[2].setActionCommand("Menu-Instructions");
        menuButtons[3].setActionCommand("Menu-ExitGame");

        menuButtonPanel = new JPanel();
        menuButtonPanel.add(buttonBox);
        menuButtonPanel.setOpaque(false);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(headerPanel);
        menuPanel.add(menuButtonPanel);
        menuPanel.setOpaque(false);


        add(menuPanel);

        setVisible(true);

    }
        
public void setListener(ActionListener l) {
    for (int i = 0; i < menuButtons.length; i++) {
        this.menuButtons[i].addActionListener(l);
    }
}

}
