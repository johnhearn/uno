package uno.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import uno.model.Card.Colour;

public class PlayerTest {
	
	private Player player = new Player("Test Player");

	@Before
	public void setupTestHand() {
		player.giveCard(new NumberCard(6, Colour.RED));
		player.giveCard(new NumberCard(7, Colour.RED));
		player.giveCard(new NumberCard(5, Colour.GREEN));
	}

	@Test
	public void testPlayerPlaysPlayable() {
		Card topCard = new NumberCard(5, Colour.BLUE);

		int numCards = player.numCards();
		Card playCard = player.playCard(topCard);
		assertThat(playCard).isEqualTo(new NumberCard(5, Colour.GREEN));
		assertThat(player.numCards()).isEqualTo(numCards - 1);
	}

	@Test
	public void testPlayerHasNoPlayableCard() {
		Card topCard = new NumberCard(4, Colour.YELLOW);

		int numCards = player.numCards();
		Card playCard = player.playCard(topCard);
		assertThat(playCard).isNull();
		assertThat(player.numCards()).isEqualTo(numCards);
	}

	@Test
	public void testSetColourWhenPlayingWildCard() {
		testSetColourWhenPlayingWildCard(new WildCard());
	}

	@Test
	public void testSetColourWhenPlayingWildFourCard() {
		testSetColourWhenPlayingWildCard(new WildFourCard());
	}

	private void testSetColourWhenPlayingWildCard(WildCard wildcard) {
		player.giveCard(wildcard);
		player.playCard(new NumberCard(3, Colour.BLUE));
		assertThat(wildcard.isDeclared()).isTrue();
		// Check that player chooses RED due to having 2 RED cards in test hand
		assertThat(wildcard.colour).isEqualTo(Colour.RED);
	}

	@Test
	public void testIterateOverHand() {
		Iterator<Card> iter = player.iterator();
		assertThat(iter.next()).isEqualTo(new NumberCard(6, Colour.RED));
		assertThat(iter.next()).isEqualTo(new NumberCard(7, Colour.RED));
		assertThat(iter.next()).isEqualTo(new NumberCard(5, Colour.GREEN));
	}

	@Test
	public void testToStringWithNoCards() {
		player = new Player("Test Player");
		assertThat(player.toString()).isEqualTo("Test Player");
	}

	@Test
	public void testToStringWithHand() {
		assertThat(player.toString()).isEqualTo("Test Player [RED 6, RED 7, GREEN 5]");
	}

	@Test
	public void testCalculateScore() {
		Player winner = new Player();
		assertThat(winner.getCurrentScore()).isEqualTo(0);
		winner.addPlayersCardsToScore(player);
		assertThat(winner.getCurrentScore()).isEqualTo(18);
		winner.resetScore();
		assertThat(winner.getCurrentScore()).isEqualTo(0);
	}

}
