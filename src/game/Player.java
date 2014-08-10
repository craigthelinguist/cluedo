package game;

import java.awt.Image;

import cards.Person;

public class Player {

	private final Person avatar;
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
	
	/**
	 * Return the image representing this player on the board.
	 * @return
	 */
	public Image getAvatarImage(){
		return avatar.getTokenImage();
	}
	
	public void setLocation(Tile tile){
		location = tile;
	}

}
