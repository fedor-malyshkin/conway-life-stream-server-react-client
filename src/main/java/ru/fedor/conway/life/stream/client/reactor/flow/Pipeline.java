package ru.fedor.conway.life.stream.client.reactor.flow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple3;
import ru.fedor.conway.life.stream.client.reactor.flow.book.AbstractBookReader;
import ru.fedor.conway.life.stream.client.reactor.flow.book.BookReaderEng;
import ru.fedor.conway.life.stream.client.reactor.flow.book.BookReaderFlux;
import ru.fedor.conway.life.stream.client.reactor.flow.book.BookReaderRus;
import ru.fedor.conway.life.stream.client.reactor.flow.life.ConwayServerJsonParser;
import ru.fedor.conway.life.stream.client.reactor.flow.life.ConwayServerWebSocketClient;
import ru.fedor.conway.life.stream.client.reactor.flow.stats.*;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RequiredArgsConstructor
@Getter
@Slf4j
public class Pipeline {

	@Value("${book.word-delay-ms}")
	private int wordDelayMs;

	@Value("${server.conway-life-stream-server.stats-flush-delay-ms}")
	private int conwayServerStatsFlushDelayMs;

	private final ConwayServerWebSocketClient serverWebSocketClient;
	private final BookReaderRus bookReaderRus = BookReaderRus.newInstance();
	private final BookReaderEng bookReaderEng = BookReaderEng.newInstance();
	private final WordStatCollectorEng wordStatCollectorEng = new WordStatCollectorEng();
	private final WordStatCollectorRus wordStatCollectorRus = new WordStatCollectorRus();
	private Flux<Tuple3<ConwayServerStats, WordStats, WordStats>> flux;


	@PostConstruct
	public void buildAndStartPipeline() {
		var engFlux = createWordStatFlux(bookReaderEng, wordStatCollectorEng);
		var rusFlux = createWordStatFlux(bookReaderRus, wordStatCollectorRus);

		serverWebSocketClient.openStream();
		flux = serverWebSocketClient.getFlux()
				.window(Duration.ofMillis(conwayServerStatsFlushDelayMs))
				.flatMap(windowFlux -> processBatches(windowFlux, engFlux, rusFlux));
	}

	private Mono<Tuple3<ConwayServerStats, WordStats, WordStats>> processBatches(Flux<String> windowFlux, Flux<WordStats> engFlux, Flux<WordStats> rusFlux) {
		var amount = windowFlux
				.map(ConwayServerJsonParser::parseEvent)
				.collectList()
				.map(ConwayServerStatsCollector::calculate);
		var engStat = engFlux
				.take(1)
				.single();
		var rusStat = rusFlux
				.take(1)
				.single();
		return Mono.zip(amount, engStat, rusStat);
	}

	private <SC extends AbstractWordStatCollector> Flux<WordStats> createWordStatFlux(AbstractBookReader bookReader, SC wordStatCollector) {
		BookReaderFlux bookReaderFlux = new BookReaderFlux(bookReader);
		var collectorSync = new WordStatsCollectorSynchronizedDecorator<>(wordStatCollector);
		bookReaderFlux.createFluxReader()
				.delayElements(Duration.ofMillis(wordDelayMs))
				.subscribe(collectorSync::analyse);

		return Flux.generate(() -> collectorSync, this::getNextWordStatsAndReturnState);
	}

	private <SC extends AbstractWordStatCollector> WordStatsCollectorSynchronizedDecorator<SC> getNextWordStatsAndReturnState(WordStatsCollectorSynchronizedDecorator<SC> collector, SynchronousSink<WordStats> sink) {
		sink.next(collector.getStatsAndReset());
		return collector;
	}


}
