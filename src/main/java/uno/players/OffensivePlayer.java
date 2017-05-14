package uno.players;

import java.util.List;

import uno.model.Card;
import uno.model.Player;

public class OffensivePlayer extends Player {

	protected Card chooseCard(List<Card> playableCards) {
		Card min = null;
		for (Card next : playableCards) {
			if (min == null || min.points() > next.points()) {
				min = next;
			}
		}
		return min;
	}
}