package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card.CardType;

public class MakeGuess extends JDialog {
	private static Board board = Board.getInstance();
	private JComboBox<String> personCombo, weaponCombo;
	private JTextField currentRoom;
	private String selectedPerson="";
	private String selectedWeapon= "";

	public MakeGuess() {
		setTitle("Make a Guess");
		setSize(500,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new GridLayout(4,1));


		add(roomGuess());
		add(personGuess());
		add(weaponGuess());
		add(createButtons());


	}


	private JPanel roomGuess(){
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(1,2));

		JLabel roomLabel = new JLabel("Your room");
		currentRoom = new JTextField(5);
		int row = board.getPlayers().get(0).getRow();
		int col = board.getPlayers().get(0).getColumn();
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
		for(Card c : board.getCards()){
			if(c.getCardType() == CardType.WEAPON){
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
				board.setSuggestion(personCombo.getSelectedItem().toString(), currentRoom.getText(), weaponCombo.getSelectedItem().toString());
				board.setSubmitted(true);
				dispose();
			}
		}

		submitButton.addActionListener(new Submit());
		class Cancel implements ActionListener{
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		}
		cancelButton.addActionListener(new Cancel());


		buttons.add(submitButton);
		buttons.add(cancelButton);
		return buttons;		
	}



	public static void main(String args[]){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");
		board.initialize();
		/*
		JFrame test = new JFrame();

		test.setResizable(false);
		test.setTitle("Clue Game");
		test.setSize(850,900); // width, height

		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */

		MakeGuess notes = new MakeGuess();
		notes.setVisible(true);




		/*
		test.setVisible(true);
		test.repaint();
		 */
	}

}
