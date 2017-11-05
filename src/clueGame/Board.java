/**
 * @author John Baker and Taewoo Kim
 * Class for the Clue Board
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import clueGame.BoardCell;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50; //change later later!!!!!!!!!
	private BoardCell[][] board;
	private Map<Character,String> legend; // has room information
	private Map<BoardCell,Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> targets;
	private ArrayList<Player> players; // arraylist to put in human and computer players
	private ArrayList<Card> cards; // array list of all the cards
	private Solution theAnswer;


	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * initialize method to create the board and set up values, loads players, cards and deals cards
	 * @throws IOException 
	 * @throws BadConfigFormatException 
	 */
	public void initialize() {
		theAnswer = new Solution("Matt", "Dining Room", "axe");
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

		try {
			loadPlayerConfigFile("PlayerConfig.txt");
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}

		loadCardConfigFile("Weapons.txt");
		shuffleAndDealCards();
		setSolution();
		calcAdjacencies();

	}
	private void setSolution() {
		// TODO MAYBE NEED THIS OR NOT
		
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
	 * @param txt legend file
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
	/**
	 * randomizes deck and deals out the cards
	 */
	private void shuffleAndDealCards() {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
		
		double numCards = cards.size();
		double numPlayers = players.size();
		int mostNum = (int)Math.floor(numCards/numPlayers);
		int lastNum = (int)Math.ceil(numCards/numPlayers);
		
	
		int counter = 0;
		for(int i = 0; i < players.size(); i++){
			if(i  < numCards%numPlayers){
				for(int j = 0; j < lastNum; j++){		
					players.get(i).getMyCards().add(cards.get(counter));
					counter++;				
				}
			}
			else {
				for(int j = 0; j < mostNum; j++){
				
					players.get(i).getMyCards().add(cards.get(counter));
					counter++;			
				}
			}
		}
	}
	/**
	 * 
	 * @param file the file that contains player imformation
	 * @throws BadConfigFormatException
	 */
	private void loadPlayerConfigFile(String file) throws BadConfigFormatException{
		players = new ArrayList<Player>();
		try {
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);
			String input = "";
			boolean human = true;
			while(in.hasNextLine()){
				input = in.nextLine();
				if (input == ""){
					break;
				}
				String[] parts = input.split(",");
				if (parts.length != 4) {
					throw new BadConfigFormatException();
				}

				if(human){
					players.add(new HumanPlayer(parts[0].trim(), Integer.parseInt(parts[1].trim()),Integer.parseInt(parts[2].trim()),parts[3].trim()));				
					human = false;
				}
				else {
					players.add(new ComputerPlayer(parts[0].trim(), Integer.parseInt(parts[1].trim()),Integer.parseInt(parts[2].trim()),parts[3].trim()));						
				}

				reader.close();

			}		
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	/**
	 * 
	 * @param file the file that contains the card information
	 */
	private void loadCardConfigFile(String file){
		 	
		cards = new ArrayList<Card>();
		//WEAPONS
		try {
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);
			String input = "";
			while(in.hasNextLine()){
				input = in.nextLine();
				if (input == ""){
					break;
				}

				cards.add(new Card(input,"weapon"));
				reader.close();

			}		
		} catch (FileNotFoundException e) {
			System.out.println("The file doesn't exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ROOM from legend arraylist
		Set<Character> keys = legend.keySet();
		
		for(Character c : keys){
			if(!(legend.get(c).equals("Closet") || legend.get(c).equals("Walkway"))){ //closet and walkway are not rooms
			cards.add(new Card(legend.get(c),"room"));
			}
		}
		
		//PEOPLE
		for(Player p : players){
			cards.add(new Card(p.getPlayerName(),"person"));
		}
	}	
	public void selectAnswer(){
		//TODO

	}
	public Card handleSuggestion(){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @param accusation -> input should be (new Solution(person, room, weapon); create the argument on the spot
	 * @return
	 */
	public boolean checkAccusation(Solution accusation){
		
		if(accusation.equals(theAnswer)){
			return true;
		}
		else {
			return false;
			
		}
	}



	/////////////////////////////////////////////////below stuff is purely for testing
	public ArrayList<Player> getPlayers() {
		return players;
	}
	

	public ArrayList<Card> getCards() {
		return cards;
	}
	public Solution getSolution() {
		return theAnswer;
	}
	public static void main(String args[]){
		
		Board board = Board.getInstance();
		board.setConfigFiles("Our_ClueLayout.csv", "Our_ClueLegend.txt");		
		board.initialize();
       
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
			else if (target == board.getCellAt(16, 15)) {
				optionFour++;
			}
		}
		System.out.println(optionOne);

		System.out.println(optionTwo);
		System.out.println(optionThree);
		System.out.println(optionFour);
		

	}




}
