package uno.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CardHolder implements Iterable<Card> {

	protected List<Card> cards = new LinkedList<>();

	public CardHolder() {
		super();
	}

	public int numCards() {
		return cards.size();
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}