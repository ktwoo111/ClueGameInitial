package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGameMain extends JFrame {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");
		board.initialize();
		
		JFrame frame = new JFrame();
		frame.setTitle("Clue Game");
		frame.setSize(700,300); // width, height
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueBoardGUI gui = new ClueBoardGUI();
		frame.add(gui,BorderLayout.CENTER);
		
		
		
		
		frame.setVisible(true);
		
		
		
		
	}
}
