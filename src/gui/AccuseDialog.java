package gui;

import game.Suggestion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import cards.Person;
import cards.Room;
import cards.Weapon;

public class AccuseDialog extends JDialog implements ActionListener{

	private GameFrame controller;

	private final JPanel optionsPanel = new JPanel();
	JButton accuseButton;
	JButton cancelButton;

	/*declare the button groups */
	ButtonGroup buttonGroupRooms;
	ButtonGroup buttonGroupCharacters;
	ButtonGroup buttonGroupWeapons;

	/*When the suggestion is created, pass these variables to constructor */
	String selectionRoom;
	String selectionCharacter;
	String selectionWeapon;

	public AccuseDialog(GameFrame frame) {
		this.controller = frame;
		setResizable(false);
		setBounds(0, 0, 450, 340);
		getContentPane().setLayout(new BorderLayout());
		//optionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		optionsPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(optionsPanel, BorderLayout.CENTER);

		GridBagLayout g = new GridBagLayout();
		g.columnWidths = new int[]{0, 0, 0, 0};
		g.rowHeights = new int[]{0, 0, 0, 0, 0};
		g.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		g.rowWeights = new double[]{0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optionsPanel.setLayout(g);
		GridBagConstraints gConstraints = new GridBagConstraints();
		gConstraints.anchor = GridBagConstraints.WEST;

		/* Add the label Select Room: */
		JLabel lblSelectRoom = new JLabel("Select Room:");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 0;
		optionsPanel.add(lblSelectRoom, gConstraints);

		/* Add the label Select Character: */
		JLabel lblSelectCharacter = new JLabel("Select Character:");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 0;
		optionsPanel.add(lblSelectCharacter, gConstraints);

		/*Add the label Select Weapon: */
		JLabel lblSelectWeapon = new JLabel("Select Weapon:");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 0;
		optionsPanel.add(lblSelectWeapon, gConstraints);



		/*******************************************************
		 * *****************************************************
		 **Adding the radio buttons for the Room selection*
		 *******************************************************
		 *******************************************************
		 */
		JRadioButton rdbtnKitchen = new JRadioButton("Kitchen");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 1;
		optionsPanel.add(rdbtnKitchen, gConstraints);

		JRadioButton rdbtnBallroom = new JRadioButton("Ballroom");
		gConstraints.insets = new Insets(0, 0, 5, 5); //change second parameter to line them up with the other options
		gConstraints.gridx = 0;
		gConstraints.gridy = 2;
		optionsPanel.add(rdbtnBallroom, gConstraints);

		JRadioButton rdbtnConservatory = new JRadioButton("Conservatory");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 3;
		optionsPanel.add(rdbtnConservatory, gConstraints);

		JRadioButton rdbtnDining = new JRadioButton("Dining");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 4;
		optionsPanel.add(rdbtnDining, gConstraints);

		JRadioButton rdbtnLounge = new JRadioButton("Lounge");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 5;
		optionsPanel.add(rdbtnLounge, gConstraints);

		JRadioButton rdbtnHall = new JRadioButton("Hall");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 6;
		optionsPanel.add(rdbtnHall, gConstraints);

		JRadioButton rdbtnLibrary = new JRadioButton("Library");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 7;
		optionsPanel.add(rdbtnLibrary, gConstraints);

		JRadioButton rdbtnStudy = new JRadioButton("Study");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 8;
		optionsPanel.add(rdbtnStudy, gConstraints);

		JRadioButton rdbtnBilliard = new JRadioButton("Billiard");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 0;
		gConstraints.gridy = 9;
		optionsPanel.add(rdbtnBilliard, gConstraints);


		/*Add the Rooms into a ButtonGroup so only one can be selected at a time */
		buttonGroupRooms = new ButtonGroup();
		buttonGroupRooms.add(rdbtnKitchen);
		buttonGroupRooms.add(rdbtnBallroom);
		buttonGroupRooms.add(rdbtnConservatory);
		buttonGroupRooms.add(rdbtnDining);
		buttonGroupRooms.add(rdbtnLounge);
		buttonGroupRooms.add(rdbtnHall);
		buttonGroupRooms.add(rdbtnLibrary);
		buttonGroupRooms.add(rdbtnStudy);
		buttonGroupRooms.add(rdbtnBilliard);


		/*******************************************************
		 * *****************************************************
		 **Adding the radio buttons for the Character selection*
		 *******************************************************
		 *******************************************************
		 */
		JRadioButton rdbtnMissScarlett = new JRadioButton("Miss Scarlett");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 1;
		optionsPanel.add(rdbtnMissScarlett, gConstraints);

		JRadioButton rdbtnMrsPeacock = new JRadioButton("Mrs Peacock");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 2;
		optionsPanel.add(rdbtnMrsPeacock, gConstraints);

		JRadioButton rdbtnMrsWhite = new JRadioButton("Mrs White");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 3;
		optionsPanel.add(rdbtnMrsWhite, gConstraints);

		JRadioButton rdbtnColonelMustard = new JRadioButton("Colonel Mustard");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 4;
		optionsPanel.add(rdbtnColonelMustard, gConstraints);

		JRadioButton rdbtnRevGreen = new JRadioButton("Reverend Green");
		gConstraints.insets = new Insets(0, 0, 5, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 5;
		optionsPanel.add(rdbtnRevGreen, gConstraints);

		JRadioButton rdbtnMrPlum = new JRadioButton("Mr Plum");
		gConstraints.insets = new Insets(0, 0, 0, 5);
		gConstraints.gridx = 1;
		gConstraints.gridy = 6;
		optionsPanel.add(rdbtnMrPlum, gConstraints);

		/*Add the Characters into a ButtonGroup so only one can be selected at a time */
		buttonGroupCharacters = new ButtonGroup();
		buttonGroupCharacters.add(rdbtnMissScarlett);
		buttonGroupCharacters.add(rdbtnMrsPeacock);
		buttonGroupCharacters.add(rdbtnMrsWhite);
		buttonGroupCharacters.add(rdbtnColonelMustard);
		buttonGroupCharacters.add(rdbtnRevGreen);
		buttonGroupCharacters.add(rdbtnMrPlum);



		/*******************************************************
		 * *****************************************************
		 **Adding the radio buttons for the Weapon selection*
		 *******************************************************
		 *******************************************************
		 */
		JRadioButton rdbtnCandleStick = new JRadioButton("Candlestick");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 1;
		optionsPanel.add(rdbtnCandleStick, gConstraints);

		JRadioButton rdbtnDagger = new JRadioButton("Dagger");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 2;
		optionsPanel.add(rdbtnDagger, gConstraints);

		JRadioButton rdbtnPipe = new JRadioButton("Pipe");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 3;
		optionsPanel.add(rdbtnPipe, gConstraints);

		JRadioButton rdbtnRevolver = new JRadioButton("Revolver");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 4;
		optionsPanel.add(rdbtnRevolver, gConstraints);

		JRadioButton rdbtnRope = new JRadioButton("Rope");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 5;
		optionsPanel.add(rdbtnRope, gConstraints);

		JRadioButton rdbtnSpanner = new JRadioButton("Spanner");
		gConstraints.insets = new Insets(0, 0, 5, 0);
		gConstraints.gridx = 2;
		gConstraints.gridy = 6;
		optionsPanel.add(rdbtnSpanner, gConstraints);


		/*Add the weapons into a ButtonGroup so only one can be selected at a time */
		buttonGroupWeapons = new ButtonGroup();
		buttonGroupWeapons.add(rdbtnCandleStick);
		buttonGroupWeapons.add(rdbtnDagger);
		buttonGroupWeapons.add(rdbtnPipe);
		buttonGroupWeapons.add(rdbtnRevolver);
		buttonGroupWeapons.add(rdbtnRope);
		buttonGroupWeapons.add(rdbtnSpanner);




		/*Add the buttons and put them at bottom right*/
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		accuseButton = new JButton("Accuse");
		accuseButton.setActionCommand("OK");
		buttonPane.add(accuseButton);
		getRootPane().setDefaultButton(accuseButton);
		accuseButton.addActionListener(this);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(this);
	}

	/**
	 * Helper method
	 * This method iterates over the elements in the button group and return the selected button as a string
	 * @param bg
	 * @return Radio button that was selected
	 */
	public String getSelection(ButtonGroup bg) {
		/*Iterate over the elements in the group */
		for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null; //might cause error if no selection made? maybe return ""??
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(e.getSource().equals(accuseButton)) {
				/*3 strings to store which one is selected */
				String currentRoom = getSelection(buttonGroupRooms);
				String currentCharacter = getSelection(buttonGroupCharacters);
				String currentWeapon = getSelection(buttonGroupWeapons);

				if (currentRoom == null || currentCharacter == null || currentWeapon == null){
					JOptionPane.showMessageDialog(null, "Select one of each", "Select All Three",JOptionPane.INFORMATION_MESSAGE);
					return;
				}


				Room selectionRoom = new Room(getSelectedRoom(currentRoom));
				Person selectionCharacter = new Person (getSelectedCharacter(currentCharacter));
				Weapon selectionWeapon = new Weapon (getSelectedWeapon(currentWeapon));
				controller.makeSuggestion(new Suggestion(selectionRoom, selectionCharacter, selectionWeapon));


				/*testing outputs*/
				System.out.println(selectionRoom);
				System.out.println(selectionCharacter);
				System.out.println(selectionWeapon);
			}
			if(e.getSource().equals(cancelButton)) {
				dispose();
			}
		}
		catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Error loading Card while making accusation! " + e1);
		}
	}

