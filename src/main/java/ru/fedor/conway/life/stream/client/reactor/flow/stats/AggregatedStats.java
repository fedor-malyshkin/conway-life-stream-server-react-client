package ru.fedor.conway.life.stream.client.reactor.flow.stats;

public record AggregatedStats(long conwayCountOfAlive, long conwayCountOfDead,
							  JsonStatisticalSummary engVowelsStat,
							  JsonStatisticalSummary engConsonantsStat,
							  JsonStatisticalSummary engLengthStat,
							  int engCountOfWords,
							  JsonStatisticalSummary rusVowelsStat,
							  JsonStatisticalSummary rusConsonantsStat,
							  JsonStatisticalSummary rusLengthStat,
							  int rusCountOfWords,
							  long mills) {
	public static AggregatedStats create(ConwayServerStats conway, WordStats engStat, WordStats rusStat, long time) {
		return new AggregatedStats(conway.countOfAlive(),
				conway.countOfDead(),
				// eng
				JsonStatisticalSummary.create(engStat.vowelsStat()),
				JsonStatisticalSummary.create(engStat.consonantsStat()),
				JsonStatisticalSummary.create(engStat.lengthStat()),
				engStat.countOfWords(),
				// rus
				JsonStatisticalSummary.create(rusStat.vowelsStat()),
				JsonStatisticalSummary.create(rusStat.consonantsStat()),
				JsonStatisticalSummary.create(rusStat.lengthStat()),
				rusStat.countOfWords(),

				time);
	}
}
