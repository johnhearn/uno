package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import project.Card.Colour;

public class PlayerTest {
	
	private Player player = new Player();

	@Before
	public void setupTestHand() {
		player.giveCard(new Card(6, Colour.RED));
		player.giveCard(new Card(5, Colour.GREEN));
	}

	@Test
	public void testPlayerPlaysPlayable() {
		Card topCard = new Card(5, Colour.BLUE);

		Card playCard = player.playCard(topCard);
		assertThat(playCard).isEqualTo(new Card(5, Colour.GREEN));
		assertThat(player.numCards()).isEqualTo(1);
	}

	@Test
	public void testPlayerHasNoPlayableCard() {
		Card topCard = new Card(4, Colour.YELLOW);

		Card playCard = player.playCard(topCard);
		assertThat(playCard).isNull();
		assertThat(player.numCards()).isEqualTo(2);
	}

}
