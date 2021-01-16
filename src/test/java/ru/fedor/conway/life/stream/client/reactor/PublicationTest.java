package ru.fedor.conway.life.stream.client.reactor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class PublicationTest {

	@Test
	public void testJavaStreamsAndReactorStream() {
		var elements = Arrays.asList("1", "2", "3", "4");
		// usual java stream
		var resFromUsualStream = elements.stream()
				.map(Integer::parseInt)
				.mapToInt(Integer::intValue)
				.sum();
		// Reactor stream
		var resFromReactorStream = Flux.fromIterable(elements)
				.map(Integer::parseInt)
				.reduce(0, Integer::sum)
				.block();
		final int expectedValue = 10;
		Assertions.assertThat(resFromUsualStream).isEqualTo(expectedValue);
		Assertions.assertThat(resFromReactorStream).isEqualTo(expectedValue);
	}
}
