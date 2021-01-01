package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordStatCollectorEngTest {


	private WordStatCollectorEng testable;

	@BeforeEach
	public void setUp() {
		testable = new WordStatCollectorEng();
	}

	@Test
	void analyse() {
		Assertions.assertThat(testable.getConsonantsStat().getMax()).isNaN();
		testable.analyse("prince");
		testable.analyse("ippolit");
		testable.analyse("fetched");
		Assertions.assertThat(testable.getConsonantsStat().getMax()).isEqualTo(5);
	}

	@Test
	void reset() {
		Assertions.assertThat(testable.getConsonantsStat().getSum()).isEqualTo(0);
		testable.analyse("prince");
		testable.analyse("ippolit");
		testable.reset();
		testable.analyse("fetched");
		Assertions.assertThat(testable.getConsonantsStat().getSum()).isEqualTo(5);
	}
}