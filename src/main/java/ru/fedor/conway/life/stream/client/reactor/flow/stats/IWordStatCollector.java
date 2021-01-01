package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.apache.commons.math3.stat.descriptive.StatisticalSummary;

interface IWordStatCollector {
	void analyse(String word);

	int getCountOfWords();

	StatisticalSummary getVowelsStat();

	StatisticalSummary getConsonantsStat();

	StatisticalSummary getLengthStat();
}
