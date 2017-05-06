package project;

public class UnoGame {

	private final Pack pack;
	private final Pile pile;
	private final Player[] players;

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
		int passesInThisRound;
		do {
			passesInThisRound = 0;
			for (int i = 0; i < players.length; i++) {
				if (nextTurn(players[i])) {
					passesInThisRound++;
				} else if (players[i].numCards() == 0) {
					return players[i];
				}
			}
		} while (passesInThisRound < players.length);
		// Everybody passed, stalemate, doesn't often happen in real games
		return null;
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

	public boolean nextTurn(Player player) {
		Card card = player.playCard(pile.topCard());
		if (card != null) {
			pile.addCard(card);
		} else {
			player.giveCard(pack.takeCard());
		}
		return card == null;
	}
}
