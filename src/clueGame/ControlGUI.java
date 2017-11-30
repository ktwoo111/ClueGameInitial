/**
 * @author John Baker and Taewoo Kim
 * Class for the control gui
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class ControlGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Board board = Board.getInstance();
	private JTextField whoseTurnText;
	private JTextField dieField;
	private JTextField guessField;
	private JTextField guessResultField;
	//for makeGuess Dialog
	private JDialog guess;
	private JComboBox<String> personCombo, weaponCombo;
	private JTextField currentRoom;
	private String selectedPerson="";
	private String selectedWeapon= "";



	public ControlGUI()  {

		setLayout(new GridLayout(2,1));
		setSize(700,200);
		add(createTopPanel(),BorderLayout.CENTER);
		add(createBottomPanel(),BorderLayout.CENTER);
		repaint();

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
		whoseTurnText.setText(board.getPlayers().get(board.getCurrentPlayer()).getPlayerName());
		whoseTurnText.setEditable(false);
		turnPanel.add(turnLabel,BorderLayout.CENTER);
		turnPanel.add(whoseTurnText,BorderLayout.CENTER);
		return turnPanel;	
	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		JButton nextPlayerButton = new JButton("Next player");
		nextPlayerButton.setPreferredSize(new Dimension(200,50));

		class MakeMove implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if (board.getCurrentPlayer() == 0 && !board.isTargetSelected()) {
					JOptionPane.showMessageDialog(null, "You must make a move before hitting Next Player", "Move First!", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					board.makeMove();
					//Make makeGuess Dialog pop up if character is in room, otherwise computer make the suggestion
					if(board.getCellAt(board.getPlayers().get(board.getCurrentPlayer()).getRow(),board.getPlayers().get(board.getCurrentPlayer()).getColumn()).isRoom()) {
						if(board.getCurrentPlayer() == 0) {
					
							MakeGuess();
					
							guess.setVisible(true);
						}
						else {

							int row = board.getPlayers().get(board.getCurrentPlayer()).getRow();
							int col = board.getPlayers().get(board.getCurrentPlayer()).getColumn();
							String currentRoom = board.getLegend().get(board.getCellAt(row, col).getInitial());
							board.setCurrentSuggestion(board.getPlayers().get(board.getCurrentPlayer()).createSuggestion(board.getCards(), currentRoom));
						
							
							//set guessField
							guessField.setText(board.getCurrentSuggestion().person + " " + board.getCurrentSuggestion().room + " " + board.getCurrentSuggestion().weapon);

							//this is the card that will be shown after handling suggestion
							Card display = board.handleSuggestion(board.getCurrentSuggestion(), board.getPlayers().get(board.getCurrentPlayer()), board.getPlayers());
							//update seenCards for all players
							board.updateSeenCardForAllPlayers(display);
							//set guessResultField
							guessResultField.setText(display.getCardName());
							
							
						}
					}
					if (board.getCurrentPlayer() == 0) {
						board.getCellAt(board.getSelectedLocation().getRow(),board.getSelectedLocation().getColumn()).setSelected(false);
					}
					board.increaseCurrentPlayer(); // go to next player
					
					board.rollDie();
					board.calcTargets(board.getPlayers().get(board.getCurrentPlayer()).getRow(),board.getPlayers().get(board.getCurrentPlayer()).getColumn(),board.getDieVal());
					whoseTurnText.setText(board.getPlayers().get(board.getCurrentPlayer()).getPlayerName());
					dieField.setText(Integer.toString(board.getDieVal()));
					repaint();
				}
			}

		}
		nextPlayerButton.addActionListener(new MakeMove());


		JButton accusationButton = new JButton("Make an accusation");
		accusationButton.setPreferredSize(new Dimension(200,50));
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
		dieField.setText(Integer.toString(board.getDieVal()));
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

	//MakeGuess Dialog
	public void MakeGuess() {
		guess = new JDialog();
		guess.setTitle("Make a Guess");
		guess.setSize(500,600);
		guess.setResizable(false);
		guess.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		guess.setLayout(new GridLayout(4,1));
		guess.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		guess.add(roomGuess());
		guess.add(personGuess());
		guess.add(weaponGuess());
		guess.add(createButtons());


	}


	private JPanel roomGuess(){
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(1,2));

		JLabel roomLabel = new JLabel("Your room");
		currentRoom = new JTextField(5);
		int row = board.getPlayers().get(board.getCurrentPlayer()).getRow();
		int col = board.getPlayers().get(board.getCurrentPlayer()).getColumn();
		currentRoom.setText(board.getLegend().get(board.getCellAt(row, col).getInitial()));
		currentRoom.setEditable(false);

		room.add(roomLabel);
		room.add(currentRoom);

		return room;
	}

	private JPanel personGuess() {
		JPanel person = new JPanel();
		person.setLayout(new GridLayout(1,2));


		JLabel personLabel = new JLabel("Person");
		person.add(personLabel);
		personCombo = new JComboBox<String>();
		for(Player p : board.getPlayers()) {
			personCombo.addItem(p.getPlayerName());
		}
		person.add(personCombo);

		return person;
	}

	private JPanel weaponGuess(){
		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(1,2));


		JLabel weaponLabel = new JLabel("Weapon");
		weapon.add(weaponLabel);
		weaponCombo = new JComboBox<String>();
		
		//System.out.println("Card size: " + board.getCards().size());
		for(Card c : board.getCards()){
			if(c.getCardType() == CardType.WEAPON){
				//System.out.println("TRIGGERED");
				weaponCombo.addItem(c.getCardName());
			}
		}
		weapon.add(weaponCombo);

		return weapon;

	}

	private JPanel createButtons(){
		JPanel buttons = new JPanel();

		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");



		class Submit implements ActionListener{
			public void actionPerformed(ActionEvent e){
				board.setCurrentSuggestion(new Solution(personCombo.getSelectedItem().toString(), currentRoom.getText(), weaponCombo.getSelectedItem().toString()));
				
				//set guessField
				guessField.setText(board.getCurrentSuggestion().person + " " + board.getCurrentSuggestion().room + " " + board.getCurrentSuggestion().weapon);
			
				//below is the card that will be shown after handling suggestion
				Card display = board.handleSuggestion(board.getCurrentSuggestion(), board.getPlayers().get(0), board.getPlayers());
				//update seenCards for all players
				board.updateSeenCardForAllPlayers(display);
				//set guessResultField
				guessResultField.setText(display.getCardName());
		
				guess.dispose();
				repaint();
			}
		}

		submitButton.addActionListener(new Submit());
		class Cancel implements ActionListener{
			public void actionPerformed(ActionEvent e){
				guess.dispose();
			}
		}
		cancelButton.addActionListener(new Cancel());


		buttons.add(submitButton);
		buttons.add(cancelButton);
		return buttons;		
	}
}
