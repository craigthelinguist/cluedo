package tests;

import static org.junit.Assert.*;
import game.Board;
import game.Player;
import game.Suggestion;
import game.Tile;

import java.io.IOException;

import org.junit.Test;

import cards.Person;
import cards.Room;
import cards.Room.Type;
import cards.Weapon;

public class TestSuggestions {

	@Test
	public void suggestionEquality(){
		
		Suggestion s1 = makeSuggestion1();
		Suggestion s2 = makeSuggestion1();
		if (!s1.equals(s2)) fail("suggestions should be equal");
		
	}

	@Test
	public void suggestionInequality(){
		Suggestion s1 = makeSuggestion1();
		Suggestion s2 = makeSuggestion2();
		if (s1.equals(s2)) fail ("suggestions aren't different");
	}
	
	/**
	 * suggestion about someone not in the game.
	 * suggestion should successfully finish.
	 * currentplayer afterwards should be the person who suggested
	 */
	@Test
	public void endingSuggestion(){
		Suggestion s = makeSuggestion2();
		Board b = createBoard();
		Player suggestor = b.getCurrentPlayer();
		b.endTurn();
		b.endSuggestion(s,suggestor);
	}
	
	/**
	 * suggestion about someone in the game.
	 * suggestion should successfully finish.
	 * the accused should now be in the room in the suggestion.
	 * currentplayer afterwards should be the person who suggested
	 */
	@Test
	public void endingSuggestion2(){
		Suggestion s = makeSuggestion1();
		Board b = createBoard();
		Player suggestor = b.getCurrentPlayer();
		b.endTurn();
		
		// person being accused is mustard in the kitchen
		Player accused = b.getCurrentPlayer();
		Tile locationb4 = accused.getLocation(); //where mustard is b4 the accusation
		suggestor.setLocation(b.tiles[3][3]); // a tile in the kitchen, suggestor has to be in same room
		Room.Type kitchen = b.tiles[3][3].room;
		if (kitchen != Room.Type.Kitchen) fail("suggestor should be in kitchen");
		assertTrue(accused.getLocation().room != s.room.type); // mustard isn't in kitchen at first
		b.endSuggestion(s,suggestor);
		if (accused.getLocation() == locationb4) fail("should have moved"); // should have moved
		
		// the accuser is actually not in the kitchen (like they should be). However,
		// board should put mustard in the room, so if mustard isn't in the kitchen
		// then something has gone wrong.
		if (accused.getLocation().room != kitchen) fail ("should be in kitchen now");
	}
	
	public Suggestion makeSuggestion2(){
		Suggestion s = null;
		try{
			Weapon w = new Weapon("Candlestick");
			Person p = new Person("plum");
			Room r = new Room("Study");
			return new Suggestion(r,p,w);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return s;
	}
	
	public Suggestion makeSuggestion1(){
		Suggestion s = null;
		try{
			Weapon w = new Weapon("Dagger");
			Person p = new Person("mustard");
			Room r = new Room("Kitchen");
			return new Suggestion(r,p,w);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * Helper method creates new board
	 * @return
	 */
	public Board createBoard() {
		Board board = null;
			try {
				Player[] playerArray = {new Player(new Person("scarlett")), new Player(new Person("mustard")), new Player(new Person("white"))};
				board = new Board(playerArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return board;
	}
	
}
