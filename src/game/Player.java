package game;

import java.awt.Image;
import java.util.LinkedList;

import cards.Card;
import cards.Person;

public class Player {

	private final Person avatar;
	private Tile location;
	private LinkedList<Card> cards;
	private boolean eliminated;
	
	public Player(Person p){
		avatar = p;
		cards = new LinkedList<>();
		eliminated = false;
	}

	public void eliminate(){
		eliminated = true;
	}
	
	public boolean eliminated(){
		return eliminated;
	}
	
	/**
	 * return a list of cards this player is holding.
	 */
	public Image[] getCardImages(){
		Image[] images = new Image[cards.size()];
		for (int i = 0; i < cards.size(); i++){
			images[i] = cards.get(i).getCardImage();
		}
		return images;
	}

	/**
	 * Add a card to this player's hand.
	 * @param c: a card.
	 */
	public void addCard(Card c){
		cards.add(c);
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
	
	public boolean isFemale(){
		return avatar.isFemale();
	}

	public String toString(){
		return avatar.toString();
	}

}
