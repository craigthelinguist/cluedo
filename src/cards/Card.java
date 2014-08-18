package cards;

import java.awt.Image;

/**
 * Represents any card in cluedo.
 * @author craigthelinguist
 *
 */
public abstract class Card {

	/**
	 * Returns the image representing this card.
	 * @return: a bufferedImage
	 */
	public abstract Image getCardImage();

	/**
	 * This is here to make sure that subclasses remember to override this. The GUI uses
	 * toString() to display info about cards to the player.
	 */
	@Override
	public abstract String toString();
	
}
