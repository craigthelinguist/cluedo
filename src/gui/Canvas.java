package gui;

import game.Player;
import game.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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
			BufferedImage image = controller.getBoard().getImage();
			paintValidTiles(image);
			g.drawImage(image,0,0,null);
			Player[] players = controller.getPlayers();
			for (int i = 0; i < players.length; i++){
				Image img = players[i].getAvatar();
				Tile tile = players[i].getLocation();
				int x = TILE_WIDTH*tile.x + 4;
				int y = TILE_WIDTH*tile.y + 4;
				g.drawImage(img,x,y,null);
			}
		}
		
	
		/**
		 * 
			  debugging code: draws tiles and their adjacencies
		if(controller.getBoard() != null) {
	
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
			
			
		}

		
			**/
		
	}
	
	/**
	 * This monster of a method iterates over all tiles in the set of validMoves and paints
	 * their inside, so as to distinguish them from regular squares.
	 * It starts by getting the colour in the middle of each tile, and then doing DFS out
	 * from it, painting every colour that is similar to it.
	 *  @param image: image to which you will panit.
	 */
	private void paintValidTiles(BufferedImage image){
		Set<Tile> tiles = controller.getBoard().getValidMoves();
	
		for (Tile tile : tiles){
			
			// get colour value in middle of tile
			int startX = tile.x*Constants.TILE_WIDTH + Constants.TILE_WIDTH/2;
			int startY = tile.y*Constants.TILE_WIDTH + Constants.TILE_WIDTH/2;
			Point start = new Point(startX,startY);
			int regular = image.getRGB(startX,startY);
			Color regularCol = new Color(regular);
			int white = (255 << 16) | (255 << 8) | 255; // tiles to be painted this colour
			
			// set up data structures for DFS
			Set<Point> visited = new HashSet<>();
			Stack<Point> toVisit = new Stack<>();
			toVisit.push(start);
			
			// this class says two colours are equal with a little bit of
			// fault tolerance. IF the colours are 'close enuogh', then it will consider
			// them equal.
			class FuzzyCompare{
				public boolean equal(Color c1, Color c2){
					int threshold = 10;
					if (Math.abs(c1.getRed() - c2.getRed()) > threshold) return false;
					if (Math.abs(c1.getBlue() - c2.getBlue()) > threshold) return false;
					if (Math.abs(c1.getGreen() - c2.getGreen()) > threshold) return false;
					return true;
				}
			}
			FuzzyCompare fc = new FuzzyCompare();
			
			while (!toVisit.isEmpty()){
				
				Point p = toVisit.pop();
				if (visited.contains(p)) continue;
				else visited.add(p);
				int pixel = image.getRGB(p.x,p.y);
				Color thisColor = new Color(pixel);
				if (!fc.equal(thisColor,regularCol)) continue;
				image.setRGB(p.x,p.y,white);
				
				// add surrounding pixels
				toVisit.add(new Point(p.x,p.y-1));
				toVisit.add(new Point(p.x,p.y+1));
				toVisit.add(new Point(p.x-1,p.y));
				toVisit.add(new Point(p.x+1,p.y));
				
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
