package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;

public class Room extends Card{
	
	public enum Type{ Kitchen, Ballroom, Conservatory, Dining, Lounge, Hall, Library, Study, Billiard }
	private final String FILEPATH = Constants.ASSETS;
	private final Type type;
	private final Image imageCard;
	
	public Room(String name) throws IOException{
		type = Type.valueOf(name);
		switch (name){
		case "Kitchen":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_kitchen.png"));
			break;
		case "Ballroom":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_ballroom.png"));
			break;
		case "Conservatory":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_conservatory.png"));
			break;
		case "Dining":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_dining.png"));
			break;
		case "Lounge":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_lounge.png"));
			break;
		case "Hall":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_hall.png"));
			break;
		case "Library":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_library.png"));
			break;
		case "Study":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_study.png"));
			break;
		case "Billiard":
			imageCard = ImageIO.read(new FileInputStream(FILEPATH+"card_billiard.png"));
			break;
		default:
				throw new IOException();
		}
	}
	
	@Override
	public Image getCardImage() {
		return imageCard;
	}

	public String toString(){
		
		switch(type){
		case Kitchen:
			return "kitchen";
		case Ballroom:
			return "ball";
		case Conservatory:
			return "conservatory";
		case Dining:
			return "dining";
		case Lounge:
			return "lounge";
		case Hall:
			return "hall";
		case Library:
			return "library";
		case Study:
			return "study";
		case Billiard:
			return "billiard";
		default:
			throw new IllegalArgumentException();
		}
	}
}
