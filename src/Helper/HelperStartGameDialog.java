/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Tobias
 */
public class HelperStartGameDialog extends JDialog{
    
        private String message;
        
          JPanel windowPanel;
        JPanel messagePanel;
        JPanel buttonPanel;
        
        JButton startGameButton;

    public HelperStartGameDialog(String message) {
         super();

        this.message = message;
        
                //Setzt Titel des Windows
        setTitle("Bestätigen");
        //Fenster kann nicht skaliert werden
        setResizable(false);
        //Fenster bleibt im Vordergrund
//        setModal(true);
        //Setzt Layout
        this.setLayout(new GridLayout(3, 1));

        //Panels
        windowPanel = new JPanel();
        messagePanel = new JPanel();
        buttonPanel = new JPanel();

        //WindowPanel
        windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));

        // MessagePanel
        JLabel label = new JLabel(this.message);
        add(label);
        windowPanel.add(messagePanel);

        //Button
        startGameButton = new JButton("Runde Starten");
        startGameButton.setActionCommand("Helper-StartGame");
        buttonPanel.add(startGameButton);
        windowPanel.add(buttonPanel);

        this.add(windowPanel);

        //Dialog location
//        setBounds(400, 250, 300, 300);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Setzt Fenstergröße automatisch
        pack();
        setVisible(false);
    }

     public void setActionListener(ActionListener l) {
         startGameButton.addActionListener(l);
     }   
    
}
