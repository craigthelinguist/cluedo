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
		if(controller.getBoard() != null) {
			if(controller.getBoard().getState() == "MOVING") {
				for(Tile t : controller.getBoard().getValidMoves()) {
					g.drawRect(t.x,t.y, 25, 25);
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
