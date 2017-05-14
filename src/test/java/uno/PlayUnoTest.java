package uno;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.output.WriterOutputStream;
import org.junit.Test;

import uno.PlayUno;

public class PlayUnoTest {

	@Test
	public void testPlayUnoGame() throws IOException {
		try (Capture capture = new Capture()) {
			PlayUno.main(new String[0]);
			BufferedReader reader = capture.readBack();
			assertThat(reader.readLine()).matches("Player 0 scored \\d+ points.");
			assertThat(reader.readLine()).matches("Player 1 scored \\d+ points.");
			assertThat(reader.readLine()).matches("Player 2 scored \\d+ points.");
			assertThat(reader.readLine()).matches("Player 3 scored \\d+ points.");
			assertThat(reader.readLine()).matches("Player \\d is the winner.");
		}
	}

	private static class Capture implements AutoCloseable {

		private final PrintStream oldStdOut;
		private final PrintStream newStdOut;
		private StringWriter writer = new StringWriter();

		private Capture() {
			oldStdOut = System.out;
			newStdOut = new PrintStream(new WriterOutputStream(writer, StandardCharsets.UTF_8));
			System.setOut(newStdOut);
		}

		private BufferedReader readBack() {
			newStdOut.flush();
			return new BufferedReader(new StringReader(writer.toString()));
		}

		@Override
		public void close() {
			System.setOut(oldStdOut);
		}
	}
}
