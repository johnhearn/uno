package uno.model;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.Test;
import org.mockito.AdditionalAnswers;

import uno.model.Card.Colour;
import uno.players.RandomPlayer;

public class GameTest {

	// System under test
	//
	private Pack pack = new Pack();
	private Pile pile = new Pile();
	private Player[] players = new Player[] { new RandomPlayer(), new RandomPlayer(), new RandomPlayer() };
	private Round round = new Round(pack, pile, players);

	@Test
	public void testGame() {
		Game game = new Game(pack, players);
		Player winner = game.play();
		for (Player player : players) {
			if (player == winner) {
				assertThat(player.getCurrentScore()).isGreaterThanOrEqualTo(500);
			} else {
				assertThat(player.getCurrentScore()).isLessThan(500);
			}
		}
	}

	@Test
	public void testPlay() {
		int numCards = pack.numCards();
		round.play();
		for (Player player : players) {
			assertThat(player.numCards()).isEqualTo(0);
		}
		assertThat(pile.numCards()).isEqualTo(0);
		assertThat(pack.numCards()).isEqualTo(numCards);
	}

	@Test
	public void testPlayRound() throws NoMoreCardsException {
		Player winner = round.playRound();
		for (Player player : players) {
			if (player == winner) {
				assertThat(player.numCards()).isEqualTo(0);
			} else {
				assertThat(player.numCards()).isNotEqualTo(0);
			}
		}
	}

	@Test
	public void testDeal() {
		Pack pack = mock(Pack.class, AdditionalAnswers.delegatesTo(this.pack));
		int numCards = pack.numCards();
		round = new Round(pack, pile, players);
		round.deal();
		for (Player player : players) {
			assertThat(player.numCards()).isEqualTo(7);
		}
		assertThat(pile.numCards()).isEqualTo(1);
		assertThat(pack.numCards()).isEqualTo(numCards - 7 * players.length - 1);
		verify(pack, times(1)).shuffle();
		assertThat(countTotalCards(pack, pile, players)).isEqualTo(numCards);
	}

	private int countTotalCards(Pack pack, Pile pile, Player... players) {
		int finalCardCount = Stream.concat(Stream.of(pack, pile), Stream.of(players)).mapToInt((ch) -> ch.numCards()).sum();
		return finalCardCount;
	}

	@Test
	public void testStalemate() {
		round = new Round(pack, pile, new MockPlayer(null));
		Player winner = round.play();
		assertThat(winner).isNull();
	}

	private static class MockPack extends Pack {
		AtomicInteger resetCount = new AtomicInteger(0);

		public MockPack(int numCards) {
			cards.clear();
			for (int i = 0; i < numCards; i++) {
				cards.add(new WildCard().withColour(Colour.BLUE));
			}
		}

		@Override
		public void putCards(CardHolder cards) {
			super.putCards(cards);
			resetCount.incrementAndGet();
		}
	}

	@Test
	public void testCardsFromPileAddedToPackWhenPackEmpty() throws Exception {
		MockPack pack = new MockPack(17);
		pile = new Pile();
		players = new Player[] { new RandomPlayer(), new MockPlayer(null) };
		round = new Round(pack, pile, players);
		round.play();
		assertThat(pack.resetCount.get()).isGreaterThan(0);
		assertThat(players[0].numCards()).isEqualTo(0);
	}

	@Test
	public void testDrawCard() throws NoMoreCardsException {
		MockPack pack = new MockPack(3);
		pile.addCard(pack.drawCard());
		pile.addCard(pack.drawCard());
		pile.addCard(pack.drawCard());
		round = new Round(pack, pile, players);
		assertThat(pack.resetCount.get()).isEqualTo(0);
		assertThat(round.drawCard()).isNotNull();
		assertThat(pack.resetCount.get()).isEqualTo(1);
		assertThat(round.drawCard()).isNotNull();
	}

	@Test(expected=NoMoreCardsException.class)
	public void testDrawCardNoMoreCards() throws NoMoreCardsException {
		MockPack pack = new MockPack(0);
		round = new Round(pack, pile, players);
		round.drawCard();
	}

	@Test
	public void testPickupOnPass() throws NoMoreCardsException {
		int numCards = pack.numCards();
		pile.addCard(pack.drawCard());
		MockPlayer player = new MockPlayer(null);
		round = new Round(pack, pile, player);
		round.nextTurn(player);
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

		protected Card chooseCard(List<Card> playableCards) {
			if (playableCards.size() > 0) {
				// most basic strategy is to play any playable card
				Card card = playableCards.get(0);
				return card;
			}
			return null;
		}
	}

	@Test
	public void testNextPlayerWildLogic() {
		assertThat(round.nextPlayer(new WildCard().withColour(Colour.BLUE))).isEqualTo(1);
	}

	@Test
	public void testNextPlayerLogic() {
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(1);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(2);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerReverseLogic() {
		assertThat(round.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(1);
		assertThat(round.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(2);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerSkipLogic() {
		assertThat(round.nextPlayer(new SkipCard(Colour.BLUE))).isEqualTo(2);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(0);
		assertThat(round.nextPlayer(new NumberCard(4, Colour.BLUE))).isEqualTo(1);
		assertThat(round.nextPlayer(new ReverseCard(Colour.BLUE))).isEqualTo(0);
		assertThat(round.nextPlayer(new SkipCard(Colour.BLUE))).isEqualTo(1);
		assertThat(round.nextPlayer(new NumberCard(3, Colour.BLUE))).isEqualTo(0);
	}

	@Test
	public void testNextPlayerDrawTwoLogic() {
		assertThat(round.nextPlayer(new DrawTwoCard(Colour.BLUE))).isEqualTo(2);
	}

	@Test
	public void testNextTurnDrawTwoLogic() throws NoMoreCardsException {
		pile.addCard(new NumberCard(1, Colour.BLUE));
		Player player = new MockPlayer(new DrawTwoCard(Colour.BLUE));
		round.nextTurn(player);
		assertThat(players[1].numCards()).isEqualTo(2);
	}

	@Test
	public void testNextPlayerWildFourLogic() {
		assertThat(round.nextPlayer(new WildFourCard().withColour(Colour.BLUE))).isEqualTo(2);
	}

	@Test
	public void testNextTurnWildFourLogic() throws NoMoreCardsException {
		pile.addCard(new NumberCard(1, Colour.BLUE));
		Player player = new MockPlayer(new WildFourCard().withColour(Colour.BLUE));
		round.nextTurn(player);
		assertThat(players[1].numCards()).isEqualTo(4);
	}

	@Test
	public void testAddUpScores() {
		Player winner = round.play();
		for (Player player : players) {
			if (player == winner) {
				assertThat(player.getCurrentScore()).isGreaterThan(0);
			} else {
				assertThat(player.getCurrentScore()).isEqualTo(0);
			}
		}
	}
}
