package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Card.Colour;

public class SpecialPlayerTest {

	private static final NumberCard BLUE0 = new NumberCard(0, Colour.BLUE);

	private void setupHand(Player player) {
		player.giveCard(new NumberCard(1, Colour.BLUE));
		player.giveCard(new NumberCard(2, Colour.BLUE));
		player.giveCard(new NumberCard(3, Colour.BLUE));
		player.giveCard(new ReverseCard(Colour.BLUE));
		player.giveCard(new WildCard());
	}

	@Test
	public void testDefensivePlayer() {
		DefensivePlayer player = new DefensivePlayer();
		setupHand(player);
		assertThat(player.playCard(BLUE0)).isEqualTo(new WildCard());
		assertThat(player.playCard(BLUE0)).isEqualTo(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(3, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(2, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(1, Colour.BLUE));
	}

	@Test
	public void testOffensivePlayer() {
		OffensivePlayer player = new OffensivePlayer();
		setupHand(player);
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(1, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(2, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(3, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new WildCard());
	}

	@Test
	public void testThoughtfulOffensivePlayer() {
		ThoughtfulOffensivePlayer player = new ThoughtfulOffensivePlayer();
		setupHand(player);
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(3, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(2, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new NumberCard(1, Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new ReverseCard(Colour.BLUE));
		assertThat(player.playCard(BLUE0)).isEqualTo(new WildCard());
	}

}
