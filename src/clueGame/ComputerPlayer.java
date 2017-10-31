package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {

	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName,row,column,color);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		//TODO
		return null;		
	}
	public void makeAccusation(){
		
		//TODO
	}
	public void createSuggestion(){
		//TODO
	}
	
}
