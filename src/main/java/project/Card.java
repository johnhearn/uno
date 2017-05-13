package project;

public abstract class Card {

	@interface Immutable {
	}

	public enum Colour {
		GREEN, YELLOW, RED, BLUE
	}

	protected Colour colour;

	public Card(Colour colour) {
		super();
		this.colour = colour;
	}

	public boolean canBePlayedOn(Card topCard) {
		return topCard.getClass() == getClass() || topCard.colour == colour;
	}

	public int nextStep(int currentStep) {
		return currentStep;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Card rhs = (Card) obj;
		return colour == rhs.colour;
	}

	@Override
	public int hashCode() {
		int hashCode = getClass().hashCode();
		hashCode = 37 * hashCode + ((colour == null) ? 0 : colour.hashCode());
		return hashCode;
	}

	public abstract int points();
}
