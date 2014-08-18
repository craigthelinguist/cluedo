package cards;

import java.awt.Image;

/**
 * This class represents anything in cluedo that can be drawn on the board.
 * @author craigthelinguist
 *
 */
public abstract class Token extends Card{

	/**
	 * Returns the image that should be drawn on the baord to represent this token.
	 * @return: a bufferedImage.
	 */
	public abstract Image getTokenImage();
	
}
