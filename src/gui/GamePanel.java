package gui;

import game.Player;

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
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
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

import cards.Card;
import main.Constants;
import main.Main;

public class GamePanel extends JPanel implements ActionListener {

	// constants
	private final int PANEL_WIDTH = Constants.TILES_ACROSS*Constants.TILE_WIDTH;
	private final int PANEL_HEIGHT = 150;
	private final int CARD_SEP = 6;

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
	private JLabel movesLabel;

	
	/*declare jlabels for the cards*/
	JLabel[] cardLabels = new JLabel[6];

	
	public GamePanel(GameFrame frame) {

		
		// size & controller
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		controller = frame;

		// set up the 3 panels
		this.setupPortrait();
		this.setupCards();
		this.setupButtons();
		this.setUpCardLables();
		
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

	/**
	 * This method creates new labels so as to not create a new label every time a player's
	 * turn ends. This stops it from creating labels to the sunset.
	 */
	private void setUpCardLables() {
		for(int i = 0; i < cardLabels.length;i++) {
			cardLabels[i] = new JLabel();
		}
	}

	private void setupButtons(){

		// create components
		buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(100,PANEL_HEIGHT));
		buttonAccuse = new JButton("Accuse");
		buttonSuggest = new JButton("Suggest");
		buttonEndTurn = new JButton("End Turn");
		buttonRollDice = new JButton("Roll Dice");
		buttonSuggest.setEnabled(false);
		buttonAccuse.setEnabled(false);
		setupButtonListeners();

		//setting up the listener for buttons
		buttonRollDice.addActionListener(this);
		buttonSuggest.addActionListener(this);
		buttonAccuse.addActionListener(this);
		movesLabel = new JLabel("Moves: 0");

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
		vertical.addComponent(movesLabel);

		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(buttonEndTurn)
			.addComponent(buttonAccuse)
			.addComponent(buttonSuggest)
			.addComponent(buttonRollDice)
			.addComponent(movesLabel)
		);
	}

	/**
	 * Enables or disables the button with the given name.
	 * @param enabled: whether the button should now be enabled (True) or disabled (false)
	 * @param name: name of the button
	 */
	public void setButtonEnabled(String name, boolean enabled){
		switch(name){
		case "Accuse":
			buttonAccuse.setEnabled(enabled);
			return;
		case "Suggest":
			buttonSuggest.setEnabled(enabled);
			return;
		case "Roll Dice":
			buttonRollDice.setEnabled(enabled);
			return;
		}
	}

	private void setupButtonListeners() {

		buttonEndTurn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				controller.buttonPressed("End Turn");
			}

		});

	}

	private void setupCards(){

		// card panel
		this.cards = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){



				// draw cards in current player's hand
				if (controller.playingGame()){

					//g.setColor(Color.LIGHT_GRAY);
					//g.fillRect(0, 0, this.getWidth(), this.getHeight());

					Player player = controller.getCurrentPlayer();
					Image[] images = player.getCardImages();

					int numCards = images.length;
					int totalWidth = numCards*Constants.CARD_WIDTH + (numCards+1)*CARD_SEP;
					int residual = this.getWidth() - totalWidth;
					int offSet = residual/2;
					int xStart = offSet;
					int offSetDown = ((GamePanel.this.PANEL_HEIGHT) - (Constants.CARD_HEIGHT))/2;


					/*testing labels for cards*/
					for(int i = 0; i < player.getCardImages().length;i++) {
						//cardLabels[i] = new JLabel();
						cardLabels[i].setToolTipText(player.getPlayersCards().get(i).getType().toString());
						
						ImageIcon icon = new ImageIcon(images[i]); 
						cardLabels[i].setIcon(icon);
						add(cardLabels[i]);
					}
					/*testing ends here*/
					
						
						
					for (int i = 0; i < images.length; i++){

						
						int x = xStart + i*Constants.CARD_WIDTH + (i+1)*CARD_SEP;
						int y = offSetDown;
						//g.drawImage(images[i],x,y,null); //draws the image of the card
						g.setColor(Color.BLACK);
						g.drawRect(x,y,Constants.CARD_WIDTH,Constants.CARD_HEIGHT);

					}
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
	 * @param player: player whose name and portrait should be displayed.
	 */
	public void updatePortrait(Player player){
		portraitBox.setIcon(new ImageIcon(player.getPortrait()));
		currentPlayer.setText(player.toString());
	}

    /** change this method later a lot of it is testing*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttonRollDice)) {
			controller.buttonPressed("Roll Dice");
		}
		else if (e.getSource().equals(buttonSuggest)){
			controller.buttonPressed("Suggestion");
		}
		else if (e.getSource().equals(buttonAccuse)){
			controller.buttonPressed("Accuse");
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

    /**
     * Set the string in the JLabel at the bottom of the buttons panel.
     * @param string: text that the JLabel should display.
     */
    public void setMovesRemaining(int amount){
    	this.movesLabel.setText("Moves: " + amount);
    }
    
}

