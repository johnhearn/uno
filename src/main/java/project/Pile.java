package project;

public class Pile extends CardHolder {

	public void addCard(Card card) {
		cards.add(card);
	}

	public Card topCard() {
		return cards.get(cards.size()-1);
	}

	public Card pickupCard() {
		return cards.remove(cards.size()-1);
	}
	
}
