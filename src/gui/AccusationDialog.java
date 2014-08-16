package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Suggestion;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cards.Person;
import cards.Room;
import cards.Weapon;

import main.Constants;

/**
 * This dialog appears when a player has made an accusation. It will display the cards
 * in your accusation, and the cards forming the solution. If the player is correct,
 * they win the game and you go back to the main menu. Otherwise they are eliminated
 * and GameFrame updates.
 * @author craigthelinguist
 */
public class AccusationDialog extends JDialog{

	private final int CARD_SEP = 6;
	private final int CARD_WD = Constants.CARD_WIDTH;
	private final int CARD_HT = Constants.CARD_HEIGHT;
	private final int CANVAS_WD = CARD_WD*3 + CARD_SEP*5;
	private final int CANVAS_HT = CARD_HT*2 + CARD_SEP*3;
	private final int PANEL_WD = CANVAS_WD;
	private final int PANEL_HT = 50;
	
	private JPanel canvas;
	private JPanel panel;
	private JLabel label;
	private JButton button;
	
	public AccusationDialog(final GameFrame controller, final Suggestion accuse, final Suggestion solution){
		
		super(controller,true); //prevents interactions with underlying frame
		
		
		// set up canvas
		canvas = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				int dialogWidth = AccusationDialog.this.getWidth();
				int offset = CARD_WD + CARD_WD/2 + CARD_SEP*3;
				int startX = dialogWidth/2 - offset;
				int startY = CARD_SEP;
								
				// draw player's accusation on top row
				g.drawImage(accuse.getPerson().getCardImage(), startX, startY, null);
				g.drawImage(accuse.getRoom().getCardImage(), startX + CARD_SEP + CARD_WD, startY, null);
				g.drawImage(accuse.getWeapon().getCardImage(), startX + CARD_SEP*2 + CARD_WD*2, startY,null);
				
				startY = CARD_SEP*2+CARD_HT;
				
				// draw solution on bottom row
				g.drawImage(solution.getPerson().getCardImage(), startX, startY, null);
				g.drawImage(solution.getRoom().getCardImage(), startX + CARD_SEP + CARD_WD, startY, null);
				g.drawImage(solution.getWeapon().getCardImage(), startX + CARD_SEP*2 + CARD_WD*2, startY, null);
				
			}
		};
		canvas.setPreferredSize(new Dimension(CANVAS_WD,CANVAS_HT));
		
		// set up label and button
		panel = new JPanel();
		boolean wonGame = accuse.equals(solution);
		if (wonGame){
			button = new JButton("You Win!");
			String text = "You guessed correctly! Congrats - you win :-)";
			label = new JLabel(text, SwingConstants.CENTER);
			
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent event) {
					controller.showVictoryDialog();
				}
			
			});
			
		}
		else{
			button = new JButton("You lose!");
			String line1 = "It was " + solution.toString();
			String line2 = "Though you can't win, you may still move around";
			String line3 = "and refute suggestions.";
			String s = "<html><div style=\"text-align: center;\">"+line1+"<br/>"+line2+"<br/>"+line3+"</html>";
			label = new JLabel(s, SwingConstants.CENTER);
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent event) {
					controller.playerLost();
				}
			
			});
		}
		
		// format the entire thing
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);

		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(canvas)
			.addComponent(button)
			.addComponent(label)
		);
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(canvas));
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(button));
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(label));
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(getMaximumSize());
		this.validate();
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
}
