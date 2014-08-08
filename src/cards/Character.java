package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;

public class Character extends Token{

	private enum Type{ Mustard, White, Scarlett, Green, Peacock, Plum }
	private final Type type;
	private final Image imageCard;
	private final Image imageToken;
	private final Image imagePortrait;
	
	public Character(String name) throws IOException{
		type = Type.valueOf(name);
		switch (name){
		case "Mustard":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_mustard.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_mustard.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_mustard.png"));
			break;
		case "White":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_white.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_white.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_white.png"));
			break;
		case "Scarlett":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_scarlett.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_scarlett.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_scarlett.png"));
			break;
		case "Green":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_green.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_green.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_green.png"));
			break;
		case "Peacock":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_peacock.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_peacock.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_peacock.png"));
			break;
		case "Plum":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_plum.png"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"portrait_plum.png"));
			imagePortrait = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_plum.png"));
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public Image getTokenImage() {
		return imageToken;
	}

	@Override
	public Image getCardImage() {
		return imageCard;
	}
	
	public Image getPortraitImage(){
		return imagePortrait;
	}

	
}
