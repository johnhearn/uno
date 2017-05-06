package project;

public class UnoGame {

	private final Pack pack;
	private final Pile pile;
	private final Player[] players;

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
		int currentPlayer = 0;
		while (pack.numCards() > 0) {
			Player player = players[currentPlayer];
			Card lastCardPlayed = nextTurn(player);
			if (lastCardPlayed != null) {
				if (player.numCards() == 0) {
					System.out.println(player + " wins");
					return player;
				}
			}
			currentPlayer = nextPlayer(currentPlayer, lastCardPlayed);
		}
		// We ran out of cards, stalemate, doesn't often happen in real games
		return null;
	}

	protected int nextPlayer(int i, Card lastCardPlayed) {
		if (lastCardPlayed != null) {
			step = lastCardPlayed.nextStep(step);
		}
		return (i + players.length + step) % players.length;
	}

	protected void deal() {
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < players.length; i++) {
				players[i].giveCard(pack.takeCard());
			}
		}
		pile.addCard(pack.takeCard());
	}

	public Player[] players() {
		return players;
	}

	public Pack getPack() {
		return pack;
	}

	public Pile getPile() {
		return pile;
	}

	public Card nextTurn(Player player) {
		Card cardPlayed = player.playCard(pile.topCard());
		if (cardPlayed != null) {
			System.out.println(player + " has discarded " + cardPlayed);
			pile.addCard(cardPlayed);
		} else {
			player.giveCard(pack.takeCard());
			System.out.println(player + " picked up card");
		}
		return cardPlayed;
	}
}
