package project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.Test;

import project.Card.Colour;

public class GameTest {

	// System under test
	//
	private Pack pack = new Pack();
	private Pile pile = new Pile();
	private Player[] players = new Player[] { new Player(), new Player(), new Player() };
	private UnoGame game = new UnoGame(pack, pile, players);
	
	@Test
	public void testGame() {
		int numCards = pack.numCards();
		Player winner = game.play();
		for (Player player : players) {
			if (player == winner) {
				assertThat(player.numCards()).isEqualTo(0);
			} else {
				assertThat(player.numCards()).isNotEqualTo(0);
			}
		}
		assertThat(countTotalCards(pack, pile, players)).isEqualTo(numCards);
	}

	@Test
	public void testDeal() {
		int numCards = pack.numCards();
		game.deal();
		for (Player player : players) {
			assertThat(player.numCards()).isEqualTo(7);
		}
		assertThat(pile.numCards()).isEqualTo(1);
		assertThat(pack.numCards()).isEqualTo(numCards - 7 * players.length - 1);
		assertThat(countTotalCards(pack, pile, players)).isEqualTo(numCards);
	}

	private int countTotalCards(Pack pack, Pile pile, Player... players) {
		int finalCardCount = Stream.concat(Stream.of(pack, pile), Stream.of(players))
				.mapToInt((ch) -> ch.numCards()).sum();
		return finalCardCount;
	}

	@Test
	public void testSetColourWhenPlayingWildCard() {
		Player player = new Player();
		player.giveCard(new WildCard());
		assertThat(player.playCard(new Card(3, Colour.BLUE)).colour).isNotNull();
	}

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
		game = new UnoGame(new PassingPlayer());
		Player winner = game.play();
		assertThat(winner).isNull();
	}

	@Test
	public void testPickupOnPass() {
		int numCards = pack.numCards();
		pile.addCard(pack.takeCard());
		PassingPlayer player = new PassingPlayer();
		game = new UnoGame(pack, pile, player);
		game.nextTurn(player);
		assertThat(pack.numCards()).isEqualTo(numCards - 2);
		assertThat(pile.numCards()).isEqualTo(1);
		assertThat(player.numCards()).isEqualTo(1);
	}

	private static class PassingPlayer extends Player {
		@Override
		public Card playCard(Card topCard) {
			return null;
		}
	}

	@Test
	public void testNextPlayerLogic() {
		assertThat(game.nextPlayer(0, new Card(3, Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(1, new Card(3, Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(2, new Card(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerReverseLogic() {
		assertThat(game.nextPlayer(0, new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(2, new Card(3, Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(1, new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(2, new Card(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerSkipLogic() {
		assertThat(game.nextPlayer(0, new SkipCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(2, new Card(3, Colour.BLUE))).isEqualTo(0);
		assertThat(game.nextPlayer(1, new ReverseCard(Colour.BLUE))).isEqualTo(0);
		assertThat(game.nextPlayer(2, new SkipCard(Colour.BLUE))).isEqualTo(0);
		assertThat(game.nextPlayer(0, new Card(3, Colour.BLUE))).isEqualTo(2);
	}
}
