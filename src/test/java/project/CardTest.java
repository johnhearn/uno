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
	public void testCardStep() {
		Card reverseCard = new Card(1, Colour.BLUE);
		assertThat(reverseCard.nextStep(+1)).isEqualTo(+1);
		assertThat(reverseCard.nextStep(-1)).isEqualTo(-1);
	}

	@Test
	public void testWildCardLogic() {
		WildCard wildcard = new WildCard();
		Pack pack = new Pack();
		while (pack.numCards() > 0) {
			assertThat(wildcard.canBePlayedOn(pack.takeCard())).isTrue();
		}
	}

	@Test
	public void testWildCardToString() {
		WildCard wildcard = new WildCard();
		assertThat(wildcard.toString()).isEqualTo("Wild");
		wildcard.colour = Colour.BLUE;
		assertThat(wildcard.toString()).isEqualTo("Wild BLUE");
	}

	@Test
	public void testReverseCardLogic() {
		ReverseCard reverseCard = new ReverseCard(Colour.BLUE);
		assertThat(reverseCard.canBePlayedOn(new Card(3, Colour.BLUE))).isTrue();
		assertThat(reverseCard.canBePlayedOn(new Card(3, Colour.RED))).isFalse();
	}

	@Test
	public void testReverseCardStep() {
		ReverseCard reverseCard = new ReverseCard(Colour.BLUE);
		assertThat(reverseCard.nextStep(+1)).isEqualTo(-1);
		assertThat(reverseCard.nextStep(-1)).isEqualTo(+1);
	}

	@Test
	public void testReverseCardToString() {
		ReverseCard reverseCard = new ReverseCard(Colour.BLUE);
		assertThat(reverseCard.toString()).isEqualTo("Reverse BLUE");
	}

	@Test
	public void testSkipCardLogic() {
		SkipCard reverseCard = new SkipCard(Colour.BLUE);
		assertThat(reverseCard.canBePlayedOn(new Card(3, Colour.BLUE))).isTrue();
		assertThat(reverseCard.canBePlayedOn(new Card(3, Colour.RED))).isFalse();
	}

	@Test
	public void testSkipCardStep() {
		SkipCard reverseCard = new SkipCard(Colour.BLUE);
		assertThat(reverseCard.nextStep(+1)).isEqualTo(+2);
		assertThat(reverseCard.nextStep(-1)).isEqualTo(-2);
	}

	@Test
	public void testSkipCardToString() {
		SkipCard reverseCard = new SkipCard(Colour.BLUE);
		assertThat(reverseCard.toString()).isEqualTo("Skip BLUE");
	}

}
