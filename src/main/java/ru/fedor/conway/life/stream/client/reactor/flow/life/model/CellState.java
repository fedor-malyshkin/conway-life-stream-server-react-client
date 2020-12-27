package ru.fedor.conway.life.stream.client.reactor.flow.life.model;

import jakarta.json.bind.annotation.JsonbCreator;

public record CellState(String state, int x, int y) {
	@JsonbCreator // for sake of JSON-B implementation only
	public CellState {}
}
