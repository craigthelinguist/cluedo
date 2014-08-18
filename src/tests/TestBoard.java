package tests;

import game.Board;
import game.Player;
import game.Tile;
import game.Tile.Terrain;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Test;

import cards.Person;
import cards.Room;

public class TestBoard {

	private Board board;
	
	/*Create a new board*/
	@Test public void createNewBoard()   {
		createBoard();
	}
	@Test public void endingTurn() {
		Board b = createBoard();
		Player previousPlayer = b.getCurrentPlayer();
		b.endTurn(); //end turn
		if(!(b.getCurrentPlayer().toString().equalsIgnoreCase(previousPlayer.toString()))) { //check to see if it's a new player's turn
			System.out.println("Successfully changed player turn");
		}
	}
	
	@Test public void movePlayer() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile previousTile = p.getLocation();
		boolean[] neighbours = {true,true,true,true};
		b.movePlayer(Tile.makePassableTile(5, 5, neighbours, null));
		Tile currentTile = p.getLocation();
		if(previousTile != currentTile) {
			System.out.println("Moving Player test passed");
		}
	}
	
	@Test public void rollDice() {
		Board b = createBoard();
		int[] dice = b.rollDice();
		if(dice.length != 0) {
			System.out.println("Rolling Dice passed");
		}
	}
	
	@Test public void eliminatePlayer() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		b.eliminatePlayer();
		if(p.eliminated() == true) {
			System.out.println("Elimiante Player test passed");
		}
	}
	
	/**
	 * Helper method creates new board
	 * @return
	 */
	public Board createBoard() {
			try {
				Player[] playerArray = {new Player(new Person("scarlett")), new Player(new Person("mustard")), new Player(new Person("white"))};
				board = new Board(playerArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return board;
	}
}
