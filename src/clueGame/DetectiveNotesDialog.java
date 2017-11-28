/**
 * @author John Baker and Taewoo Kim
 * Class for the detective notes dialog
 */
package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class DetectiveNotesDialog extends JDialog {

	private static Board board = Board.getInstance();
	private JComboBox<String> personCombo, roomCombo, weaponCombo;

	public DetectiveNotesDialog(){
		setTitle("Detective Notes");
		setSize(500,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		JPanel checkBoxes = new JPanel();
		checkBoxes.setLayout(new GridLayout(3,1));
		checkBoxes.add(createPeople());

		checkBoxes.add(createRooms());	

		checkBoxes.add(createWeapons());


		JPanel guessBoxes = new JPanel();
		guessBoxes.setLayout(new GridLayout(3,1));
		guessBoxes.add(createPersonGuess());
		guessBoxes.add(createRoomGuess());
		guessBoxes.add(createWeaponsGuess());


		add(checkBoxes);
		add(guessBoxes);

	}

	private JPanel createPeople() {
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for(Player p : board.getPlayers()) {
			JCheckBox checkBox = new JCheckBox(p.getPlayerName());
			peoplePanel.add(checkBox);
		}
		return peoplePanel;
	}

	private JPanel createPersonGuess() {
		JPanel personGuess = new JPanel();

		personCombo = new JComboBox<String>();
		for(Player p : board.getPlayers()) {
			personCombo.addItem(p.getPlayerName());
		}
		personCombo.addItem("Unsure");
		personGuess.add(personCombo);
		personGuess.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return personGuess;
	}

	private JPanel createRooms() {
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		for(char c : board.getLegend().keySet()) {
			if (c != 'W' && c != 'X') {
				JCheckBox checkBox = new JCheckBox(board.getLegend().get(c));
				roomsPanel.add(checkBox);
			}
		}
		return roomsPanel;
	}

	private JPanel createRoomGuess() {
		JPanel roomGuess = new JPanel();
		roomGuess.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		roomCombo = new JComboBox<String>();
		for(char c : board.getLegend().keySet()) {
			if (c != 'W' && c != 'X') {
				roomCombo.addItem(board.getLegend().get(c));
			}
		}
		roomCombo.addItem("Unsure");
		roomGuess.add(roomCombo);
		return roomGuess;
	}

	private JPanel createWeapons() {
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for(Card c : board.getCards()){
			if (c.getCardType() == CardType.WEAPON) {
				JCheckBox checkBox = new JCheckBox(c.getCardName());
				weaponsPanel.add(checkBox);
			}
		}
		return weaponsPanel;
	}

	private JPanel createWeaponsGuess() {
		JPanel weaponsGuess = new JPanel();
		weaponsGuess.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		weaponCombo = new JComboBox<String>();
		for(Card c : board.getCards()){
			if(c.getCardType() == CardType.WEAPON){
				weaponCombo.addItem(c.getCardName());
			}
		}
		weaponCombo.addItem("Unsure");
		weaponsGuess.add(weaponCombo);
		return weaponsGuess;
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

		DetectiveNotesDialog notes = new DetectiveNotesDialog();
		notes.setVisible(true);




		/*
		test.setVisible(true);
		test.repaint();
		 */
	}


}
