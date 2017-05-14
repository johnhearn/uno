package uno.model;

import java.util.List;

public class DefensivePlayer extends Player {

	public Card chooseCard(List<Card> playableCards) {
		Card max = null;
		for (Card next : playableCards) {
			if (max == null || max.points() < next.points()) {
				max = next;
			}
		}
		return max;
	}
}