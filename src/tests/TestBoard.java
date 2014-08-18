package tests;

import static org.junit.Assert.*;
import game.Board;
import game.Player;
import game.Suggestion;
import game.Tile;
import game.Tile.Terrain;
import gui.GameFrame;
import gui.GamePanel;
import gui.MenuBar;
import gui.NewGameDialog;

import java.io.IOException;

import javax.swing.JOptionPane;

import main.Constants;

import org.junit.Test;

import cards.Person;
import cards.Room;
import cards.Weapon;

public class TestBoard {

	private Board board;
	
	/**
	 * test board creates without throwing error
	 */
	@Test public void createNewBoard()   {
		createBoard();
	}
	
	/**
	 * test that you can end turn and it will be another player's turn
	 */
	@Test public void endingTurn() {
		Board b = createBoard();
		Player previousPlayer = b.getCurrentPlayer();
		b.endTurn(); //end turn
		if (b.getCurrentPlayer() == previousPlayer) fail("next players turn");
	}
	
	/**
	 * test that you can't move if you haven't rolled die
	 */
	@Test public void movingOutOfSequence() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile previousTile = p.getLocation();
		Tile newTile = b.tiles[previousTile.y][previousTile.x-1];
		b.movePlayer(newTile);
		if (p.getLocation() == newTile) fail("player shouldn't be able to move yet");
	}
	
	/**
	 * test that player can roll dice, move, and have their position updated
	 */
	@Test public void validMove() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile previousTile = p.getLocation();
		Tile newTile = b.tiles[previousTile.y][previousTile.x-1];
		b.rollDice();
		b.movePlayer(newTile);
		if (p.getLocation() != newTile) fail("player didn't move");
	}
	
	/**
	 * test player can't move into impasasble tile
	 */
	@Test public void invalidMove() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile previousTile = p.getLocation();
		Tile newTile = b.tiles[previousTile.y][previousTile.x+1];
		b.rollDice();
		b.movePlayer(newTile);
		if (p.getLocation() != previousTile) fail("player shouldn't have moved");
	}
	
	/**
	 * test board throws error when you get tile off the board dimensions
	 */
	@Test public void gettingInvalidTile() {
		Board b = createBoard();
		try{
			Tile t = b.tileFromCoordinates(-5,0);
			fail("board should have recognised something bad happened");
		}
		catch(IllegalArgumentException e){
			
		}
		catch(ArrayIndexOutOfBoundsException e){
			fail("board should have thrown illegal argument!");
		}
	}
	
	/**
	 * check board gives you the right tiles
	 */
	@Test public void gettingValidTiles() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile t1 = p.getLocation();
		int TILE_WD = Constants.TILE_WIDTH;
		int col = t1.x*TILE_WD + 1; // +1 so it's inside the tile and not on the boundary
		int row = t1.y*TILE_WD + 1;
		Tile t2 = b.tileFromCoordinates(col,row);
		if (t1 != t2) fail("should have been the same tile");
		if (b.tileFromPosition(t1.x,t1.y) != t1) fail("should have been the same tile");
	}
	
	/**
	 * test player can't move into a passable tile across the map
	 */
	@Test public void movingTooFar() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		Tile previousTile = p.getLocation();
		Tile newTile = b.tiles[1][10]; // a tile on other side of board
		b.rollDice();
		b.movePlayer(newTile);
		if (p.getLocation() != previousTile) fail("player shouldn't have moved");		
	}
	
	@Test public void rollDice() {
		Board b = createBoard();		
		for (int i = 0; i < 1000; i++){
			int[] die = b.rollDice();
			if (die.length != 2) fail("should only roll 2 die");
			if (b.getMovesLeft() < 2) fail("minimum roll of 2");
			if (b.getMovesLeft() > 12) fail("max roll of 12");
			b.endTurn();
		}
		
	}
	
	/**
	 * can't roll twice
	 */
	@Test public void invalidRoll(){
		Board b = createBoard();
		b.rollDice();
		int b4 = b.getMovesLeft();
		if (b.rollDice() != null) fail("shouldn't have rolled any die");
		if (b.getMovesLeft() != b4)  fail("num moves should still be the same");
	}
	
	/**
	 * test that a player correctly eliminates
	 */
	@Test public void eliminatePlayer() {
		Board b = createBoard();
		Player p = b.getCurrentPlayer();
		if (p.eliminated()) fail("player shouldn't be eliminated");
		b.eliminatePlayer();
		if (!p.eliminated()) fail("player should be eliminated");
	}
	
	/**
	 * check game is over when everyone is eliminated
	 */
	@Test public void allPlayerEliminated() {
		Board b = createBoard();
		Player[] players = b.getPlayers();
		for(int i = 0; i < players.length; i ++) {
			assertFalse(b.everyoneLost());
			b.eliminatePlayer();
			b.endTurn();
		}
		assertTrue (b.everyoneLost());
		if (!b.everyoneLost()) System.out.println("everyone should have been eliminated");
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
