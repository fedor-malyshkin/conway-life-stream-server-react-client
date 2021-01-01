package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
public class WordStatsCollectorSynchronizedDecorator<T extends AbstractWordStatCollector> {

	private final T collector;

	@Synchronized
	public WordStats getStatsAndReset() {
		var result = new WordStats(new SummaryStatistics(collector.getVowelsStat()),
				new SummaryStatistics(collector.getConsonantsStat()),
				new SummaryStatistics(collector.getLengthStat()),
				collector.getCountOfWords());
		collector.reset();
		return result;
	}


	@Synchronized
	public void analyse(@NonNull String word) {
		collector.analyse(word);
	}
}
