package project;

import java.util.Collections;
import java.util.Random;

import project.Card.Colour;

public class Pack extends CardHolder {

	public Pack() {
		for (Colour colour : Colour.values()) {
			for (int i = 0; i <= 9; i++) {
				cards.add(new NumberCard(i, colour));
			}
		}
		for (Colour colour : Colour.values()) {
			for (int i = 1; i <= 9; i++) {
				cards.add(new NumberCard(i, colour));
			}
		}
		for (int i = 0; i < 4; i++) {
			cards.add(new WildCard());
		}
		for (int i = 0; i < 4; i++) {
			cards.add(new WildFourCard());
		}
		for (Colour colour : Colour.values()) {
			cards.add(new ReverseCard(colour));
			cards.add(new ReverseCard(colour));
		}
		for (Colour colour : Colour.values()) {
			cards.add(new SkipCard(colour));
			cards.add(new SkipCard(colour));
		}
		for (Colour colour : Colour.values()) {
			cards.add(new DrawTwoCard(colour));
			cards.add(new DrawTwoCard(colour));
		}
	}

	public Card drawCard() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(cards, new Random());
	}

	public void putCards(CardHolder cards) {
		this.cards.addAll(cards.cards);
		cards.cards.clear();
	}
}
