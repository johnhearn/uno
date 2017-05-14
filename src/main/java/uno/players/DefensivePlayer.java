package uno.players;

import java.util.List;

import uno.model.Card;
import uno.model.Player;

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