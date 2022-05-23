package de.dhbw.p2pchat.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class ServerAppTest {

	@Test
	void testCreateTerminalHandler() {
		ClientApp.main(null);
		assertNotNull(ClientApp.getTerminalHandler());
	}
	
	@Test
	void testNotConnected() {
		ClientApp.main(null);
		assertFalse(ClientApp.isIsConnectedToOtherClient());
	}

}
