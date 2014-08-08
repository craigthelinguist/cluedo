package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;


public class Weapon extends Token{

	private enum Type{ Candlestick, Dagger, Pipe, Revolver, Rope, Spanner }
	private final Type type;
	private final Image imageCard;
	private final Image imageToken;
	
	public Weapon(String name) throws IOException{
		type = Type.valueOf(name);
		switch (name){
		case "Candlestick":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_candlestick"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_candlestick"));
			break;
		case "Dagger":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_dagger"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_dagger"));
			break;
		case "Pipe":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_pipe"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_pipe"));
			break;
		case "Revolver":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_revolver"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_revolver"));
			break;
		case "Rope":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_rope"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_rope"));
			break;
		case "Spanner":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_spanner"));
			imageToken = ImageIO.read(new FileInputStream(Constants.ASSETS+"token_spanner"));
			break;
		default:
			throw new IOException();
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

}
