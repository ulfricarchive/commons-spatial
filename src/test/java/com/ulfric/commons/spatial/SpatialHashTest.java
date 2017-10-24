package com.ulfric.commons.spatial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.ulfric.spatialregions.shape.Point2d;
import com.ulfric.spatialregions.shape.Square;

class SpatialHashTest {

	private SpatialHash<Boolean> spatialHash;

	@BeforeEach
	void setup() {
		this.spatialHash = new SpatialHash<>(100);
	}

	@Test
	void testRoundPositiveCoordinateLookup() {
		spatialHash.put(square(100, 100, 200, 200), true);
		Truth.assertThat(spatialHash.getAny(150, 150)).isTrue();
	}

	@Test
	void testRoundPositiveCoordinateLookupLowEdge() {
		spatialHash.put(square(100, 100, 200, 200), true);
		Truth.assertThat(spatialHash.getAny(100, 100)).isTrue();
	}

	@Test
	void testRoundPositiveCoordinateLookupHighEdge() {
		spatialHash.put(square(100, 100, 200, 200), true);
		Truth.assertThat(spatialHash.getAny(100, 200)).isTrue();
	}

	@Test
	void testRandomPositiveCoordinateLookupSmall() {
		spatialHash.put(square(10, 17, 7, 11), true);
		Truth.assertThat(spatialHash.getAny(10, 11)).isTrue();
	}

	@Test
	void testRandomPositiveCoordinateLookupSmall2() {
		spatialHash.put(square(17, 19, 10, 5), true);
		Truth.assertThat(spatialHash.getAny(15, 19)).isTrue();
	}

	@Test
	void testRandomPositiveCoordinateLookupSmallMany() {
		spatialHash.put(square(10, 10, 20, 20), true);
		for (int x = 10; x <= 20; x++) {
			for (int z = 10; z <= 20; z++) {
				Truth.assertThat(spatialHash.getAny(x, z)).isTrue();
			}
		}
	}

	@Test
	void testRandomPositiveCoordinateLookupBig() {
		spatialHash.put(square(1793, 1927, 1043, 511), true);
		Truth.assertThat(spatialHash.getAny(1353, 1829)).isTrue();
	}

	private Square square(int x1, int z1, int x2, int z2) {
		return new Square(Point2d.builder().setX(x1).setZ(z1).build(), Point2d.builder().setX(x2).setZ(z2).build());
	}

}
