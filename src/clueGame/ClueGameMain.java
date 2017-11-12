/**
 * @author John Baker and Taewoo Kim
 * Class for the main gui controller
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGameMain extends JFrame {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetectiveNotesDialog notes;
	private boolean createdNotes = false;

	
	
	public ClueGameMain(){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");
		board.initialize();
		
		
	
		setTitle("Clue Game");
		setSize(700,850); // width, height

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueBoardGUI gui = new ClueBoardGUI();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		add(gui);	
		setResizable(false);
		
		ControlGUI bottomGui = new ControlGUI();
		add(bottomGui,BorderLayout.SOUTH);
		
		setVisible(true);
		repaint();
		
	}
	
	
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		return menu;
		
	}
	
	private JMenuItem createDetectiveNotes(){
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(!createdNotes){
				notes = new DetectiveNotesDialog();
				createdNotes = !createdNotes;
				}
				
				notes.setVisible(true);
				
			}
			
		}
		item.addActionListener(new MenuItemListener());
		return item;
		
	}
	
	
	
	public static void main(String args[]){
		ClueGameMain hi = new ClueGameMain();
		
		
		
		
	}
}
