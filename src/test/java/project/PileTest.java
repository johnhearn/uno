package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class PileTest {
	
	private static final NumberCard GREEN2 = new NumberCard(2, Colour.GREEN);
	
	private Pile pile = new Pile();
	
	@Test
	public void testPileTopCard() {
		pile.addCard(GREEN2);
		assertThat(pile.topCard()).isEqualTo(GREEN2);
		pile.addCard(new NumberCard(2, Colour.RED));
		assertThat(pile.topCard()).isEqualTo(new NumberCard(2, Colour.RED));
		pile.addCard(new NumberCard(2, Colour.BLUE));
		assertThat(pile.topCard()).isEqualTo(new NumberCard(2, Colour.BLUE));
	}

	@Test(expected = RuntimeException.class)
	public void testPileDoesNotAcceptInvalidCard() {
		pile.addCard(new NumberCard(1, Colour.GREEN));
		pile.addCard(GREEN2);
		pile.addCard(new NumberCard(3, Colour.BLUE));
	}

	@Test
	public void testPileToString() {
		pile.addCard(GREEN2);
		assertThat(pile.toString()).isEqualTo(GREEN2.toString());
	}

}
