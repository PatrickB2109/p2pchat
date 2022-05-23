package de.dhbw.p2pchat.userinput;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.dhbw.p2pchat.client.userinput.SplittedCommand;

class SplittedCommandTest {

	private SplittedCommand splittedCommand = new SplittedCommand("test1 2 test3", " ");

	@Test
	void testStringAt() {
		assertThat(splittedCommand.getStringAt(0), is("test1"));
		assertThat(splittedCommand.getStringAt(1), is("2"));
		assertThat(splittedCommand.getStringAt(2), is("test3"));
	}

	@Test
	void testUppercase() {
		assertThat(splittedCommand.getStringUppercaseAt(0), is("TEST1"));
	}

	@Test
	void testIntAt() {
		assertThat(splittedCommand.getIntAt(1), is(2));
	}

	@Test
	void testCommandUpToPos() {
		assertThat(splittedCommand.getCommandUpToPos(3), is("test1 2 test3"));
	}

}
