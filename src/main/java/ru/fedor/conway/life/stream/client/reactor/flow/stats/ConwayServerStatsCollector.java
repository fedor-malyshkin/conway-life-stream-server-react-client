package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import ru.fedor.conway.life.stream.client.reactor.flow.life.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.fedor.conway.life.stream.client.reactor.flow.life.IConwayServerEvent.*;

public class ConwayServerStatsCollector {

	private Map<String, CellStateEnum> fieldState = new HashMap<>();

	public ConwayServerStats calculate(List<IConwayServerEvent> events) {
		events.forEach(this::processEvent);
		return calculateState();
	}

	private void processEvent(IConwayServerEvent event) {
		switch (event.getEventType()) {
			case EVENT_TYPE_NAME_SNAPSHOT -> applySnapshot(event);
			case EVENT_TYPE_NAME_GAME_ENDED, EVENT_TYPE_NAME_GAME_STARTED -> resetStat();
			case EVENT_TYPE_NAME_GAME_TURN_ENDED -> doNothing();
			case EVENT_TYPE_NAME_FIELDS_UPDATE -> applyUpdate(event);
			default -> throw new IllegalStateException("Unexpected value: " + event.getClass());
		}
	}

	private void applySnapshot(IConwayServerEvent event) {
		SnapshotEvent snapshot = (SnapshotEvent) event;
		snapshot.data()
				.forEach(cs -> fieldState.put(createKey(cs), cs.state()));
	}

	private void applyUpdate(IConwayServerEvent event) {
		FieldsUpdateEvent fieldsUpdateEvent = (FieldsUpdateEvent) event;
		fieldsUpdateEvent.data()
				.forEach(cs -> fieldState.put(createKey(cs), cs.state()));
	}

	private void doNothing() {
	}

	private void resetStat() {
		fieldState = new HashMap<>();
	}

	private String createKey(CellState cs) {
		return cs.x() + "-" + cs.y();
	}

	private ConwayServerStats calculateState() {
		final long active = fieldState.values().stream()
				.filter(CellStateEnum.ACTIVE::equals)
				.count();
		return new ConwayServerStats(active, fieldState.values().size() - active);
	}
}
