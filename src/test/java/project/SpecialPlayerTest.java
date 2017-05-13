package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class SpecialPlayerTest {

	@Test
	public void testMaxCardPlayed() {
		DefensivePlayer player = new DefensivePlayer();
		player.giveCard(new NumberCard(1, Colour.BLUE));
		player.giveCard(new NumberCard(2, Colour.BLUE));
		player.giveCard(new NumberCard(3, Colour.BLUE));
		player.giveCard(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new NumberCard(4, Colour.BLUE))).isEqualTo(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new NumberCard(4, Colour.BLUE))).isEqualTo(new NumberCard(3, Colour.BLUE));
		assertThat(player.playCard(new NumberCard(3, Colour.BLUE))).isEqualTo(new NumberCard(2, Colour.BLUE));
		assertThat(player.playCard(new NumberCard(2, Colour.BLUE))).isEqualTo(new NumberCard(1, Colour.BLUE));
	}

	@Test
	public void testMinCardPlayed() {
		OffensivePlayer player = new OffensivePlayer();
		player.giveCard(new NumberCard(1, Colour.BLUE));
		player.giveCard(new NumberCard(2, Colour.BLUE));
		player.giveCard(new NumberCard(3, Colour.BLUE));
		player.giveCard(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(new NumberCard(2, Colour.BLUE))).isEqualTo(new NumberCard(1, Colour.BLUE));
		assertThat(player.playCard(new NumberCard(1, Colour.BLUE))).isEqualTo(new NumberCard(2, Colour.BLUE));
		assertThat(player.playCard(new NumberCard(2, Colour.BLUE))).isEqualTo(new NumberCard(3, Colour.BLUE));
		assertThat(player.playCard(new NumberCard(3, Colour.BLUE))).isEqualTo(new ReverseCard(Colour.BLUE));
	}

}
