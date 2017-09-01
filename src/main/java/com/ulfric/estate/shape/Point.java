package com.ulfric.estate.shape;

import com.ulfric.commons.value.Bean;

public final class Point extends Bean {

	public static final Point ZERO = Point.builder().build();

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder implements org.apache.commons.lang3.builder.Builder<Point> {
		private int x;
		private int y;
		private int z;

		Builder() {}

		@Override
		public Point build() {
			return new Point(this.x, this.y, this.z);
		}

		public Builder setX(int x) {
			this.x = x;
			return this;
		}

		public Builder setY(int y) {
			this.y = y;
			return this;
		}

		public Builder setZ(int z) {
			this.z = z;
			return this;
		}
	}

	private final int x;
	private final int y;
	private final int z;

	Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

}
