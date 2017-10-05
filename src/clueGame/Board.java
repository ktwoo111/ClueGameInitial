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
	
	public void initialize(){
		
		
	}
	
	public void loadRoomConfig(){
		
	}
	
	public void loadBoardConfig(){
		
		
	}
	
	public void calcAdjacencies(){
		
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength){
		
		
	}
	
	public void setConfigFiles(String csv, String txt){
		
		
	}
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
