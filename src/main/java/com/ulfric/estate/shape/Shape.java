package com.ulfric.estate.shape;

import com.ulfric.commons.naming.Named;
import com.ulfric.commons.value.Bean;

import java.util.Objects;

public abstract class Shape extends Bean implements Named {

	protected final Point min;
	protected final Point max;

	public Shape(Point min, Point max) {
		Objects.requireNonNull(min, "min");
		Objects.requireNonNull(max, "max");

		int x1 = min.getX();
		int y1 = min.getY();
		int z1 = min.getZ();
		int x2 = max.getX();
		int y2 = max.getY();
		int z2 = max.getZ();

		this.min = Point.builder()
					.setX(Math.min(x1, x2))
					.setY(Math.min(y1, y2))
					.setZ(Math.min(z1, z2))
					.build();
		this.max = Point.builder()
				.setX(Math.max(x1, x2))
				.setY(Math.max(y1, y2))
				.setZ(Math.max(z1, z2))
				.build();
	}

	public final Point getMin() {
		return min;
	}

	public final Point getMax() {
		return max;
	}

	public abstract boolean containsPoint(int x, int y, int z);

}
