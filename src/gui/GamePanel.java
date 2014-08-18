package gui;

import game.Board;
import game.Player;
import game.Suggestion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cards.Card;

import main.Constants;

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
	private JLabel playerLocation;
	private ImageIcon playerImage;

	// card panel: draws the player's cards
	private JPanel cards;

	// only one of buttonPanel or refutePanel is visible at any time.
	private JPanel visiblePanel;
	
	// button panel: contains buttons and the dice
	private JPanel buttons;
	private JButton buttonAccuse;
	private JButton buttonSuggest;
	private JButton buttonEndTurn;
	private JButton buttonRollDice;
	private JLabel movesLabel;
	
	// refute panel: contains options for refuting a suggestion
	private JPanel refutePanel;
	private JComboBox<Card> refuteOptions;
	private JButton refuteButton;
	private JLabel refuteLabel;
	private boolean refuting = false; // whether you're currently refuting suggestion or not
	private Player suggestor = null; // who made suggestion
	private Suggestion suggestion = null;
	private GroupLayout layout;

	
	/**
	 * Create new panel and set up buttons, area for cards and portrait.
	 * Game panel has all the choices the player can make while playing.
	 * @param frame : frame to which this panel is attached
	 */
	public GamePanel(GameFrame frame) {

		// size & controller
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		controller = frame;

		// set up the 3 panels
		this.setupPortrait();
		this.setupCards();
		this.setupButtons();
		this.setupRefutation();
		
		// layout
		layout = new GroupLayout(this);
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
		//visiblePanel = buttons;

	}

	/**
	 * Set up the buttons on the panel.
	 * -End Turn : ends the player's turn
	 * -Accuse : make an accusation
	 * -Suggest: make a suggestion
	 * -Roll Dice : roll the dice and move 
	 */
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
	 * Set up the refutation panel when player makes suggestion
	 */
	private void setupRefutation() {

		refutePanel = new JPanel();
		refutePanel.setPreferredSize(buttons.getPreferredSize());
		
		refuteOptions = new JComboBox<>();
		refuteOptions.setEditable(false);
		refuteOptions.setMaximumSize(new Dimension(refutePanel.getPreferredSize().width,10));
		refuteOptions.setMinimumSize(refuteOptions.getMinimumSize());
		
		refuteLabel = new JLabel("Choice");
		refuteButton = new JButton("Pass");

		
		GroupLayout layout = new GroupLayout(refutePanel);
		refutePanel.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateGaps(true);
		
		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(refuteButton)
			.addComponent(refuteLabel)
			.addComponent(refuteOptions)
		);
		vertical.addComponent(refuteButton);
		vertical.addComponent(refuteLabel);
		vertical.addComponent(refuteOptions);
		
	}
	
	/**
	 * Enables or disables the button with the given name.
	 * @param enabled: whether the button should now be enabled (True) or disabled (false)
	 * @param name: name of the button
	 */
	protected void setButtonEnabled(String name, boolean enabled){
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

	/**
	 * Add ActionListener to Button
	 */
	private void setupButtonListeners() {

		buttonEndTurn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				controller.buttonPressed("End Turn");
			}

		});

	}

	/**
	 * Sets up the cards so that they can be displayed on the panel
	 */
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

					for (int i = 0; i < images.length; i++){

						int x = xStart + i*Constants.CARD_WIDTH + (i+1)*CARD_SEP;
						int y = offSetDown;
						g.drawImage(images[i],x,y,null);
						g.setColor(Color.BLACK);
						g.drawRect(x,y,Constants.CARD_WIDTH,Constants.CARD_HEIGHT);

					}
				}

			}
		};
		cards.setPreferredSize(new Dimension(450,PANEL_HEIGHT));

	}

	/**
	 * Set up portrait on the panel to identify whose turn it currently is.
	 */
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
		playerLocation = new JLabel("");
		
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
		vertical.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(playerLocation));
		
		horizontal.addGroup(layout.createParallelGroup(Alignment.CENTER)
			.addComponent(currentPlayer)
			.addComponent(portraitBox)
			.addComponent(playerLocation)
		);

	}

	/**
	 * Update all info the GamePanel is displaying. It will
	 * @param player: player whose name and portrait should be displayed.
	 */
	protected void updateGamePanel(){ 
		// update the portrait being displayed
		Player player = controller.getCurrentPlayer();
		portraitBox.setIcon(new ImageIcon(player.getPortrait()));
		if (player.eliminated()) currentPlayer.setForeground(Color.RED);
		else currentPlayer.setForeground(Color.BLACK);
		currentPlayer.setText(player.toString());
		playerLocation.setText(player.getLocation().getRoomName());
		
		// refuting
		if (refuting){
			refuteOptions.removeAllItems();
			if (player.hasCard(suggestion.person)) refuteOptions.addItem(suggestion.person);
			if (player.hasCard(suggestion.room)) refuteOptions.addItem(suggestion.room);
			if (player.hasCard(suggestion.weapon)) refuteOptions.addItem(suggestion.weapon);
			System.out.println(refuteOptions.getItemCount());
			if (refuteOptions.getItemCount() == 0){
				refuteButton.setText("Pass");
			}
			else{
				refuteButton.setText("Refute");
				refuteOptions.setSelectedIndex(0);
			}

			// set action listeners for the button
			ActionListener[] listeners = refuteButton.getActionListeners();
			for (int i = 0; i < listeners.length; i++){
				if (listeners[i] == null) break;
				else refuteButton.removeActionListener(listeners[i]);
			}
			if (refuteOptions.getItemCount() == 0){
				
				refuteButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.buttonPressed("Pass");
					}			
				});
				
			}
			else{

				refuteButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.refute((Card)(refuteOptions.getSelectedItem()));
					}			
				});
				
			}
		
		}
				
		// rolling & moving
		// enable/diasble buttons as appropriate
		else{
			Board board = controller.getBoard();
			String state = board.getState();
			boolean eliminated = board.getCurrentPlayer().eliminated();
			switch(state){

			case "ROLLING":
				setButtonEnabled("Accuse",false);
				setButtonEnabled("Suggest",false);
				setButtonEnabled("Roll Dice",true);
				break;
			case "MOVING":
				setButtonEnabled("Accuse", eliminated ? false : true);
				setButtonEnabled("Suggest",eliminated ? false : true);
				setButtonEnabled("Roll Dice",false);
				break;
			case "SUGGESTING":
				setButtonEnabled("Accuse", eliminated ? false : true);
				setButtonEnabled("Suggest",eliminated ? false : true);
				setButtonEnabled("Roll Dice",false);
				break;
			case "DONE":
				setButtonEnabled("Accuse", eliminated ? false : true);
				setButtonEnabled("Suggest",false);
				setButtonEnabled("Roll Dice", false);
				break;
			}
			
			// can only suggest when in a room
			if (player.getLocation().getRoomName().equals("Hallway"))
				setButtonEnabled("Suggest", false);
			
			// update amount of moves
			int amount = board.getMovesLeft();
	    	this.movesLabel.setText("Moves: " + amount);
			
		}
		
		if(player.getLocation().getRoomName().equals("")) { //if the player is not in a room
			setButtonEnabled("Suggest", false); //set the suggest button to false (can't suggest if not in room)
		}

		
		this.validate();
		this.repaint();
		
	}

    /**
     * Responsible for telling the controller what button has been pressed.
     */
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

    /**
     * Activate this GamePanel. It should make itself visible and update
     * itself.
     */
	protected void activate() {
		endRefuting();
		this.updateGamePanel();
		setVisible(true);
	}
	
	/**
	 * Deactivate this GamePanel. It should hide.
	 */
	protected void deactivate(){
		endRefuting();
		setVisible(false);
	}
	
	/**
	 * Set this GamePanel to be in refuting mode. Refuting mode means that the
	 * players are taking turns to refute a suggestion, or pass.
	 * @param suggestor: who made suggestion
	 * @param suggestion: the suggestion made
	 */
	protected void startRefuting(final Player suggestor, Suggestion suggestion){
		if (!refuting){
			this.layout.replace(buttons,refutePanel);
		}
		refuting=true;
		this.suggestor=suggestor;
		this.suggestion=suggestion;
	}
	
	/**
	 * End the refuting.
	 */
	protected void endRefuting(){
		if (refuting) this.layout.replace(refutePanel, buttons);
		refuting=false;
		this.suggestor=null;
		this.suggestion=null;
	}
	
	/**
	 * Return the player who made the current suggestion.
	 * @return: a player
	 */
	protected Player getSuggestor(){
		return suggestor;
	}
	
	/**
	 * Getter. Returns the current suggestion being made.
	 * @return: a suggestion.
	 */
	protected Suggestion getSuggestion(){
		return suggestion;
	}
	
    
}

