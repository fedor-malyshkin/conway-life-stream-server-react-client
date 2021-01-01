package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import java.util.Set;

public final class WordStatCollectorEng extends AbstractWordStatCollector {
	private final Set<Character> vowelsSet = Set.of('a', 'e', 'i', 'o', 'u', 'y');

	@Override
	protected Set<Character> getVowelsSet() {
		return vowelsSet;
	}
}
