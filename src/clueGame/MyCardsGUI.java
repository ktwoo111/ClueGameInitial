package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class MyCardsGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7742025385777436122L;
	private Board board = Board.getInstance();
	public MyCardsGUI() {
		add(CreatePanel());
	}

	private JPanel CreatePanel() {
		JPanel myCards = new JPanel();
		JPanel people = new JPanel();
		JPanel rooms = new JPanel();
		JPanel weapons = new JPanel();
		int peopleNum = 0;
		int weaponsNum = 0;
		int roomsNum = 0;
		for (Card c : board.getPlayers().get(0).getMyCards()) {
			if (c.getCardType() == CardType.PERSON) {
				peopleNum++;
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weaponsNum++;
			}
			else {
				roomsNum++;
			}
		}
		myCards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridBagLayout());
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		myCards.setLayout(new GridLayout(board.getPlayers().get(0).getMyCards().size(),1));
		if(roomsNum > 1){
			rooms.setLayout(new GridLayout(roomsNum,1));
		}
		if(weaponsNum > 1){
			weapons.setLayout(new GridLayout(weaponsNum,1));
		}
		if(peopleNum > 1){
			people.setLayout(new GridLayout(peopleNum,1));
		}

		for (Card c : board.getPlayers().get(0).getMyCards()) {
			if (c.getCardType() == CardType.PERSON) {
				JTextField personCard = new JTextField();
				personCard = new JTextField();
				personCard.setPreferredSize(new Dimension(150,30));
				personCard.setEditable(false);
				personCard.setBackground(Color.WHITE);
				personCard.setText(c.getCardName());
				people.add(personCard);
			}
			else if (c.getCardType() == CardType.WEAPON) {
				JTextField weaponCard = new JTextField();
				weaponCard = new JTextField();
				weaponCard.setPreferredSize(new Dimension(150,30));
				weaponCard.setEditable(false);
				weaponCard.setBackground(Color.WHITE);
				weaponCard.setText(c.getCardName());
				weapons.add(weaponCard);
			}
			else {
				JTextField roomCard = new JTextField();
				roomCard = new JTextField();
				roomCard.setPreferredSize(new Dimension(150,30));
				roomCard.setEditable(false);
				roomCard.setBackground(Color.WHITE);
				roomCard.setText(c.getCardName());
				rooms.add(roomCard);
			}
		}

		myCards.add(rooms);
		myCards.add(people);
		myCards.add(weapons);
		myCards.setSize(new Dimension(1000,850));

		return myCards;
	}

	public static void main(String args[]){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");
		board.initialize();
		JFrame test = new JFrame();
		test.setTitle("Clue Game");
		test.setSize(700,850); // width, height

		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyCardsGUI heil = new MyCardsGUI();
		test.add(heil);
		test.setVisible(true);




	}

}
