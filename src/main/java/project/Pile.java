package project;

public class Pile extends CardHolder {

	public void addCard(Card card) {
		cards.add(card);
	}

	public Card topCard() {
		return cards.get(cards.size()-1);
	}
	
	@Override
	public String toString() {
		return topCard().toString();
	}
}
