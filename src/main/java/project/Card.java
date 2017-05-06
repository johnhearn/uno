package project;

public class Card {
	
	public enum Colour {
		GREEN,
		YELLOW,
		RED,
		BLUE
	}
	
	private final int number;
	private final Colour colour;

	public Card(int number, Colour colour) {
		super();
		this.number = number;
		this.colour = colour;
	}

	public boolean canBePlayedOn(Card topCard) {
		return topCard.number == number || topCard.colour == colour;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Card) && number == ((Card)obj).number && colour == ((Card)obj).colour;
	}
	
	@Override
	public String toString() {
		return colour.name() + " " + number;
	}
}
