package game;

import cards.Room;
import cards.Weapon;

public class Accusation {

	private Room room;
	private Character character;
	private Weapon weapon;

	/**
	 * An accusation equals another if the cards comprising them are the same.
	 * @return: whether two accusations are the same.
	 */
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof Accusation)) return false;
		Accusation acc = (Accusation)other;
		return room==acc.room && character==acc.character && weapon==acc.weapon;
	}

}
