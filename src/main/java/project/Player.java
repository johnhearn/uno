package project;

import java.util.LinkedList;
import java.util.List;

import project.Card.Colour;

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
			chooseWildCardColour(whichCard);
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

	protected void chooseWildCardColour(Card whichCard) {
		whichCard.colour = Colour.BLUE;
	}

	@Override
	public String toString() {
		return name + ((cards.size() > 0) ? " " + super.toString() : "");
	}

	private int sumOfHand() {
		return cards.stream().mapToInt((c) -> c.points()).sum();
	}

	public void addPlayersCardsToScore(Player player) {
		score += player.sumOfHand();
	}

	public int score() {
		return score;
	}
}
