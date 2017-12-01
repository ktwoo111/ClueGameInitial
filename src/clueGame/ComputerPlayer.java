package clueGame;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private char previousLocation;
	
	public ComputerPlayer(String playerName, int row, int column, String color) {
		super(playerName,row,column,color);
		previousLocation = 'Z'; // just a random non room character
	}


	public BoardCell pickLocation(Set<BoardCell> targets, char currentLocation){
		ArrayList<BoardCell> targetsList = new ArrayList<BoardCell>(targets);
		if(currentLocation != 'W' && currentLocation != 'X'){
			previousLocation = currentLocation;
		}
		boolean containsRoom = false;
		boolean prevRoomExistsInList = false;
		BoardCell chosenTarget = new BoardCell(0,0,"W"); // just random initialization of boardcell
		for (BoardCell t : targetsList) {
			if (t.getInitial() != 'W') {containsRoom = true;}
			if (t.getInitial() == previousLocation) {prevRoomExistsInList = true;}
		}
		//if no room or there's a previous room is in list, go anywhere 
		if (containsRoom == false || prevRoomExistsInList == true ) {
			Random rand = new Random();
			int n = rand.nextInt(targets.size());
			chosenTarget = targetsList.get(n);
		}
		//if a visited room exist in list, go anywhere but there		
		else {
			for (BoardCell t : targetsList) {
				if (t.getInitial() != 'W') {chosenTarget = t;}
			}
		}
		return chosenTarget;
	}
	public void makeAccusation(){
		
	}


	/**
	 * 
	 * @param cards
	 * @param currentRoom get currentRoom by getting getCellAt for player's location, get initial, and then get room string value by accessing legend
	 * @return
	 */
	@Override
	public Solution createSuggestion(ArrayList<Card> cards, String currentRoom){ 	

		ArrayList<Card> remainingCards = new ArrayList<Card>();		
		for(Card c: cards) {
			remainingCards.add(c);
		}

		remainingCards.removeAll(super.getMyCards());
		remainingCards.removeAll(super.getSeenCards());
	


		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();

		for(Card c : remainingCards) {
			if (c.getCardType() == CardType.PERSON) {
				people.add(c);
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c);
			}
		}
		Random randPerson = new Random();
		int n = randPerson.nextInt(people.size());
		String person = people.get(n).getCardName();

		Random randWeapon = new Random();
		int k = randWeapon.nextInt(weapons.size());
		String weapon = weapons.get(k).getCardName();

		return new Solution(person, currentRoom, weapon);

	}

	public char getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(char previousLocation) {
		this.previousLocation = previousLocation;
	}

	public static void main(String args[]){
		Board board;
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
		ComputerPlayer test = new ComputerPlayer("Thomas", 19, 8,"black");

		// if only one person not seen, choose that one.
		test.getSeenCards().add(new Card("Gett","person"));
		test.getSeenCards().add(new Card("Connor","person"));
		test.getSeenCards().add(new Card("Eddie","person"));
		test.getSeenCards().add(new Card("Matt","person"));

		test.getMyCards().add(new Card("Thomas","person"));

		test.createSuggestion(board.getCards(), "Library");


	}

}
