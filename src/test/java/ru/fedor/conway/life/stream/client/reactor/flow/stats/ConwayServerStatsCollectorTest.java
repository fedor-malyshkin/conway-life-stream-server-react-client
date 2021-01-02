package ru.fedor.conway.life.stream.client.reactor.flow.stats;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fedor.conway.life.stream.client.reactor.flow.life.*;

import java.util.Arrays;
import java.util.List;

class ConwayServerStatsCollectorTest {

	private static SnapshotEvent fixtureSnapshot;
	private static FieldsUpdateEvent fixtureUpdate;
	private ConwayServerStatsCollector testable;

	@BeforeAll
	static void setUpClass() {
		final List<CellState> snapshotStates = Arrays.asList(new CellState(CellStateEnum.ACTIVE, 0, 0),
				new CellState(CellStateEnum.DEAD, 1, 0),
				new CellState(CellStateEnum.DEAD, 0, 1),
				new CellState(CellStateEnum.ACTIVE, 1, 1));
		final List<CellState> updateStates = Arrays.asList(new CellState(CellStateEnum.DEAD, 0, 0),
				new CellState(CellStateEnum.DEAD, 1, 0));
		fixtureSnapshot = new SnapshotEvent(snapshotStates, 2, 2);
		fixtureUpdate = new FieldsUpdateEvent(updateStates, 0, 0);
	}

	@BeforeEach
	void setUp() {
		testable = new ConwayServerStatsCollector();
	}

	@Test
	void calculateSnapshot() {
		final ConwayServerStats stats = testable.calculate(Arrays.asList(fixtureSnapshot));

		Assertions.assertThat(stats.countOfAlive()).isEqualTo(2);
		Assertions.assertThat(stats.countOfDead()).isEqualTo(2);
	}

	@Test
	void calculateSnapshotAndUpdate() {
		final ConwayServerStats stats = testable.calculate(Arrays.asList(fixtureSnapshot,
				fixtureUpdate));

		Assertions.assertThat(stats.countOfAlive()).isEqualTo(1);
		Assertions.assertThat(stats.countOfDead()).isEqualTo(3);
	}

	@Test
	void calculateSnapshotUpdateReset() {
		final ConwayServerStats stats = testable.calculate(Arrays.asList(fixtureSnapshot,
				fixtureUpdate,
				new GameEndedEvent()));

		Assertions.assertThat(stats.countOfAlive()).isEqualTo(0);
		Assertions.assertThat(stats.countOfDead()).isEqualTo(0);
	}

	@Test
	void calculateSnapshotUpdateResetAndSnapshot() {
		final ConwayServerStats stats = testable.calculate(Arrays.asList(fixtureSnapshot,
				fixtureUpdate,
				new GameEndedEvent(),
				fixtureSnapshot));

		Assertions.assertThat(stats.countOfAlive()).isEqualTo(2);
		Assertions.assertThat(stats.countOfDead()).isEqualTo(2);
	}
}