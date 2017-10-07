/**
 * @author John Baker and Taewoo Kim
 * Class for the Clue Board
 */
package clueGame;

import java.util.HashMap;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 100; //change later later!!!!!!!!!
	private BoardCell[][] board;
	private HashMap<Character,String> legend;
	private HashMap<BoardCell,Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;


	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/**
	 * initialize method to create the board and set up values
	 */
	public void initialize(){


	}
	/**
	 * Loads the rooms for the board
	 */
	public void loadRoomConfig(){

	}
	/**
	 * Loads the configuration of the board
	 */
	public void loadBoardConfig(){


	}
	/**
	 * calculates the adjacent squares for a given cell
	 */
	public void calcAdjacencies(){


	}
	/**
	 * calculates the targets for a roll
	 * @param cell Current cell
	 * @param pathLength Number of squares left to move
	 */
	public void calcTargets(BoardCell cell, int pathLength){


	}
	/**
	 * loads the information from the configuration files
	 * @param csv layout file
	 * @param txt tegend file
	 */
	public void setConfigFiles(String csv, String txt){


	}
	/**
	 * 
	 * @return legend HashMap containing the character symbols and names of rooms
	 */
	public HashMap<Character, String> getLegend() {
		return legend;
	}
	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}




}
