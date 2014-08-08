package cards;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constants;

public class Room extends Card{
	
	private enum Type{ Kitchen, Ballroom, Conservatory, Dining, Lounge, Hall, Library, Study, Billiard }
	private final Type type;
	private final Image imageCard;
	
	public Room(String name) throws IOException{
		type = Type.valueOf(name);
		switch (name){
		case "Kitchen":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_kitchen.png"));
			break;
		case "Ballroom":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_ballroom.png"));
			break;
		case "Conservatory":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_conservatory.png"));
			break;
		case "Dining":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_dining.png"));
			break;
		case "Lounge":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_lounge.png"));
			break;
		case "Hall":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_hall.png"));
			break;
		case "Library":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_library.png"));
			break;
		case "Study":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_study.png"));
			break;
		case "Billiard":
			imageCard = ImageIO.read(new FileInputStream(Constants.ASSETS+"card_billiard.png"));
			break;
		default:
				throw new IOException();
		}
	}
	
	@Override
	public Image getCardImage() {
		return imageCard;
	}

}
