package project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
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

		List<Card> pile = game.getPile().cards;
		for (int i = 1; i < pile.size(); i++) {
			assertThat(pile.get(i).canBePlayedOn(pile.get(i - 1))).isTrue();
		}
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
		assertThat(game.getPack().numCards()).isEqualTo(cards - 7 * PLAYERS - 1);
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

	private void setupTestHand(Player player) {
		player.giveCard(new Card(6, Colour.RED));
		player.giveCard(new Card(5, Colour.GREEN));
	}

	@Test
	public void testPlayerPlaysPlayable() {
		Player player = new Player();
		setupTestHand(player);

		Card topCard = new Card(5, Colour.BLUE);

		Card playCard = player.playCard(topCard);
		assertThat(playCard).isEqualTo(new Card(5, Colour.GREEN));
		assertThat(player.numCards()).isEqualTo(1);
	}

	@Test
	public void testPlayerHasNoPlayableCard() {
		Player player = new Player();
		setupTestHand(player);

		Card topCard = new Card(4, Colour.YELLOW);

		Card playCard = player.playCard(topCard);
		assertThat(playCard).isNull();
		assertThat(player.numCards()).isEqualTo(2);
	}

	@Test
	public void testStalemate() {
		UnoGame game = new UnoGame(new PassingPlayer());
		Player winner = game.play();
		assertThat(winner).isNull();
	}

	@Test
	public void testPickupOnPass() {
		Pack pack = new Pack();
		Pile pile = new Pile();
		pile.addCard(pack.takeCard());
		PassingPlayer player = new PassingPlayer();
		UnoGame game = new UnoGame(pack, pile, player);
		game.nextTurn(player);
		assertThat(pack.numCards()).isEqualTo(34);
		assertThat(pile.numCards()).isEqualTo(1);
		assertThat(player.numCards()).isEqualTo(1);
	}

	private static class PassingPlayer extends Player {
		@Override
		public Card playCard(Card topCard) {
			return null;
		}
	}
}
