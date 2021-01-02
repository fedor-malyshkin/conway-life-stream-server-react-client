package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import static ru.fedor.conway.life.stream.client.reactor.flow.life.IConwayServerEvent.*;

@Slf4j
public class ConwayServerJsonProcessor {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	public static final GameTurnEndedEvent EVENT_GAME_TURN_ENDED = new GameTurnEndedEvent();
	public static final GameStartedEvent EVENT_GAME_STARTED = new GameStartedEvent();
	public static final GameEndedEvent EVENT_GAME_ENDED = new GameEndedEvent();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static IConwayServerEvent parseEvent(String json) {
		String typeName = null;
		try {
			var jsonNode = readTree(json);
			var fileType = jsonNode.get("type");
			typeName = fileType.textValue();
			return switch (typeName) {
				case EVENT_TYPE_NAME_SNAPSHOT -> parseSnapshot(jsonNode);
				case EVENT_TYPE_NAME_GAME_ENDED -> EVENT_GAME_ENDED;
				case EVENT_TYPE_NAME_GAME_STARTED -> EVENT_GAME_STARTED;
				case EVENT_TYPE_NAME_GAME_TURN_ENDED -> EVENT_GAME_TURN_ENDED;
				case EVENT_TYPE_NAME_FIELDS_UPDATE -> parseFieldEvent(jsonNode);
				default -> throw new IllegalArgumentException("Unknown type: " + typeName);
			};
		} catch (JsonProcessingException e) {
			log.error("Exception while processing JSON (type '{}'): {}", typeName, e.getMessage(), e);
			return null;
		}
	}

	protected static JsonNode readTree(String json) throws JsonProcessingException {
		return objectMapper.readTree(json);
	}

	protected static FieldsUpdateEvent parseFieldEvent(JsonNode jsonNode) throws JsonProcessingException {
		return objectMapper.treeToValue(jsonNode, FieldsUpdateEvent.class);
	}

	protected static SnapshotEvent parseSnapshot(JsonNode jsonNode) throws JsonProcessingException {
		return objectMapper.treeToValue(jsonNode, SnapshotEvent.class);
	}

	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Exception while marshalling object: {}", e.getMessage(), e);
		}
		return "{}";
	}

}
