package ru.fedor.conway.life.stream.client.reactor.flow.logic;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.Set;

@Getter
public sealed abstract class AbstractWordStatCollector
		implements IWordStatCollector
		permits WordStatCollectorEng, WordStatCollectorRus {
	private final SummaryStatistics vowelsStat = new SummaryStatistics();
	private final SummaryStatistics consonantsStat = new SummaryStatistics();
	private final SummaryStatistics lengthStat = new SummaryStatistics();
	private int countOfWords = 0;

	public void reset() {
		vowelsStat.clear();
		consonantsStat.clear();
		lengthStat.clear();
		countOfWords = 0;
	}

	public void analyse(@NonNull String word) {
		int countOfVowels = 0;
		int countOfConsonants = 0;
		var vowelsSet = getVowelsSet();
		for (int i = 0; i < word.length(); i++) {
			var isVowel = vowelsSet.contains(word.charAt(i));
			countOfVowels += isVowel ? 1 : 0;
			countOfConsonants += isVowel ? 0 : 1;
		}
		vowelsStat.addValue(countOfVowels);
		consonantsStat.addValue(countOfConsonants);
		lengthStat.addValue(word.length());
		countOfWords++;
	}

	protected abstract Set<Character> getVowelsSet();

}
