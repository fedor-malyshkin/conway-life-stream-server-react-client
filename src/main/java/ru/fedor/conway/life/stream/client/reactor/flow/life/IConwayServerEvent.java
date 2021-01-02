package ru.fedor.conway.life.stream.client.reactor.flow.life;

public sealed interface IConwayServerEvent
		permits FieldsUpdateEvent, GameEndedEvent, GameStartedEvent, GameTurnEndedEvent, SnapshotEvent {

	public static final String EVENT_TYPE_NAME_SNAPSHOT = "snapshot";
	public static final String EVENT_TYPE_NAME_GAME_ENDED = "game-ended";
	public static final String EVENT_TYPE_NAME_GAME_STARTED = "game-start";
	public static final String EVENT_TYPE_NAME_GAME_TURN_ENDED = "game-turn-ended";
	public static final String EVENT_TYPE_NAME_FIELDS_UPDATE = "field-event";

	String getEventType();

}
