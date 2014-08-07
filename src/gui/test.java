package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class test extends JFrame{

	private JComponent canvas;

	public test(){


		canvas = new JComponent(){

			@Override
			protected void paintComponent(Graphics g){

				final int numTilesAcross = 26;
				final int numTilesDown = 27;

				final int tileWd = 30;


				g.setColor(Color.RED);
				g.fillRect(0,0,1000,1000);


				g.setColor(Color.BLACK);


				int boardWidth = tileWd * numTilesAcross;
				g.drawRect(0, 0, boardWidth, 20);
				int yOffset = 20;

				for (int i = 0; i < numTilesAcross; i++){
					for (int j = 0; j < numTilesDown; j++){

						int x1 = i*tileWd;
						int y1 = yOffset + j*tileWd;
						if (i == 0 || i == numTilesAcross-1 || j == 0 || j == numTilesDown-1){
							g.fillRect(x1, y1, tileWd, tileWd);
						}
						else{
							g.drawRect(x1, y1, tileWd, tileWd);
						}

					}
				}

				int gameOffset = 25*tileWd + yOffset;
				g.drawRect(0, gameOffset, boardWidth, 200);

			}

		};

		canvas.setPreferredSize(new Dimension(1000,1000));

		this.add(canvas);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}



	public static void main(String[] args){
		new test();
	}

}
