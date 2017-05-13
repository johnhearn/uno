package project;

import project.Card.Immutable;

@Immutable
public class SkipCard extends Card {

	public SkipCard(Colour colour) {
		super(colour);
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
