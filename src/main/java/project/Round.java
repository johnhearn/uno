package project;

public class Round {

	private final Pack pack;
	private final Pile pile;
	private final Player[] players;

	private int currentPosition = 0;
	private int step = +1;

	public Round(Player... players) {
		this(new Pack(), new Pile(), players);
	}

	protected Round(Pack pack, Pile pile, Player... players) {
		this.pack = pack;
		this.pile = pile;
		this.players = players;
	}

	public Player play() {
		deal();
		while (pack.numCards() > 0) {
			Player player = players[currentPosition];
			nextTurn(player);
			if (player.numCards() == 0) {
				for(Player p : players) {
					player.addPlayersCardsToScore(p);
				}
				log(player + " wins");
				return player;
			}
			if (pack.numCards() == 0) {
				pack.resetPack(pile);
				pile.addCard(pack.drawCard());
			}
		}
		// We ran out of cards, stalemate, doesn't often happen in real games
		return null;
	}

	protected void deal() {
		for (int i = 0; i < players.length; i++) {
			players[i].newGame();
		}
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < players.length; i++) {
				players[i].giveCard(drawCard());
			}
		}
		pile.addCard(drawCard());
	}

	protected Card nextTurn(Player player) {
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

	protected Card drawCard() {
		Card drawCard = pack.drawCard();
		if (drawCard == null) {
			pack.resetPack(pile);
			pile.addCard(pack.drawCard());
			drawCard = pack.drawCard();
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

	private void drawCards(int numCards) {
		Player player = players[position(currentPosition + step)];
		for (int i = 0; i < numCards; i++) {
			player.giveCard(drawCard());
		}
		log(player + " picks up " + numCards +" cards");
	}

	private int position(int i) {
		return (i + players.length) % players.length;
	}
	
	private void log(String line) {
		//System.out.println(line);
	}
}