	/**
	 * Return the room that was selected
	 * @param s
	 * @return String name of room that was selected
	 */
	public String getSelectedRoom(String s) {
		if(s == null) {
			return null;
		}
        switch (s) {
    	case "Kitchen":
    		return "Kitchen";
    	case "Ballroom":
    		return "Ballroom";
    	case "Conservatory":
    		return  "Conservatory";
    	case "Dining":
    		return  "Dining";
    	case "Lounge":
    		return  "Lounge";
    	case "Hall":
    		return  "Hall";
    	case "Library":
    		return  "Library";
    	case "Study":
    		return  "Study";
    	case "Billiard":
    		return "Billiard";
    	default:
    		System.out.println("No selection made");
    		break;
        }
		return null;
	}

	/**
	 * Return the Character that was selected
	 * @param s
	 * @return String name of character that was selected
	 */
	public String getSelectedCharacter(String s) {
		if(s == null) {
			return null;
		}
        switch (s) {
    	case "Colonel Mustard":
    		return "Mustard";
    	case "Mrs White":
    		return "White";
    	case "Miss Scarlett":
    		return "Scarlett";
    	case "Reverend Green":
    		return "Green";
    	case "Mrs Peacock":
    		return "Peacock";
    	case "Mr Plum":
    		return "Plum";
    	default:
    		System.out.println("No selection made");
    		break;
        }
		return null;
	}

	/**
	 * Return the weapon that was selected
	 * @param s
	 * @return String name of weapon that was selected
	 */
	public String getSelectedWeapon(String s) {
		if(s == null) {
			return null;
		}
        switch (s) {
    	case "Candlestick":
    		return "Candlestick";
    	case "Dagger":
    		return "Dagger";
    	case "Pipe":
    		return "Pipe";
    	case "Revolver":
    		return "Revolver";
    	case "Rope":
    		return "Rope";
    	case "Spanner":
    		return "Spanner";
    	default:
    		System.out.println("No selection made");
    		break;
        }
		return null;
	}

	/*for testing the dialog*/
	public static void main(String[] args) {
		try {
			AccuseDialog dialog = new AccuseDialog(new GameFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
