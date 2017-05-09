package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class SpecialPlayerTest {

	@Test
	public void testMaxCardPlayed() {
		MaxCardPlayer player = new MaxCardPlayer();
		player.giveCard(new Card(1, Colour.BLUE));
		player.giveCard(new Card(2, Colour.BLUE));
		player.giveCard(new Card(3, Colour.BLUE));
		player.giveCard(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new Card(4, Colour.BLUE))).isEqualTo(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new Card(4, Colour.BLUE))).isEqualTo(new Card(3, Colour.BLUE));
		assertThat(player.playCard(new Card(3, Colour.BLUE))).isEqualTo(new Card(2, Colour.BLUE));
		assertThat(player.playCard(new Card(2, Colour.BLUE))).isEqualTo(new Card(1, Colour.BLUE));
	}

	@Test
	public void testMinCardPlayed() {
		MinCardPlayer player = new MinCardPlayer();
		player.giveCard(new Card(1, Colour.BLUE));
		player.giveCard(new Card(2, Colour.BLUE));
		player.giveCard(new Card(3, Colour.BLUE));
		player.giveCard(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new Card(2, Colour.BLUE))).isEqualTo(new Card(1, Colour.BLUE));
		assertThat(player.playCard(new Card(1, Colour.BLUE))).isEqualTo(new Card(2, Colour.BLUE));
		assertThat(player.playCard(new Card(2, Colour.BLUE))).isEqualTo(new Card(3, Colour.BLUE));
		assertThat(player.playCard(new Card(3, Colour.BLUE))).isEqualTo(new ReverseCard(Colour.BLUE));
	}

}
