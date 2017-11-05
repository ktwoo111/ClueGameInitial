package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;


	public Player(String playerName, int row, int column, String color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = convertColor(color);
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
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
	

	public Card disproveSuggestion(Solution suggestion){
		
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		for(Card c: getMyCards()){
			if(c.getCardName().equals(suggestion.person) || c.getCardName().equals(suggestion.room) || c.getCardName().equals(suggestion.weapon)){
				matchingCards.add(c);
			}

		}

		if(matchingCards.size() == 1){
			return matchingCards.get(0);
		}
		else if (matchingCards.size() > 1){
			Random rand = new Random();
			return matchingCards.get(rand.nextInt(matchingCards.size()));
		}
		else{
			return null;
		}	
		
		
	}
	
	
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

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	

}
