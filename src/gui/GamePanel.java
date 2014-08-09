package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import main.Constants;
import main.Main;

public class GamePanel extends JPanel implements ActionListener {

	// constants
	private final int PANEL_WIDTH = Constants.TILES_ACROSS*Constants.TILE_WIDTH;
	private final int PANEL_HEIGHT = 150;
	private final int CARD_SEP = 5;
	private final int CARD_WIDTH = 60;
	private final int CARD_HEIGHT = 100;
	private final char SEPARATOR = File.separatorChar;
	private final String FILEPATH = Constants.ASSETS;
	
	// controller
	private GameFrame controller;
	
	// portrait panel: contains name of current player and portrait
	private JPanel portrait;
	private JLabel currentPlayer;
	private JLabel portraitBox;
	private ImageIcon playerImage;
	
	// card panel: draws the player's cards
	private JPanel cards;
	
	// button panel: contains buttons and the dice
	private JPanel buttons;
	private JButton buttonAccuse;
	private JButton buttonSuggest;
	private JButton buttonEndTurn;
	private JButton buttonRollDice;
	private JLabel dice1Label;
	private ImageIcon dice1;
	private JLabel dice2Label;
	private ImageIcon dice2;
	
	public GamePanel(GameFrame frame) {
		
		// size & controller
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		controller = frame;
		
		// set up the 3 panels
		this.setupPortrait();
		this.setupCards();
		this.setupButtons();
		
		// add panels to this
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		//layout.setAutoCreateContainerGaps(true);
		
		
		horizontal.addComponent(portrait);
		horizontal.addComponent(cards);
		horizontal.addComponent(buttons);
		
		vertical.addGroup(layout.createParallelGroup()
			.addComponent(portrait)
			.addComponent(cards)
			.addComponent(buttons)
		);
		
	}
	
	private void setupButtons(){

		// create components
		buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(100,PANEL_HEIGHT));
		buttonAccuse = new JButton("Accuse");
		buttonSuggest = new JButton("Suggest");
		buttonEndTurn = new JButton("End Turn");
		buttonRollDice = new JButton("Roll Dice");
		
		//setting up the listener for buttons
		buttonRollDice.addActionListener(this);
		buttonSuggest.addActionListener(this);

		try{
			dice1 = new ImageIcon(ImageIO.read(new FileInputStream(FILEPATH+"shitdice1.png")));
			dice2 = new ImageIcon(ImageIO.read(new FileInputStream(FILEPATH+"shitdice1.png")));
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error loading dice: " + e.getMessage());
			System.exit(1);
		}
		dice1Label = new JLabel(dice1);
		dice2Label = new JLabel(dice2);
		
		// dice panel
		JPanel dicePanel = new JPanel();
		GroupLayout diceLayout = new GroupLayout(dicePanel);
		GroupLayout.SequentialGroup diceHorizontal = diceLayout.createSequentialGroup();
		GroupLayout.SequentialGroup diceVertical = diceLayout.createSequentialGroup();
		diceLayout.setHorizontalGroup(diceHorizontal);
		diceLayout.setVerticalGroup(diceVertical);
		
		diceHorizontal.addComponent(dice1Label);
		diceHorizontal.addComponent(dice2Label);
		diceVertical.addGroup(diceLayout.createParallelGroup()
			.addComponent(dice1Label)
			.addComponent(dice2Label)
		);	

		// layout
		GroupLayout layout = new GroupLayout(buttons);
		buttons.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);
		
		vertical.addComponent(buttonEndTurn);
		vertical.addComponent(buttonAccuse);
		vertical.addComponent(buttonSuggest);
		vertical.addComponent(buttonRollDice);
		vertical.addComponent(dicePanel);
		
		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(buttonEndTurn)
			.addComponent(buttonAccuse)
			.addComponent(buttonSuggest)
			.addComponent(buttonRollDice)
			.addComponent(dicePanel)
		);
	}
	
	private void setupCards(){

		// card panel
		this.cards = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 0, cards.getWidth(), 150);
				
				g.setColor(Color.BLACK);
				for (int i = 0; i < 6; i++){					
					int x = i*CARD_WIDTH + i*CARD_SEP;
					int y = 0;
					g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
				}
			}
		};
		cards.setPreferredSize(new Dimension(450,PANEL_HEIGHT));
		
	}
	
	private void setupPortrait(){

		// create components
		this.portrait = new JPanel();
		portrait.setPreferredSize(new Dimension(100,PANEL_HEIGHT));
		portrait.setMinimumSize(portrait.getPreferredSize());
		portrait.setMaximumSize(portrait.getPreferredSize());
		currentPlayer = new JLabel("Miss Scarlett", SwingConstants.CENTER);
		currentPlayer.setPreferredSize(new Dimension(100,20)); //stop label from resizing
		currentPlayer.setMinimumSize(currentPlayer.getPreferredSize());
		currentPlayer.setMaximumSize(currentPlayer.getPreferredSize());
		try{
			playerImage = new ImageIcon(ImageIO.read(new FileInputStream(FILEPATH+"portrait_scarlett.png")));
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error loading portrait: " + e.getMessage());
			System.exit(1);
		}
		portraitBox = new JLabel(playerImage);
		
		// layout
		GroupLayout layout = new GroupLayout(portrait);
		portrait.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		//layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(currentPlayer));
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(portraitBox));
		
		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(currentPlayer)
			.addComponent(portraitBox)
		);
		
	}
	
	/**
	 * Set the currently displayed portrait.
	 * @param filename: name of the image that the portrait should display.
	 */
	public void setPortrait(String filename){
		try{
			portraitBox.setIcon(new ImageIcon(ImageIO.read(new FileInputStream(FILEPATH+filename))));
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error loading portrait: " + e.getMessage());
			return;
		}
	}
	
    /** change this method later a lot of it is testing*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttonRollDice)) {
			int[] array = controller.getBoard().rollDice(); //only creating array to test if the dice numbers were changed
			for(int i = 0; i < array.length; i++) { //test to see what's inside this array
				System.out.println(array[i]);
			}
		}
		if(e.getSource().equals(buttonSuggest)) {
			/*makes the suggestion dialog appear*/
			SuggestionDialog sd = new SuggestionDialog();
			sd.setVisible(true);
	        //sd.setBounds(400,0,400,400);
		}

	}
	
	
	
	
    /* testing the panel*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	UIManager.put("swing.boldMetal", Boolean.FALSE);
            	JFrame frame = new JFrame("GamePanel");
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.getContentPane().add(new GamePanel(null));
            	frame.pack();
            	frame.setVisible(true);
            }
        });
    }


    	
}

