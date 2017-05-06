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

	public Player play() {
		deal();
		while (true) {
			for (int i = 0; i < players.length; i++) {
				Card card = players[i].playCard(pile.topCard());
				pile.addCard(card);
				if (players[i].numCards() == 0) {
					return players[i];
				}
			}
		}
	}

	protected void deal() {
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < players.length; i++) {
				this.players[i].giveCard(pack.takeCard());
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
}
