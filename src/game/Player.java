package game;

import cards.Person;

public class Player {

	private Person avatar;
	private Tile location;
	
	public Player(Person p){
		avatar = p;
	}
	
	/**
	 * Return tile this player is standing on.
	 * @return
	 */
	public Tile getLocation(){
		return location;
	}
	
	public void setLocation(Tile tile){
		location = tile;
	}

}
