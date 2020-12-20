package ru.fedor.conway.life.stream.client.reactor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedor.conway.life.stream.client.reactor.model.Stats;

import java.time.Duration;

@RestController
@RequestMapping("/stats")
public class StatsController {

	@GetMapping("/snapshot")
	public Mono<Stats> getStats() {
		return Mono.just(new Stats(1));
	}

	@GetMapping("/stream")
	public Flux<Stats> getStatsChain() {
		return Flux.range(1, 100)
				.map(Stats::new);
	}
}
