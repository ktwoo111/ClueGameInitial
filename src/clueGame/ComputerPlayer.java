package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell previousLocation;
	
	public ComputerPlayer(String playerName, int row, int column, String color) {
		super(playerName,row,column,color);
		previousLocation = new BoardCell(row, column, "W");
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardCell pickLocation(Set<BoardCell> targets){
		return null;	
	}
	public void makeAccusation(){
		
		//TODO
	}
	public void createSuggestion(){
		//TODO
	}

	public BoardCell getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(BoardCell previousLocation) {
		this.previousLocation = previousLocation;
	}
	
}
