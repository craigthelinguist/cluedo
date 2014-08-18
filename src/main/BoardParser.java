package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cards.Room;
import cards.Room.Type;

import game.Tile;

/**
 * This class is a parser that will read a .txt representation of a Board and the corresponding
 * 2d array of tiles.
 * @author craigthelinguist
 */
public class BoardParser {

	private static List<String> roomNames = new ArrayList<>();
	static {
		roomNames.add("kitchen");
		roomNames.add("conservatory");
		roomNames.add("ballroom");
		roomNames.add("billiard");
		roomNames.add("dining");
		roomNames.add("library");
		roomNames.add("lounge");
		roomNames.add("study");
		roomNames.add("hall");
	}

	
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
					Room.Type room = null;
					List<String> rooms = roomNames;

					int pointer = 2;
					if (rooms.contains(array[pointer])){
						room = getRoom(array[pointer]);
						pointer++;
					}
					
					try{
						if (array[pointer].equalsIgnoreCase("impassable")){
							tiles[row][col] = Tile.makeImpassableTile(col,row);
						}
						else if (array[pointer].equalsIgnoreCase("portal")){
							boolean[] neighbours = scanNeighbours(array,3);
							tiles[row][col] = Tile.makeSpawnTile(col,row,neighbours,room);
						}
						else if (array[pointer].equalsIgnoreCase("passage")){
							throw new UnsupportedOperationException("secret passages don't work yet");
						}
						else if (array[pointer].equalsIgnoreCase("passable")){
							boolean[] neighbours = scanNeighbours(array,3);
							tiles[row][col] = Tile.makePassableTile(col, row, neighbours,room);
						}
						else { //this tile belongs to a room
							String roomName = array[pointer]; 
							boolean[] neighbours = scanNeighbours(array,3);
							tiles[row][col] = Tile.makeRoomTile(col,row,neighbours,roomName,room);
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

	/**
	 * Return the room enum from a string.
	 * @param name: name of the room
	 * @return: an enum representing the room.
	 */
	private static Room.Type getRoom(String name) {
		Room.Type room = null;
		switch(name){
		case "kitchen":
			room = Room.Type.Kitchen;
			break;
		case "ballroom":
			room = Room.Type.Ballroom;
			break;
		case "billiard":
			room = Room.Type.Billiard;
			break;
		case "dining":
			room = Room.Type.Dining;
			break;
		case "library":
			room = Room.Type.Library;
			break;
		case "lounge":
			room = Room.Type.Lounge;
			break;
		case "study":
			room = Room.Type.Study;
			break;
		case "hall":
			room = Room.Type.Hall;
			break;
		case "conservatory":
			room = Room.Type.Conservatory;
			break;
		default:
			room = Room.Type.Hallway;
			break;
		}
		return room;
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
