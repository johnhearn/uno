package uno.model;

public class WildFourCard extends WildCard {

	@Override
	public int nextStep(int currentStep) {
		return currentStep*2;
	}
	
	@Override
	public String toString() {
		return "Wild Four" + ((colour == null) ? "" : " " + colour.name());
	}
}
