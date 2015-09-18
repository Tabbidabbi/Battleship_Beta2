/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gameobjects.Playfield;

import IO.IO;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author Tobias
 */
public class FieldGui extends JButton implements Serializable {
    
	private static final long serialVersionUID = -787848770021504750L;
    private boolean active;
    private boolean isShot;
    private boolean isWater;
    private boolean isHit;
    private boolean hasShip;
    private int shipNumber;
    private String fieldNumber;

    /**
     * Konstruktor
     */
    public FieldGui() {
        setFont(new Font("Serif", Font.BOLD, 30 ));
        setBorder(new LineBorder(new Color(200, 214, 222)));
        setVisible(true);
        setBackground(new Color(64, 164, 223));
        this.active = true;
        this.hasShip = false;
        this.isWater = true;
        this.isHit = false;
        this.isShot = false;
    }

    /**
     * Gibt zurück, ob das Feld aktiv ist.
     * Active wird geprüft, um Schiffe zu setzen.
     * @return boolean active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setzt Feld aktiv oder inaktiv.
     * Auf dem Feld wird die Lage des Schiffes und drumherum auf inaktiv gesetzt.
     * @param boolean active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Gibt zurück, ob auf das Feld geschossen wurde.
     * @return boolean isShot
     */
    public boolean getIsShot() {
		return isShot;
	}

    /**
     * Setzt Schuss auf das Feld, versieht es mit einem Zeichen und
     * gibt die Schiffsnummer zurück, wenn sich eines auf dem Feld befindet.
     * @return int shipNumber
     */
    public int setIsShot(){
		if(this.isShot == false){
			this.isShot = true;
			if(getHasShip() == true){
				this.setText("X");
				this.setIsHit(true);
				return getShipNumber();
				//IO.println("Sie haben ein Schiff getroffen!");
			}
			else{
				this.setText("O");
				//IO.println("Sie haben auf Wasser geschossen!");
			}
		}
		else{
			IO.println("Sie haben bereits auf dieses Feld geschossen. Ein verschenkter Schuss!");
		}
		return 99;
	}

    /**
     * Gibt zurück, ob diese Feld nur Wasser ist.
     * @return boolean isWater
     */
	public boolean getIsWater() {
		return isWater;
	}

	/**
	 * Setzt Feld auf Wasser.
	 * @param boolean isWater
	 */
	public void setIsWater(boolean isWater) {
		this.isWater = isWater;
	}

	/**
	 * Gibt zurück, ob Schiff getroffen wurde.
	 * !Schiff muss sich auf dem Feld befinden!
	 * @return boolean isHit
	 */
	public boolean getIsHit() {
		return isHit;
	}

	/**
	 * Setzt Hit auf dem Feld, jedoch nur, wenn dich ein Schiff auf dem Feld befindet,
	 * welches abgeschossen wurde.
	 * @param isHit
	 */
	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}
	
	/**
	 * Gibt zurück, ob sich ein Schiff auf dem Feld befindet.
	 * @return boolean hasShip
	 */
	public boolean getHasShip() {
		return hasShip;
	}

	/**
	 * Setzt boolean, dass sich ein Schiff auf dem Feld befindet.
	 * @param hasShip
	 */
	public void setHasShip(boolean hasShip) {
		this.hasShip = hasShip;
	}

	/**
	 * Gibt Schiffsnummer des Schiffes zurück, welches sich auf dem Feld befindet.
	 * @return int shipNumber
	 */
	public int getShipNumber() {
		return shipNumber;
	}

	/**
	 * Setzt Schiffsnummer auf das Feld.
	 * @param shipNumber
	 */
	public void setShipNumber(int shipNumber) {
		this.shipNumber = shipNumber;
	}

	/**
	 * Gibt Feldnummer zurück.
	 * FieldNumber ist Koordiante.
	 * @return String fieldNumber
	 */
	public String getFieldNumber() {
		return fieldNumber;
	}

	/**
	 * Setzt Feldnummer.
	 * @param fieldNumber
	 */
	public void setFieldNumber(String fieldNumber) {
		this.fieldNumber = fieldNumber;
	}

	/**
	 * Setzt Text des Feldbuttons
	 * @param String text (X, O)
	 */
	public void setText(String text) {
        super.setText(text); 
    }
}
