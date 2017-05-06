package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class PileTest {
	
	private Pile pile = new Pile();
	
	@Test
	public void testPileTopCard() {
		pile.addCard(new Card(2, Colour.GREEN));
		assertThat(pile.topCard()).isEqualTo(new Card(2, Colour.GREEN));
		pile.addCard(new Card(2, Colour.RED));
		assertThat(pile.topCard()).isEqualTo(new Card(2, Colour.RED));
		pile.addCard(new Card(2, Colour.BLUE));
		assertThat(pile.topCard()).isEqualTo(new Card(2, Colour.BLUE));
	}

	@Test(expected = RuntimeException.class)
	public void testPileDoesNotAcceptInvalidCard() {
		pile.addCard(new Card(1, Colour.GREEN));
		pile.addCard(new Card(2, Colour.GREEN));
		pile.addCard(new Card(3, Colour.BLUE));
	}



}
