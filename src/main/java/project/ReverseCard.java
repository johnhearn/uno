package project;

public class ReverseCard extends Card {
	
	public ReverseCard(Colour colour) {
		super(-2, colour);
	}

	@Override
	public boolean canBePlayedOn(Card topCard) {
		return topCard.colour == colour;
	}

	@Override
	public int nextStep(int currentStep) {
		return -currentStep;
	}
	
	@Override
	public String toString() {
		return "Reverse " + colour.name();
	}
	
	@Override
	public int points() {
		return 20;
	}
}
