package project;

public class UnoGame {

	private final Pack pack;
	private final Pile pile;
	private final Player[] players;

	private int currentPosition = 0;
	private int step = +1;

	protected UnoGame(int players) {
		super();
		this.pack = new Pack();
		this.pile = new Pile();
		this.players = new Player[players];
		for (int i = 0; i < players; i++) {
			this.players[i] = new Player();
		}
	}

	public UnoGame(Player... players) {
		this(new Pack(), new Pile(), players);
	}

	protected UnoGame(Pack pack, Pile pile, Player... players) {
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
				System.out.println(player + " wins");
				return player;
			}
		}
		// We ran out of cards, stalemate, doesn't often happen in real games
		return null;
	}

	protected void deal() {
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < players.length; i++) {
				players[i].giveCard(pack.drawCard());
			}
		}
		pile.addCard(pack.drawCard());
	}

	protected Card nextTurn(Player player) {
		Card cardPlayed = player.playCard(pile.topCard());
		if (cardPlayed != null) {
			System.out.println(player + " has discarded " + cardPlayed);
			pile.addCard(cardPlayed);
			if (cardPlayed instanceof DrawTwoCard) {
				drawCards(2);
			} else if (cardPlayed instanceof WildFourCard) {
				drawCards(4);
			}
		} else {
			player.giveCard(pack.drawCard());
			System.out.println(player + " picked up card");
		}
		nextPlayer(cardPlayed);
		return cardPlayed;
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
		System.out.println(player + " picks up " + numCards +" cards");
	}

	private int position(int i) {
		return (i + players.length) % players.length;
	}
}
