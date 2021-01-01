package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class ConwayServerJsonParser {


	private final ObjectMapper objectMapper;

	{
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static IConwayServerEvent parseEvent(String json) {
		return new Snapshot(Collections.emptyList(), 0, 0);
	}

	public Snapshot parseSnapshot(String json) throws JsonProcessingException {
		return objectMapper.readValue(json, Snapshot.class);
	}

}
