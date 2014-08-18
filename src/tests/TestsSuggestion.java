package tests;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Test;

import cards.Person;
import cards.Room;
import cards.Weapon;
import game.Board;
import game.Player;
import game.Suggestion;
import game.Tile;
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
	
	/**
	 * Test valid suggestion. Move Player to a room and suggest it.
	 * @throws IOException
	 */
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
			JOptionPane.showMessageDialog(gf, "Error! " + e);
		}
		board.getCurrentPlayer().setLocation(board.tiles[1][4]); //set player's location in kitchen
		gf.makeSuggestion(new Suggestion(new Room("Kitchen"), new Person ("scarlett"), new Weapon("Candlestick"))); //make suggestion while in this room
		if(gamePanel.getRefuting() == false) {
			System.out.println("TestValidSuggestion Passed");
			return;
		}	
		System.out.println("TestValidSuggestion Failed");
	}
	
	/**
	 * Test invalid suggestion. Suggest a room a player is not in.
	 * @throws IOException
	 */
	@Test public void testInvalidSuggestion() throws IOException  {
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
			JOptionPane.showMessageDialog(gf, "Error! " + e);
		}
		gf.makeSuggestion(new Suggestion(new Room("Kitchen"), new Person ("scarlett"), new Weapon("Candlestick"))); //make suggestion while not in this room
		if(gamePanel.getRefuting() == false) {
			System.out.println("TestInvalidSuggestion passed");
			return;
		}
		System.out.println("TestInvalidSuggestion failed");
	}
}
