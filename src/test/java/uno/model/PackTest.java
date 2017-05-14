package uno.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import uno.model.Pack;
import uno.model.Pile;
import uno.model.WildCard;

public class PackTest {

	@Test
	public void testNumberOfCardsInPack() {
		// The deck consists of 108 cards, of which there are 25 of each color (red, green, blue, and yellow), each
		// color having two of each rank except zero. The ranks in each color are zero to nine, "Skip", "Draw Two", and
		// "Reverse" (the last three being "action cards"). In addition, the deck contains four each of "Wild" and "Wild
		// Draw Four" cards.
		Pack pack = new Pack();
		assertThat(pack.numCards()).isEqualTo(108);
	}

	@Test
	public void testPutCards() throws Exception {
		Pack pack = new Pack();
		int numCardsInPack = pack.numCards();

		Pile pile = new Pile();
		pile.addCard(new WildCard());
		pile.addCard(new WildCard());
		pile.addCard(new WildCard());

		pack.putCards(pile);

		assertThat(pack.numCards()).isEqualTo(numCardsInPack + 3);
		assertThat(pile.numCards()).isEqualTo(0);
	}

	@Test
	public void testDrawCard() throws Exception {
		Pack pack = new Pack();
		while (pack.numCards() > 0) {
			assertThat(pack.drawCard()).isNotNull();
		}
		assertThat(pack.drawCard()).isNull();
	}
}
