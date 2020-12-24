package ru.fedor.conway.life.stream.client.reactor.flow.logic;

import java.util.Set;

public final class WordStatCollectorRus extends AbstractWordStatCollector {
	private final Set<Character> vowelsSet = Set.of('а', 'е', 'ё', 'и', 'о', 'у', 'ю', 'я', 'ы', 'э');

	@Override
	protected Set<Character> getVowelsSet() {
		return vowelsSet;
	}
}
