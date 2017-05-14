package uno.players;

import java.util.List;

import uno.model.Card;
import uno.model.Player;

public class RandomPlayer extends Player {

	public RandomPlayer() {
		super();
	}

	public RandomPlayer(String name) {
		super(name);
	}

	protected Card chooseCard(List<Card> playableCards) {
		if (playableCards.size() > 0) {
			// most basic strategy is to play any playable card
			Card card = playableCards.get(0);
			return card;
		}
		return null;
	}
}