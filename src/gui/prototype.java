package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class prototype {

	public prototype(){
		
		JFrame frame = new JFrame();

		final int card_wd = 55;
		final int card_ht = 100;
		final int buttonpane_wd = 100;
		final int playerpane_wd = 100;
		final int pane_wd = 650;
		final int pane_ht = 150;
		
		
		JPanel panel = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g){
				
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, playerpane_wd, pane_ht);
				g.drawRect(650-buttonpane_wd, 0, buttonpane_wd, pane_ht);
				
				for (int i = 0; i < 7; i++){
					
					int x = playerpane_wd + i*card_wd;
					int y = 0;
					g.drawRect(x,y,card_wd,card_ht);
					
					
				}
				
				
			}
			
		};
		
		
		panel.setPreferredSize(new Dimension(650,150));
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	
	
	
	public static void main(String[] args){
		new prototype();
	}
	
}
