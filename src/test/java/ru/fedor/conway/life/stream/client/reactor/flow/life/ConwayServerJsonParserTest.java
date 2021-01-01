package ru.fedor.conway.life.stream.client.reactor.flow.life;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConwayServerJsonParserTest {

	private ConwayServerJsonParser testable;

	@BeforeEach
	public void setUp() {
		testable = new ConwayServerJsonParser();
	}

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

		var snapshot = testable.parseSnapshot(json);
		Assertions.assertThat(snapshot).isNotNull();
		// Assertions.assertThat(snapshot.data()).isNotEmpty();
		Assertions.assertThat(snapshot.height()).isEqualTo(50);
		Assertions.assertThat(snapshot.width()).isEqualTo(100);

		// var fistCell = snapshot.data().get(0);
//		Assertions.assertThat(fistCell.state()).isEqualTo("dead");
//		Assertions.assertThat(fistCell.x()).isEqualTo(29);
//		Assertions.assertThat(fistCell.y()).isEqualTo(0);
	}
}