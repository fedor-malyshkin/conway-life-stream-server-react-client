package ru.fedor.conway.life.stream.client.reactor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;
import ru.fedor.conway.life.stream.client.reactor.flow.life.ConwayServerJsonProcessor;
import ru.fedor.conway.life.stream.client.reactor.flow.stats.ConwayServerStats;

@RestController
@Slf4j
public class StatsController {

	@Autowired
	private Pipeline pipeline;

	@GetMapping(value = "/stats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> produceStatsStream() {
		return pipeline.getFlux()
				.map(ConwayServerJsonProcessor::toJson);
	}
}
