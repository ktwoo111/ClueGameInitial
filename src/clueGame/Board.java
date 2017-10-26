/**
 * @author John Baker and Taewoo Kim
 * Class for the Clue Board
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50; //change later later!!!!!!!!!
	private BoardCell[][] board;
	private Map<Character,String> legend;
	private Map<BoardCell,Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> targets;


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
	 * @throws IOException 
	 * @throws BadConfigFormatException 
	 */
	public void initialize() {

		legend = new HashMap<Character,String>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e1) {
			System.out.println(e1);
			System.out.println(e1.getMessage());
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		//System.out.println("DONE3");

		calcAdjacencies();

	}
	/**
	 * Loads the rooms for the board
	 * @throws BadConfigFormatException 
	 * @throws IOException 
	 */
	public void loadRoomConfig() throws BadConfigFormatException{
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
				if (parts.length != 3) {
					throw new BadConfigFormatException();
				}
				if (!parts[2].trim().equalsIgnoreCase("Card") && !parts[2].trim().equalsIgnoreCase("Other")){
					throw new BadConfigFormatException(parts[2].trim());
				}
				legend.put(parts[0].charAt(0), parts[1].trim());
				reader.close();
			}		
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
	/**
	 * Loads the configuration of the board
	 * @throws BadConfigFormatException 
	 */
	public void loadBoardConfig() throws BadConfigFormatException{
		try {
			FileReader reader1 = new FileReader(boardConfigFile);
			Scanner in1 = new Scanner(reader1);
			String[] parts = in1.nextLine().split(",");
			numColumns = parts.length;
			int counter = 1;
			while(in1.hasNextLine()){
				in1.nextLine();
				counter++;
			}	
			reader1.close();
			numRows = counter;
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			String input = "";
			int i = 0; // is the row
			while(in.hasNextLine()){
				if (i >= numRows) {
					throw new BadConfigFormatException();
				}
				input = in.nextLine();
				if (input == ""){
					break;
				}
				String[] parts = input.split(",");
				if (parts.length != numColumns) {
					throw new BadConfigFormatException();
				}
				if (!legend.containsKey(parts[0].charAt(0))){
					throw new BadConfigFormatException(parts[0]);
				}
				for(int j = 0; j < numColumns; j++){ // j is the column
					board[i][j] = new BoardCell(i,j,parts[j]);
				}
				i++;
			}	
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist.");
		} catch (IOException e) {		
			e.printStackTrace();
		}

	}
	/**
	 * calculates the adjacent squares for a given cell
	 */
	public void calcAdjacencies(){
		adjMatrix = new HashMap<BoardCell,Set<BoardCell>>();
		for (int i = 0; i < numRows; i++){ //row
			for (int j = 0; j < numColumns; j++){ //column
				adjMatrix.put(board[i][j], new HashSet<BoardCell>());
				if (board[i][j].isWalkway()) {
					if(i -1 >= 0 && (board[i-1][j].getDoorDirection() == DoorDirection.DOWN || board[i-1][j].isWalkway())){			
						adjMatrix.get(board[i][j]).add(board[i-1][j]);
					}
					if(i+1 < numRows && (board[i+1][j].getDoorDirection() == DoorDirection.UP || board[i+1][j].isWalkway())){
						adjMatrix.get(board[i][j]).add(board[i+1][j]);

					}
					if(j -1 >= 0 && (board[i][j-1].getDoorDirection() == DoorDirection.RIGHT || board[i][j-1].isWalkway())){
						adjMatrix.get(board[i][j]).add(board[i][j-1]);

					}
					if(j +1 < numColumns && (board[i][j+1].getDoorDirection() == DoorDirection.LEFT || board[i][j+1].isWalkway())){
						adjMatrix.get(board[i][j]).add(board[i][j+1]);					
					}
				}
				else if (board[i][j].isDoorway()) {
					if (board[i][j].getDoorDirection() == DoorDirection.RIGHT) {
						adjMatrix.get(board[i][j]).add(board[i][j+1]);
					}
					else if (board[i][j].getDoorDirection() == DoorDirection.LEFT) {
						adjMatrix.get(board[i][j]).add(board[i][j-1]);
					}
					else if (board[i][j].getDoorDirection() == DoorDirection.UP) {
						adjMatrix.get(board[i][j]).add(board[i-1][j]);
					}
					else if (board[i][j].getDoorDirection() == DoorDirection.DOWN) {
						adjMatrix.get(board[i][j]).add(board[i+1][j]);
					}
				}
			}

		}

	}
	/**
	 * calculates the targets for a roll
	 * @param startcell Current cell
	 * @param pathLength Number of squares left to move
	 */

	public void calcTargets(int i, int j, int pathLength){
		//targets.clear();
		Set<BoardCell> visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		findAllTargets(board[i][j], pathLength, visited, targets);
		targets.remove(board[i][j]);

	}

	private void findAllTargets(BoardCell startCell, int pathLength, Set<BoardCell> visited, Set<BoardCell> targets){
		visited.add(startCell);
		for(BoardCell cell : adjMatrix.get(startCell)){
			if(!visited.contains(cell)){
				visited.add(cell);
				if(pathLength == 1 || cell.isDoorway()){
					targets.add(cell);
					
				}
				else {
					findAllTargets(cell, pathLength-1, visited, targets);
				}
			
				visited.remove(cell);
			}
			
		
		}

	}

	public Set<BoardCell> getTargets() {
		return targets;
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
	public Map<Character, String> getLegend() {
		return legend;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public int getNumRows() {

		return numRows;
	}
	public int getNumColumns() {

		return numColumns;
	}
	public Set<BoardCell> getAdjList(int i, int j) {

		return adjMatrix.get(board[i][j]);
	}


	
	public static void main(String args[]){
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
		board.calcTargets(6, 12, 4);
		System.out.println(board.getTargets().size());
	for (BoardCell x : board.getTargets()){
			System.out.println(x);
		}	
		
		
		
	}
	
	
}
