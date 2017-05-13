package project;

public class WildCard extends Card {

	public WildCard() {
		super(null);
	}

	@Override
	public boolean canBePlayedOn(Card topCard) {
		return true;
	}
	
	@Override
	public String toString() {
		return "Wild" + ((colour == null) ? "" : " " + colour.name());
	}
	
	public WildCard withColour(Colour colour) {
		this.colour = colour;
		return this;
	}
	
	public boolean isDeclared() {
		return colour != null;
	}
	
	@Override
	public int points() {
		return 50;
	}
	
	@Override
	public boolean equals(Object obj) {
		// Ignore colour
		return obj != null && getClass() == obj.getClass();
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
