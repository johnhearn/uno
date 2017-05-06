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
		for (int i = 0; i < 4; i++) {
			cards.add(new WildCard());
		}
		for(Colour colour : Colour.values()) {
			cards.add(new ReverseCard(colour));
		}
		for(Colour colour : Colour.values()) {
			cards.add(new SkipCard(colour));
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
