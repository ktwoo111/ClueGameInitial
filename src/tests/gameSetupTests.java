package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		//TODO
	}
	
	@Test
	public void dealingCardsTest() {
		//TODO
	}

}
