package ru.fedor.conway.life.stream.client.reactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;
import ru.fedor.conway.life.stream.client.reactor.flow.life.ConwayServerWebSocketClient;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
public class Config implements WebFluxConfigurer {

	@Autowired
	private WebSocketHandler webSocketHandler;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET, POST, PUT, DELETE")
				.allowedHeaders("*");
	}

	@Bean
	public HandlerMapping webSocketHandlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/stats/ws", webSocketHandler);

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setOrder(1);
		handlerMapping.setUrlMap(map);
		return handlerMapping;
	}

	@Bean
	public ConwayServerWebSocketClient serverWebSocketClient() {
		return new ConwayServerWebSocketClient();
	}

	@Bean
	public Pipeline pipeline(ConwayServerWebSocketClient serverWebSocketClient) {
		return new Pipeline(serverWebSocketClient);
	}
}