package game;

import cards.Room;
import cards.Weapon;

public class Suggestion {

	public final Room room;
	public final Character character;
	public final Weapon weapon;

	public Suggestion(Room r, Character c, Weapon w){
		room = r;
		character = c;
		weapon = w;
	}
	
	/**
	 * An Suggestion equals another if the cards comprising them are the same.
	 * @return: whether two Suggestions are the same.
	 */
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof Suggestion)) return false;
		Suggestion acc = (Suggestion)other;
		return room==acc.room && character==acc.character && weapon==acc.weapon;
	}

}
