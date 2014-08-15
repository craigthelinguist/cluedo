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
	private Token token;
	private Room room;
	private Terrain terrain;
	private Token occupant; // person on this tile
	public final int x;
	public final int y;
	public final boolean canGoNorth;
	public final  boolean canGoEast;
	public final boolean canGoSouth;
	public final boolean canGoWest;
	

	private Tile(int x, int y, Terrain t, boolean goNorth, boolean goEast, boolean goSouth, boolean goWest){
		this.x = x;
		this.y = y;
		terrain = t;
		this.canGoNorth = goNorth;
		this.canGoEast = goEast;
		this.canGoSouth = goSouth;
		this.canGoWest = goWest;
		
	}

	public static Tile makePassableTile(int x, int y, boolean goNorth, boolean goEast, boolean goSouth, boolean goWest){
		return new Tile(x,y,Terrain.PASSABLE, goNorth, goEast, goSouth, goWest);
	}

	public static Tile makeImpassableTile(int x, int y, boolean goNorth, boolean goEast, boolean goSouth, boolean goWest){
		return new Tile(x,y,Terrain.IMPASSABLE, goNorth, goEast, goSouth, goWest);
	}

	public static Tile makeSpawnPointTile(int x, int y , boolean goNorth, boolean goEast, boolean goSouth, boolean goWest){
		return new Tile(x,y,Terrain.SPAWN,  goNorth, goEast, goSouth, goWest);
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
