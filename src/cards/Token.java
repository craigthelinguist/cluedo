package cards;

import java.awt.Image;

public abstract class Token extends Card{

	/**
	 * Returns the image that should be drawn on the baord to represent this token.
	 * @return: a bufferedImage.
	 */
	public abstract Image getTokenImage();
	
}
