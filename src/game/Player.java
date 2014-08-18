package game;

import java.awt.Image;
import java.util.LinkedList;

import cards.Card;
import cards.Person;

/**
 * A player in the game of cluedo. They have a list of cards, an avatar, and a current location.
 * They also know if they've been eliminated. No two players should have the same avatar.
 * @author craigthelinguist
 *
 */
public class Player {

	public final Person avatar;
	private Tile location;
	private LinkedList<Card> cards;
	private boolean eliminated;
	
	public Player(Person p){
		avatar = p;
		cards = new LinkedList<>();
		eliminated = false;
	}

	/**
	 * Set this player to be eliminated from the game.
	 */
	public void eliminate(){
		eliminated = true;
	}
	
	/**
	 * Check if this player has been eliminated from the game.
	 * @return: true if the player has been eliminated; false otherwise.
	 */
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
	 * Return true if the player has the specified card in their hand.
	 * @param c: card to check.
	 * @return: true if player is holding the card; false otherwise.
	 */
	public boolean hasCard(Card c){
		return cards.contains(c);
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
