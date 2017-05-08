package project;

public class DrawTwoCard extends Card {

	public DrawTwoCard(Colour colour) {
		super(-4, colour);
	}

	@Override
	public int nextStep(int currentStep) {
		return currentStep*2;
	}
	
	@Override
	public String toString() {
		return "Draw Two " + colour.name();
	}
	
	@Override
	public int points() {
		return 20;
	}
}
