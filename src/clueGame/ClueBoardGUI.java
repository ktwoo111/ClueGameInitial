/**
 * @author John Baker and Taewoo Kim
 * Class for drawing the board
 */
package clueGame;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	/*
	private class selectionListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int col = (int) Math.floor(e.getX()/BoardCell.BOX_DIMENSION);
			int row = (int) Math.floor(e.getY()/BoardCell.BOX_DIMENSION);
			if(board.getCurrentPlayer() == 0 && board.getTargets().contains(board.getCellAt(row, col))) {
				board.setSelectedLocation(board.getCellAt(row, col));
			}
			else {
				JOptionPane.showMessageDialog(null, "You must make a move before hitting Next Player", "Move First!", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	*/
	
		
	
}
