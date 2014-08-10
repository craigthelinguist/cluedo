package gui;

import game.Player;
import cards.Person;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class NewGameDialog extends JDialog {

	private final String[] playerOptions = new String[]{ "Human", "Computer", "None" };
	private final String[] characterOptions = new String[]{ "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Professor Plum" };
	private final int NUM_PLAYERS = 6;

	private JComboBox[] players = new JComboBox[NUM_PLAYERS];
	private JComboBox[] characters = new JComboBox[NUM_PLAYERS];
	private GameFrame controller;
	
	public NewGameDialog(GameFrame frame){

		// set up for the dialog
		super(frame,true); // prevents interaction with underlying JFrame
		controller = frame;	
		this.setBounds(50, 50, 550, 450);
		
		/* button for starting the game */
		JButton gameStarter = new JButton("Start Game");
		gameStarter.addActionListener(new StartButtonListener());
	
		ComboBoxListener comboListener = new ComboBoxListener();

		/* combo boxes for setting options */
		for (int i = 0; i < NUM_PLAYERS; i++){
			characters[i] = new JComboBox<>(characterOptions);
			characters[i].setSelectedIndex(i);
		}
		for (int i = 0; i < NUM_PLAYERS; i++){
			players[i] = new JComboBox<>(playerOptions);
			if (i > 2){
				players[i].setSelectedIndex(2);
				characters[i].setEnabled(false);
			}
			players[i].addActionListener(comboListener);
		}
		
		

		/* labels */
		JLabel labelTitle = new JLabel("Welcome! Please set up a new game: ");
		JLabel labelPlayers = new JLabel("Players");
		JLabel labelCharacters = new JLabel("Characters");

		/** Layout **/
		//JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		/* First two rows */
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(labelTitle)
		);
		vertical.addGroup(layout.createParallelGroup()
			.addComponent(labelPlayers)
			.addComponent(labelCharacters)
		);

		/* Remaining rows */
		for (int i = 0; i < 6; i++){

			vertical.addGroup(layout.createParallelGroup()
				.addComponent(players[i])
				.addComponent(characters[i])
			);

		}

		/* Last row is a button */
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(gameStarter)
		);
	
		
		/* Create first column */
		ParallelGroup col1 = layout.createParallelGroup()
			.addComponent(labelTitle)
			.addComponent(labelPlayers);
		horizontal.addGroup(col1);

		/* Create second column */
		ParallelGroup col2 = layout.createParallelGroup();
		col2.addComponent(labelCharacters);
		horizontal.addGroup(col2);

		/* Add all player combo boxes to first column */
		for (int i = 0; i < players.length; i++){
			col1.addComponent(players[i]);
		}

		/* Add all character combo boxes to second column */
		for (int i = 0; i < characters.length; i++){
			col2.addComponent(characters[i]);
		}
		
		/* Add button at end */
		col1.addComponent(gameStarter);

		/* Frame setup */
		//this.add(panel);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(getMaximumSize());
		this.validate();
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * The ComboBoxListener is for prettifying the JDialog as the user is configuring
	 * the game setup. For example, it will grey out a text field if the player for
	 * that field is 'None'.
	 * @author craigthelinguist
	 *
	 */
	private class ComboBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			// if the player is 'None' then grey-out the corresponding Character combobox
			JComboBox<String> combo = (JComboBox<String>)(event.getSource());
			for (int i = 0; i < 6; i++){
				if (players[i] == combo){
					if (combo.getSelectedItem().equals("None")) characters[i].setEnabled(false);
					else characters[i].setEnabled(true);
					return;
				}
			}
			
		}
		
	}
	
	/**
	 * When the player clicks the start button this listener will validate the parameters
	 * they have entered. If they are bad, it will display an appropriate error message.
	 * Otherwise it will extract the information from the dropdowns and send that info to
	 * GameFrame, which will create a new game.
	 * @author craigthelinguist
	 */
	private class StartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event){

			// check at least three player dropdowns are not "None"
			// check at least one human is playing
			int playerCount = 0;
			boolean atLeastOneHuman = false;
			for (int i = 0; i < players.length; i++){
				String choice = (String)(players[i].getSelectedItem());
				if (choice.equals("Human")) atLeastOneHuman = true;
				if (!choice.equals("None")){
					playerCount++;
				}
			}
			if (!atLeastOneHuman){
				JOptionPane.showMessageDialog(controller, "Need at least 1 human player!");
				return;
			}
			if (playerCount < 3){
				JOptionPane.showMessageDialog(controller, "Need at least 3 players!");
				return;
			}

			// check no two character choices are the same
			List<String> charChoices = new ArrayList<>();
			for (int i = 0; i < characters.length; i++){
				String player = (String)(players[i].getSelectedItem());
				if (player.equals("None")) continue;
				String choice = (String)(characters[i].getSelectedItem());
				if (charChoices.contains(choice)){
					JOptionPane.showMessageDialog(controller, "More than one player is " + choice + "! Everyone must be a unique character.");
					return;
				}
				charChoices.add(choice);
			}

			try{
				Player[] playerArray = new Player[playerCount];
				int j = 0;
				for (int i = 0; i < characters.length; i++){
					if (players[i].getSelectedItem().equals("None")) continue;
				
					String name = (String) characters[i].getSelectedItem();
				
					switch(name){
					case "Miss Scarlett":
						playerArray[j++] = new Player(new Person("scarlett"));
						break;
					case "Colonel Mustard":
						playerArray[j++] = new Player(new Person("mustard"));
						break;
					case "Mrs. White":
						playerArray[j++] = new Player(new Person("white"));
						break;
					case "Reverend Green":
						playerArray[j++] = new Player(new Person("green"));
						break;
					case "Mrs. Peacock":
						playerArray[j++] = new Player(new Person("peacock"));
						break;
					case "Professor Plum":
						playerArray[j++] = new Player(new Person("plum"));
						break;
					}
				}
				controller.newGame(playerArray);
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(controller, "Error creating game! " + e);
				controller.newGame(null);
			}
			finally{
				NewGameDialog.this.dispose();
			}

		}

	}

	public static void main(String[] args){

		new NewGameDialog(new GameFrame());

	}


}
