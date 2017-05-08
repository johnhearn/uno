package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PackTest {

	@Test
	public void testNumberOfCardsInPack() {
		// How many cards? 1->9 * 4 colours + 4 wild + 4 reverse + 4 skip + 4 draw two + 4 wild four
		Pack pack = new Pack();
		assertThat(pack.numCards()).isEqualTo(56);
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
