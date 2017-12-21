package com.ulfric.commons.spatial.shape;

import java.util.Objects;

import com.ulfric.commons.value.Bean;

public abstract class Shape extends Bean {

	protected final Point2d min;
	protected final Point2d max;

	public Shape(Point2d min, Point2d max) {
		Objects.requireNonNull(min, "min");
		Objects.requireNonNull(max, "max");

		int x1 = min.getX();
		int z1 = min.getZ();
		int x2 = max.getX();
		int z2 = max.getZ();

		this.min = Point2d.builder()
					.setX(Math.min(x1, x2))
					.setZ(Math.min(z1, z2))
					.build();
		this.max = Point2d.builder()
				.setX(Math.max(x1, x2))
				.setZ(Math.max(z1, z2))
				.build();
	}

	public final Point2d getMin() {
		return min;
	}

	public final Point2d getMax() {
		return max;
	}

	public abstract boolean containsPoint(int x, int z);

}
