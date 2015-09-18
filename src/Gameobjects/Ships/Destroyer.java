package Gameobjects.Ships;

import java.io.Serializable;

public class Destroyer extends Ship implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4752146596402397185L;
	String name = "Destroyer";
        boolean isPlaced;

	//Zerstörer positionieren
    public Destroyer(int number) {
        super("D", 5, false, number, false, 3, 0, 3, "Destroyer");
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
