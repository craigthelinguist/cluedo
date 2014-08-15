package game;

import cards.Room;
import cards.Token;

public class Tile {

	/**
	 * Signifies the 'type' of this tile - that is whether players can move onto this tile, or
	 * whether it is a secret passage.
	 * @author craigaaro
	 */
	private enum Terrain{
		PASSABLE,
		IMPASSABLE,
		SPAWN;
	}

	// fields
	private Room.Type room; // room this tile is in
	private Token occupant; // person on this tile
	private Terrain terrain;
	public final int x;
	public final int y;

	public final boolean NORTH;
	public final boolean EAST;
	public final boolean SOUTH;
	public final boolean WEST;

	private final Tile passage;
	
	private Tile(int x, int y, Terrain t, boolean[] neighbours, Tile secretPassage){

		this.x = x;
		this.y = y;
		terrain = t;
		NORTH = neighbours[0];
		EAST = neighbours[1];
		SOUTH = neighbours[2];
		WEST = neighbours[3];
		passage = secretPassage;

	}


	public static Tile makePassableTile(int x, int y, boolean[] neighbours){
		return new Tile(x,y,Terrain.PASSABLE,neighbours,null);
	}
	

	
	public static Tile makeSpawnTile(int x, int y, boolean[] neighbours){
		return new Tile(x,y,Terrain.SPAWN,neighbours,null);
	}
	
	public static Tile makeImpassableTile(int x, int y){
		return new Tile(x,y,Terrain.IMPASSABLE,new boolean[4],null);
	}


	public static Tile makeSecretPassageTile(int x, int y, boolean[] neighbours, Tile passage){
		return new Tile(x,y,Terrain.PASSABLE,neighbours,passage);
	}
	
	/**
	 * Check if tile is passable.
	 * @return: true if the tile is passable; false otherwise.
	 */
	public boolean passable(){
		return terrain != Terrain.IMPASSABLE;
	}

	/**
	 * Check if tile is a spawn point.
	 * @returnL true if tile is spawn point; false otherwise.
	 */
	public boolean isSpawnPoint(){
		return terrain == Terrain.SPAWN;
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
		if (EAST != other.EAST)
			return false;
		if (NORTH != other.NORTH)
			return false;
		if (SOUTH != other.SOUTH)
			return false;
		if (WEST != other.WEST)
			return false;
		if (occupant == null) {
			if (other.occupant != null)
				return false;
		} else if (!occupant.equals(other.occupant))
			return false;
		if (passage == null) {
			if (other.passage != null)
				return false;
		} else if (!passage.equals(other.passage))
			return false;
		if (room != other.room)
			return false;
		if (terrain != other.terrain)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	public String toString(){
		return "Row: " + y + ", Col: " + x;
	}

	public boolean isCanGoNorth() {
		return canGoNorth;
	}

	public boolean isCanGoEast() {
		return canGoEast;
	}

	public boolean isCanGoSouth() {
		return canGoSouth;
	}

	public boolean isCanGoWest() {
		return canGoWest;
	}

}
