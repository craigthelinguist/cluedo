package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;

import cards.Card;
import cards.Person;
import cards.Room;
import cards.Weapon;
import main.BoardParser;
import main.Constants;
import gui.GameFrame;


public class Board {

	// constants
	private final int TILES_ACROSS = Constants.TILES_ACROSS;
	private final int TILES_DOWN = Constants.TILES_DOWN;
	private final int TILE_WIDTH = Constants.TILE_WIDTH;
	private final String FILEPATH = Constants.ASSETS;

	// immutable fields associated with this instance of a game
	private final Tile[][] tiles;
	private final Player[] players;
	private final Image imageBoard;
	private final Suggestion solution;

	// mutable fields associated with game state
	private int currentPlayer;
	private int moves; //how many squares they can move
	private List<Tile> validMoves; //list of squares the player may move to
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
		validMoves = new ArrayList<>();

		// set the starting position for each player
		Tile[] spawnPoints = findSpawnPoints();
		for (int i = 0; i < players.length; i++){
			players[i].setLocation(spawnPoints[i]);
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
		System.out.println(solution);

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
		validMoves = new ArrayList<>();
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
		if (!validMoves.contains(goal)) return false; // invalid move
		Tile oldPosition = players[currentPlayer].getLocation();
		players[currentPlayer].setLocation(goal);
		int distMoved = Math.abs((oldPosition.x + oldPosition.y) - (goal.x + goal.y));
		moves -= distMoved;
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
		state = State.MOVING;
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
		return tiles[cellX][cellY];
	}

	/**
	 * Compute the list of tiles that the current player is allowed to move to,
	 * given the current number of moves available to them. Does this using a
	 * breadth-first search.
	 * @return: a list of tiles that the currentPlayer can move to.
	 */
	private List<Tile> computeValidMoves(){
		if (moves == 0) return new ArrayList<>();
		List<Tile> validTiles = new ArrayList<>();
		Tile start = players[currentPlayer].getLocation();

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
			if (validTiles.contains(tile)) continue;
			validTiles.add(tile);
			int depth = node.depth;
			if (depth == moves) continue;

			// get tiles adjacent to tile
			Tile above, below, left, right;
			above = below = left = right = null;
			if (tile.y > 0) above = tiles[tile.x][tile.y-1];
			if (tile.x > 0) left = tiles[tile.x-1][tile.y];
			if (tile.y < tiles[0].length-1) below = tiles[tile.x][tile.y+1];
			if (tile.y < tiles.length-1) right = tiles[tile.x][tile.y+1];

			// add adjacent tiles to queue
			if (above != null && !validTiles.contains(above) && !queue.contains(above) && above.passable()){
				queue.add(new Node(above,depth+1));
			}
			if (left != null && !validTiles.contains(left) && !queue.contains(left) && left.passable()){
				queue.add(new Node(left,depth+1));
			}
			if (below != null && !validTiles.contains(below) && !queue.contains(below) && below.passable()){
				queue.add(new Node(below,depth+1));
			}
			if (right != null && !validTiles.contains(right) && !queue.contains(right) && right.passable()){
				queue.add(new Node(right,depth+1));
			}

		}

		return validTiles;
	}

	/**
	 * Return the image representing this board.
	 * @return: a BufferedImage;
	 */
	public Image getImage(){
		return imageBoard;
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

	public List<Tile> getValidMoves() {
		return validMoves;
	}

	public void setValidMoves(List<Tile> validMoves) {
		this.validMoves = validMoves;
	}

}
