package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class gameSetupTests {

	
	private static Board board;
	@BeforeClass
	public static void setUOnce() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
	}
	
	@Before
	public void setUp(){
		
	}
	
	
	
	@Test
	public void loadingPeopleTest() {
		assertEquals(6,board.getPlayers().size()); // better have 6 players in the arraylist
		//testing for 1st	
		//test so that starting location in not outside board or even inside room
		assertTrue(board.getCellAt(board.getPlayers().get(0).getRow(),board.getPlayers().get(0).getColumn()).isWalkway());
		//Name is not empty
		assertTrue(board.getPlayers().get(0).getPlayerName() != null || board.getPlayers().get(0).getPlayerName() != "" );
		//Color is not empty
		assertTrue(board.getPlayers().get(0).getColor() != null);
		
		//testing for 3rd
		//test so that starting location in not outside board or even inside room
		assertTrue(board.getCellAt(board.getPlayers().get(2).getRow(),board.getPlayers().get(2).getColumn()).isWalkway());
		//Name is not empty
		assertTrue(board.getPlayers().get(2).getPlayerName() != null || board.getPlayers().get(2).getPlayerName() != "" );
		//Color is not empty
		assertTrue(board.getPlayers().get(2).getColor() != null);
		//testing for 6th
		//test so that starting location in not outside board or even inside room
		assertTrue(board.getCellAt(board.getPlayers().get(5).getRow(),board.getPlayers().get(5).getColumn()).isWalkway());
		//Name is not empty
		assertTrue(board.getPlayers().get(5).getPlayerName() != null || board.getPlayers().get(5).getPlayerName() != "" );
		//Color is not empty
		assertTrue(board.getPlayers().get(5).getColor() != null);
	}
	
	@Test
	public void createAndloadDeckOfCardsTest() {
		//Test total number of cards is equal 21
		assertEquals(21,board.getCards().size());
		
		//Test that the correct number of each card is in the deck
		int weapon = 0;
		int people = 0;
		int room = 0;
		for (int i = 0; i < 21; i++){
			if(board.getCards().get(i).getCardType() == Card.CardType.WEAPON){weapon++;}
			else if(board.getCards().get(i).getCardType() == Card.CardType.ROOM){room++;}
			else if(board.getCards().get(i).getCardType() == Card.CardType.PERSON){people++;}
			
		}
		
		assertEquals(6,people);
		assertEquals(6,weapon);
		assertEquals(9,room);
		
		//Test that one of each card has the correct name associated with it
		boolean testRoom = false;
		boolean testPerson = false;
		boolean testWeapon = false;
		for (int i = 0; i < 21; i++) {
			if (board.getCards().get(i).getCardName().equals("Gett")) {
				testPerson = true;
			}
			else if (board.getCards().get(i).getCardName().equals("ComputerGame Room")) {
				testRoom = true;
			}
			else if (board.getCards().get(i).getCardName().equals("axe")) {
				testWeapon = true;
			}
		}
		assertTrue(testRoom && testWeapon && testPerson);
	}
	
	@Test
	public void dealingCardsTest() {
		//All cards should be dealt
		int counter = 0;
		for(Player p : board.getPlayers()) {
			counter += p.getMyCards().size();
		}
		assertEquals(counter, board.getCards().size());
		
		//All players have ROUGHLY the same number of cards
		double numCards = board.getCards().size();
		double numPlayers = board.getPlayers().size();
		boolean correctCardNumber = true;
		for(Player p : board.getPlayers()) {
			if (p.getMyCards().size() != (int)Math.floor(numCards/numPlayers) && p.getMyCards().size() != (int)Math.ceil(numCards/numPlayers)) {
				correctCardNumber = false;
			}
		}
		assertTrue(correctCardNumber);
		
		//No two players have the same card
		Map<String,Integer> duplicateCheck = new HashMap<String,Integer>();
		for(Player p:board.getPlayers()) {
			for (Card c:p.getMyCards()) {
				if(!duplicateCheck.containsKey(c.getCardName())){
				duplicateCheck.put(c.getCardName(), 1);
				}
				else {
					duplicateCheck.put(c.getCardName(), duplicateCheck.get(c.getCardName()) + 1);	
				}
			}
		}
		
		Set<String> keys = duplicateCheck.keySet();
		boolean noDuplicate = true;
		for (String x : keys){
			if(duplicateCheck.get(x) > 1){
				noDuplicate = false;			
			}
		}
		
		assertTrue(noDuplicate);
		
	}

}
