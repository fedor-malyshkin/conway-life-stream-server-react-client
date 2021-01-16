package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.apache.commons.math3.stat.descriptive.StatisticalSummary;

public record WordStats(StatisticalSummary vowelsStat,
						StatisticalSummary consonantsStat,
						StatisticalSummary lengthStat,
						int countOfWords) {
}
