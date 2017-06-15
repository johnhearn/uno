package uno.players;

import java.util.List;

import uno.model.Card;
import uno.model.Player;

public class ThoughtfulOffensivePlayer extends Player {

	public Card chooseCard(List<Card> playableCards) {
		Card minValueCard = null;
		Card maxNumberCard = null;
		for (int i = 0; i < playableCards.size(); i++) {
			Card next = playableCards.get(i);
			if (minValueCard == null || minValueCard.points() > next.points()) {
				minValueCard = next;
			}
			if (next.points() < 50 && (maxNumberCard == null || maxNumberCard.points() < next.points())) {
				minValueCard = next;
			}
		}
		return minValueCard;
	}
}