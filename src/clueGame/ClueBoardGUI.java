/**
 * @author John Baker and Taewoo Kim
 * Class for drawing the board
 */
package clueGame;


import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClueBoardGUI extends JPanel{
	private static Board board = Board.getInstance();
	private static final long serialVersionUID = 1L;
	JLabel roomText; // = new JLabel("Guess");	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++){
				board.getCellAt(i, j).draw(g);
			}
		}
		
	}
}
