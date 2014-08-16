package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import game.Board;
import game.Player;
import game.Suggestion;
import game.Tile;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame {

	// constants


	// model fields
	private Board board;

	// view fields
	private MenuBar menu;
	private GamePanel gamePanel;
	private Canvas canvas;

	public GameFrame() throws IOException{

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
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

		    	int dialogButton = JOptionPane.YES_NO_OPTION;
		    	int dialogResult = JOptionPane.showConfirmDialog((Component) windowEvent.getSource(), "Are you sure you want to exit?", "Warning",dialogButton);
		    	if(dialogResult==0){
		    		System.exit(0);
		    	}

		    }

			});

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);



	}

	public Board getBoard(){
		return board;
	}

	/**
	 * Check if this GameFrame is in a game.
	 * @return: true if there's a game being played, false otherwise.
	 */
	protected boolean playingGame(){
		return board != null;
	}

	/**
	 * Respond to a mouse press.
	 * @param x
	 * @param y
	 */
	protected void mousePressed(int x, int y){
		//System.out.println(board.tileFromCoordinates(x, y));
		Tile tile = board.tileFromCoordinates(x, y);
		System.out.println("Can travel: " + tile.canTravel(getCurrentPlayer()));
		if (board.movePlayer(tile)) updateGUI();
	}

	/**
	 * Respond to a button press.
	 * @param button: name of button that was pressed.
	 */
	protected void buttonPressed(String button){

		if (button.equals("End Turn")){
			board.endTurn();
			gamePanel.updatePortrait(board.getCurrentPlayer());
		}
		else if (button.equals("Roll Dice")){
			board.rollDice();
		}
		else if (button.equals("Suggestion")){
			new SuggestionDialog(this, false);
		}
		else if (button.equals("Accuse")){
			new SuggestionDialog(this, true);
		}
		if (board != null) updateGUI();
	}

	/**
	 * Generate a new game with the given array of players.
	 * @param players: an array of players.
	 */
	protected void newGame(Player[] players){
		if (players == null) return;
		try{
			board = new Board(players);
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
	 * Updates the GUI to display relevant information depending on the given state of play.
	 * @param state
	 */
	private void updateGUI(){

		String state = board.getState();
		boolean eliminated = board.getCurrentPlayer().eliminated();
		
		switch(state){

		case "ROLLING":
			gamePanel.setButtonEnabled("Accuse",false);
			gamePanel.setButtonEnabled("Suggest",false);
			gamePanel.setButtonEnabled("Roll Dice",true);
			break;
		case "MOVING":
			gamePanel.setButtonEnabled("Accuse", eliminated ? false : true);
			gamePanel.setButtonEnabled("Suggest",eliminated ? false : true);
			gamePanel.setButtonEnabled("Roll Dice",false);
			break;
		case "SUGGESTING":
			gamePanel.setButtonEnabled("Accuse", eliminated ? false : true);
			gamePanel.setButtonEnabled("Suggest",eliminated ? false : true);
			gamePanel.setButtonEnabled("Roll Dice",false);
			break;
		case "DONE":
			gamePanel.setButtonEnabled("Accuse", false);
			gamePanel.setButtonEnabled("Suggest",false);
			gamePanel.setButtonEnabled("Roll Dice", false);
			break;
		}

		gamePanel.setMovesRemaining(board.getMovesLeft());
		repaint();
		
	}

	/**
	 * Return the player whose turn it is.
	 * @return: player whose turn it is.
	 */
	protected Player getCurrentPlayer(){
		return board.getCurrentPlayer();
	}

	protected void makeSuggestion(Suggestion suggest) {
		
		
	}

	/**
	 * The current player has accused someone of being the murderer. If they are correct,
	 * they win the game. Otherwise they are eliminated. If only one person remains then
	 * they should be declared the winner.
	 * @param suggestion: the triple of cards you think solves the murder.
	 */
	protected void makeAccusation(Suggestion accusation) {
		new AccusationDialog(this,accusation,board.solution);
	}


	/**
	 * Bring up new game dialog.
	 */
	protected void newGamePrompt(){
		new NewGameDialog(this);
	}

	/**
	 * Current player has won the game. Display this to everyone.
	 */
	protected void playerWon(){
		Player winner = board.getCurrentPlayer();
		StringBuilder sb = new StringBuilder();
		sb.append(winner.toString() + " has won the game!");
		sb.append(" The murderer was " + board.solution.toString());
		new TalkDialog(this,winner,sb.toString(),"");
		board = null;
		gamePanel.setVisible(false);
		canvas.deactivateListener();
		this.pack();
		this.repaint();
	}
	

	/**
	 * Current player has lost the game. Update the board to reflect this and move to
	 * next player's turn.
	 */
	public void playerLost() {
		Player loser = board.getCurrentPlayer();
		String pronoun = loser.isFemale() ? "she" : "he";
		StringBuilder sb = new StringBuilder();
		sb.append(loser.toString() + " has been eliminated!");
		sb.append(" Although " + loser.toString() + " cannot solve the mystery, " + pronoun + " may still move around and refute suggestions.");
		new TalkDialog(this,loser,sb.toString(),"");
		board.eliminatePlayer();
		this.buttonPressed("End Turn");
	}


	public static void main(String[] args){
		try {
			new GameFrame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
