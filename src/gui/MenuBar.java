package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import main.Constants;

/**
 * The MenuBar provides drop-down options for the player, like starting a new game
 * or getting help. It is apart of GameFrame.
 * @author craigthelinguist
 */
public class MenuBar extends JMenuBar implements MenuListener, ActionListener {

	// constants
	private final int MENU_HT = 20;
	private final int MENU_WD = Constants.TILES_ACROSS*Constants.TILE_WIDTH;

	// fields
	private JMenu file, help, cheats;
	private JMenuItem newGame, exit, instructions, solution;
	private GameFrame controller;
	
	/**
	 * MenuBar Constructor sets up the menu bar and layout
	 * adds the JMenus and JMenuItems onto the JMenuBar.
	 */
	public MenuBar(GameFrame frame) {
		
		// set size and field
        this.setPreferredSize(new Dimension(MENU_WD,MENU_HT));
        controller = frame;
        
		// set up file menu
        file = new JMenu("File");
        add(file);
        newGame = new JMenuItem("New Game"); 
        file.add(newGame);
        exit = new JMenuItem("Exit");
        file.add(exit);
        
        // set up help menu
        help = new JMenu("Help");
        add(help);
        instructions = new JMenuItem("Instructions");
        help.add(instructions);
        
        // set up cheats menu
        cheats = new JMenu("Cheats");
        add(cheats);
        solution = new JMenuItem("Solution");
        cheats.add(solution);
        
        // add action listeners
        newGame.addActionListener(this);
        exit.addActionListener(this);
        instructions.addActionListener(this);
        solution.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(exit)) {
			System.exit(0); //if user has clicked exit, exit the program.
		}
		else if(e.getSource().equals(instructions)) {
			try {
				displayInstructions();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, "Error loading instructions: " + e);
				return;
			}
		}
		else if (e.getSource().equals(newGame)){
			controller.newGamePrompt();
		}
		else if (e.getSource().equals(solution)){
			if (controller.playingGame()){
				System.out.println("Solution is: " + controller.getBoard().solution.toString());
			}
			
		}
	}

	/** 
	 * Reads a text file that contains instructions how to play and then displays it 
	 * in a JOptionPane.
	 * @throws IOException
	 */
	public static void displayInstructions()throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader("gameInstructions/instructions.txt"));
		JTextArea textArea = new JTextArea(20,50);
				
		/*create new border with title */
		TitledBorder border  = BorderFactory.createTitledBorder("How To Play:");
		/*create new font for the title */
		Font font = new Font ("Arial", Font.BOLD, 20);
		/*set title font and center the title */
		border.setTitleFont(font);
		border.setTitleJustification(TitledBorder.CENTER);
		/*set the border around text area */
		textArea.setBorder(border);
		
		textArea.read(br,null);
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
	    br.close(); //close the bufferedreader
	    JOptionPane.showMessageDialog(null, textArea, "How-To-Play",JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * MenuListener
	 */
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub
	}
	
	/*Main method to test the menu bar*/
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("MenuBarTesting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,400);
        frame.setVisible(true);
        
        frame.setJMenuBar(new MenuBar(null));
    }

}
