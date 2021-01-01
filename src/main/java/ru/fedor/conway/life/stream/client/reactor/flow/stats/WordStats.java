package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public record WordStats(SummaryStatistics vowelsStat,
						SummaryStatistics consonantsStat,
						SummaryStatistics lengthStat,
						int countOfWords) {
}
