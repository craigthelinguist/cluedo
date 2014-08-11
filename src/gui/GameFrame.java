package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import game.Board;
import game.Player;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {

	// constants


	// model fields
	private Board board;

	// view fields
	private MenuBar menu;
	private GamePanel gamePanel;
	private Canvas canvas;

	public GameFrame(){

		menu = new MenuBar(this);
		gamePanel = new GamePanel(this);
		canvas = new gui.Canvas(this);

		this.setLayout(new BorderLayout());
		this.add(menu,BorderLayout.NORTH);
		this.add(canvas,BorderLayout.CENTER);
		this.add(gamePanel,BorderLayout.SOUTH);
		gamePanel.setVisible(false);

		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public Board getBoard(){
		return board;
	}

	/**
	 * Check if this GameFrame is in a game.
	 * @return: true if there's a game being played, false otherwise.
	 */
	public boolean playingGame(){
		return board != null;
	}

	/**
	 * Respond to a mouse press.
	 * @param x
	 * @param y
	 */
	public void mousePressed(int x, int y){
		System.out.println(board.tileFromCoordinates(x, y));
	}

	/**
	 * Respond to a button press.
	 * @param button: name of button that was pressed.
	 */
	public void buttonPressed(String button){

		if (button.equals("End Turn")){
			board.endTurn();
			gamePanel.updatePortrait(board.getCurrentPlayer());
		}


		this.repaint();

	}

	/**
	 * Bring up new game dialog.
	 */
	public void newGamePrompt(){
		new NewGameDialog(this);
	}

	/**
	 * Generate a new game with the given array of players.
	 * @param players: an array of players.
	 */
	public void newGame(Player[] players){
		if (players == null) return;
		try{
			board = new Board(players,this);
			gamePanel.setVisible(true);
			gamePanel.updatePortrait(board.getCurrentPlayer());
			this.pack();
			canvas.activateListener();
			canvas.repaint();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(this, "Error loading board! " + e);
		}
	}

	/**
	 * Return the array of players in the game.
	 * @return:
	 */
	protected Player[] getPlayers(){
		if (board == null) throw new NullPointerException("Trying to get player array from a board that doesn't exist!");
		return board.getPlayers();
	}

	/**
	 * Enables and disables buttons depending on the name of the state the player is in.
	 * @param state
	 */
	public void enableButtonsForState(String state){

		switch(state){

		case "ROLLING":
			gamePanel.setButtonEnabled("Accuse",false);
			gamePanel.setButtonEnabled("Suggest",false);
			gamePanel.setButtonEnabled("Roll Dice",true);
			return;
		}

	}

	/**
	 * Return the player whose turn it is.
	 * @return: player whose turn it is.
	 */
	protected Player getCurrentPlayer(){
		return board.getCurrentPlayer();
	}

	public static void main(String[] args){
		new GameFrame();
	}

}
