package project;

public class SkipCard extends Card {

	public SkipCard(Colour colour) {
		super(-3, colour);
	}

	@Override
	public boolean canBePlayedOn(Card topCard) {
		return topCard.colour == colour;
	}

	@Override
	public int nextStep(int currentStep) {
		return currentStep*2;
	}
	
	@Override
	public String toString() {
		return "Skip " + colour.name();
	}
	
	@Override
	public int points() {
		return 20;
	}
}
