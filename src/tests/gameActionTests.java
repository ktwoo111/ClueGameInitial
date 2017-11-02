package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();

	}

	@Before
	public void setUp() throws Exception {



	}

	@Test
	public void selectTargetLocationtest() {
		//choosing randomly if no room
		ComputerPlayer compPlayer = new ComputerPlayer("Thomas", 16, 17,"black");
		int row = compPlayer.getRow(); // 4th player which is computer
		int column = compPlayer.getColumn();
		board.calcTargets(row, column, 1);
		int optionOne = 0;
		int optionTwo = 0;
		int optionThree = 0;
		int optionFour = 0;
		for (int i = 0; i < 100; i++) {
			BoardCell target = compPlayer.pickLocation(board.getTargets(), 'W');
			if (target == board.getCellAt(15, 17)) {
				optionOne++;
			}
			else if (target == board.getCellAt(16, 18)) {
				optionTwo++;
			}
			else if (target == board.getCellAt(17, 17)) {
				optionThree++;
			}
			else if (target == board.getCellAt(16, 16)) {
				optionFour++;
			}
		}
		assertTrue((optionOne>0) && (optionTwo>0) && (optionThree > 0) && (optionFour > 0));

		//Test target with room in target list and previous location not being a room
		board.calcTargets(row, column, 3);
		BoardCell target = compPlayer.pickLocation(board.getTargets(), 'W');
		assertEquals(target, board.getCellAt(17, 19));

		//Test target with room in target list and previous location being a room
		board.calcTargets(row, column, 3);
		BoardCell targetTwo = compPlayer.pickLocation(board.getTargets(), 'G');
		assertTrue(!(targetTwo.equals(board.getAdjList(17, 19))));






	}

	@Test
	public void checkAccusationtest() {
		//"Matt", "Dining Room", "axe" is the solution
		//Correct Accusation test
		assertTrue(board.checkAccusation(board.getSolution()));

		//Solution with wrong person
		assertTrue(!(board.checkAccusation(new Solution("Gett", "Dining Room", "axe" ))));

		//Solution with wrong weapon
		assertTrue(!(board.checkAccusation(new Solution("Matt", "Dining Room", "wire" ))));

		//Solution with wrong room
		assertTrue(!(board.checkAccusation(new Solution("Matt", "ComputerGame Room", "axe" ))));
	}

	@Test
	public void creatingSuggestiontest() {
		ComputerPlayer test = new ComputerPlayer("Thomas", 19, 8,"black");

		//making sure suggestion is in the same place as player
		assertEquals("Library", test.createSuggestion(board.getCards(),"Library").room);

		// if only one person not seen, choose that one.
		test.getSeenCards().add(new Card("Gett","person"));
		test.getSeenCards().add(new Card("Connor","person"));
		test.getSeenCards().add(new Card("Eddie","person"));
		test.getSeenCards().add(new Card("Matt","person"));
		test.getMyCards().add(new Card("Thomas","person"));

		//should only pop out Liz as answer
		assertEquals("Liz", test.createSuggestion(board.getCards(),"Library").person);


		// if only one weapon not seen, choose that one.
		test.getSeenCards().add(new Card("axe","weapon"));
		test.getSeenCards().add(new Card("gun","weapon"));
		test.getSeenCards().add(new Card("textbook","weapon"));

		test.getMyCards().add(new Card("wire","weapon"));
		test.getMyCards().add(new Card("pillow","weapon"));

		//should pop out knife
		assertEquals("knife", test.createSuggestion(board.getCards(),"Library").weapon);

		test.getSeenCards().clear();
		test.getMyCards().clear();

		test.getSeenCards().add(new Card("axe","weapon"));
		test.getSeenCards().add(new Card("gun","weapon"));
		test.getSeenCards().add(new Card("Gett","person"));
		test.getSeenCards().add(new Card("Connor","person"));

		//if multiple weapons not seen, choose random
		for(int i = 0; i < 100; i++){
			assertTrue(!test.createSuggestion(board.getCards(), "Library").weapon.equals("axe"));
			assertTrue(!test.createSuggestion(board.getCards(), "Library").weapon.equals("gun"));
		}

		//if multiple people not seen, choose random
		for (int i = 0; i < 100; i++){
			assertTrue(!test.createSuggestion(board.getCards(), "Library").person.equals("Gett"));
			assertTrue(!test.createSuggestion(board.getCards(), "Library").person.equals("Connor"));
		}









	}
	@Test
	public void disproveSuggestiontest() {
		fail("Not yet implemented");
	}
	@Test
	public void handlingSuggestiontest() {
		fail("Not yet implemented");
	}


}
