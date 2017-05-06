package project;

import java.util.Collections;
import java.util.Random;

import project.Card.Colour;

public class Pack extends CardHolder {

	public Pack() {
		for (Colour colour : Colour.values()) {
			for (int i = 1; i <= 9; i++) {
				cards.add(new Card(i, colour));
			}
		}
		shuffle();
	}

	public Card takeCard() {
		return cards.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(cards, new Random());
	}
}
