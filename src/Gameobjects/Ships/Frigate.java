package Gameobjects.Ships;

import java.io.Serializable;

public class Frigate extends Ship implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263420806064302275L;
	String name = "Frigate";
        boolean isPlaced;
	
	//Frigate positionieren
	
	public Frigate(int number){
		super("F", 4, false, number, false, 2, 0, 2, "Frigate");
                this.isPlaced = false;
		// TODO Auto-generated constructor stub
	}

	//Getter und Setter Methoden
	
        @Override
	public String getName() {
		return name;
	}

        @Override
	public void setName(String name) {
		this.name = name;
	}
}
