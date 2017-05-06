package project;

public class DrawTwoCard extends SkipCard {

	public DrawTwoCard(Colour colour) {
		super(colour);
	}

	@Override
	public String toString() {
		return "Draw Two " + colour.name();
	}
}
