package project;

public class PlayUno {
	
	public static void main(String[] args) {
		new PlayUno().play();
	}

	public void play() {
		Player[] players = new Player[] { new Player("Player 0"), new Player("Player 1"), new Player("Player 2"), new Player("Player 3") };
		Player winner = new Game(new Pack(), players).play();
		for(Player player : players) {
			System.out.println(player + " scored " + player.getCurrentScore() + " points.");
		}
		System.out.println(winner + " is the winner.");		
	}
}
