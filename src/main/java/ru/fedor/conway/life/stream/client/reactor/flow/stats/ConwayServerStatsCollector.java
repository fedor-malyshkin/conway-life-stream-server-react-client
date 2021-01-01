package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import ru.fedor.conway.life.stream.client.reactor.flow.life.IConwayServerEvent;

import java.util.List;

public class ConwayServerStatsCollector {
	public static ConwayServerStats calculate(List<IConwayServerEvent> events) {
		return new ConwayServerStats(events.size());
	}
}
