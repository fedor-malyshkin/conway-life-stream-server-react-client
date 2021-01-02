package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConwayServerJsonProcessorTest {

	@Test
	public void testSnapshotParsing() throws JsonProcessingException {
		var json = """
				{"data":[
				{"how-long":9,"state":"dead","x":29,"y":0},
				{"how-long":9,"state":"dead","x":50,"y":15},
				{"how-long":9,"state":"dead","x":4,"y":38},
				{"how-long":9,"state":"dead","x":68,"y":9},
				{"how-long":9,"state":"dead","x":67,"y":11},
				{"how-long":9,"state":"dead","x":29,"y":19},
				{"how-long":1,"state":"dead","x":7,"y":19},
				{"how-long":9,"state":"dead","x":28,"y":37}]
				,"height":50,"type":"snapshot","width":100}""";

		var snapshot = ConwayServerJsonProcessor.parseSnapshot(ConwayServerJsonProcessor.readTree(json));
		Assertions.assertThat(snapshot).isNotNull();
		Assertions.assertThat(snapshot.data()).isNotEmpty();
		Assertions.assertThat(snapshot.height()).isEqualTo(50);
		Assertions.assertThat(snapshot.width()).isEqualTo(100);

		var firstCell = snapshot.data().get(0);
		Assertions.assertThat(firstCell.state()).isEqualTo(CellStateEnum.DEAD);
		Assertions.assertThat(firstCell.x()).isEqualTo(29);
		Assertions.assertThat(firstCell.y()).isEqualTo(0);
	}

	@Test
	public void testFieldEventParsing() throws JsonProcessingException {
		var json = """
				{"data":[
				{"state":"active","x":1,"y":2},
				{"how-long":12,"state":"dead","x":0,"y":1}],
				"turn-number":2,"type":"field-event","steps-left":1}""";

		var snapshot = ConwayServerJsonProcessor.parseFieldEvent(ConwayServerJsonProcessor.readTree(json));
		Assertions.assertThat(snapshot).isNotNull();
		Assertions.assertThat(snapshot.data()).isNotEmpty();
		Assertions.assertThat(snapshot.turnNumber()).isEqualTo(2);
		Assertions.assertThat(snapshot.stepsLeft()).isEqualTo(1);

		var firstCell = snapshot.data().get(0);
		Assertions.assertThat(firstCell.state()).isEqualTo(CellStateEnum.ACTIVE);
		Assertions.assertThat(firstCell.x()).isEqualTo(1);
		Assertions.assertThat(firstCell.y()).isEqualTo(2);
	}

	@Test
	public void testGameEndParsing() throws JsonProcessingException {
		var json = """
				{"data":[
				{"how-long":9,"state":"dead","x":29,"y":0},
				{"how-long":9,"state":"dead","x":50,"y":15},
				{"how-long":9,"state":"dead","x":4,"y":38},
				{"how-long":9,"state":"dead","x":68,"y":9},
				{"how-long":9,"state":"dead","x":67,"y":11},
				{"how-long":9,"state":"dead","x":29,"y":19},
				{"how-long":1,"state":"dead","x":7,"y":19},
				{"how-long":9,"state":"dead","x":28,"y":37}]
				,"height":50,"type":"snapshot","width":100}""";

		var snapshot = ConwayServerJsonProcessor.parseSnapshot(ConwayServerJsonProcessor.readTree(json));
		Assertions.assertThat(snapshot).isNotNull();

	}

	@Test
	public void testGeneralParsing() throws JsonProcessingException {
		var json = """
				{"data":[
				{"how-long":9,"state":"dead","x":29,"y":0},
				{"how-long":9,"state":"dead","x":50,"y":15},
				{"how-long":9,"state":"dead","x":4,"y":38},
				{"how-long":9,"state":"dead","x":68,"y":9},
				{"how-long":9,"state":"dead","x":67,"y":11},
				{"how-long":9,"state":"dead","x":29,"y":19},
				{"how-long":1,"state":"dead","x":7,"y":19},
				{"how-long":9,"state":"dead","x":28,"y":37}]
				,"height":50,"type":"snapshot","width":100}""";

		var snapshot = ConwayServerJsonProcessor.parseEvent(json);
		Assertions.assertThat(snapshot).isInstanceOf(SnapshotEvent.class);

	}
}