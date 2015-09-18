/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gameobjects.Playfield;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Game.*;
import IO.IO;

import java.awt.color.ColorSpace;
import java.util.ArrayList;

/**
 *
 * @author Tobias
 */
public class OpponentViewGui extends JPanel {

    private Settings gameSettings;
    private FieldGui playfieldButton;

    private FieldGui[][] opponentViewMatrix;
    private JPanel opponentViewMatrixPanel;

    public OpponentViewGui(Settings gameSettings) {
        setLayout(new BorderLayout());
        this.gameSettings = gameSettings;
        //Gegneransicht
        opponentViewMatrix = new FieldGui[gameSettings.getPlayfieldSize() + 1][gameSettings.getPlayfieldSize() + 1];

        opponentViewMatrixPanel = new JPanel();
        opponentViewMatrixPanel.setLayout(new GridLayout(gameSettings.getPlayfieldSize() + 1, gameSettings.getPlayfieldSize() + 1));
        for (int i = 0; i < opponentViewMatrix.length; i++) {
            for (int j = 0; j < opponentViewMatrix[i].length; j++) {
                opponentViewMatrix[i][j] = new FieldGui();
                opponentViewMatrix[i][j].setEnabled(true);
                opponentViewMatrix[i][j].setActionCommand("" + i + "#" + j);
                opponentViewMatrix[i][0].setText("" + i);
                opponentViewMatrix[i][0].setBackground(Color.white);
                opponentViewMatrix[i][0].setEnabled(false);
                opponentViewMatrix[i][0].setActive(false);
                opponentViewMatrix[0][j].setText("" + j);
                opponentViewMatrix[0][j].setEnabled(false);
                opponentViewMatrix[0][j].setActive(false);
                opponentViewMatrix[0][j].setBackground(Color.white);
                opponentViewMatrix[0][0].setText("Y  /  X");
                opponentViewMatrix[0][0].setFont(new Font("Serif", Font.BOLD, 10));
                opponentViewMatrixPanel.add(opponentViewMatrix[i][j]);
//                if (playfieldMatrix[i][j].isActive()) {
//                    playfieldMatrix[i][j].setText("a");
//                } else { 
//                    playfieldMatrix[i][j].setText("d");
//                }
            }
        }
        add(opponentViewMatrixPanel);
        setOpaque(false);
        setVisible(true);

    }

    public FieldGui getPlayfieldButton() {
        return playfieldButton;
    }

    public void setPlayfieldButton(FieldGui playfieldButton) {
        this.playfieldButton = playfieldButton;
    }

    public FieldGui[][] getOpponentViewMatrix() {
        return opponentViewMatrix;
    }

    /**
     * Setzt Schuß auf das Feld, das getroffen wurde
     *
     * @param String coordinate
     * @param int shootRange
     * @param boolean orientation
     * @return hitShips int-Array mit Anzahl der getroffenen Schiffe
     */
    public ArrayList<Integer> setShot(String[] coordinate, int shootRange, boolean orientation) {
        //Array, in dem  die getroffenen Schiffe stehen
        ArrayList<Integer> hitShips = new ArrayList<>();
        if (orientation == true) {
            for (int i = 0; i < shootRange; i++) {
                try {
                    if (this.opponentViewMatrix[Integer.parseInt(coordinate[0])][Integer.parseInt(coordinate[1]) + i].setIsShot() != 99) {
                        hitShips.add(this.opponentViewMatrix[Integer.parseInt(coordinate[0])][Integer.parseInt(coordinate[1]) + i].setIsShot());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < shootRange; i++) {
                try {
                    if (this.opponentViewMatrix[Integer.parseInt(coordinate[0]) + i][Integer.parseInt(coordinate[1])].setIsShot() != 99) {
                        hitShips.add(this.opponentViewMatrix[Integer.parseInt(coordinate[0]) + i][Integer.parseInt(coordinate[1])].setIsShot());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            }
        }
        return hitShips;
    }
    
       public ArrayList<Integer> setAiShot(int yCoordinate,int xCoordinate, int shootRange, boolean orientation) {
        //Array, in dem  die getroffenen Schiffe stehen
        ArrayList<Integer> hitShips = new ArrayList<>();
        if (orientation == true) {
            for (int i = 0; i < shootRange; i++) {
                try {
                    if (this.opponentViewMatrix[yCoordinate][(xCoordinate) + i].setIsShot() != 99) {
                        hitShips.add(this.opponentViewMatrix[yCoordinate] [(xCoordinate) + i].setIsShot());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < shootRange; i++) {
                try {
                    if (this.opponentViewMatrix[(yCoordinate) + i][(xCoordinate)].setIsShot() != 99) {
                        hitShips.add(this.opponentViewMatrix[(yCoordinate) + i][(xCoordinate)].setIsShot());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            }
        }
        return hitShips;
    }

    public void setOpponentViewButtonListener(ActionListener l) {

        for (int i = 0; i < opponentViewMatrix.length; i++) {
            for (int j = 0; j < opponentViewMatrix[i].length; j++) {
                opponentViewMatrix[i][j].addActionListener(l);
            }

        }
    }

}
