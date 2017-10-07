/**
 * @author John Baker and Taewoo Kim
 * Class for the Clue Board
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

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
		
		legend = new HashMap<Character,String>();
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			String[] parts = in.nextLine().split(",");
			numColumns = parts.length;
			int counter = 1;
			while(in.hasNextLine()){
				in.nextLine();
				counter++;
			}		
			numRows = counter;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		board = new BoardCell[numRows][numColumns];
		System.out.println(numColumns );
		loadRoomConfig();
		loadBoardConfig();
		//System.out.println("DONE3");
		
	}
	/**
	 * Loads the rooms for the board
	 */
	public void loadRoomConfig(){
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);
			String input = "";
			while(in.hasNextLine()){
				input = in.nextLine();
				if (input == ""){
					break;
				}
				String[] parts = input.split(",");
				//System.out.println(parts[0].charAt(0));
				//System.out.println(parts[1]);				
				legend.put(parts[0].charAt(0), parts[1].trim());
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * Loads the configuration of the board
	 */
	public void loadBoardConfig(){
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			String input = "";
			int i = 0; // is the row
			while(in.hasNextLine()){
				input = in.nextLine();
				if (input == ""){
					break;
				}
				String[] parts = input.split(",");
				for(int j = 0; j < numColumns; j++){ // j is the column
					board[i][j] = new BoardCell(i,j,parts[j]);
				}
				i++;
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * calculates the adjacent squares for a given cell
	 */
	public void calcAdjacencies(){
		for (int i = 0; i < numRows; i++){ //row
			for (int j = 0; j < numColumns; j++){ //column
				adjMatrix.put(board[i][j], new HashSet<BoardCell>());
				if(i -1 >= 0){			
					adjMatrix.get(board[i][j]).add(board[i-1][j]);
				}
				if(i+1 < numRows){
					adjMatrix.get(board[i][j]).add(board[i+1][j]);

				}
				if(j -1 >= 0){
					adjMatrix.get(board[i][j]).add(board[i][j-1]);

				}
				if(j +1 < numColumns){
					adjMatrix.get(board[i][j]).add(board[i][j+1]);					
				}			
			}

		}

	}
	/**
	 * calculates the targets for a roll
	 * @param startcell Current cell
	 * @param pathLength Number of squares left to move
	 */
	
	public void calcTargets(BoardCell startCell, int pathLength){
		HashSet<BoardCell> visited = new HashSet<BoardCell>();
		HashSet<BoardCell> targets = new HashSet<BoardCell>();
		findAllTargets(startCell, pathLength, visited, targets);
		targets.remove(startCell);
	}

	public void findAllTargets(BoardCell startCell, int pathLength, HashSet<BoardCell> visited, HashSet<BoardCell> targets){
		visited.add(startCell);

		for(BoardCell cell : adjMatrix.get(startCell)){
			if(!visited.contains(cell)){
				visited.add(cell);
				if(pathLength == 1){
					targets.add(cell);
				}
				else {
					findAllTargets(cell, pathLength-1, visited, targets);
				}
			}
			visited.remove(cell);
		}

	}
	/**
	 * loads the information from the configuration files
	 * @param csv layout file
	 * @param txt tegend file
	 */
	public void setConfigFiles(String csv, String txt){		
			roomConfigFile = txt;
			boardConfigFile = csv;
	}
	/**
	 * 
	 * @return legend HashMap containing the character symbols and names of rooms
	 */
	public HashMap<Character, String> getLegend() {
		return legend;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public int getNumRows() {
		
		return numRows;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	
	public static void main(String args[]){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
		System.out.println("DONE OVER ALL");
	}




}
