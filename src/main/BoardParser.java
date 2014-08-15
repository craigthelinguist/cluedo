package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import game.Tile;

/**
 * This class is a parser that will read a .txt representation of a Board and the corresponding
 * 2d array of tiles.
 * @author craigthelinguist
 */
public class BoardParser {
	
	/**
	 * RULES
	 * X: a border tile
	 * @: a portal/spawning point
	 * #: an impassable tile
	 * .: a passable tile
	 * slash: start of a comment
	 * each pair of distinct numbers represents a hidden passage connecting one to the other.
	 */
	
	// cannot instantiate BoardParser
	private BoardParser(){}
	
	public static Tile[][] readBoard(final String FILENAME)
	throws FileNotFoundException, IOException, IllegalArgumentException{
		
		Scanner scan = null;
		Tile tiles[][] = null;
		
		try{
			final String FILEPATH = Constants.ASSETS;
			scan = new Scanner(new File(FILEPATH + FILENAME));
			int tilesDown = Constants.TILES_DOWN;
			int tilesAcross = Constants.TILES_ACROSS;
			tiles = new Tile[tilesDown][tilesAcross];
			String line = null;
			
			int numTilesToScan = tilesAcross*tilesDown;
			int tilesScanned = 0;
			while (tilesScanned < numTilesToScan){
				
				line = scan.nextLine().trim();
				if (line.isEmpty()) continue; // empty line
				else if (line.startsWith("//")) continue; //comment
				else{
					
					String[] array = line.split(" ");
					int row = Integer.parseInt(array[0]);
					int col = Integer.parseInt(array[1]);
					
					
					try{
						if (array[2].equalsIgnoreCase("impassable")){
							tiles[row][col] = Tile.makeImpassableTile(col,row);
						}
						else if (array[2].equalsIgnoreCase("portal")){
							boolean[] neighbours = scanNeighbours(array,3);
							tiles[row][col] = Tile.makeSpawnTile(col,row,neighbours);
						}
						else if (array[2].equalsIgnoreCase("passage")){
							throw new UnsupportedOperationException("secret passages don't work yet");
						}
						else{
							boolean[] neighbours = scanNeighbours(array,2);
							tiles[row][col] = Tile.makePassableTile(col, row, neighbours);
						}
					}
					catch(IllegalArgumentException e){
						throw new IllegalArgumentException("Done goofed on row " + row + " and col " + col);
					}
					
					tilesScanned++;
				
					
				}
				
			}
			
		}
		catch(Exception e){
			throw e;
		}
		finally{
			scan.close();
		}

		return tiles;
		
	}

	private static boolean[] scanNeighbours(String[] array, int index) throws IllegalArgumentException{

		for (int i = index; i < index+4; i++){
			if (! (array[i].equals("t") || array[i].equals("f")) ) throw new IllegalArgumentException();
		}
		
		boolean north = array[index].equals("t");
		boolean east = array[index+1].equals("t");
		boolean south = array[index+2].equals("t");
		boolean west = array[index+3].equals("t");
		boolean[] bools = new boolean[]{ north, east, south, west };
		return bools;
		
	}
	
	public static void main(String[] args){
		
		try{
			BoardParser.readBoard("board.txt");
		}
		catch(Exception e){
			System.out.println("Done goofed: " + e);
		}
		
	}
	
}
