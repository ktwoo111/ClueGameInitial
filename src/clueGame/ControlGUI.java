package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField whoseTurnText;
	private JTextField dieField;
	private JTextField guessField;
	private JTextField guessResultField;
	
	
	
	public ControlGUI()  {

		
	
		setLayout(new GridLayout(2,1));
		add(createTopPanel(),BorderLayout.CENTER);
		add(createBottomPanel(),BorderLayout.CENTER);
		
		

		
	}
	

	
	private JPanel createTopPanel(){
		JPanel topPanel = new JPanel();
		topPanel.add(createTurnPanel());
		topPanel.add(Box.createVerticalStrut(50));
		topPanel.add(createButtonPanel());
		return topPanel;
		
		
	}
	
	
	
	private JPanel createTurnPanel(){
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel turnLabel = new JLabel("Whomst've turn?");
		whoseTurnText = new JTextField(20);
		whoseTurnText.setEditable(false);
		turnPanel.add(turnLabel,BorderLayout.CENTER);
		turnPanel.add(whoseTurnText,BorderLayout.CENTER);
		return turnPanel;	
	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		JButton nextPlayerButton = new JButton("Next player");
		nextPlayerButton.setPreferredSize(new Dimension(200,100));
		JButton accusationButton = new JButton("Make an accusation");
		accusationButton.setPreferredSize(new Dimension(200,100));
		buttonPanel.add(nextPlayerButton,BorderLayout.WEST);
		buttonPanel.add(accusationButton,BorderLayout.EAST);
		
		return buttonPanel;
		
	}
	
	
	private JPanel createBottomPanel() {
		JPanel bottomPanelDie = new JPanel();
		JPanel bottomPanelGuess = new JPanel();
		JPanel bottomPanelGuessResult = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		JLabel nameLabelDie = new JLabel("Roll");
		dieField = new JTextField(2);
		dieField.setEditable(false);
		bottomPanelDie.add(nameLabelDie);
		bottomPanelDie.add(dieField);
		bottomPanelDie.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		JLabel nameLabelGuess = new JLabel("Guess");
		guessField = new JTextField(30);
		guessField.setEditable(false);
		bottomPanelGuess.add(nameLabelGuess);
		bottomPanelGuess.add(guessField);
		bottomPanelGuess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		
		JLabel nameLabelGuessResult = new JLabel("Response");
		guessResultField = new JTextField(10);
		guessResultField.setEditable(false);
		bottomPanelGuessResult.add(nameLabelGuessResult);
		bottomPanelGuessResult.add(guessResultField);
		bottomPanelGuessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		
		bottomPanel.add(bottomPanelDie);
		bottomPanel.add(bottomPanelGuess);
		bottomPanel.add(bottomPanelGuessResult);
		
		return bottomPanel;
	}
	public static void main(String args[]){
		JFrame frame = new JFrame();
		frame.setTitle("Clue Game");
		frame.setSize(700,300); // width, height
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlGUI gui = new ControlGUI();
		frame.add(gui,BorderLayout.CENTER);
		
		
		
		
		frame.setVisible(true);
		
		
		
		
	}

}
