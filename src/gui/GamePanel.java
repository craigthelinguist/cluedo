package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class GamePanel extends JPanel {

	public GamePanel() {
		
		/* Create dice image icon */
        ImageIcon diceImage = createImageIcon("../images/Untitled.png", "dice"); 
        /*create 3 card image icons */
        ImageIcon cardImage = createImageIcon("../images/testImage.png", "card 1"); 
        ImageIcon cardImage2 = createImageIcon("../images/testImage2.png", "card2"); 
        ImageIcon cardImage3 = createImageIcon("../images/testImage2.png", "card3"); 
        ImageIcon currentPlayerImage = createImageIcon("../images/Miss Scarlett.png", "playerImage");
        
        /* Create the labels */
		JLabel currentPlayerLabel = new JLabel("Current Player Turn:");
		JLabel imageOfCurrentPlayerTurn = new JLabel(currentPlayerImage);
		JLabel diceLabel = new JLabel(diceImage); //place icon inside label
		JButton rollDiceButton = new JButton("Roll Dice");
		JLabel playersCardLabel = new JLabel("Player's Cards:");
		JLabel cardLabel1 = new JLabel(cardImage);
		JLabel cardLabel2 = new JLabel(cardImage2);
		JLabel cardLabel3 = new JLabel(cardImage3);
		
		//Set layout for this panel
		GroupLayout groupLayout = new GroupLayout(this);
		
		/* Horizontal Group */
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(currentPlayerLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(imageOfCurrentPlayerTurn, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addComponent(diceLabel, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(rollDiceButton)
					.addGap(149)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(playersCardLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(cardLabel1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cardLabel2, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(cardLabel3, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		/*Vertical Group */
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(currentPlayerLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(playersCardLabel))
						.addComponent(imageOfCurrentPlayerTurn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(diceLabel, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
						.addComponent(cardLabel1, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
						.addComponent(cardLabel2, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
						.addComponent(cardLabel3, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(203, Short.MAX_VALUE)
					.addComponent(rollDiceButton)
					.addGap(24))
		);
		setLayout(groupLayout);

	}
	
	/** This method reads images from the file path and returns an ImageIcon 
	 * 
	 * @param path
	 * @param description of image
	 * @return ImageIcon
	 */
    protected static ImageIcon createImageIcon(String path, String description) {
    	java.net.URL imgURL = GamePanel.class.getResource(path);
    	if (imgURL != null) {
    		return new ImageIcon(imgURL, description);
    	} else {
    		System.err.println("Error finding file: " + path);
    		return null;
    	}
    }
    
    /* testing the panel*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                 
        createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("GamePanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GamePanel());
        frame.pack();
        frame.setVisible(true);
    }
	
}
