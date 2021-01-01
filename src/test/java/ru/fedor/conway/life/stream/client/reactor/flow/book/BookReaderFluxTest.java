package ru.fedor.conway.life.stream.client.reactor.flow.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class BookReaderFluxTest {


	private BookReaderFlux testable;

	@BeforeEach
	public void setUp() {
		var reader = BookReaderEng.newInstance("book/war-and-peace-eng-short.txt");
		testable = new BookReaderFlux(reader);
	}


	@Test
	public void testFlow() {
		var step = StepVerifier.create(testable.createFluxReader().take(5))
				.expectNext("so")
				.expectNext("spoke")
				.expectNext("in")
				.expectNext("july")
				.expectNext("so")
				.verifyComplete();

	}

}