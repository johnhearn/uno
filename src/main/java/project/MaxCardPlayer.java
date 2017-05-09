package project;

public class MaxCardPlayer extends Player {

	public Card playCard(Card topCard) {
		Card max = null;
		for(Card next : cards) {
			if (next.canBePlayedOn(topCard)) {
				if (max == null || max.points() < next.points()) {
					max = next;
				}
			}
		}
		cards.remove(max);
		return max;
	}
}