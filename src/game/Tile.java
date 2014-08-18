package game;

import cards.Room;
import cards.Token;

public class Tile {

	/**
	 * Signifies the 'type' of this tile - that is whether players can move onto this tile, or
	 * whether it is a secret passage.
	 * @author craigaaro
	 */
	public enum Terrain{ //changed this to public so i can test in TestBoardParser
		PASSABLE,
		IMPASSABLE,
		SPAWN,
		ROOM;
	}

	// fields
	public final Room.Type room; // room this tile is in
	private Player occupant; // person on this tile
	private Terrain terrain;
	public final int x;
	public final int y;

	public final boolean NORTH;
	public final boolean EAST;
	public final boolean SOUTH;
	public final boolean WEST;

	private final Tile passage;
	
	/**
	 * Create new Tile 
	 * 
	 * @param x : the position on the board
	 * @param y : the position on the board
	 * @param t : type of terrain (enum)
	 * @param neighbours : array indicating whether you can travel north/east/south/west
	 * @param secretPassage : indicating whether this tile is a secret passage
	 * @param rroom : type of room
	 */
	private Tile(int x, int y, Terrain t, boolean[] neighbours, Tile secretPassage, Room.Type rroom){

		this.x = x;
		this.y = y;
		terrain = t;
		NORTH = neighbours[0];
		EAST = neighbours[1];
		SOUTH = neighbours[2];
		WEST = neighbours[3];
		passage = secretPassage;
		room = rroom;
	}


	public static Tile makePassableTile(int x, int y, boolean[] neighbours, Room.Type rroom){
		return new Tile(x,y,Terrain.PASSABLE,neighbours,null,rroom);
	}
	

	
	public static Tile makeSpawnTile(int x, int y, boolean[] neighbours, Room.Type rroom){
		return new Tile(x,y,Terrain.SPAWN,neighbours,null,rroom);
	}
	
	public static Tile makeImpassableTile(int x, int y){
		return new Tile(x,y,Terrain.IMPASSABLE,new boolean[4],null,null);
	}


	public static Tile makeSecretPassageTile(int x, int y, boolean[] neighbours, Tile passage, Room.Type rroom){
		return new Tile(x,y,Terrain.PASSABLE,neighbours,passage,rroom);
	}
	
	public static Tile makeRoomTile(int x, int y, boolean[] neighbours, String roomName, Room.Type rroom){
		return new Tile(x,y,Terrain.ROOM,neighbours,null,rroom);
	}

	public boolean canTravel(Player p){
		return terrain != Terrain.IMPASSABLE && (p == occupant || occupant == null);
	}
	
	/**
	 * Check if tile is a spawn point.
	 * @returnL true if tile is spawn point; false otherwise.
	 */
	public boolean isSpawnPoint(){
		return terrain == Terrain.SPAWN;
	}
	
	/**
	 * Set that someone is now on this tile.
	 * @param t: the thing on this tile.
	 */
	public void setOccupant(Player p){
		occupant = p;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (EAST ? 1231 : 1237);
		result = prime * result + (NORTH ? 1231 : 1237);
		result = prime * result + (SOUTH ? 1231 : 1237);
		result = prime * result + (WEST ? 1231 : 1237);
		result = prime * result
				+ ((occupant == null) ? 0 : occupant.hashCode());
		result = prime * result + ((passage == null) ? 0 : passage.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((terrain == null) ? 0 : terrain.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	public String toString(){
		return "Row: " + y + ", Col: " + x;
	}


	public Terrain getTerrain() {
		return terrain;
	}


	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}


	public String getRoomName() {
		if (room == null) return "Hallway";
		return room.name();
	}
	
	/**
	 * Check if someone is standing on this tile.
	 * @return: true is tile is occupied, false otherwise.
	 */
	public boolean occupied(){
		return occupant != null;
	}

}
