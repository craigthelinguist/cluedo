package tests;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Test;

import cards.Person;
import game.Board;
import game.Player;
import gui.GameFrame;
import gui.GamePanel;
import gui.MenuBar;
import gui.NewGameDialog;

public class TestsSuggestion {
	
	private GameFrame gf;
	private MenuBar menu;
	private GamePanel gamePanel;
	private Board board;

	private String terrain;
	
	@Test public void testValidSuggestion() throws IOException  {
		/*set up the gui*/
		gf = new GameFrame();
		menu = new MenuBar(gf);
		gamePanel = new GamePanel(gf);
		gui.Canvas canvas = new gui.Canvas(gf);
		
		/*create new game*/
		new NewGameDialog(gf);
		try{
			Player[] playerArray = {new Player(new Person("scarlett")), new Player(new Person("mustard")), new Player(new Person("white"))};
			//gf.newGame(playerArray);
			board = new Board(playerArray);
			//gamePanel.activate();
			//gamePanel.updateGamePanel();
			gf.setVisible(true);
			canvas.activateListener();
			gf.pack();
			canvas.repaint();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(gf, "Error creating game! " + e);
		}
	}
}
