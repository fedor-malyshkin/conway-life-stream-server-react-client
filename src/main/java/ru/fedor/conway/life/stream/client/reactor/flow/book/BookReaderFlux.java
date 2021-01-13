package ru.fedor.conway.life.stream.client.reactor.flow.book;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
public class BookReaderFlux {

	public static Flux<String> createFluxReader(AbstractBookReader bookReader) {
		return Flux.generate(() -> bookReader, BookReaderFlux::getNextWordAndReturnState);
	}

	private static AbstractBookReader getNextWordAndReturnState(AbstractBookReader reader, reactor.core.publisher.SynchronousSink<String> sink) {
		try {
			sink.next(reader.nextWord());
		} catch (IOException e) {
			log.error("Exception while producing the next word: {}", e.getMessage(), e);
			sink.error(e);
		}
		return reader;
	}
}
