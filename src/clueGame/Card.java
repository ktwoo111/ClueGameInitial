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
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		return true;
	}


	public CardType getCardType(){
		return cardType;
		
	}
	
	public String getCardName(){
		return cardName;
		
	}

}
