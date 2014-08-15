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
	private Room room; // room this tile is in
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

	public String toString(){
		return "("+x+","+y+")";
	}

}
