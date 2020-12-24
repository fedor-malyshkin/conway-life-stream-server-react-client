package ru.fedor.conway.life.stream.client.reactor.flow.book;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BookReaderTest {
	private AbstractBookReader testableEng;
	private AbstractBookReader testableRus;

	@BeforeEach
	public void setUp() {
		testableEng = BookReaderEng.newInstance("book/war-and-peace-eng-short.txt");
		testableRus = BookReaderRus.newInstance("book/war-and-peace-rus-short.txt");
	}

	@Test
	void nextWordEng() throws IOException {
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("so");
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("spoke");
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("in");
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("july");
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("so");
		Assertions.assertThat(testableEng.nextWord()).isEqualTo("spoke");
	}


	@Test
	void nextWordRus() throws IOException {
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("так");
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("говорила");
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("в");
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("июле");
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("так");
		Assertions.assertThat(testableRus.nextWord()).isEqualTo("говорила");
	}

	@Test
	void nextWordWithWrongFile() {
		Assertions.assertThatThrownBy(() -> BookReaderEng.newInstance("short.txt"))
				.isInstanceOf(IllegalStateException.class);
	}
}