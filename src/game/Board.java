package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.imageio.ImageIO;

import cards.Card;
import cards.Person;
import cards.Room;
import cards.Weapon;
import main.BoardParser;
import main.Constants;
import gui.GameFrame;
import gui.TalkDialog;


public class Board {

	// constants
	private final int TILES_ACROSS = Constants.TILES_ACROSS;
	private final int TILES_DOWN = Constants.TILES_DOWN;
	private final int TILE_WIDTH = Constants.TILE_WIDTH;
	private final String FILEPATH = Constants.ASSETS;

	// immutable fields associated with this instance of a game
	public final Tile[][] tiles;
	private final Player[] players;
	private final BufferedImage imageBoard;
	public final Suggestion solution;

	// mutable fields associated with game state
	private int currentPlayer;
	private int moves; //how many squares they can move
	private Map<Tile,Integer> validMoves; // Mapping of Tile Player Can Move To -> Distance from player position
	private State state;

	/**
	 * State keeps track of which part of the player's turn is happening.
	 *  - ROLLING: the player may roll dice or end turn.
	 *  - MOVING: the player may move, suggest, accuse, or end turn.
	 *  - SUGGESTING: the player may suggest, accuse, or end turn.
	 *  - DONE: the player can only end turn.
	 * @author craigthelinguist
	 */
	private enum State{
		ROLLING, MOVING, SUGGESTING, DONE
	};

	public Board(Player[] players) throws IOException{
		this.players = players;
		tiles = BoardParser.readBoard("board.txt");
		imageBoard = ImageIO.read(new FileInputStream(FILEPATH + "board.png"));
		state = State.ROLLING;
		currentPlayer = 0;
		validMoves = new HashMap<>();

		// set the starting position for each player
		Tile[] spawnPoints = findSpawnPoints();
		for (int i = 0; i < players.length; i++){
			players[i].setLocation(spawnPoints[i]);
			spawnPoints[i].setOccupant(players[i]);
		}

		// make cards and shuffle them
		List<Card> cards = Constants.generateCards();
		Collections.shuffle(cards);

		// generate a winning combination (person who committed murder)
		Room r = null; Weapon w = null; Person p = null;
		for (int i = 0; i < cards.size(); i++){
			Card card = cards.get(i);
			if (card instanceof Room && r == null){
				r = (Room)card;
				cards.remove(i);
				i--;
			}
			else if (card instanceof Weapon && w == null){
				w = (Weapon)card;
				cards.remove(i);
				i--;
			}
			else if (card instanceof Person && p == null){
				p = (Person)card;
				cards.remove(i);
				i--;
			}
			if (p != null && w != null && r != null) break; //finished
		}
		if (r == null || w == null || p == null) throw new IOException("Board failed to load the cards.");
		solution = new Suggestion(r,p,w);

		// deal cards among remaining players
		int j = 0;
		for (int i = 0; i < cards.size(); i++){
			players[j].addCard(cards.get(i));
			j = (j+1) % players.length;
		}

		// set starting conditions
		currentPlayer = 0;
	}

