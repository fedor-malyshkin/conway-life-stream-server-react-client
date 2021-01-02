package ru.fedor.conway.life.stream.client.reactor.flow.life;

public record GameStartedEvent() implements IConwayServerEvent {

	@Override
	public String getEventType() {
		return IConwayServerEvent.EVENT_TYPE_NAME_GAME_STARTED;
	}
}
