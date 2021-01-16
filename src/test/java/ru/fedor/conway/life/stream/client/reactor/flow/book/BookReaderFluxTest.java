package ru.fedor.conway.life.stream.client.reactor.flow.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class BookReaderFluxTest {

	private BookReaderEng reader;

	@BeforeEach
	public void setUp() {
		reader = BookReaderEng.newInstance("book/war-and-peace-eng-short.txt");
	}

	@Test
	public void testFlow() {
		var step = StepVerifier.create(BookReaderFlux.createFluxReader(reader)
				.take(5))
				.expectNext("so")
				.expectNext("spoke")
				.expectNext("in")
				.expectNext("july")
				.expectNext("so")
				.verifyComplete();

	}

}