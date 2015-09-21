package Gameobjects.Ships;

import java.io.Serializable;

import IO.IO;

public abstract class Ship implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7853606283486598296L;
	private boolean orientation;
    private boolean sunk;
    private int currentReloadTime;
    private int hitpoints;
    private int number;
    private int reloadTime;
    private int shootRange;
    private int size;    
    private String name;
    private String sign;
    
    private String description;
    
    /**
     * Konstruktor Schiff
     * @param sign
     * @param size
     * @param sunk
     * @param number
     * @param orientation
     * @param reloadTime
     * @param currentReloadTime
     * @param shootRange
     * @param name
     */
	public Ship(String sign, int size, boolean sunk, int number, boolean orientation, int reloadTime, int currentReloadTime, int shootRange, String name) {
        this.sign = sign;
        this.number = number;
        this.size = size;
        this.sunk = sunk;
        this.orientation = orientation;
        this.reloadTime = reloadTime;
        this.currentReloadTime = currentReloadTime;
        this.shootRange = shootRange;
        this.hitpoints = size;
        this.name = name;
        this.description = "You choosed " + name + " : "
                                    + "The " + name + " can shoot on " + shootRange + "\n" + " (contigous) fields and has a reload time "
                + " of  " + reloadTime + " rounds.";
    }

	/**
	 * Gibt Größe zurück
	 * @return size
	 */
    public int getSize() {
        return size;
    }

    /**
     * Setzt Größe
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gibt zurück, ob Schiff gesunken ist
     * @return sunk
     */
    public boolean getIsSunk() {
        return sunk;
    }

    /**
     * Setzt Schiff gesunken
     * @param sunk
     */
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    /**
     * Gibst Schiffsnummer zurück
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setzt uUmmer
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gibt Orienierung zurück
     * @return orientation
     */
    public boolean isOrientation() {
        return orientation;
    }

    /**
     * Setzt Orienierung
     * @param orientation
     */
    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    /**
     * gibt Reloadzeit zurück
     * @return reloadTime
     */
    public int getReloadTime() {
        return reloadTime;
    }

    /**
     * Setzt Nachladezeitzeit
     * @param reloadTime
     */
    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    /**
     * Gibt momentane Reloadzeit zurück
     * @return
     */
    public int getCurrentReloadTime() {
        return currentReloadTime;
    }

    /**
     * Setzt  momentaneReloadzeit
     */
    public void setCurrentReloadTime() {
        this.currentReloadTime = this.reloadTime + 1;
    }
    
    /**
     * Setzt  momentane Relodzeit runter
     */
    public void setDownReloadTime() {
        this.currentReloadTime--;
    }

    /**
     * Gibt Reichweite zurück
     * @return
     */
    public int getShootRange() {
        return shootRange;
    }

    /**
     * Setzt Reichweite
     * @param shootRange
     */
    public void setShootRange(int shootRange) {
        this.shootRange = shootRange;
    }

    /**
     * Gibt Trefferpunkte zurück
     * @return hitpoints
     */
    public int getHitpoints() {
        return hitpoints;
    }
    

    /**
     * Setzt Trefferpunkte
     */
    public void setHitpoints() {
    	this.hitpoints--;
    	if(getHitpoints() == 0){
    		setSunk(true);
    		IO.println("Ship was sunk.");
    		//test
    		IO.println(getNumber()+ " " + getName() + " " + getHitpoints() + " " + getIsSunk());
    	}
    }

    /**
     * Gibt Zeichen zurück
     * @return sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setzt Zeichen
     * @param sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Gibt Namen zurück
     * @return Namen
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt Namen
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    
       
       
}
