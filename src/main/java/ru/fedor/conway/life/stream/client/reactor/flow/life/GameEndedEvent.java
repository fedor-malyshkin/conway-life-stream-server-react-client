package ru.fedor.conway.life.stream.client.reactor.flow.life;

public record GameEndedEvent() implements IConwayServerEvent {

	@Override
	public String getEventType() {
		return IConwayServerEvent.EVENT_TYPE_NAME_GAME_ENDED;
	}
}
