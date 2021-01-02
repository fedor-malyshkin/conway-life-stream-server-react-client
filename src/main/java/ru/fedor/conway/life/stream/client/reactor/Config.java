package ru.fedor.conway.life.stream.client.reactor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;
import ru.fedor.conway.life.stream.client.reactor.flow.life.ConwayServerWebSocketClient;

@Configuration
@EnableWebFlux
public class Config implements WebFluxConfigurer {

	@Value("${book.word-delay-ms}")
	private int wordDelayMs;

	@Value("${source.conway-life-stream-server.stats-flush-delay-ms}")
	private int conwayServerStatsFlushDelayMs;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET")
				.allowedHeaders("*");
	}

	@Bean
	public ConwayServerWebSocketClient serverWebSocketClient() {
		return new ConwayServerWebSocketClient();
	}

	@Bean
	public Pipeline pipeline(ConwayServerWebSocketClient serverWebSocketClient) {
		var result = new Pipeline(serverWebSocketClient, conwayServerStatsFlushDelayMs, wordDelayMs);
		result.buildAndStartPipeline();
		return result;
	}
}