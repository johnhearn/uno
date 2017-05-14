package uno.model;

import uno.model.Card.Immutable;

@Immutable
public class NumberCard extends Card {

	private final int number;

	public NumberCard(int number, Colour colour) {
		super(colour);
		this.number = number;
	}

	public boolean canBePlayedOn(Card topCard) {
		return topCard.colour == colour || (topCard instanceof NumberCard && ((NumberCard) topCard).number == number);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && number == ((NumberCard) obj).number;
	}

	@Override
	public int hashCode() {
		return super.hashCode()*37 + number;
	}
	
	@Override
	public String toString() {
		return colour.name() + " " + number;
	}

	public int points() {
		return number;
	}
}
