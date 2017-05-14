package uno.model;

import uno.model.Card.Immutable;

@Immutable
public class ReverseCard extends Card {
	
	public ReverseCard(Colour colour) {
		super(colour);
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
