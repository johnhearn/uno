package project;

public class Player extends CardHolder {
	
	public void giveCard(Card card) {
		cards.add(card);
	}

	public Card playCard(Card topCard) {
		return cards.remove(0);
	}
}
