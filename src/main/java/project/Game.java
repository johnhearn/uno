package project;

public class Game {
	
	private final Pack pack;
	private final Pile pile = new Pile();
	private final Player[] players;

	public Game(Pack pack, Player... players) {
		this.pack = pack;
		this.players = players;
	}

	public Player play() {
		while (true) {
			Round game = new Round(pack, pile, players);
			Player winner = game.play();
			if (winner != null && winner.getCurrentScore() > 500) {
				return winner;
			}
		}
	}

}
