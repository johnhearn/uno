package project;

import java.util.Iterator;

import project.Card.Colour;

public class Player extends CardHolder {

	private static int counter;
	private String name;
	private int score;

	public Player() {
		this("Player " + counter);
	}

	public Player(String name) {
		this.name = name;
		counter++;
	}

	public void giveCard(Card card) {
		cards.add(card);
	}

	public Card playCard(Card topCard) {
		Iterator<Card> iter = cards.iterator();
		while (iter.hasNext()) {
			Card next = iter.next();
			if (next.canBePlayedOn(topCard)) {
				iter.remove();
				if (next instanceof WildCard) {
					next.colour = Colour.BLUE;
				}
				return next;
			}
		}
		return null;
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
