package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char previousLocation;
	
	public ComputerPlayer(String playerName, int row, int column, String color) {
		super(playerName,row,column,color);
		previousLocation = 'Z'; // just a random non room character
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public BoardCell pickLocation(Set<BoardCell> targets, char currentLocation){
		ArrayList<BoardCell> targetsList = new ArrayList<BoardCell>(targets);
		if(currentLocation != 'W' && currentLocation != 'X'){
		previousLocation = currentLocation;
		}
		boolean containsRoom = false;
		boolean prevRoomExistsInList = false;
		BoardCell chosenTarget = new BoardCell(0,0,"W"); // just random initialization of boardcell
		for (BoardCell t : targetsList) {
			if (t.getInitial() != 'W') {containsRoom = true;}
			if (t.getInitial() == previousLocation) {prevRoomExistsInList = true;}
		}
		//if no room or there's a previous room is in list, go anywhere 
		if (containsRoom == false || prevRoomExistsInList == true ) {
			Random rand = new Random();
			int n = rand.nextInt(targets.size());
			chosenTarget = targetsList.get(n);
		}
		//if a visited room exist in list, go anywhere but there		
		else {
			for (BoardCell t : targetsList) {
				if (t.getInitial() != 'W') {chosenTarget = t;}
			}
		}
		return chosenTarget;
	}
	public void makeAccusation(){
		
		//TODO
	}
	public void createSuggestion(){
		//TODO
	}

	public char getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(char previousLocation) {
		this.previousLocation = previousLocation;
	}
	
}
