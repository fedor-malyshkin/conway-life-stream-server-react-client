package ru.fedor.conway.life.stream.client.reactor.flow.life.model;

import jakarta.json.bind.annotation.JsonbCreator;

import java.util.List;

public record Snapshot(List<CellState> data, int height, int width) {
	@JsonbCreator  // to transfer this annotation to generated constructor
	public Snapshot {}
}
