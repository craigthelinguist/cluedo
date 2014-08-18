package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import game.Player;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Constants;

import cards.Person;

/**
 * This is a chat box. It's meant to display a character talking in the game.
 * @author craigthelinguist
 */
public class TalkDialog extends JDialog{

	private JButton button;
	
	/**
	 * A Dialog which displays a character talking in game.
	 * 
	 * @param controller: controller to which this dialog is attached.
	 * @param player: the player talking. Their portrait will be drawn.
	 * @param talker: name of talker that will be displayed.
	 * @param msg: the message to be displayed.
	 */
	public TalkDialog(JFrame controller, final Image portrait, String talker, String msg){
		super(controller,true);
		
		// set up portrait that will be drawn
		JPanel canvas = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				if (portrait == null) g.drawImage(portrait,0,0,null);
				else g.drawImage(portrait,0,0,null);
			}
		};
		canvas.setPreferredSize(new Dimension(Constants.CARD_WIDTH, Constants.CARD_HEIGHT));
		
		
		// set up button
		button = new JButton("Done");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// lay things out
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		// set up text area
		StringBuilder sb = new StringBuilder(talker);
		sb.append('\n'); sb.append(msg);
		JTextArea text = new JTextArea(sb.toString());
		text.setLineWrap(true);
		text.setEditable(false);
		text.setVisible(true);
		text.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(300, Constants.CARD_HEIGHT));
		scroll.setViewportView(text);
		scroll.setVisible(true);
		
		horizontal.addGroup(
			layout.createParallelGroup()	
				.addComponent(canvas)
				.addComponent(button))
			.addComponent(scroll);	
		
		vertical.addGroup(layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup()
				.addComponent(canvas)
				.addComponent(button))
			.addComponent(scroll)
		);
		
		// dialog set up
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(getMaximumSize());
		this.validate();
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private class VictoryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
