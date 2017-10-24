package com.ulfric.commons.spatial.flag;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.google.gson.JsonObject;

class FlagAcceptanceTest {

	@BeforeEach
	void setup() {
		Flags.registerFlag(Happy.class);
	}

	@AfterEach
	void teardown() {
		Flags.unregisterFlag(Happy.class);
	}

	@Test
	void testFlagCreationWithProperty() {
		JsonObject json = new JsonObject();
		json.addProperty("happy", true);

		Flags flags = Flags.create(json);
		Truth.assertThat(flags).isInstanceOf(Happy.class);
	}

	@Test
	void testFlagCreationWithoutProperty() {
		JsonObject json = new JsonObject();

		Flags flags = Flags.create(json);
		Truth.assertThat(flags).isNotInstanceOf(Happy.class);
	}

	@Test
	void testFlagBooleanFlagTrue() {
		JsonObject json = new JsonObject();
		json.addProperty("happy", true);

		Happy happy = (Happy) Flags.create(json);
		Truth.assertThat(happy.isHappy()).isTrue();
	}

	@Test
	void testFlagBooleanFlagFalse() {
		JsonObject json = new JsonObject();
		json.addProperty("happy", false);

		Happy happy = (Happy) Flags.create(json);
		Truth.assertThat(happy.isHappy()).isFalse();
	}

	interface Happy extends Flag {
		boolean isHappy();
	}

}
