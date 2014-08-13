package game;

import cards.Person;
import cards.Room;
import cards.Weapon;

public class Suggestion {

	public final Room room;
	public final Person person;
	public final Weapon weapon;

	public Suggestion(Room r, Person p, Weapon w){
		room = r;
		person = p;
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
		return room==acc.room && person==acc.person && weapon==acc.weapon;
	}

}
