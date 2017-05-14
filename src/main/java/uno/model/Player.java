package uno.model;

import java.util.LinkedList;
import java.util.List;

import uno.model.Card.Colour;

public class Player extends CardHolder {

	private static int counter;
	private String name;
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
			chooseWildCardColour((WildCard)whichCard);
		}
		cards.remove(whichCard);
		return whichCard;
	}

	protected List<Card> playableCards(Card topCard) {
		playableCards.clear();
		for (Card card : cards) {
			if (card.canBePlayedOn(topCard) && !(card instanceof WildFourCard)) {
				playableCards.add(card);
			}
		}
		if (playableCards.isEmpty()) {
			for (Card card : cards) {
				if (card instanceof WildFourCard) {
					playableCards.add(card);
				}
			}
		}
		return playableCards;
	}

	protected Card chooseCard(List<Card> playableCards) {
		if (playableCards.size() > 0) {
			// most basic strategy is to play any playable card
			Card card = playableCards.get(0);
			return card;
		}
		return null;
	}

	protected void chooseWildCardColour(WildCard whichCard) {
		int red=0,green=0,blue=0,yellow=0;
		for(Card card : playableCards) {
			if(card.colour == Colour.RED) red++;
			else if(card.colour == Colour.GREEN) green++;
			else if(card.colour == Colour.BLUE) blue++;
			else if(card.colour == Colour.YELLOW) yellow++;
		}
		if(red >= green && red >= blue && red >= yellow) whichCard.colour = Colour.RED;
		else if(green >= red && green >= blue && green >= yellow) whichCard.colour = Colour.GREEN;
		else if(blue >= red && blue >= green && blue >= yellow) whichCard.colour = Colour.BLUE;
		else whichCard.colour = Colour.YELLOW;
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
