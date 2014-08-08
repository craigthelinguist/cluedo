package gui;

import java.awt.BorderLayout;

import game.Board;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	// constants

	
	// model fields
	private Board board;
	
	// view fields
	private MenuBar menu;
	private GamePanel gamePanel;
	private Canvas canvas;
	
	public GameFrame(Board board){
		
		menu = new MenuBar(this);
		gamePanel = new GamePanel(this);
		canvas = new gui.Canvas(this);
		
		this.setLayout(new BorderLayout());
		this.add(menu,BorderLayout.NORTH);
		this.add(canvas,BorderLayout.CENTER);
		this.add(gamePanel,BorderLayout.SOUTH);
		
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args){
		new GameFrame(null);
	}
	
}
