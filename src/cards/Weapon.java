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
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_candlestick.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_candlestick.png"));
			break;
		case "Dagger":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_dagger.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_dagger.png"));
			break;
		case "Pipe":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_pipe.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_pipe.png"));
			break;
		case "Revolver":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_revolver.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_revolver.png"));
			break;
		case "Rope":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_rope.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_rope.png"));
			break;
		case "Spanner":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_spanner.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_spanner.png"));
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

	public String toString(){
		
		switch (type){
		case Candlestick:
			return "candlestick";
		case Dagger:
			return "dagger";
		case Pipe:
			return "pipe";
		case Revolver:
			return "revolver";
		case Rope:
			return "rope";
		case Spanner:
			return "spanner";
		default:
			throw new IllegalArgumentException();
		}
		
	}

	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof Weapon)) return false;
		Weapon w = (Weapon)other;
		return w.type == type;
	}
	
}
