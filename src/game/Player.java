package game;

import java.awt.Image;
import java.util.LinkedList;

import cards.Card;
import cards.Person;

public class Player {

	private final Person avatar;
	private Tile location;
	private LinkedList<Card> cards;

	public Player(Person p){
		avatar = p;
		cards = new LinkedList<>();
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
	public Image getAvatar(){
		return avatar.getTokenImage();
	}

	/**
	 * Return the portrait representing this player in the GamePanel.
	 */
	public Image getPortrait(){
		return avatar.getPortraitImage();
	}

	public void setLocation(Tile tile){
		location = tile;
	}

	public String toString(){
		return avatar.toString();
	}

}
