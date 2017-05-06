package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class CardTest {

	@Test
	public void testCardLogic() {
		Card green1 = new Card(1, Colour.GREEN);
		Card red1 = new Card(1, Colour.RED);
		Card red2 = new Card(2, Colour.RED);
		Card blue4 = new Card(4, Colour.BLUE);
		assertThat(green1.canBePlayedOn(red1)).isTrue();
		assertThat(red1.canBePlayedOn(red2)).isTrue();
		assertThat(red2.canBePlayedOn(blue4)).isFalse();
	}

	@Test
	public void testWildCardLogic() {
		WildCard wildcard = new WildCard();
		Pack pack = new Pack();
		while (pack.numCards() > 0) {
			assertThat(wildcard.canBePlayedOn(pack.takeCard())).isTrue();
		}
	}

}
