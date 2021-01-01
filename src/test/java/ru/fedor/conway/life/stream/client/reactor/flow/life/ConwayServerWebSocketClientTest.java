package ru.fedor.conway.life.stream.client.reactor.flow.life;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;

import java.lang.reflect.Field;

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
		var p = new Pipeline(t);

		var wordDelayMs = ReflectionUtils.findField(Pipeline.class, "wordDelayMs");
		var conwayServerStatsFlushDelayMs = ReflectionUtils.findField(Pipeline.class, "conwayServerStatsFlushDelayMs");
		ReflectionUtils.makeAccessible(wordDelayMs);
		ReflectionUtils.setField(wordDelayMs, p, 50);
		ReflectionUtils.makeAccessible(conwayServerStatsFlushDelayMs);
		ReflectionUtils.setField(conwayServerStatsFlushDelayMs, p, 5000);
		p.buildAndStartPipeline();
		p.getFlux().subscribe(tpl -> System.out.println(tpl.getT1() + " : " +tpl.getT2().countOfWords()+ " : "+ tpl.getT3().countOfWords()));
		Thread.sleep(50 * 1_000);
		t.close();
	}

}