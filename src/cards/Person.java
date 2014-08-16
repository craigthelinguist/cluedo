package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.Constants;

public class Person extends Token{

	private enum Type{ mustard, white, scarlett, green, peacock, plum }
	private final String FILEPATH = Constants.ASSETS;
	private final Type type;
	private final Image imageCard;
	private final Image imageToken;
	private final Image imagePortrait;

	public Person(String name) throws IOException{


		type = Type.valueOf(name);
		switch (name){
		case "mustard":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_mustard.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_mustard.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_mustard.png"));
			break;
		case "white":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_white.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_white.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_white.png"));
			break;
		case "scarlett":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_scarlett.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_scarlett.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_scarlett.png"));
			break;
		case "green":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_green.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_green.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_green.png"));
			break;
		case "peacock":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_peacock.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_peacock.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_peacock.png"));
			break;
		case "plum":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_plum.png"));
			imagePortrait = ImageIO.read(new FileInputStream(FILEPATH+"portrait_plum.png"));
			imageToken = ImageIO.read(new FileInputStream(FILEPATH+"token_plum.png"));
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

	public String toString(){

		switch(type){
		case mustard:
			return "Col. Mustard";
		case scarlett:
			return "Miss Scarlett";
		case plum:
			return "Prof. Plum";
		case peacock:
			return "Mrs. Peacock";
		case green:
			return "Rvd. Green";
		case white:
			return "Mrs. White";
		}
		throw new IllegalArgumentException("Getting string of a Person with an invalid type.");

	}


}
