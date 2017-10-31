package clueGame;

import java.awt.Color;

public abstract class Player {
	public String playerName;
	public int row;
	public int column;
	public Color color;

	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}


	public abstract Card disproveSuggestion(Solution suggestion);

}
