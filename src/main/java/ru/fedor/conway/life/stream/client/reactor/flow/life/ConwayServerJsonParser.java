package ru.fedor.conway.life.stream.client.reactor.flow.life;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import ru.fedor.conway.life.stream.client.reactor.flow.life.model.Snapshot;

public class ConwayServerJsonParser {

	private final Jsonb jsonb;

	public ConwayServerJsonParser() {
		jsonb = JsonbBuilder.create();
	}


	public Snapshot parseSnapshot(String json) {
		return jsonb.fromJson(json, Snapshot.class);
	}
}
