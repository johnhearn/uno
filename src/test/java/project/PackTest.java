package project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PackTest {

	@Test
	public void test() {
		// How many cards? 1->9 * 4 colours
		Pack pack = new Pack();
		assertThat(pack.numCards()).isEqualTo(36);
	}

}
