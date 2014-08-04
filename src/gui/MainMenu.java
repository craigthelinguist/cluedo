package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu {

	private final String[] playerOptions = new String[]{ "Human", "Computer", "None" };
	private final String[] characterOptions = new String[]{ "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Professor Plum" };
	private final int NUM_PLAYERS = 6;

	JComboBox[] players = new JComboBox[NUM_PLAYERS];
	JComboBox[] characters = new JComboBox[NUM_PLAYERS];

	private MainMenu(){

		JFrame frame = new JFrame("Cluedo");
		frame.setSize(500,400);

		/* button for starting the game */
		JButton gameStarter = new JButton("Start Game");
		gameStarter.addActionListener(new StartButtonListener());

		/* combo boxes for setting options */
		for (int i = 0; i < NUM_PLAYERS; i++){
			players[i] = new JComboBox<>(playerOptions);
			if (i > 2) players[i].setSelectedIndex(2);
		}
		for (int i = 0; i < NUM_PLAYERS; i++){
			characters[i] = new JComboBox<>(characterOptions);
			characters[i].setSelectedIndex(i);
		}

		/* labels */
		JLabel labelTitle = new JLabel("Welcome! Please set up a new game: ");
		JLabel labelPlayers = new JLabel("Players");
		JLabel labelCharacters = new JLabel("Characters");

		/** Layout **/
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
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
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/**
	 * The player clicks the start button in order to create a new instance of a cluedo
	 * game. This listener captures that behaviour.
	 * @author craigthelinguist
	 */
	private class StartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			startGame();
		}

		private void startGame(){


			// check at least three player dropdowns are not "None"
			// check at least one human is playing
			int count = 0;
			boolean atLeastOneHuman = false;
			for (int i = 0; i < players.length; i++){
				String choice = (String)(players[i].getSelectedItem());
				if (choice.equals("Human")) atLeastOneHuman = true;
				if (!choice.equals("None")){
					count++;
				}
			}
			if (!atLeastOneHuman){
				System.out.println("Must have at least one human player!");
				return;
			}
			if (count < 3){
				System.out.println("Too few players! Must have at least 3.");
				return;
			}

			// check no two character choices are the same
			List<String> charChoices = new ArrayList<>();
			for (int i = 0; i < characters.length; i++){
				String player = (String)(players[i].getSelectedItem());
				if (player.equals("None")) continue;
				String choice = (String)(characters[i].getSelectedItem());
				if (charChoices.contains(choice)){
					System.out.printf("More than one player is %s! Everyone must be a unique character.\n", choice);
					return;
				}
				charChoices.add(choice);
			}

			// TODO: create new board
			System.out.println("Game is setup okay :-)");
			/**
			 * SET UP A NEW BOARD
			 */

		}


	}


	public static void main(String[] args){

		new MainMenu();

	}


}
