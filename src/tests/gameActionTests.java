package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

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
		fail("Not yet implemented");
	}
	@Test
	public void disproveSuggestiontest() {
		fail("Not yet implemented");
	}
	@Test
	public void handlingSuggestiontest() {
		fail("Not yet implemented");
	}
	@Test
	public void creatingSuggestiontest() {
		fail("Not yet implemented");
	}

}
