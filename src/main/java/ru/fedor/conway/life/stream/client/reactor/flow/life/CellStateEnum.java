package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CellStateEnum {
	@JsonProperty("dead")
	DEAD,
	@JsonProperty("active")
	ACTIVE
}
