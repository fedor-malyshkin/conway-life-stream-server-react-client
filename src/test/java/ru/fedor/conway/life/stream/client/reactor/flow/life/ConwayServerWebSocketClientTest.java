package ru.fedor.conway.life.stream.client.reactor.flow.life;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("used only on manual testing with working server in background")
class ConwayServerWebSocketClientTest {

	@Test
	public void testReconnect() throws InterruptedException {
		ConwayServerWebSocketClient t = new ConwayServerWebSocketClient("ws://localhost:8079/ws");
		t.openStream();
		t.getFlux().subscribe(System.out::println, System.err::println);
		Thread.sleep(10 * 60 * 1_000);
		t.close();
	}

	@Test
	public void testCloseConnection() throws InterruptedException {
		ConwayServerWebSocketClient t = new ConwayServerWebSocketClient("ws://localhost:8079/ws");
		t.openStream();
		t.getFlux().subscribe(System.out::println, System.err::println);
		Thread.sleep(5 * 1_000);
		t.close();
	}


}