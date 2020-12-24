package ru.fedor.conway.life.stream.client.reactor.flow.book;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
abstract sealed class AbstractBookReader
		permits BookReaderEng, BookReaderRus {
	private final String bookPath;
	private BufferedReader buffReader;
	private Queue<String> words = new LinkedList<>();
	private Optional<InputStream> inputStreamOpt;

	protected AbstractBookReader(String bookPath) {
		this.bookPath = bookPath;
	}

	protected void openBook() {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		inputStreamOpt = Optional.ofNullable(contextClassLoader.getResourceAsStream(bookPath));
		buffReader = inputStreamOpt
				.map(is -> new BufferedReader(new InputStreamReader(is)))
				.orElseThrow(() -> new IllegalStateException("Problem with opening: " + bookPath));
	}

	private void closeBook() {
		silentlyClose(buffReader);
		inputStreamOpt.ifPresent(this::silentlyClose);
	}

	public String nextWord() throws IOException {
		var wordOpt = pollNextWord();
		if (wordOpt.isPresent())
			return wordOpt.get();

		readNextLine();
		return nextWord();
	}

	private void readNextLine() throws IOException {
		var line = buffReader.readLine();
		if (Objects.isNull(line)) {
			closeBook();
			openBook();
			line = buffReader.readLine();
		}
		words = processLine(line);
	}

	private Queue<String> processLine(String line) {
		return Arrays.stream(line.split("\\s", -1))
				.map(String::trim)
				.map(String::toLowerCase)
				.map(s -> s.replaceAll(getNonLetterRegExp(), ""))
				.filter(l -> l.length() > 0)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	protected abstract String getNonLetterRegExp();

	private Optional<String> pollNextWord() {
		return Optional.ofNullable(words.poll());
	}

	private void silentlyClose(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {
			log.error("Cannot close {}. But carry on.", e.getMessage(), e);
		}
	}
}
