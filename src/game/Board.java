package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import gui.GameFrame;


public class Board {

	// immutable fields
	private GameFrame frame;
	private Tile[][] tiles;
	private Player[] players;
	
	// mutable fields
	private int currentPlayer; //whose turn is it?
	private int moves; //how far they can move
	private List<Tile> validMoves; //where the player is allowed to move to
	
	
	/**
	 * End the current player's turn. Update the currentPlayer.
	 */
	public void endTurn(){
		currentPlayer = currentPlayer+1 % players.length;
		moves = 0;
	}
	
	/**
	 * Roll dice and update the number of moves the player can make.
	 * @return: a pair of ints in an array, which represent the values rolled
	 * with each dice.
	 */
	public int[] rollDice(){
		int dice1 = (int)(Math.random()*5+1);
		int dice2 = (int)(Math.random()*5+1);
		moves = dice1+dice2;
		return new int[]{ dice1, dice2 };
	}
	
	/**
	 * Compute the list of tiles that the current player is allowed to move to,
	 * given the current number of moves available to them. Does this using a
	 * breadth-first search.
	 * @return: a list of tiles that the currentPlayer can move to.
	 */
	private List<Tile> getValidMoves(){
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
			if (above != null && !validTiles.contains(above) && !queue.contains(above)){
				queue.add(new Node(above,depth+1));
			}
			if (left != null && !validTiles.contains(left) && !queue.contains(left)){
				queue.add(new Node(left,depth+1));
			}
			if (below != null && !validTiles.contains(below) && !queue.contains(below)){
				queue.add(new Node(below,depth+1));
			}
			if (right != null && !validTiles.contains(right) && !queue.contains(right)){
				queue.add(new Node(right,depth+1));
			}
			
		}
		
		return validTiles;
	}
	
}