	/**
	 * Find and return an array of tiles that are starting points. Only returns as
	 * many tiles as there are players in the game.
	 * @return
	 */
	private Tile[] findSpawnPoints(){
		Tile[] spawnPoints = new Tile[players.length];
		int k = 0;
		outer: for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[0].length; j++){
				if (tiles[i][j].isSpawnPoint()){
					spawnPoints[k++] = tiles[i][j];
					if (k == players.length) break outer;
				}
			}
		}
		return spawnPoints;
	}

	/**
	 * End the current player's turn. update the currentPlayer and the game state.
	 */
	public void endTurn(){
		currentPlayer = (currentPlayer+1)%players.length;
		moves = 0;
		validMoves = new HashMap<>();
		state = State.ROLLING;
	}

	/**
	 * Attempt to move the current player to the given Tile. If this move is
	 * invalid, nothing will happen. Otherwise the player will be moved to
	 * that tile and the game state will be updated.
	 * @param goal: the tile the player is trying to move to.
	 */
	public boolean movePlayer(Tile goal){
		if (state == State.ROLLING || moves == 0) return false;
		if (!validMoves.containsKey(goal)) return false; // invalid move
		Integer distMoved = validMoves.get(goal);
		moves -= distMoved;
		Player player = players[currentPlayer];
		Tile oldPosition = player.getLocation();
		player.setLocation(goal);
		oldPosition.setOccupant(null);
		goal.setOccupant(player);
		validMoves = computeValidMoves();
		if (moves == 0) state = State.SUGGESTING;
		return true;
	}
	
	/**
	 * Roll dice and update the number of moves the player can make, and update the
	 * game state.
	 * @return: a pair of ints in an array, which represent the values rolled
	 * with each dice. null if you are not able to roll.
	 */
	public int[] rollDice(){
		if (state != State.ROLLING) return null;
		int dice1 = (int)(Math.random()*5+1);
		int dice2 = (int)(Math.random()*5+1);
		moves = dice1+dice2;
		System.out.println(moves); //testing output of dice
		state = State.MOVING;
		validMoves = computeValidMoves(); //testing
		return new int[]{ dice1, dice2 };
	}

	/**
	 * From the given point (x,y) returns the appropriate Tile on this board.
	 * @param x: x part of a point on the board.
	 * @param y: y part of a point on the board.
	 * @return: the Tile containing point (x,y)
	 */
	public Tile tileFromCoordinates(int x, int y){
		int boardWidth = TILES_ACROSS * TILE_WIDTH;
		int boardHeight = TILES_DOWN * TILE_WIDTH;
		if (x < 0 || y < 0 || x > boardWidth || x > boardHeight)
			throw new IllegalArgumentException("Point is not on the board");
		int cellX = x / TILE_WIDTH;
		int cellY = y / TILE_WIDTH;
		return tiles[cellY][cellX];
	}

	/**
	 * Compute the set of tiles that the current player is allowed to move to,
	 * given the current number of moves available to them. Does this using a
	 * breadth-first search.
	 * @return: a list of tiles that the currentPlayer can move to.
	 */
	private Map<Tile,Integer> computeValidMoves(){
		if (moves == 0) return new HashMap<>();
		Map<Tile,Integer> validTiles = new HashMap<>();
		Player player = players[currentPlayer];
		Tile start = player.getLocation();

		// node that remembers each tile and its depth
		class Node{
			int depth;
			Tile tile;
			public Node(Tile t, int d){
				depth = d;
				tile = t;
			}
		};

		// use queue to keep track of nodes for BFS
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(new Node(start,0));

		while (!queue.isEmpty()){
			Node node = queue.poll();
			Tile tile = node.tile;
			if (validTiles.containsKey(tile)) continue;
			if (tile.canTravel(player)) validTiles.put(tile,node.depth);
			int depth = node.depth;
			if (depth == moves) continue;

			// get tiles adjacent to tile
			if (tile.NORTH){
				Tile above = tiles[tile.y-1][tile.x];
				if (queue.contains(above)) break;
				else queue.offer(new Node(above,depth+1));
			}
			if (tile.EAST){
				Tile right = tiles[tile.y][tile.x+1];
				if (queue.contains(right)) break;
				else queue.offer(new Node(right,depth+1));
			}
			if (tile.SOUTH){
				Tile below = tiles[tile.y+1][tile.x];
				if (queue.contains(below)) break;
				else queue.offer(new Node(below,depth+1));
			}
			if (tile.WEST){
				Tile left = tiles[tile.y][tile.x-1];
				if (queue.contains(left)) break;
				else queue.offer(new Node(left,depth+1));
			}
			
		}
		
		validTiles.remove(start); // tile you're standing on
		return validTiles;
	}

	public Tile tileFromPosition(int x, int y){
		return tiles[y][x];
	}

	/**
	 * Return a clone of the image representing this board.
	 * @return: a BufferedImage;
	 */
	public BufferedImage getImage(){
		ColorModel model = imageBoard.getColorModel();
		boolean alpha = model.isAlphaPremultiplied();
		WritableRaster raster = imageBoard.copyData(null);
		return new BufferedImage(model, raster, alpha, null);
	}

	/**
	 * Return the array of players in this game.
	 * @return: an array.
	 */
	public Player[] getPlayers(){
		return players;
	}

	/**
	 * Return the player whose turn it currently is.
	 * @return: a Player.
	 */
	public Player getCurrentPlayer(){
		return players[currentPlayer];
	}

	/**
	 * Return a representation of hte board state as a string.
	 * @return: a string
	 */
	public String getState(){
		return state.toString();
	}
	
	public int getMovesLeft(){
		return moves;
	}

	public Set<Tile> getValidMoves() {
		return validMoves.keySet();
	}

	/**
	 * Mark the current player as being eliminated.
	 */
	public void eliminatePlayer() {
		players[currentPlayer].eliminate();
	}

	/**
	 * Return true if every player has been eliminated.
	 * @return: true if all have been elmiinated, false otherwise.
	 */
	public boolean everyoneLost() {
		for (int i = 0; i < players.length; i++){
			if (!players[i].eliminated()) return false;
		}
		return true;
	}

	/**
	 * A round of suggesting is over. The turn now goes to the specified player.
	 * The person who was accused should be moved to the same room.
	 * @param suggestor: person who made suggestion.
	 */
	public void endSuggestion(Suggestion suggestion, Player suggestor) {
		int accused = -1;
		Person avatar = suggestion.getPerson();
		for (int i = 0; i < players.length; i++){
			if (players[i] == suggestor) currentPlayer = i;
			else if (players[i].avatar.equals(avatar)) accused = i;
		}
		
		// if the accused is playing, move them next to the suggestor
		// do BFS around the suggestor until you find an empty tile
		if (accused != -1){
			Tile start = players[currentPlayer].getLocation();
			Queue<Tile> queue = new ArrayDeque<>();
			Set<Tile> visited = new HashSet<>();
			queue.offer(start);
			Room.Type room = start.room;
			while (!queue.isEmpty()){
				Tile t = queue.poll();
				visited.add(t);
				if (!t.occupied()){
					players[accused].getLocation().setOccupant(null);
					players[accused].setLocation(t);
					t.setOccupant(players[accused]);
					break;
				}
				
				// recurse.
				Tile north = tiles[t.y-1][t.x];
				if (north.room == room) queue.offer(north);
				
				Tile south = tiles[t.y+1][t.x];
				if (south.room == room) queue.offer(south);
				
				Tile east = tiles[t.y][t.x+1];
				if (east.room == room) queue.offer(east);
				
				Tile west = tiles[t.y][t.x-1];
				if (west.room == room) queue.offer(west);
				
				
			}		
		}
		
		this.state = State.DONE;
	}

}
