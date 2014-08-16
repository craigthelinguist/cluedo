package cards;

import java.awt.Image;

public abstract class Card {

	/**
	 * Returns the image that should be displayed when drawing this card in the gamePanel.
	 * @return: a bufferedImage
	 */
	public abstract Image getCardImage();

	@Override
	public abstract String toString();
	
}
