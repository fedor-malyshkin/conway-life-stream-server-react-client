package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FieldsUpdateEvent(List<CellState> data,
								@JsonProperty("steps-left") int stepsLeft,
								@JsonProperty("turn-number") int turnNumber) implements IConwayServerEvent {
	@Override
	public String getEventType() {
		return IConwayServerEvent.EVENT_TYPE_NAME_FIELDS_UPDATE;
	}
}
