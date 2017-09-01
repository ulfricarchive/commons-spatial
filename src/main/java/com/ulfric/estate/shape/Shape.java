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

		this.min = min;
		this.max = max;
	}

	public final Point getMin() {
		return min;
	}

	public final Point getMax() {
		return max;
	}

	public abstract boolean containsPoint(int x, int y, int z);

}
