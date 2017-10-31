package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;

public abstract class Player {
	public String playerName;
	public int row;
	public int column;
	public Color color;

	public Player(String playerName, int row, int column, String color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = convertColor(color);
	}

	private Color convertColor(String strColor){
		Color color;
		try{
			Field field =Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
			
		}
		catch(Exception e)
		{ color = null;}
		return color;
		
	}
	

	public abstract Card disproveSuggestion(Solution suggestion);
	
	
	////////////////////////below stuff is purely for testing
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	

}
