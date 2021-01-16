package ru.fedor.conway.life.stream.client.reactor.flow.life;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;

@Disabled("used only on manual testing with working server in background")
class ConwayServerWebSocketClientTest {

	public void testReconnect() throws InterruptedException {
		ConwayServerWebSocketClient t = new ConwayServerWebSocketClient("ws://localhost:8079/ws");
		t.openStream();
		t.getFlux().subscribe(System.out::println, System.err::println);
		Thread.sleep(10 * 60 * 1_000);
		t.close();
	}

	public void testCloseConnection() throws InterruptedException {
		ConwayServerWebSocketClient t = new ConwayServerWebSocketClient("ws://localhost:8079/ws");
		t.openStream();
		t.getFlux().subscribe(System.out::println, System.err::println);
		Thread.sleep(5 * 1_000);
		t.close();
	}

	@Test
	public void testPipeline() throws InterruptedException {
		ConwayServerWebSocketClient t = new ConwayServerWebSocketClient("ws://localhost:8079/ws");
		var p = new Pipeline(t, 5000, 50);
		p.buildAndStartPipeline();
		p.getFlux().subscribe(System.out::println);
		Thread.sleep(50 * 1_000);
		t.close();
	}

}