package ru.fedor.conway.life.stream.client.reactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fedor.conway.life.stream.client.reactor.flow.Pipeline;

import java.util.Objects;

@RestController
public class HealthController {


	@Autowired
	private Pipeline pipeline;


	@GetMapping(value = "/health")
	public ResponseEntity<String> checkStatus() {
		if (Objects.nonNull(pipeline.getHotFlux()))
			return ResponseEntity.ok("Everything is fine :)\n");
		else
			return ResponseEntity.status(404).build();
	}
}
