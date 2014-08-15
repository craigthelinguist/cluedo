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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Constants;

public class Canvas extends JPanel implements MouseListener{

	// constants
	private final int CANVAS_WD = Constants.TILES_ACROSS * Constants.TILE_WIDTH;
	private final int CANVAS_HT = Constants.TILES_DOWN * Constants.TILE_WIDTH;
	private final int TILE_WIDTH = Constants.TILE_WIDTH;
	private static final String FILEPATH = Constants.ASSETS;

	// fields
	private GameFrame controller;
	private MouseListener listener;
	private Image titleImage;

	public Canvas(GameFrame frame) throws IOException{
		controller = frame;
		this.setPreferredSize(new Dimension(CANVAS_WD,CANVAS_HT));
		titleImage = ImageIO.read(new FileInputStream(FILEPATH+"title.jpg"));
	}

	/**
	 * Activates the mouse listener for this canvas.
	 */
	public void activateListener(){
	//	this.setPreferredSize(new Dimension(CANVAS_WD,CANVAS_HT));
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


			g.drawImage(titleImage,0,0,this.CANVAS_WD,this.CANVAS_HT,null);

		}
		else{

			if (controller == null) throw new NullPointerException("Something fucked up");
				g.drawImage(controller.getBoard().getImage(),0,0,null);
				Player[] players = controller.getPlayers();
				for (int i = 0; i < players.length; i++){

					Image img = players[i].getAvatar();
					Tile tile = players[i].getLocation();
					int x = TILE_WIDTH*tile.x + 4;
					int y = TILE_WIDTH*tile.y + 4;
					g.drawImage(img,x,y,null);


			}
		}
		if(controller.getBoard() != null) {
			if(controller.getBoard().getState() == "MOVING") {
				g.setColor(Color.GREEN);
				for(Tile t : controller.getBoard().getValidMoves()) {
					g.drawRect(t.x*Constants.TILE_WIDTH,t.y*Constants.TILE_WIDTH, 25, 25);
					g.drawRect(t.x*Constants.TILE_WIDTH,t.y*Constants.TILE_WIDTH, 24, 24);
				}
			}
			
			/**
			 * debugging code: draws tiles and their adjacencies
			Board b = controller.getBoard();
			Tile[][] tiles = b.tiles;
			final int TILE_WIDTH = Constants.TILE_WIDTH;
			
			for (int i = 0; i < tiles.length; i++){
				for (int j = 0; j < tiles[i].length; j++){
					Tile t = tiles[i][j];
					

					g.setColor(Color.ORANGE);
					
					if (!t.passable()){
						g.fillRect(j*TILE_WIDTH,i*TILE_WIDTH,TILE_WIDTH,TILE_WIDTH);
					}
					else{
						g.drawRect(j*TILE_WIDTH, i*TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
						int centreX = j*TILE_WIDTH + (TILE_WIDTH/2);
						int centreY = i*TILE_WIDTH + (TILE_WIDTH/2);
						g.setColor(Color.BLUE);						
						if (t.NORTH){
							g.drawLine(centreX, centreY, centreX, centreY-(TILE_WIDTH/2));
						}
						if (t.EAST){
							g.drawLine(centreX,centreY,centreX+(TILE_WIDTH/2),centreY);
						}
						if (t.SOUTH){
							g.drawLine(centreX,centreY,centreX,centreY+(TILE_WIDTH/2));
						}
						if (t.WEST){
							g.drawLine(centreX,centreY,centreX-(TILE_WIDTH/2),centreY);
						}
						
						
					}
					
				}
			}
			**/
			
			
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
