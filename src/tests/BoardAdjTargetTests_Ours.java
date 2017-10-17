package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests_Ours {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(1, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(1, 21);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(24, 13);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(22, 10);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(25, 0);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(2, 12);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY UP 
		Set<BoardCell> testList = board.getAdjList(17, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 19)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(19, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(10, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 1)));
		//TEST DOORWAY RIGHT
		testList = board.getAdjList(19, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(4, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 16)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(19, 15);
		assertTrue(testList.contains(board.getCellAt(19, 14)));
		assertTrue(testList.contains(board.getCellAt(19, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 21);
		assertTrue(testList.contains(board.getCellAt(4, 21)));
		assertTrue(testList.contains(board.getCellAt(6, 21)));
		assertTrue(testList.contains(board.getCellAt(5, 22)));
		assertTrue(testList.contains(board.getCellAt(5, 20)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(2, 4);
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(16, 20);
		assertTrue(testList.contains(board.getCellAt(17, 20)));
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		assertTrue(testList.contains(board.getCellAt(16, 21)));
		assertTrue(testList.contains(board.getCellAt(16, 19)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are BROWN on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// on corner piece near bathroom; two possible
		Set<BoardCell> testList = board.getAdjList(11, 19);
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(11, 18)));
		assertEquals(2, testList.size());
		
		//middle of walkway all four possible
		testList = board.getAdjList(22, 16);
		assertTrue(testList.contains(board.getCellAt(22, 17)));
		assertTrue(testList.contains(board.getCellAt(22, 15)));
		assertTrue(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(23, 16)));
		assertEquals(4, testList.size());

		// next to closet should only have three
		testList = board.getAdjList(14, 11);
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertEquals(3, testList.size());

		
		// Test next to one room and three walkways
		testList = board.getAdjList(3,8);
		assertTrue(testList.contains(board.getCellAt(2, 8)));
		assertTrue(testList.contains(board.getCellAt(4, 8)));
		assertTrue(testList.contains(board.getCellAt(3, 9)));
		assertTrue(testList.contains(board.getCellAt(3, 7)));
		assertEquals(4, testList.size());
		
		
		// corner, next to edge of the board
		testList = board.getAdjList(17, 0);
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(17, 1)));
		assertEquals(2, testList.size());
		
		// edge of board
		testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertEquals(3, testList.size());

	}
	
	//10/10/2017 12:06 PM WE SAID NEXT TIME********************************
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are PINK on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(0, 3, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 4)));
		assertTrue(targets.contains(board.getCellAt(1, 3)));	
		
		board.calcTargets(6, 12, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 11)));
		assertTrue(targets.contains(board.getCellAt(6, 13)));	
		assertTrue(targets.contains(board.getCellAt(5, 12)));			
	}
	
	

	
	// Tests of just walkways, 2 steps
	// These are PINK on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(15, 6, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		assertTrue(targets.contains(board.getCellAt(15, 8)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		
		board.calcTargets(0, 3, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 3)));
		assertTrue(targets.contains(board.getCellAt(1, 4)));		
	}
	
	// Tests of just walkways, 4 steps
	// These are PINK on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(6, 12, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 14)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 13)));
		assertTrue(targets.contains(board.getCellAt(5, 11)));
		assertTrue(targets.contains(board.getCellAt(6, 10)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are PINK on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(10, 17, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 17)));
		assertTrue(targets.contains(board.getCellAt(5, 16)));	
		assertTrue(targets.contains(board.getCellAt(6, 15)));	
		assertTrue(targets.contains(board.getCellAt(5, 17)));	
		assertTrue(targets.contains(board.getCellAt(6, 19)));	
		assertTrue(targets.contains(board.getCellAt(7, 20)));	
		assertTrue(targets.contains(board.getCellAt(8, 21)));
		assertTrue(targets.contains(board.getCellAt(11, 18)));
		assertTrue(targets.contains(board.getCellAt(10, 19)));
		assertTrue(targets.contains(board.getCellAt(9, 20)));
		assertTrue(targets.contains(board.getCellAt(8, 17)));
		assertTrue(targets.contains(board.getCellAt(7, 18)));
		assertTrue(targets.contains(board.getCellAt(8, 19)));
		assertTrue(targets.contains(board.getCellAt(9, 18)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 19)));		
	}	
	
	
	
	// Test getting into a room
	// These are PINK on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(11, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		
		//into Room
		assertTrue(targets.contains(board.getCellAt(10, 1)));

		//other options
		assertTrue(targets.contains(board.getCellAt(12, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are PINK on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(21, 5, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(13, targets.size());
		
		
		//two doors
		
		assertTrue(targets.contains(board.getCellAt(20, 4)));
		assertTrue(targets.contains(board.getCellAt(19, 4)));
		
	//other options
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(25, 5)));
		assertTrue(targets.contains(board.getCellAt(24, 6)));
		assertTrue(targets.contains(board.getCellAt(23, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 7)));
		assertTrue(targets.contains(board.getCellAt(22, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));
		assertTrue(targets.contains(board.getCellAt(23, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		
	}

	// Test getting out of a room
	// These are PINK on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 1, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall		
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 1)));
		
		
		// Take two steps
		board.calcTargets(4, 1, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 2)));
		assertTrue(targets.contains(board.getCellAt(5, 0)));
		assertTrue(targets.contains(board.getCellAt(6, 1)));
	}

}

