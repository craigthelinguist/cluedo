package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar implements MenuListener, ActionListener {

	// constants
	private final int MENU_HT = 20;
	private final int MENU_WD = GameFrame.TILES_ACROSS*GameFrame.TILE_WIDTH;

	// fields
	private JMenu file, help;
	private JMenuItem newGame, exit, instructions;
	private GameFrame controller;
	
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
        exit.addActionListener(this);
        
        // set up help menu
        help = new JMenu("Help");
        add(help);
        instructions = new JMenuItem("Instructions");
        help.add(instructions);
        instructions.addActionListener(this);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(exit)) {
			System.exit(0); //if user has clicked exit, exit the program.
		}
		if(e.getSource().equals(instructions)) {
			try {
				displayInstructions();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void displayInstructions()throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader("gameInstructions/instructions.txt"));
		JTextArea textArea = new JTextArea(500,380);
		textArea.read(br,null);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension(500, 380));
	    br.close();
	    JOptionPane.showMessageDialog(null, scrollPane, "How-To-Play",JOptionPane.INFORMATION_MESSAGE);
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
        JFrame frame = new JFrame("MenuBarTesting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,400);
        frame.setVisible(true);
        
        frame.setJMenuBar(new MenuBar(null));
    }

}
