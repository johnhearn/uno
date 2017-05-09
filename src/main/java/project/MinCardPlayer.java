package project;

public class MinCardPlayer extends Player {

	public Card playCard(Card topCard) {
		Card min = null;
		for(Card next : cards) {
			if (next.canBePlayedOn(topCard)) {
				if (min == null || min.points() > next.points()) {
					min = next;
				}
			}
		}
		cards.remove(min);
		return min;
	}
}