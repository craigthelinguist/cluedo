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
		SPAWN,
		PASSAGE; // secret passage in corner rooms
	}

	// fields
	private Token token;
	private Room room;
	private Terrain terrain;
	private Token occupant; // person on this tile
	public final int x;
	public final int y;

	public Tile(int x_, int y_){
		x=x_;
		y=y_;
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
	
}
