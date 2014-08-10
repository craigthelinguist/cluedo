package gui;

import game.Board;
import game.Player;
import game.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import main.Constants;

public class Canvas extends JPanel implements MouseListener{

	// constants
	private final int CANVAS_WD = Constants.TILES_ACROSS * Constants.TILE_WIDTH; 
	private final int CANVAS_HT = Constants.TILES_DOWN * Constants.TILE_WIDTH;
	private final int TILE_WIDTH = Constants.TILE_WIDTH;
	
	// fields
	private GameFrame controller;
	private MouseListener listener;
	
	public Canvas(GameFrame frame){
		controller = frame;
		this.setPreferredSize(new Dimension(CANVAS_WD,CANVAS_HT));
	}
		
	/**
	 * Activates the mouse listener for this canvas.
	 */
	public void activateListener(){
		this.addMouseListener(this);
	}
	
	/**
	 * Deactivates the mouse listener for this canvas.
	 */
	public void deactivateListener(){
		this.removeMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		
		if (!controller.playingGame()){
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		else{
		
			if (controller.getBoard() != null){
				g.drawImage(controller.getBoard().getImage(),0,0,null);
			}
			if (controller.getPlayers() != null){

				g.drawImage(controller.getBoard().getImage(),0,0,null);
				Player[] players = controller.getPlayers();
				for (int i = 0; i < players.length; i++){
					
					Image img = players[i].getAvatarImage();
					Tile tile = players[i].getLocation();
					int x = TILE_WIDTH*tile.x + 4;
					int y = TILE_WIDTH*tile.y + 4;
					g.drawImage(img,x,y,null);
					
				}
			}
			
			
			/*
			 code for debugging: draws a grid of tiles over the board
			*/
			int numTilesAcross = Constants.TILES_ACROSS;
			int numTilesDown = Constants.TILES_DOWN;
			int tileWd = Constants.TILE_WIDTH;
			g.setColor(Color.RED);
			for (int i = 0; i < numTilesAcross; i++){
				for (int j = 0; j < numTilesDown+20; j++){

					int x1 = i*tileWd;
					int y1 = j*tileWd;
					if (i == 0 || i == numTilesAcross-1 || j == 0 || j == numTilesDown-1){
						g.fillRect(x1, y1, tileWd, tileWd);
					}
					else{
						g.drawRect(x1, y1, tileWd, tileWd);
					}

				}
			}
			
			
			
		}
		
		

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		controller.mousePressed(event.getX(),event.getY());
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
