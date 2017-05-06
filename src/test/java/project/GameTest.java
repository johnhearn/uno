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
	public void testStalemate() {
		game = new UnoGame(new MockPlayer(null));
		Player winner = game.play();
		assertThat(winner).isNull();
	}

	@Test
	public void testPickupOnPass() {
		int numCards = pack.numCards();
		pile.addCard(pack.drawCard());
		MockPlayer player = new MockPlayer(null);
		game = new UnoGame(pack, pile, player);
		game.nextTurn(player);
		assertThat(pack.numCards()).isEqualTo(numCards - 2);
		assertThat(pile.numCards()).isEqualTo(1);
		assertThat(player.numCards()).isEqualTo(1);
	}

	private static class MockPlayer extends Player {
		final Card card;
		public MockPlayer(Card card) {
			this.card = card;
		}
		@Override
		public Card playCard(Card topCard) {
			return card;
		}
	}

	@Test
	public void testNextPlayerLogic() {
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerReverseLogic() {
		assertThat(game.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerSkipLogic() {
		assertThat(game.nextPlayer(new SkipCard(Colour.BLUE))).isEqualTo(2);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(0);
		assertThat(game.nextPlayer(new Card(4, Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(0);
		assertThat(game.nextPlayer(new SkipCard(Colour.BLUE))).isEqualTo(1);
		assertThat(game.nextPlayer(new Card(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerDrawTwoLogic() {
		assertThat(game.nextPlayer(new DrawTwoCard(Colour.BLUE))).isEqualTo(2);
	}

	@Test
	public void testNextTurnDrawTwoLogic() {
		pile.addCard(new Card(1, Colour.BLUE));
		Player player = new MockPlayer(new DrawTwoCard(Colour.BLUE));
		game.nextTurn(player);
		assertThat(players[1].numCards()).isEqualTo(2);
	}

}
