package project;

import java.util.Iterator;

public class Player extends CardHolder {
	
	private static int counter;
	private String name;
	
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
				return next;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name + " " + super.toString();
	}
}
