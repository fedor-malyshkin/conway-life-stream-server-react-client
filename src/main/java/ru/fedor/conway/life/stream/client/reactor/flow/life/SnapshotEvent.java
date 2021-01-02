package ru.fedor.conway.life.stream.client.reactor.flow.life;

import java.util.List;

public record SnapshotEvent(List<CellState> data,
							int height,
							int width) implements IConwayServerEvent {

	@Override
	public String getEventType() {
		return IConwayServerEvent.EVENT_TYPE_NAME_SNAPSHOT;
	}
}
