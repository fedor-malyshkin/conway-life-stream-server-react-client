package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.apache.commons.math3.stat.descriptive.StatisticalSummary;

public record JsonStatisticalSummary(double mean, double variance, double standardDeviation,
									 double max, double min, long n, double sum) {

	public static JsonStatisticalSummary create(StatisticalSummary summary) {
		return new JsonStatisticalSummary(summary.getMean(), summary.getVariance(), summary.getStandardDeviation(),
				summary.getMax(), summary.getMin(), summary.getN(), summary.getSum());
	}

}
