package uno;

import uno.model.Game;
import uno.model.Pack;
import uno.model.Player;
import uno.players.RandomPlayer;

public class PlayUno {
	
	public static void main(String[] args) {
		new PlayUno().play();
	}

	public void play() {
		Player[] players = new Player[] { new RandomPlayer("Player 0"), new RandomPlayer("Player 1"), new RandomPlayer("Player 2"), new RandomPlayer("Player 3") };
		Player winner = new Game(new Pack(), players).play();
		for(Player player : players) {
			System.out.println(player + " scored " + player.getCurrentScore() + " points.");
		}
		System.out.println(winner + " is the winner.");		
	}
}
