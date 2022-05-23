package de.dhbw.p2pchat.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServerSocketHandlerTest {

	@Test
	void test() {
		int port = 34567;
		ServerSocketHandler serverSocketHandler = new ServerSocketHandler();
		serverSocketHandler.start(port);
		assertEquals(port, serverSocketHandler.getPort());
	}

}
