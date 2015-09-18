package Helper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class HelperMultipleChoiceDialog extends JDialog implements ActionListener {
	private String[] array;
	private String message;
	private JRadioButton[] radioButtons;
	private ButtonGroup group;
	private String result;
	
	/**
	 * Konstuktor
	 * @param String[] array
	 * @param String message
	 */
	public HelperMultipleChoiceDialog(String[] array, String message){
		super();
		this.array = array;
		this.message = message;
		
		//Setzt Titel des Windows
		setTitle("Auswahl");
		//Fenster kann nicht skaliert werden
		setResizable(false);
		//Fenster bleibt im Vordergrund
		setModal(true);
		//Setzt Layout
		this.setLayout(new GridLayout(3,1));
		
		//Panels
		JPanel windowPanel = new JPanel();
		JPanel messagePanel = new JPanel();
		JPanel radioButtonsPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		//WindowPanel
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
		radioButtonsPanel.setLayout(new BoxLayout(radioButtonsPanel, BoxLayout.Y_AXIS));
		
		// MessagePanel
		messagePanel.setBorder(null);
		//Erstellt JLabel
		JLabel label = new JLabel(this.message, SwingConstants.CENTER);
		add(label);
		windowPanel.add(messagePanel);
		
		//RadioButtons
		this.radioButtons = new JRadioButton[this.array.length]; 
		this.group = new ButtonGroup();
		//Erstllt RadioButtons
		for(int counter = 0; counter < this.array.length; counter++){
			this.radioButtons[counter] = new JRadioButton();
			this.radioButtons[counter].setBorder(null);
			this.radioButtons[counter].setText(this.array[counter]);
			radioButtonsPanel.add(this.radioButtons[counter]);
			this.group.add(this.radioButtons[counter]);
		}
		windowPanel.add(radioButtonsPanel);
		
		//Button
		JButton button = new JButton("OK"); 
	    buttonPanel.add(button); 
	    button.addActionListener(this);
	    windowPanel.add(buttonPanel);
	    
	    this.add(windowPanel);
		
	    //Dialog location
	    final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
	
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    //Setzt Fenstergröße automatisch
	    pack(); 
	    setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		for(int counter = 0; counter < this.radioButtons.length; counter++){
			if(this.radioButtons[counter].isSelected()){
				System.out.println(e.getActionCommand());
				System.out.println(this.radioButtons[counter].getText());
				this.result = this.radioButtons[counter].getText(); 
				setVisible(false);
				dispose();
			}
		}
	}
	
	public String getResult(){
		return this.result;
	}
	
	public static void main(String[] args) {
		String[] array = {"eins Zerstörer", "zwei Zerstörer", "drei Frigatte", "vier Frigatte", "fünf Korvette", "sechs Korvette", "sieben U-Boot", "acht U-Boot"};
		HelperMultipleChoiceDialog dialog = new HelperMultipleChoiceDialog(array, "Mit welchem Schiff willst du schiessen?");
		System.out.println("Übergabe des Wertes: " + dialog.getResult());
}
}
