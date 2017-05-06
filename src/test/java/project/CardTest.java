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
			assertThat(wildcard.canBePlayedOn(pack.drawCard())).isTrue();
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
	public void testWildFourCardLogic() {
		WildFourCard wildcard = new WildFourCard();
		Pack pack = new Pack();
		while (pack.numCards() > 0) {
			assertThat(wildcard.canBePlayedOn(pack.drawCard())).isTrue();
		}
	}

	@Test
	public void testWildFourCardStep() {
		WildFourCard wildcard = new WildFourCard();
		assertThat(wildcard.nextStep(+1)).isEqualTo(+2);
		assertThat(wildcard.nextStep(-1)).isEqualTo(-2);
	}

	@Test
	public void testWildFourCardToString() {
		WildFourCard wildcard = new WildFourCard();
		assertThat(wildcard.toString()).isEqualTo("Wild Four");
		wildcard.colour = Colour.BLUE;
		assertThat(wildcard.toString()).isEqualTo("Wild Four BLUE");
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
		SkipCard skipCard = new SkipCard(Colour.BLUE);
		assertThat(skipCard.canBePlayedOn(new Card(3, Colour.BLUE))).isTrue();
		assertThat(skipCard.canBePlayedOn(new Card(3, Colour.RED))).isFalse();
	}

	@Test
	public void testSkipCardStep() {
		SkipCard skipCard = new SkipCard(Colour.BLUE);
		assertThat(skipCard.nextStep(+1)).isEqualTo(+2);
		assertThat(skipCard.nextStep(-1)).isEqualTo(-2);
	}

	@Test
	public void testSkipCardToString() {
		SkipCard skipCard = new SkipCard(Colour.BLUE);
		assertThat(skipCard.toString()).isEqualTo("Skip BLUE");
	}

	@Test
	public void testDrawTwoCardLogic() {
		DrawTwoCard drawTwoCard = new DrawTwoCard(Colour.BLUE);
		assertThat(drawTwoCard.canBePlayedOn(new Card(3, Colour.BLUE))).isTrue();
		assertThat(drawTwoCard.canBePlayedOn(new Card(3, Colour.RED))).isFalse();
	}

	@Test
	public void testDrawTwoCardStep() {
		DrawTwoCard drawTwoCard = new DrawTwoCard(Colour.BLUE);
		assertThat(drawTwoCard.nextStep(+1)).isEqualTo(+2);
		assertThat(drawTwoCard.nextStep(-1)).isEqualTo(-2);
	}

	@Test
	public void testDrawTwoCardToString() {
		DrawTwoCard drawTwoCard = new DrawTwoCard(Colour.BLUE);
		assertThat(drawTwoCard.toString()).isEqualTo("Draw Two BLUE");
	}

}
