package Multimedia;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.io.*;
import javax.imageio.*;

public class BackgroundImagePanel extends JPanel {
    
    private Image image = null;
    
    
    public BackgroundImagePanel(String imageSource) {
                if (imageSource != null) {
                MediaTracker mt = new MediaTracker(this);
                image = Toolkit.getDefaultToolkit().getImage(imageSource);
                mt.addImage(image, 0);
                
                try {
                    mt.waitForAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
    @Override
      protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
        }
        
        
        
    }
    
    
    

