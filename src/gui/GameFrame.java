package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

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
	
	public GameFrame(){
		
		try{
			this.board = new Board(null);
		}
		catch(IOException e){
			
		}
		
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
	
	public Board getBoard(){
		return board;
	}
	
	public static void main(String[] args){
		new GameFrame();
	}
	
}
