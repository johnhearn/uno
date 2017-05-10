package project;

public class Round {

	private final Pack pack;
	private final Pile pile;
	private final Player[] players;

	private int currentPosition = 0;
	private int step = +1;

	protected Round(Pack pack, Pile pile, Player... players) {
		this.pack = pack;
		this.pile = pile;
		this.players = players;
	}

	public Player play() {
		Player winner;
		try {
			winner = playRound();
		} catch (NoMoreCardsException e) {
			winner = null;
		}
		for (Player player : players) {
			pack.putCards(player);
		}
		pack.putCards(pile);
		return winner;
	}

	protected Player playRound() throws NoMoreCardsException {
		deal();
		Player player;
		boolean endOfGame = false;
		do {
			player = players[currentPosition];
			nextTurn(player);
			if (player.numCards() == 0) {
				endOfGame = true;
			}
			if (pack.numCards() == 0) {
				pack.putCards(pile);
				pile.addCard(pack.drawCard());
			}
		} while (!endOfGame);

		for (Player p : players) {
			player.addPlayersCardsToScore(p);
		}
		log(player + " wins");
		return player;
	}

	protected void deal() {
		pack.shuffle();
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < players.length; i++) {
				players[i].giveCard(pack.drawCard());
			}
		}
		pile.addCard(pack.drawCard());
	}

	protected Card nextTurn(Player player) throws NoMoreCardsException {
		Card cardPlayed = player.playCard(pile.topCard());
		if (cardPlayed != null) {
			log(player + " has discarded " + cardPlayed);
			pile.addCard(cardPlayed);
			if (cardPlayed instanceof DrawTwoCard) {
				drawCards(2);
			} else if (cardPlayed instanceof WildFourCard) {
				drawCards(4);
			}
		} else {
			player.giveCard(drawCard());
			log(player + " picked up card");
		}
		nextPlayer(cardPlayed);
		return cardPlayed;
	}

	protected Card drawCard() throws NoMoreCardsException {
		Card drawCard = pack.drawCard();
		if (drawCard == null) {
			pack.putCards(pile);
			pack.shuffle();
			pile.addCard(pack.drawCard());
			drawCard = pack.drawCard();
			if (drawCard == null) {
				throw new NoMoreCardsException();
			}
		}
		return drawCard;
	}

	protected int nextPlayer(Card lastCardPlayed) {
		if (lastCardPlayed != null) {
			step = lastCardPlayed.nextStep(step);
		}
		currentPosition = position(currentPosition + step);
		step = (step > 0) ? +1 : -1;
		return currentPosition;
	}

	private void drawCards(int numCards) throws NoMoreCardsException {
		Player player = players[position(currentPosition + step)];
		for (int i = 0; i < numCards; i++) {
			player.giveCard(drawCard());
		}
		log(player + " picks up " + numCards + " cards");
	}

	private int position(int i) {
		return (i + players.length) % players.length;
	}

	private void log(String line) {
		//System.out.println(line);
	}
}
