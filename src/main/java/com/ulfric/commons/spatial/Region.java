package com.ulfric.commons.spatial;

import java.util.Objects;

import com.ulfric.commons.naming.Named;
import com.ulfric.commons.spatial.flag.Flags;
import com.ulfric.commons.spatial.shape.Square;
import com.ulfric.commons.value.Bean;
import com.ulfric.commons.value.Weighted;
import com.ulfric.commons.value.builder.Buildable;

public final class Region extends Bean implements Named, Weighted, Comparable<Region>, Buildable<Region> {

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder implements com.ulfric.commons.value.builder.Builder<Region> {

		private String name;
		private int weight;
		private Square bounds;
		private Flags flags;

		Builder() {
		}

		@Override
		public Region build() {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(bounds, "bounds");

			return new Region(name, weight, bounds, flags());
		}

		private Flags flags() {
			return flags == null ? Flags.EMPTY : flags;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		public Builder setBounds(Square bounds) {
			this.bounds = bounds;
			return this;
		}

		public Builder setFlags(Flags flags) {
			this.flags = flags;
			return this;
		}
	}

	private final String name;
	private final int weight;
	private final Square bounds;
	private final Flags flags;

	private Region(String name, int weight, Square bounds, Flags flags) {
		this.name = name;
		this.weight = weight;
		this.bounds = bounds;
		this.flags = flags;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public String getName() {
		return name;
	}

	public Square getBounds() {
		return bounds;
	}

	public Flags getFlags() {
		return flags;
	}

	@Override
	public int compareTo(Region other) {
		return Integer.compare(weight, other.weight);
	}

	@Override
	public Builder copyToBuilder() {
		return Region.builder()
			.setName(name)
			.setWeight(weight)
			.setBounds(bounds)
			.setFlags(flags);
	}

}