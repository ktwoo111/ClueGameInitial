package clueGame;

public class Card {
	private String cardName;
	public enum CardType {PERSON,WEAPON,ROOM};
	private CardType cardType;
	
	public Card(String name, String type){
		cardName = name;
		if(type == "person"){cardType = CardType.PERSON;}
		else if (type == "weapon"){cardType = CardType.WEAPON;}
		else if (type == "room"){cardType = CardType.ROOM;}
	}
	
	
	public boolean equals(){
		//TODO:
		
		return true;
	}
	
	public CardType getCardType(){
		return cardType;
		
	}
	
	public String getCardName(){
		return cardName;
		
	}

}
