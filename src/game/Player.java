package game;

public class Player {

	private Character avatar;
	private Tile location;
	
	public Player(Character character, Tile startPosition){
		avatar = character;
		location = startPosition;
	}
	
	/**
	 * Return tile this player is standing on.
	 * @return
	 */
	public Tile getLocation(){
		return location;
	}
	
	public void setLocation(Tile tile){
		location = tile;
	}

}
