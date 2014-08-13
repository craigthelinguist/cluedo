package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;


public class Weapon extends Token{

	private enum Type{ Candlestick, Dagger, Pipe, Revolver, Rope, Spanner }
	private final String FILEPATH = Constants.ASSETS;
	private final Type type;
	private final Image imageCard;
	private final Image imageToken;

	public Weapon(String name) throws IOException{
		type = Type.valueOf(name);
		switch (name){
		case "Candlestick":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_candlestick"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_candlestick"));
			break;
		case "Dagger":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_dagger"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_dagger"));
			break;
		case "Pipe":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_pipe"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_pipe"));
			break;
		case "Revolver":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_revolver"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_revolver"));
			break;
		case "Rope":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_rope"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_rope"));
			break;
		case "Spanner":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_spanner"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_spanner"));
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
