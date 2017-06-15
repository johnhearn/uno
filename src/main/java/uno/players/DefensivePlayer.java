package uno.players;

import java.util.List;

import uno.model.Card;
import uno.model.Player;

public class DefensivePlayer extends Player {

	public Card chooseCard(List<Card> playableCards) {
		Card max = null;
		for (int i = 0; i < playableCards.size(); i++) {
			Card next = playableCards.get(i);
			if (max == null || max.points() < next.points()) {
				max = next;
			}
		}
		return max;
	}
}