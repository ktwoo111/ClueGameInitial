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
import javax.swing.JOptionPane;

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
		
		
		setResizable(false);
		setTitle("Clue Game");
		setSize(850,900); // width, height

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueBoardGUI gui = new ClueBoardGUI();
		
		MyCardsGUI cardsGui = new MyCardsGUI();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		add(gui,BorderLayout.CENTER);	
		add(cardsGui,BorderLayout.EAST);
		
		
		ControlGUI bottomGui = new ControlGUI();
		add(bottomGui,BorderLayout.SOUTH);
		
		setVisible(true);
		repaint();
		
		JOptionPane popUp = new JOptionPane();
		popUp.showMessageDialog(this,"You are "+ board.getPlayers().get(0).getPlayerName()+ " press Next Player to begin play","Welcome to Clue, your poor soul", JOptionPane.INFORMATION_MESSAGE);
		
		
	}
	
	
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		menu.add(createExit());
		return menu;
		
	}
	
	private JMenuItem createExit(){
		JMenuItem item = new JMenuItem("Exit");
		class Exit implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
				
			}
			
		}
		item.addActionListener(new Exit());
		return item;
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
