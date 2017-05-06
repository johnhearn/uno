package project;

import java.util.LinkedList;
import java.util.List;

public class CardHolder {

	protected List<Card> cards = new LinkedList<>();

	public CardHolder() {
		super();
	}

	public int numCards() {
		return cards.size();
	}

}