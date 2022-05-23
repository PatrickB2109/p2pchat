package de.dhbw.p2pchat.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerAppTest {

	@Test
	void getSameInstance() {
		ServerApp app = ServerApp.getInstance();
		assertEquals(app, ServerApp.getInstance());
	}

}
