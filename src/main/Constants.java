package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cards.Card;
import cards.Person;
import cards.Room;
import cards.Weapon;

/**
 * You cannot instantiate this class. It just contains game constants.
 * @author craigthelinguist
 */
public class Constants {

	public static final String ASSETS = "assets" + File.separatorChar;
	public static final int TILES_ACROSS = 26;
	public static final int TILES_DOWN = 27;
	public static final int TILE_WIDTH = 25;

	public static List<Card> generateCards() throws IOException{

		List<Card> cards = new ArrayList<>();

		// generate rooms
		cards.add( new Room("Kitchen") );
		cards.add( new Room("Ballroom") );
		cards.add( new Room("Conservatory") );
		cards.add( new Room("Dining") );
		cards.add( new Room("Lounge") );
		cards.add( new Room("Hall") );
		cards.add( new Room("Library") );
		cards.add( new Room("Study") );
		cards.add( new Room("Billiard") );

		// generate characters
		cards.add( new Person("mustard") );
		cards.add( new Person("scarlett") );
		cards.add( new Person("green") );
		cards.add( new Person("white") );
		cards.add( new Person("plum") );
		cards.add( new Person("peacock") );

		// generate weapons
		cards.add( new Weapon("Candlestick"));
		cards.add( new Weapon("Dagger"));
		cards.add( new Weapon("Pipe"));
		cards.add( new Weapon("Revolver"));
		cards.add( new Weapon("Rope"));
		cards.add( new Weapon("Spanner"));

		return cards;


	}

	private Constants(){}

}
