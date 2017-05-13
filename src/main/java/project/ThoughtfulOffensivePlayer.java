package project;

import java.util.List;

public class ThoughtfulOffensivePlayer extends Player {

	public Card chooseCard(List<Card> playableCards) {
		Card minValueCard = null;
		Card maxNumberCard = null;
		for (Card next : playableCards) {
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