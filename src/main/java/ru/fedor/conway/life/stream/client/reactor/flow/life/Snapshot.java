package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Snapshot(List<CellState> data, int height, int width) implements IConwayServerEvent {
	@JsonCreator  // to transfer this annotation to generated constructor
	public Snapshot {
	}
}
