/**
 * @author John Baker and Taewoo Kim
 * Class for the Clue Board
 */
package clueGame;

import java.awt.Graphics;
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

import javax.swing.JPanel;

import clueGame.BoardCell;
import clueGame.Card.CardType;

public class Board {
	/**
	 * 
	 */

	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50; //change later later!!!!!!!!!
	private BoardCell[][] board;
	private Map<Character,String> legend; // has room information
	private Map<BoardCell,Set<BoardCell>> adjMatrix;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> targets;
	private int currentPlayer = 0;
	private int dieVal = 0;
	private BoardCell selectedLocation; //11/21/2017: not initializing this to new BoardCell(0,0,"Q") seems to work
	private ArrayList<Player> players; // arraylist to put in human and computer players
	private ArrayList<Card> cards; // array list of all the cards
	private Solution theAnswer;
	private boolean targetSelected = false;
	private boolean isSubmitted = false;
	private Solution currentSuggestion = new Solution("NULL","NULL","NULL");
	private boolean firstTime = true;
	private Solution currentAccusation = new Solution("NULL","NULL","NULL");
	private boolean lastSuggestionDisproven = true;



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
		setAnswer();
		shuffleAndDealCards();
		calcAdjacencies();

		//rolling die and calcTarget to just to start off the game
		rollDie();
		calcTargets(players.get(currentPlayer).getRow(),players.get(currentPlayer).getColumn(),dieVal);

	}
	private void setAnswer() {
		Card room = new Card("null", "weapon");
		Card weapon = new Card("null", "person");
		Card person = new Card("null", "room");
		Random rand = new Random();

		while (room.getCardType() != CardType.ROOM) {
			room = cards.get(rand.nextInt(cards.size()));
		}
		while (weapon.getCardType() != CardType.WEAPON) {
			weapon = cards.get(rand.nextInt(cards.size()));
		}
		while (person.getCardType() != CardType.PERSON) {
			person = cards.get(rand.nextInt(cards.size()));
		}
		theAnswer = new Solution(person.getCardName(),room.getCardName(),weapon.getCardName());
		

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
		
		ArrayList<Card> deckWithOutSolutionCards = new ArrayList<Card>();
		for (Card c : cards) {
			if(c.getCardName() != theAnswer.person && c.getCardName() != theAnswer.room && c.getCardName() != theAnswer.weapon) {
			deckWithOutSolutionCards.add(c);
			}
		}

		double numCards = deckWithOutSolutionCards.size();
		double numPlayers = players.size();
		int mostNum = (int)Math.floor(numCards/numPlayers);
		int lastNum = (int)Math.ceil(numCards/numPlayers);


		int counter = 0;
		for(int i = 0; i < players.size(); i++){
			if(i  < numCards%numPlayers){
				for(int j = 0; j < lastNum; j++){		
					players.get(i).getMyCards().add(deckWithOutSolutionCards.get(counter));
					counter++;				
				}
			}
			else {
				for(int j = 0; j < mostNum; j++){

					players.get(i).getMyCards().add(deckWithOutSolutionCards.get(counter));
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
	
	public void updateSeenCardForAllPlayers(Card seen) {
		for (Player p: players) {
			p.updateSeenCards(seen);			
		}
		
	}
	/**
	 * 
	 * @param suggestion the suggestion being handled
	 * @param accuser the person that is making the suggestion
	 * @param playersList the list of player, needed for tests
	 * @return returns either null or the card that disproves the suggestion
	 */
	public Card handleSuggestion(Solution suggestion, Player accuser, ArrayList<Player> playersList){
		ArrayList<Card> disproveCardsComputer = new ArrayList<Card>();
		ArrayList<Card> disproveCardsHuman = new ArrayList<Card>();
		boolean playerHasCard = false;
		boolean computerHasCard = false;
		for(Player p : playersList){
			if (p == playersList.get(0)) {
				Card playerCard = p.disproveSuggestion(suggestion);
				if (!p.equals(accuser)){
					disproveCardsHuman.add(playerCard);
					playerHasCard = true;
				}
			}
			else {
				Card card = p.disproveSuggestion(suggestion);
				if (card != null && !p.equals(accuser)) {
					disproveCardsComputer.add(card);
					computerHasCard = true;
				}
			}

		}
		if (disproveCardsHuman.size() == 0 && disproveCardsComputer.size() == 0) {
			return null;
		}
		else if (playerHasCard && !computerHasCard) {
			return disproveCardsHuman.get(0);
		}
		else if  (!playerHasCard && computerHasCard){
			return disproveCardsComputer.get(0);
		}
		else if (playerHasCard && computerHasCard) {
			return disproveCardsComputer.get(0);
		}
		else {
			return null;
		}
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


	public void rollDie(){
		Random rand = new Random();
		dieVal = rand.nextInt(6)+1;

	}

	public void setCurrentSuggestion(Solution suggestion){
		 currentSuggestion = suggestion;
	}
	public Solution getCurrentSuggestion(){
		 return currentSuggestion;
	}
	
	
	public void makeMove(){ // for the players				
		if(currentPlayer == 0){
			players.get(currentPlayer).changeCurrentLocation(selectedLocation.getRow(),selectedLocation.getColumn());
			targetSelected = false;

		}
		else {
			//gets the place where computer player wants to go
			BoardCell destination = players.get(currentPlayer).pickLocation(targets, getCellAt(players.get(currentPlayer).getRow(),players.get(currentPlayer).getColumn()).getInitial());
			//assigns new current location with destination's row and columns
			players.get(currentPlayer).changeCurrentLocation(destination.getRow(),destination.getColumn());
		}

	}
	


	/////////////////////////////////////////////////below stuff is purely for testing
	public ArrayList<Player> getPlayers() {
		return players;
	}


	public ArrayList<Card> getCards() {
		return cards;
	}
	public Solution getAnswer() {
		return theAnswer;
	}


	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public void increaseCurrentPlayer() {
		currentPlayer++;
		if(currentPlayer == players.size()){
			currentPlayer = 0;
		}
		
	}
	public int getDieVal() {
		return dieVal;
	}
	
	

	public BoardCell getSelectedLocation() {
		return selectedLocation;
	}
	public void setSelectedLocation(BoardCell selectedLocation) {
		this.selectedLocation = selectedLocation;
	}
	public boolean isTargetSelected() {
		return targetSelected;
	}
	public void setTargetSelected(boolean targetSelected) {
		this.targetSelected = targetSelected;
	}
	public boolean isSubmitted() {
		return isSubmitted;
	}
	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	public Solution getCurrentAccusation() {
		return currentAccusation;
	}
	public void setCurrentAccusation(Solution currentAccusation) {
		this.currentAccusation = currentAccusation;
	}
	public boolean isLastSuggestionDisproven() {
		return lastSuggestionDisproven;
	}
	public void setLastSuggestionDisproven(boolean lastSuggestionDisproven) {
		this.lastSuggestionDisproven = lastSuggestionDisproven;
	}
	
	




}
