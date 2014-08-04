package board;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args){

		JFrame frame = new JFrame("Cluedo");
		frame.setSize(500,400);

		JButton gameStarter = new JButton("Start Game");
		JComboBox<String>[] players = new JComboBox[6];
		JComboBox<String>[] characters = new JComboBox[6];

		String[] playerOptions = new String[]{ "Player", "Computer", "None" };
		String[] characterOptions = new String[]{ "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Professor Plum" };

		for (int i = 0; i < 6; i++){
			players[i] = new JComboBox<>(playerOptions);
			characters[i] = new JComboBox<>(characterOptions);
		}

		JLabel labelTitle = new JLabel("Welcome! Please set up a new game: ");
		JLabel labelPlayers = new JLabel("Players");
		JLabel labelCharacters = new JLabel("Characters");


		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);

		// first two rows
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(labelTitle)
		);
		vertical.addGroup(layout.createParallelGroup()
			.addComponent(labelPlayers)
			.addComponent(labelCharacters)
		);


		// add each row
		for (int i = 0; i < players.length; i++){

			vertical.addGroup(layout.createParallelGroup()
				.addComponent(players[i])
				.addComponent(characters[i])
			);

		}

		// last row is a button for starting the game
		vertical.addComponent(gameStarter);

		ParallelGroup col1 = layout.createParallelGroup();
		col1.addComponent(labelTitle);
		col1.addComponent(labelPlayers);
		horizontal.addGroup(col1);

		ParallelGroup col2 = layout.createParallelGroup();
		col2.addComponent(labelCharacters);
		horizontal.addGroup(col2);


		// add each player combobox to col1
		for (int i = 0; i < players.length; i++){
			col1.addComponent(players[i]);
		}

		// add each character combobox to col2
		for (int i = 0; i < characters.length; i++){
			col2.addComponent(characters[i]);
		}

		col1.addComponent(gameStarter);

		frame.add(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
