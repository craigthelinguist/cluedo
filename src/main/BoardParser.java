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
	throws FileNotFoundException, IOException{
		
		Scanner scan = null;
		Tile tiles[][] = null;
		
		try{
			String FILEPATH = Constants.ASSETS;
			int TILES_ACROSS = Constants.TILES_ACROSS;
			int TILES_DOWN = Constants.TILES_DOWN;
			tiles = new Tile[TILES_DOWN][TILES_ACROSS];
			String boardTokens = "X@#.";
		
			scan = new Scanner(new File(FILEPATH + FILENAME));
			String line = null;
		
			while (true){
				line = scan.nextLine().trim();
				if (line.isEmpty()) continue; // empty line
				if (boardTokens.contains(""+line.charAt(0))) break;
			}

			for (int i = 0; i < TILES_DOWN; i++){
		
				char[] array = line.toCharArray();
				if (array.length != TILES_ACROSS) throw new IOException("Bad board dimensions");
				for (int j = 0; j < TILES_ACROSS; j++){
					tiles[i][j] = readTile(array[j],i,j);
				}
				if (i < TILES_DOWN-1) line = scan.nextLine();
			
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
	
	/**
	 * Returns a Tile from the given character.
	 * @param c
	 */
	private static Tile readTile(char c, int x, int y) throws IOException{
		switch(c){
		case 'X':
			return Tile.makeImpassableTile(x, y);
		case '#':
			return Tile.makeImpassableTile(x, y);
		case '@':
			// should return a passage
			return Tile.makePassableTile(x, y);
		case '.':
			return Tile.makePassableTile(x, y);
		default:
			throw new IOException("Bad character");
		}
	}
	
	public static void main(String[] args){
		
		try{
			BoardParser.readBoard("board.txt");
		}
		catch(Exception e){
			System.out.println("Error:" + e);
		}
		
	}
	
}
