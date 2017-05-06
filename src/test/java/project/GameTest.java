package project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

import project.Card.Colour;

public class GameTest {

	private final int PLAYERS = 3;

	@Test
	public void testGame() {
		UnoGame game = new UnoGame(PLAYERS);
		int cards = game.getPack().numCards();
		Player winner = game.play();
		for (Player player : game.players()) {
			if (player == winner) {
				assertThat(player.numCards()).isEqualTo(0);
			} else {
				assertThat(player.numCards()).isNotEqualTo(0);
			}
		}
		assertThat(countTotalCards(game)).isEqualTo(cards);
	}

	@Test
	public void testDeal() {
		UnoGame game = new UnoGame(PLAYERS);
		int cards = game.getPack().numCards();
		game.deal();
		for (Player player : game.players()) {
			assertThat(player.numCards()).isEqualTo(7);
		}
		assertThat(game.getPile().numCards()).isEqualTo(1);
		assertThat(game.getPack().numCards()).isEqualTo(cards - 7*PLAYERS - 1);
		assertThat(countTotalCards(game)).isEqualTo(cards);
	}

	private int countTotalCards(UnoGame game) {
		int finalCardCount = Stream.concat(Stream.of(game.getPack(), game.getPile()), Arrays.stream(game.players()))
				.mapToInt((ch) -> ch.numCards()).sum();
		return finalCardCount;
	}

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
	public void testPileTopCard() {
		Pile pile = new Pile();
		pile.addCard(new Card(1, Colour.GREEN));
		assertThat(pile.topCard()).isEqualTo(new Card(1, Colour.GREEN));
		pile.addCard(new Card(2, Colour.RED));
		assertThat(pile.topCard()).isEqualTo(new Card(2, Colour.RED));
		pile.addCard(new Card(3, Colour.BLUE));
		assertThat(pile.topCard()).isEqualTo(new Card(3, Colour.BLUE));
	}

}
