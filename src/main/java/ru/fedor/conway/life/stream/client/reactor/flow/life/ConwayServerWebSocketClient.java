package ru.fedor.conway.life.stream.client.reactor.flow.life;

import io.netty.channel.ChannelOption;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.WebsocketClientSpec;

import javax.annotation.PreDestroy;
import java.net.URI;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A client with ability to reconnect after some pause and that can close it connection.
 */
@NoArgsConstructor
@Slf4j
public class ConwayServerWebSocketClient {

	@Value("${source.conway-life-stream-server.ws}")
	private String streamServerWsAddress;

	@Value("${source.conway-life-stream-server.reconnect-ms}")
	private long streamServerWsReconnectMs = 1500;

	private final Queue<String> replayQueue = new CircularFifoQueue<>(100);

	private final Sinks.Many<String> responseSink = Sinks.many().unicast().onBackpressureBuffer(replayQueue);

	private volatile boolean shouldWeReconnect = true;

	private final AtomicReference<Mono<Void>> closeSwitch = new AtomicReference<>(Mono.empty());

	private final WebSocketHandlerWithCloseSwitch socketHandlerWithCloseSwitch = new WebSocketHandlerWithCloseSwitch();

	public ConwayServerWebSocketClient(String streamServerWsAddress) {
		this.streamServerWsAddress = streamServerWsAddress;
	}

	public void openStream() {
		WebSocketClient wsClient = new ReactorNettyWebSocketClient(getHttpClient(), this::getWebSocketClientSpecBuilder);

		Mono<Void> handle = wsClient.execute(
				URI.create(streamServerWsAddress),
				socketHandlerWithCloseSwitch);

		log.info("Connecting to {}...", streamServerWsAddress);
		handle
				.doFinally(this::reconnect)
				.subscribe();
	}

	private void reconnect(SignalType signalType) {
		if (!shouldWeReconnect) return;
		log.info("Will try to reconnect due {}", signalType.toString());
		log.info("Will reconnect in {} msec.", streamServerWsReconnectMs);
		safeSleep(streamServerWsReconnectMs);
		openStream();
	}

	private void safeSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
		}
	}

	private class WebSocketHandlerWithCloseSwitch implements WebSocketHandler {
		@Override
		public Mono<Void> handle(WebSocketSession session) {
			closeSwitch.set(session.close(CloseStatus.NORMAL));
			return session.receive()
					.map(WebSocketMessage::getPayloadAsText)
					.doOnNext(responseSink::tryEmitNext)
					// .doOnComplete(responseSink::tryEmitComplete)
					.doOnError(responseSink::tryEmitError)
					.then();
		}
	}

	private WebsocketClientSpec.Builder getWebSocketClientSpecBuilder() {
		// I know that it is a BAD practice - but we play, isn't? :)
		return WebsocketClientSpec.builder()
				.maxFramePayloadLength(10_000_000);
	}

	private HttpClient getHttpClient() {
		return HttpClient.create()
				.option(ChannelOption.SO_KEEPALIVE, true);
	}

	public Flux<String> getFlux() {
		return responseSink.asFlux();
	}

	@PreDestroy
	public void close() {
		log.info("Will stop connection right now.");
		shouldWeReconnect = false;
		closeSwitch.get()
				.subscribe();
		// down the road we will stop
	}

}
