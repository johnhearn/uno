package project;

public class WildCard extends Card {

	public WildCard() {
		super(-1, null);
	}
	
	@Override
	public boolean canBePlayedOn(Card topCard) {
		return true;
	}
	
	@Override
	public String toString() {
		return "Wild";
	}
}
