package uno.model;

import java.util.LinkedList;
import java.util.List;

import uno.model.Card.Colour;

public abstract class Player extends CardHolder {

	private static int counter;
	protected final String name;
	private int score;

	private LinkedList<Card> playableCards = new LinkedList<>();

	public Player() {
		this.name = getClass().getSimpleName() + " " + counter;
		counter++;
	}

	public Player(String name) {
		this.name = name;
		counter++;
	}

	public void giveCard(Card card) {
		cards.add(card);
	}

	public Card playCard(Card topCard) {
		Card whichCard = chooseCard(playableCards(topCard));
		if (whichCard instanceof WildCard) {
			whichCard.colour = chooseWildCardColour((WildCard)whichCard);
		}
		cards.remove(whichCard);
		return whichCard;
	}

	protected List<Card> playableCards(Card topCard) {
		playableCards.clear();
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.canBePlayedOn(topCard) && !(card instanceof WildFourCard)) {
				playableCards.add(card);
			}
		}
		if (playableCards.isEmpty()) {
			for (int i = 0; i < cards.size(); i++) {
				Card card = cards.get(i);
				if (card instanceof WildFourCard) {
					playableCards.add(card);
				}
			}
		}
		return playableCards;
	}

	protected abstract Card chooseCard(List<Card> playableCards);

	protected Colour chooseWildCardColour(WildCard whichCard) {
		int red=0,green=0,blue=0,yellow=0;
		for (int i=0;i<cards.size();i++) {
			Card card = cards.get(i);
			if(card.colour == Colour.RED) red++;
			else if(card.colour == Colour.GREEN) green++;
			else if(card.colour == Colour.BLUE) blue++;
			else if(card.colour == Colour.YELLOW) yellow++;
		}
		Colour colour;
		if(red >= green && red >= blue && red >= yellow) colour = Colour.RED;
		else if(green >= red && green >= blue && green >= yellow) colour = Colour.GREEN;
		else if(blue >= red && blue >= green && blue >= yellow) colour = Colour.BLUE;
		else colour = Colour.YELLOW;
		return colour;
	}

	@Override
	public String toString() {
		return name + ((cards.size() > 0) ? " " + super.toString() : "");
	}

	public void addPlayersCardsToScore(Player player) {
		score += player.sumOfHand();
	}

	private int sumOfHand() {
		return cards.stream().mapToInt((c) -> c.points()).sum();
	}

	public int getCurrentScore() {
		return score;
	}

	public void resetScore() {
		score = 0;
	}
}
