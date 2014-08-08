package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Constants;

public class Canvas extends JPanel {

	// constants
	private final int CANVAS_WD = Constants.TILES_ACROSS * Constants.TILE_WIDTH;
	private final int CANVAS_HT = Constants.TILES_DOWN * Constants.TILE_WIDTH;
	
	// fields
	private GameFrame controller;
	
	public Canvas(GameFrame frame){
		controller = frame;
		this.setPreferredSize(new Dimension(CANVAS_WD,CANVAS_HT));
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);

		int numTilesAcross = Constants.TILES_ACROSS;
		int numTilesDown = Constants.TILES_DOWN;
		int tileWd = Constants.TILE_WIDTH;
		
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < numTilesAcross; i++){
			for (int j = 0; j < numTilesDown; j++){

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
