package ru.fedor.conway.life.stream.client.reactor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedor.conway.life.stream.client.reactor.flow.book.BookReaderEng;
import ru.fedor.conway.life.stream.client.reactor.flow.book.BookReaderFlux;
import ru.fedor.conway.life.stream.client.reactor.model.Stats;

import java.time.Duration;

@Component
@Slf4j
public class StatsWsHandler implements WebSocketHandler {

	@Override
	public Mono<Void> handle(WebSocketSession webSocketSession) {
		BookReaderFlux bookReaderFlux = new BookReaderFlux(BookReaderEng.newInstance());

		return webSocketSession.send(bookReaderFlux.createFluxReader()
				.delayElements(Duration.ofMillis(500))
				.map(webSocketSession::textMessage))
				.and(webSocketSession.receive()
						.map(WebSocketMessage::getPayloadAsText));
	}
}
