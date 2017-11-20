package com.ulfric.commons.spatial.shape;

import com.ulfric.commons.value.Bean;

public final class Point2d extends Bean {

	public static final Point2d ZERO = Point2d.builder().build();

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder implements org.apache.commons.lang3.builder.Builder<Point2d> {
		private int x;
		private int z;

		Builder() {}

		@Override
		public Point2d build() {
			return new Point2d(this.x, this.z);
		}

		public Builder setX(int x) {
			this.x = x;
			return this;
		}

		public Builder setZ(int z) {
			this.z = z;
			return this;
		}
	}

	private final int x;
	private final int z;

	Point2d(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

}
