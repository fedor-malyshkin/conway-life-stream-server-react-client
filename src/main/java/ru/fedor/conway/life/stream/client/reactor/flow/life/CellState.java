package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.annotation.JsonCreator;

public record CellState(String state, int x, int y) implements IConwayServerEvent {
	@JsonCreator  // to transfer this annotation to generated constructor
	public CellState {
	}
}
